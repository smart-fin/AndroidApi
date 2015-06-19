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

public class CashPayment extends Activity {

    EditText sum;
    EditText vat;
    EditText description;
    EditText fullDescription;
    EditText receiptNumber;
    CheckBox fiscalizationFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_cash_payment);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(CashPayment.this, layout, titleView, Settings.getParameters(), false);

        sum = (EditText) findViewById(R.id.sum);
        vat = (EditText) findViewById(R.id.vat);
        description = (EditText) findViewById(R.id.description);
        fullDescription = (EditText) findViewById(R.id.fullDescription);
        receiptNumber = (EditText) findViewById(R.id.receiptNumber);
        fiscalizationFlag = (CheckBox) findViewById(R.id.fiscalizationFlag);
    }

    public void pay(View v) {

        Intent intent = new Intent();
        intent.setAction(APIActions.CASH_PAYMENT);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        // Сумма платежа в МДЕ (в копейках)
        intent.putExtra(Extras.paramAmount, Utils.parseSum(sum.getText().toString()));

        // НДС (в промилях)
        intent.putExtra(Extras.paramValueAddedTaxRate, Utils.parseVat(vat.getText().toString()));

        // Назначение платежа
        if (description.getText().length() > 0) {
            intent.putExtra(Extras.paramDescription, description.getText().toString());
        }
        // Подробное назначение платежа, используется для печати подробной информации об услуге на кассовом чеке
        if (fullDescription.getText().length() > 0) {
            intent.putExtra(Extras.paramFullDescription, fullDescription.getText().toString());
        }        // Номер чека
        if (receiptNumber.getText().length() > 0) {
            intent.putExtra(Extras.paramReceiptNumber, receiptNumber.getText().toString());
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

        startActivityForResult(intent, RequestCode.CASH_PAYMENT.ordinal());
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
