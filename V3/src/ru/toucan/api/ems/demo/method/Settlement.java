package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ru.toucan.api.APIActions;
import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Settings;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.merchant.common.Extras;

public class Settlement extends Activity {


    EditText dateTimeFrom;
    EditText dateTimeTill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Settings.getParameters()== null) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_setup_settings), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_test_settlement);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(Settlement.this, layout, titleView, Settings.getParameters(), false);

        dateTimeFrom = (EditText) findViewById(R.id.dateTimeFrom);
        dateTimeTill = (EditText) findViewById(R.id.dateTimeTill);

        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_time_format));
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        dateTimeFrom.setText(df.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)));
        dateTimeTill.setText(df.format(new Date(System.currentTimeMillis())));
    }

    public void pay(View v) {
        if (dateTimeFrom.getText().length() != 18) {
            Toast.makeText(this, getString(R.string.date_time_format_wrong), Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateTimeTill.getText().length() != 18) {
            Toast.makeText(this, getString(R.string.date_time_format_wrong), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(APIActions.SETTLEMENT);

        // Параметры по умолчанию из GET_PARAMETERS
        intent = Utils.getDefaultParameters(intent);

        // Дата и время начала периода
        intent.putExtra(Extras.paramDateTimeFrom, dateTimeFrom.getText().toString());

        // Дата и время окончания периода
        intent.putExtra(Extras.paramDateTimeTill, dateTimeTill.getText().toString());

        Utils.dumpBundle(intent.getExtras());

        startActivityForResult(intent, RequestCode.SETTLEMENT.ordinal());
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
