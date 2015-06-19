package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Settings;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.merchant.common.Extras;

public class Close extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_close);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(Close.this, layout, titleView, Settings.getParameters(), false);
    }

    public void pay(View v) {

        Intent intent = new Intent();
        intent.setAction(APIActions.CLOSE);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        Utils.dumpBundle(intent.getExtras());

        startActivityForResult(intent, RequestCode.CLOSE.ordinal());
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
