package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Settings;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.merchant.common.Extras;

public class ViewPayment extends Activity {

    EditText payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_view_payment);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(ViewPayment.this, layout, titleView, Settings.getParameters(), false);

        payment = (EditText) findViewById(R.id.payment);

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
            Toast.makeText(this, getString(R.string.enter_payment_id), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(APIActions.VIEW_PAYMENT);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        // ID платежа
        intent.putExtra(Extras.paramPayment, Integer.decode(payment.getText().toString()));

        Utils.dumpBundle(intent.getExtras());

        startActivityForResult(intent, RequestCode.VIEW_PAYMENT.ordinal());
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
