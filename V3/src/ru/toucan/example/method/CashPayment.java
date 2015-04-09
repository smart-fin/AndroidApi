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

public class CashPayment extends Activity {

    private static final int REQUEST_CODE = 2;

    EditText sum;
    EditText vat;
    EditText description;
    EditText fullDescription;
    EditText receiptNumber;
    EditText secureCode;
    CheckBox fiscalizationFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cash_payment);

        sum = (EditText) findViewById(R.id.sum);
        vat = (EditText) findViewById(R.id.vat);
        description = (EditText) findViewById(R.id.description);
        fullDescription = (EditText) findViewById(R.id.fullDescription);
        receiptNumber = (EditText) findViewById(R.id.receiptNumber);
        secureCode = (EditText) findViewById(R.id.secureCode);
        fiscalizationFlag = (CheckBox) findViewById(R.id.fiscalizationFlag);
    }

    public void pay(View v) {
        // обязательные поля
//        if (sum.getText().length() < 1) {
//            Toast.makeText(this, "Сумма — обязательное поле", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (description.getText().length() < 1 && receiptNumber.getText().length() < 1) {
//            Toast.makeText(this, "Должно быть указано назначение или номер чека", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (secureCode.getText().length() != 4) {
//            Toast.makeText(this, "Код доступа должен содержать 4 цифры", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent intent = new Intent();
        intent.setAction(APIActions.CASH_PAYMENT);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

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

        // Код доступа к приложению
        intent.putExtra(Extras.paramSecureCode, secureCode.getText().toString());

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
