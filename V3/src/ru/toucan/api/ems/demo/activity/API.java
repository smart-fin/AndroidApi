package ru.toucan.api.ems.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.method.ReturnCashPayment;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.api.ems.demo.method.CancelPayment;
import ru.toucan.api.ems.demo.method.CashPayment;
import ru.toucan.api.ems.demo.method.Close;
import ru.toucan.api.ems.demo.method.Open;
import ru.toucan.api.ems.demo.method.Payment;
import ru.toucan.api.ems.demo.method.ReturnPayment;
import ru.toucan.api.ems.demo.method.Settlement;
import ru.toucan.api.ems.demo.method.ViewPayment;
import ru.toucan.merchant.common.Extras;

public class API extends Activity {

    private int paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_api);
    }

    private void toActivity(Class<?> cls) {
        startActivityForResult(new Intent(API.this, cls), 1);
    }

    private void toActivity(Class<?> cls, int paymentId) {
        Intent intent = new Intent(API.this, cls);
        intent.putExtra(Extras.paymentId, paymentId);
        startActivityForResult(intent, 1);
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
        toActivity(CancelPayment.class, getIntent().getIntExtra(Extras.paymentId, 0));
    }

    public void returnPayment(View v) {
        toActivity(ReturnPayment.class, getIntent().getIntExtra(Extras.paymentId, 0));
    }

    public void returnCashPayment(View v) {
        toActivity(ReturnCashPayment.class, getIntent().getIntExtra(Extras.paymentId, 0));
    }

    public void settlement(View v) {
        toActivity(Settlement.class);
    }

    public void setup(View v) {
        toActivity(Setup.class);
    }

    public void open(View v) {
        toActivity(Open.class);
    }

    public void close(View v) {
        toActivity(Close.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(Utils.TAG, "onActivityResult " + data + " requestCode "+ requestCode + " resultCode " + resultCode);
        if (data == null) return;

        Utils.dumpBundle(data.getExtras());

        TextView resultView = (TextView) findViewById(R.id.resultView);
        resultView.setText(Utils.printResult(data, getIntent()));
    }
}
