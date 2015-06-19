package ru.toucan.api.ems.demo.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ru.toucan.api.APIActions;
import ru.toucan.api.ems.demo.R;
import ru.toucan.api.ems.demo.utils.RequestCode;
import ru.toucan.api.ems.demo.utils.Utils;
import ru.toucan.merchant.common.Extras;

public class GetParameters extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        get();
    }

    public void get() {
        Intent intent = new Intent();
        intent.setAction(APIActions.GET_PARAMETERS);

        Utils.dumpBundle(intent.getExtras());

        startActivityForResult(intent, RequestCode.GET_PARAMETERS.ordinal());
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
