package ru.toucan.example.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.APIExtras;
import ru.toucan.example.Utils;
import ru.toucan.example.activity.NewAPI;
import ru.toucan.example2.R;
import ru.toucan.merchant.common.Extras;

public class CancelPayment extends Activity {

    public static final int REQUEST_CODE = 4;

    TextView sum;
    TextView payment;
    TextView secureCode;
    CheckBox fiscalizationFlag;
    TextView rrnCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cancel_payment);

        sum = (TextView) findViewById(R.id.sum);
        secureCode = (TextView) findViewById(R.id.secureCode);
        rrnCode = (TextView) findViewById(R.id.rrnCode);
        payment = (TextView) findViewById(R.id.payment);
        fiscalizationFlag = (CheckBox) findViewById(R.id.fiscalizationFlag);

        if (getIntent().hasExtra(Extras.paymentId)) {
            int paymentId = getIntent().getIntExtra(Extras.paymentId, 0);
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
        intent.setAction(APIActions.CANCEL_PAYMENT);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

        // Сумма платежа в МДЕ (в копейках)
        if (sum.getText().length() > 0) {
            intent.putExtra(Extras.paramAmount, Utils.parseSum(sum.getText().toString()));
        }

        // Код доступа к приложению
        intent.putExtra(Extras.paramSecureCode, secureCode.getText().toString());

        // ID платежа
        if (payment.getText().length() > 0) {
            intent.putExtra(Extras.paramPayment, Integer.decode(payment.getText().toString()));
        }

        // RRN
        if (rrnCode.getText().length() > 0) {
            intent.putExtra(Extras.paramRRNCode, rrnCode.getText().toString());
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
