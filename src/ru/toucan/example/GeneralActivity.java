package ru.toucan.example;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GeneralActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountView = (EditText) findViewById(R.id.amountView);
                if (amountView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, "Введите сумму платежа", Toast.LENGTH_LONG);
                    return;
                }
                try {
                    Double.parseDouble(amountView.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GeneralActivity.this, "Сумма платежа введена неверно", Toast.LENGTH_LONG);
                    return;
                }
                EditText descView = (EditText) findViewById(R.id.descView);
                if (descView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, "Введите назначение платежа", Toast.LENGTH_LONG);
                    return;

                }
                makePayment(amountView.getText().toString(), descView.getText().toString());
            }
        });

        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistoryPayments();
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
    private void makePayment(String amount, String desc) {

        Intent intent = new Intent("ru.toucan.CALL_FOR_PAYMENT");
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra("packageName", getPackageName());
        intent.putExtra("Pin", "1111");
        // amount - сумма платежа
        intent.putExtra("amount", amount);
        // desc - назначение платежа
        intent.putExtra("desc", desc);
        startActivity(intent);

        // Регистрируем ресивер для получения ответа
        IntentFilter pluginFilter = new IntentFilter();
        pluginFilter.addAction("ACTION_DELIVER_PAYMENT");
        registerReceiver(receiver, pluginFilter);
    }

    /**
     * Запрос истории операций
     */
    private void showHistoryPayments() {
        Intent intent = new Intent("ru.toucan.CALL_FOR_HISTORY");
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra("packageName", getPackageName());
        intent.putExtra("pin", "1111");
        startActivity(intent);

        // Регистрируем ресивер для получения ответа
        IntentFilter pluginFilter = new IntentFilter();
        pluginFilter.addAction("ACTION_DELIVER_HISTORY");
        registerReceiver(receiver, pluginFilter);
    }

    /**
     * Result success = new Result(0, "success");
     * Result success_put_to_queue = new Result(1, "success_put_to_queue");
     * Result server_error = new Result(20, "server_error");
     * Result server_parser_error = new Result(21, "server_parser_error");
     * Result connection_timeout = new Result(3, "connection_timeout");
     * Result network_is_not_available = new Result(4, "network_is_not_available");
     * Result network_is_available = new Result(5, "network_is_available");
     * Result system_service_error = new Result(6, "system_service_error");
     * Result unknown_error = new Result(100, "unknown_error");
     * Result reader_not_init = new Result(1001, "Ридер не инициализирован");
     * Result reader_not_identify = new Result(1002, "Неизвестный ридер");
     * Result transaction_aborted_by_card = new Result(1003, "Операция отклонена картой");
     * Result card_blocked = new Result(1004, "Карта заблокирована");
     * Result insert_card = new Result(13, "Вставьте карту чипом в ридер");
     * Result payment_not_confirmed = new Result(15, "Платеж не подтвержден");
     * Result payment_canceled = new Result(16, "Платеж отменен");
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
            Log.d("2can", "got in receiver EXTRA_CONTENT");
            if ("ACTION_DELIVER_PAYMENT".equals(i.getAction())) {

                int result = i.getIntExtra("result", -1);
                Log.d("2can", "got in receiver " + result);
                String msg = result == 0 ? "Платеж завершен успешно" : "Ошибка платежа";
                msg += "\nСпособ оплаты: " + i.getStringExtra("paymentType");
                Alert.show("Внимание", msg, "Ок", null, GeneralActivity.this);
                // после получения ответа - удаляем ресивер
                unregisterReceiver(receiver);
            }
        }
    };

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return " - ";
        }
    }
}
