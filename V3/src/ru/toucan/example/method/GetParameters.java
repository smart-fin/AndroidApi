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

public class GetParameters extends Activity {

    public static final int REQUEST_CODE = 7;

    EditText secureCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_get_parameters);

        secureCode = (EditText) findViewById(R.id.secureCode);
    }

    public void pay(View v) {
        Intent intent = new Intent();
        intent.setAction(APIActions.GET_PARAMETERS);

        // Имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.paramPackageName, getPackageName());

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
