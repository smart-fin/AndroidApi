package ru.toucan.example.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ru.toucan.api.APIActions;
import ru.toucan.api.APIExtras;
import ru.toucan.example.Utils;
import ru.toucan.example.activity.NewAPI;
import ru.toucan.example2.R;
import ru.toucan.merchant.common.Extras;

public class Settlement extends Activity {

    public static final int REQUEST_CODE = 4;

    EditText secureCode;
    EditText dateTimeFrom;
    EditText dateTimeTill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_settlement);

        secureCode = (EditText) findViewById(R.id.secureCode);
        dateTimeFrom = (EditText) findViewById(R.id.dateTimeFrom);
        dateTimeTill = (EditText) findViewById(R.id.dateTimeTill);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        dateTimeFrom.setText(df.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)));
        dateTimeTill.setText(df.format(new Date(System.currentTimeMillis())));
    }

    public void pay(View v) {
        // обязательные поля
        if (secureCode.getText().length() != 4) {
            Toast.makeText(this, "Код доступа должен содержать 4 цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateTimeFrom.getText().length() != 18) {
            Toast.makeText(this, "Формат даты и времени начала периода - yyyy-MM-ddHH:mm:ss, поле обязательное", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateTimeTill.getText().length() != 18) {
            Toast.makeText(this, "Формат даты и времени конца периода - yyyy-MM-ddHH:mm:ss, поле обязательное", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(APIActions.SETTLEMENT);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

        // Код доступа к приложению
        intent.putExtra(Extras.paramSecureCode, secureCode.getText().toString());

        // Дата и время начала периода
        intent.putExtra(Extras.paramDateTimeFrom, dateTimeFrom.getText().toString());

        // Дата и время окончания периода
        intent.putExtra(Extras.paramDateTimeTill, dateTimeTill.getText().toString());

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
