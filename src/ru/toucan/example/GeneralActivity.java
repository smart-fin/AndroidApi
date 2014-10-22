package ru.toucan.example;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GeneralActivity extends Activity {

    private static final int PAYMENT_RESULT_CODE = 0x101010;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountView = (EditText) findViewById(R.id.amountView);
                if (amountView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_amount), Toast.LENGTH_LONG);
                    return;
                }
                try {
                    Double.parseDouble(amountView.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_correct_amount), Toast.LENGTH_LONG);
                    return;
                }
                EditText descView = (EditText) findViewById(R.id.descView);
                if (descView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_desc), Toast.LENGTH_LONG);
                    return;

                }
                makePayment(Double.parseDouble(amountView.getText().toString()), descView.getText().toString());
            }
        });
findViewById(R.id.btnPaymentCash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountView = (EditText) findViewById(R.id.amountView);
                if (amountView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_amount), Toast.LENGTH_LONG);
                    return;
                }
                try {
                    Double.parseDouble(amountView.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_correct_amount), Toast.LENGTH_LONG);
                    return;
                }
                EditText descView = (EditText) findViewById(R.id.descView);
                if (descView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_desc), Toast.LENGTH_LONG);
                    return;

                }
                makePaymentCash(Double.parseDouble(amountView.getText().toString()), descView.getText().toString());
            }
        });

        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistory();
            }
        });

        setTitle(getString(R.string.app_name) + " " + getVersionName(getApplicationContext()));
    }

    /**
     * Запрос на проведение платежа
     * (Откроется активити, в котором нужно будет указать сумму и назначение платежа, а также выбрать тип оплаты)
     * <p/>
     * Если клиент не был активирован, откроется окно активации
     * После успешной активации нужно будет настроить код доступа и затем проводить платеж
     * <p/>
     * Если клиент уже был ранее активирован, будет запрошен пин-код
     */
    private void makePayment(double amount, String desc) {

        Intent intent = new Intent(Actions.PAYMENT_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra("packageName", getPackageName());
        intent.putExtra("Pin", "1111");
        // amount - сумма платежа
        intent.putExtra("amount", amount);
        // desc - назначение платежа
        intent.putExtra("desc", desc);
        // desc - назначение платежа
        intent.putExtra("paymentType", "card");
        try {
            startActivityForResult(intent, PAYMENT_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }
    private void makePaymentCash(double amount, String desc) {

        Intent intent = new Intent(Actions.PAYMENT_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra("packageName", getPackageName());
        intent.putExtra("Pin", "1111");
        // amount - сумма платежа
        intent.putExtra("amount", amount);
        // desc - назначение платежа
        intent.putExtra("desc", desc);
        // desc - назначение платежа
        intent.putExtra("paymentType", "cash");
        try {
            startActivityForResult(intent, PAYMENT_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    /**
     * Запрос на просмотр истории платежей
     * <p/>
     * Если клиент не был активирован, откроется окно активации
     * После успешной активации нужно будет настроить код доступа и затем проводить платеж
     * <p/>
     * Если клиент уже был ранее активирован, будет запрошен пин-код
     */
    private void showHistory() {

        Intent intent = new Intent(Actions.HISTORY_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra("packageName", getPackageName());
        intent.putExtra("Pin", "1111");
        try{
        startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return " - ";
        }
    }

    /**
     * Ответ
     * Result success = new Result(0, "success");
     * Result success_put_to_queue = new Result(1, "success_put_to_queue");
     * Result server_error = new Result(20, "server_error");
     * Result server_parser_error = new Result(21, "server_parser_error");
     * Result connection_timeout = new Result(3, "connection_timeout");
     * Result network_is_not_available = new Result(4, "network_is_not_available");
     * Result network_is_available = new Result(5, "network_is_available");
     * Result system_service_error = new Result(6, "system_service_error");
     * Result unknown_error = new Result(100, "unknown_error");
     * Result cancel_activation = new Result(101, "cancel_activation");
     * Result cancel_operation = new Result(200, "Отмена операции");
     * Result waiting_timeout = new Result(400, "waiting_timeout");
     * Result reader_not_init = new Result(1001, "Ридер не инициализирован");
     * Result reader_not_identify = new Result(1002, "Неизвестный ридер");
     * Result transaction_aborted_by_card = new Result(1003, "Операция отклонена картой");
     * Result card_blocked = new Result(1004, "Карта заблокирована");
     * Result dont_use_card = new Result(1005, "Не принимайте эту карту к оплате!");
     * Result insert_card = new Result(13, "Вставьте карту чипом в ридер");
     * Result try_payment_again = new Result(14, "Попробуйте провести платёж заново");
     * Result payment_not_confirmed = new Result(15, "Платеж не подтвержден");
     * Result payment_canceled = new Result(16, "Платеж отменен");
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("2can", "got result " + resultCode);
        String msg = null;
        if (data == null) {
            msg = "Ошибка. ErrorCode: " + resultCode;
        } else {
            msg = resultCode == 0 ? "Платеж завершен успешно" : ("Ошибка платежа. ErrorCode: " + resultCode);
            msg += "\nСпособ оплаты: " + data.getStringExtra("paymentType");
        }
        Alert.show("Внимание", msg, "Ок", null, GeneralActivity.this);
        // после получения ответа - удаляем ресивер
    }
}
