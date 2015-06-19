package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class ReturnPayment extends Activity {

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
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_return_payment);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(ReturnPayment.this, layout, titleView, Settings.getParameters(), false);

        sum = (EditText) findViewById(R.id.sum);
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
        Intent intent = new Intent();
        intent.setAction(APIActions.RETURN_PAYMENT);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        // ID платежа
        if (payment.getText().length() > 0) {
            intent.putExtra(Extras.paramPayment, Integer.decode(payment.getText().toString()));
        }

        // Сумма возврата в МДЕ (в копейках)
        if (sum.getText().length() > 0) {
            intent.putExtra(Extras.paramAmount, Utils.parseSum(sum.getText().toString()));
        }

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

        startActivityForResult(intent, RequestCode.RETURN_PAYMENT.ordinal());
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
