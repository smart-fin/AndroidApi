package ru.toucan.example.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.APIExtras;
import ru.toucan.example.Utils;
import ru.toucan.example.activity.NewAPI;
import ru.toucan.example2.R;
import ru.toucan.merchant.common.Extras;

public class ViewPayment extends Activity {

    public static final int REQUEST_CODE = 4;

    EditText secureCode;
    EditText payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_payment);

        payment = (EditText) findViewById(R.id.payment);
        secureCode = (EditText) findViewById(R.id.secureCode);

        if (getIntent().hasExtra("paymentId")) {
            int paymentId = getIntent().getIntExtra("paymentId", 0);
            if (paymentId > 0) {
                payment.setText(Integer.toString(paymentId));
            }
        }
    }

    public void pay(View v) {
        // обязательные поля
        if (payment.getText().length() < 1) {
            Toast.makeText(this, "Должен быть указан ID платежа", Toast.LENGTH_SHORT).show();
            return;
        }

        if (secureCode.getText().length() != 4) {
            Toast.makeText(this, "Код доступа должен содержать 4 цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(APIActions.VIEW_PAYMENT);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

        // Код доступа к приложению
        intent.putExtra(Extras.paramSecureCode, secureCode.getText().toString());

        // ID платежа
        intent.putExtra(Extras.paramPayment, Integer.decode(payment.getText().toString()));

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
