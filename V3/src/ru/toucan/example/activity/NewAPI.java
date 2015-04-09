package ru.toucan.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringWriter;

import ru.toucan.api.APIExtras;
import ru.toucan.api.APIPaymentResult;
import ru.toucan.api.APIResult;
import ru.toucan.example.method.CancelPayment;
import ru.toucan.example.method.CashPayment;
import ru.toucan.example.method.Close;
import ru.toucan.example.method.GetParameters;
import ru.toucan.example.method.Open;
import ru.toucan.example.method.Payment;
import ru.toucan.example.method.ReturnPayment;
import ru.toucan.example.method.Settlement;
import ru.toucan.example.method.ViewPayment;
import ru.toucan.example2.R;
import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.common.Extras;

public class NewAPI extends Activity {
    private TextView resultView;
    private int paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newapi);
        resultView = (TextView)findViewById(R.id.resultView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        StringWriter sw = new StringWriter();

        if (intent.hasExtra(Extras.requestCode)) {
            sw.append("Request code: " + intent.getIntExtra(Extras.requestCode, -1));
            sw.append("\n\n");
        }
        if (intent.hasExtra(Extras.resultCode)) {
            int rc = intent.getIntExtra(Extras.resultCode, -1);
            if (rc >= 0) {
                sw.append("Result code: " + rc);
                sw.append("\n");
                sw.append("Default message: ");
                sw.append(APIResult.getResult(rc).text);
                sw.append("\n\n");
            }
        }
        if (intent.hasExtra(Extras.result)) {
            ParcelableObject result = intent.getParcelableExtra(Extras.result);

            sw.append("Result data:\n");
            sw.append(result.toString());

            if (result instanceof APIPaymentResult) {
                paymentId = ((APIPaymentResult)result).Payment;
            }
        }

        resultView.setText(sw.toString());
    }

    private void toActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("paymentId", paymentId);
        startActivity(intent);
    }

    private void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    public void payment(View v) {
        toActivity(Payment.class);
    }
    public void cashPayment(View v) {
        toActivity(CashPayment.class);
    }
    public void viewPayment(View v) {
        toActivity(ViewPayment.class);
    }
    public void cancelPayment(View v) {
        toActivity(CancelPayment.class);
    }
    public void returnPayment(View v) {
        toActivity(ReturnPayment.class);
    }
    public void settlement(View v) {
        toActivity(Settlement.class);
    }
    public void getParameters(View v) {
        toActivity(GetParameters.class);
    }
    public void open(View v) {
        toActivity(Open.class);
    }
    public void close(View v) {
        toActivity(Close.class);
    }

}
