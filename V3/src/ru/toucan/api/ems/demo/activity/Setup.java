package ru.toucan.api.ems.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.method.parameters.XmlParser;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Settings;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.api.ems.demo.method.GetLastError;
import ru.toucan.api.ems.demo.method.GetParameters;
import ru.toucan.api.ems.demo.method.GetVersion;
import ru.toucan.merchant.common.Extras;

/**
 * Created by Nastya on 17.04.2015.
 */
public class Setup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup);

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
        TextView titleView = (TextView) findViewById(R.id.captionView);
        Utils.createFields(Setup.this, layout, titleView, Settings.getParameters(), true);
    }


    private void saveParameters() {

        String parameters = getIntent().getStringExtra(Extras.tableParameters);

        if (parameters == null)  {
            parameters = Settings.getParameters();

            if (parameters == null ) return;
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);

        Settings.setParameters(XmlParser.update(parameters, layout));
    }

    public void getParameters(View v) {
        toActivity(GetParameters.class);
    }

    public void getVersion(View v) {
        toActivity(GetVersion.class);
    }

    public void getLastError(View v) {
        toActivity(GetLastError.class);
    }

    private void toActivity(Class<?> cls) {
        startActivityForResult(new Intent(Setup.this, cls), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        Utils.dumpBundle(data.getExtras());

        TextView resultView = (TextView) findViewById(R.id.resultView);
        resultView.setText(Utils.printResult(data, getIntent()));

        if (resultCode == RESULT_OK && data.hasExtra(Extras.tableParameters)) {
            Settings.setParameters(data.getStringExtra(Extras.tableParameters));

            LinearLayout layout = (LinearLayout) findViewById(R.id.groupBox);
            TextView titleView = (TextView) findViewById(R.id.captionView);
            Utils.createFields(Setup.this, layout, titleView, data.getStringExtra(Extras.tableParameters), true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveParameters();
        }
        return super.onKeyDown(keyCode, event);
    }
}
