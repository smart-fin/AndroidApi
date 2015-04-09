package ru.toucan.example.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.APIExtras;
import ru.toucan.example.Utils;
import ru.toucan.example.activity.NewAPI;
import ru.toucan.example2.R;
import ru.toucan.merchant.common.Extras;

public class ReturnPayment extends Activity {

    public static final int REQUEST_CODE = 4;

    EditText secureCode;
    EditText payment;
    EditText sum;
    EditText reason;
    EditText receiptNumber;
    EditText rrnCode;
    EditText secureCardNumber;
    EditText authorizationCode;
    CheckBox getPayInfo;
    CheckBox fiscalizationFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_return_payment);

        sum = (EditText) findViewById(R.id.sum);
        secureCode = (EditText) findViewById(R.id.secureCode);
        rrnCode = (EditText) findViewById(R.id.rrnCode);
        payment = (EditText) findViewById(R.id.payment);
        reason = (EditText) findViewById(R.id.reason);
        receiptNumber = (EditText) findViewById(R.id.receiptNumber);
        secureCardNumber = (EditText) findViewById(R.id.secureCardNumber);
        authorizationCode = (EditText) findViewById(R.id.authorizationCode);
        getPayInfo = (CheckBox) findViewById(R.id.getPayInfo);
        fiscalizationFlag = (CheckBox) findViewById(R.id.fiscalizationFlag);

        if (getIntent().hasExtra("paymentId")) {
            int paymentId = getIntent().getIntExtra("paymentId", 0);
            if (paymentId > 0) {
                payment.setText(Integer.toString(paymentId));
            }
        }
    }

    public void pay(View v) {
        // обязательные поля
        if (secureCode.getText().length() != 4) {
            Toast.makeText(this, "Код доступа должен содержать 4 цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(APIActions.RETURN_PAYMENT);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

        // ID платежа
        if (payment.getText().length() > 0) {
            intent.putExtra(Extras.paramPayment, Integer.decode(payment.getText().toString()));
        }

        // Сумма возврата в МДЕ (в копейках)
        if (sum.getText().length() > 0) {
            intent.putExtra(Extras.paramAmount, Utils.parseSum(sum.getText().toString()));
        }

        // Код доступа к приложению
        intent.putExtra(Extras.paramSecureCode, secureCode.getText().toString());


        // Причина возврата
        if (reason.getText().length() > 0) {
            intent.putExtra(Extras.paramReason, reason.getText().toString());
        }

        // Номер чека
        if (receiptNumber.getText().length() > 0) {
            intent.putExtra(Extras.paramReceiptNumber, receiptNumber.getText().toString());
        }

        // RRN
        if (rrnCode.getText().length() > 0) {
            intent.putExtra(Extras.paramRRNCode, rrnCode.getText().toString());
        }

        // Безопасный номер карты
        if (secureCardNumber.getText().length() > 0) {
            intent.putExtra(Extras.paramSecureCardNumber, secureCardNumber.getText().toString());
        }

        // Переключатель показывающий надо ли возвращать полную информацию о платеже
        if (getPayInfo.isChecked()) {
            // Необходимо возвращать полную информацию о платеже (значение по умолчанию)
            intent.putExtra(Extras.paramGetPayInfo, APIExtras.valueRequestFullPaymentInfo);
        } else {
            // полную информацию о платеже возвращать не требуется
            intent.putExtra(Extras.paramGetPayInfo, APIExtras.valueDoNotRequestFullPaymentInfo);
        }

        // Необходимость фискализации
        if (fiscalizationFlag.isChecked()) {
            // фискализация нужна(значение по-умолчанию)
            intent.putExtra(Extras.paramFiscalizationFlag, APIExtras.valueFiscalizationRequired);
        } else {
            // фискализация не нужна
            intent.putExtra(Extras.paramFiscalizationFlag, APIExtras.valueFiscalizationNotRequired);
        }

        Utils.dumpBundle(intent.getExtras());

        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Результат: " + resultCode, Toast.LENGTH_SHORT).show();

        data.putExtra(Extras.requestCode, requestCode);
        data.putExtra(Extras.resultCode, resultCode);

        data.setClass(this, NewAPI.class);
        startActivity(data);
    }
}
