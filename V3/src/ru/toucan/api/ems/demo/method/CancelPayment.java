package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.APIExtras;
import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Settings;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.merchant.common.Extras;

public class CancelPayment extends Activity {

    TextView sum;
    TextView payment;
    CheckBox fiscalizationFlag;
    TextView rrnCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_cancel_payment);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(CancelPayment.this, layout, titleView, Settings.getParameters(), false);

        sum = (TextView) findViewById(R.id.sum);
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

        Intent intent = new Intent();
        intent.setAction(APIActions.CANCEL_PAYMENT);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        // Сумма платежа в МДЕ (в копейках)
        if (sum.getText().length() > 0) {
            intent.putExtra(Extras.paramAmount, Utils.parseSum(sum.getText().toString()));
        }

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

        startActivityForResult(intent, RequestCode.CANCEL_PAYMENT.ordinal());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, getString(R.string.results) + " " + resultCode + " [" + (resultCode==RESULT_OK?"RESULT_OK":
                (resultCode==RESULT_CANCELED?"RESULT_CANCELED":"???")) + "]", Toast.LENGTH_SHORT).show();

        data.putExtra(Extras.requestCode, requestCode);
        data.putExtra(Extras.resultCode, resultCode);

        setResult(resultCode, data);
        finish();
    }
}
