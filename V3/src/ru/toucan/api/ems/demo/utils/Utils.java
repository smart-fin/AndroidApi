package ru.toucan.api.ems.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.StringWriter;

import ru.toucan.api.APIPaymentResult;
import ru.toucan.api.ems.demo.method.parameters.Group;
import ru.toucan.api.ems.demo.method.parameters.Page;
import ru.toucan.api.ems.demo.method.parameters.Parameter;
import ru.toucan.api.ems.demo.method.parameters.XmlParser;
import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.common.Extras;

public class Utils {
    public static final String TAG = "2CAN";

    public static int parseSum(String text) {
        int result = 0;
        try {
            String[] parts = text.split("[,.]");
            result = Integer.decode(parts[0]) * 100;
            if (parts.length > 1) {
                result += Integer.decode((parts[1] + "00").substring(0, 2));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static int parseVat(String text) {
        int result = 0;
        String[] parts = text.split("[,.]");
        try {
            result = Integer.decode(parts[0]) * 10;
            if (parts.length > 1) {
                result += Integer.decode((parts[1] + "0").substring(0, 1));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static void dumpBundle(Bundle bundle) {
        Log.d(TAG, "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        if (bundle == null) {
            Log.d(TAG, "bundle is null");
        } else {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                if (value != null) {
                    Log.d(TAG, String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                } else {
                    Log.d(TAG, String.format("%s null ()", key));
                }
            }
        }
        Log.d(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    public static String parse(Intent intent) {
        StringWriter sw = new StringWriter();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            sw.append("bundle is null");
        } else {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);

                if (key.equals(Extras.resultCode) || key.equals(Extras.requestCode))
                    continue;

                if (value != null) {
                    sw.append(String.format("%s: %s", key, value.toString())).append("\n\n");
                } else {
                    sw.append(String.format("%s: null", key)).append("\n\n");
                }
            }
        }
        return sw.toString();
    }

    public static String printResult(Intent data, Intent intent) {
        StringWriter sw = new StringWriter();
        if (data.hasExtra(Extras.requestCode)) {
            int rc = data.getIntExtra(Extras.requestCode, -1);
            sw.append("Request code: " + rc + " [" + (rc<RequestCode.names.length?RequestCode.names[rc]:"???") + "]")
                    .append("\n\n");
        }
        if (data.hasExtra(Extras.resultCode)) {
            int rc = data.getIntExtra(Extras.resultCode, 0);
            sw.append("Result code: " + rc + " [" + (rc==Activity.RESULT_OK?"RESULT_OK":
                    (rc== Activity.RESULT_CANCELED?"RESULT_CANCELED":"???")) + "]")
                    .append("\n\n");
        }
        if (data.hasExtra(Extras.result)) { // FULL RESULT
            ParcelableObject result = data.getParcelableExtra(Extras.result);

            sw.append("Result data FULL:\n")
                    .append(result.toString());

            if (result instanceof APIPaymentResult) {
                intent.putExtra(Extras.paymentId, ((APIPaymentResult) result).Payment);
            }
        } else { // SHORT RESULT
            if (data.hasExtra(Extras.requestCode) && data.hasExtra(Extras.resultCode))
                sw.append("Result data SHORT:\n")
                        .append(Utils.parse(data));
        }

        return sw.toString();
    }

    public static void createFields(Activity context, LinearLayout layout, TextView titleView, String tableParameters, boolean enabled) {
        if (tableParameters == null) return;

        try {
            Page page = XmlParser.get(tableParameters);
            layout.removeAllViews();
            for (Group group : page.groups) {
                if (titleView != null)
                    titleView.setText(group.caption);
                for (Parameter parameter : group.parameters) {
                    layout.addView(Utils.createField(context, parameter, enabled));
                }
            }
            Log.d(Utils.TAG, page.toString());
        } catch (Exception e) {
            Log.d(Utils.TAG, e.getLocalizedMessage());
        }
    }

    public static View createField(Context context, Parameter parameter, boolean enabled) {
        return create(context, parameter.name, parameter.caption, parameter.defaultValue, enabled);
    }

    private static LinearLayout create(Context context, String name, String caption, String value, boolean enabled) {
        LinearLayout linearLayout = new LinearLayout(context);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(createTitle(context, caption + " (" + name +")"));
        linearLayout.addView(createValue(context, name, value, enabled));
        return linearLayout;
    }

    private static View createTitle(Context context, String title) {
        TextView textTitle = new TextView(context);
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        paramsLeft.setMargins(0, 0, 0, 0);
        textTitle.setLayoutParams(paramsLeft);
        textTitle.setText(title + ":");
        return textTitle;
    }

    private static View createValue(Context context, String name, String value, boolean enabled) {
        EditText textValue = new EditText(context);
        textValue.setText(value);
        textValue.setTag(name);
        textValue.setEnabled(enabled);
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        paramsRight.setMargins(0, 0, 0, 0);
        textValue.setLayoutParams(paramsRight);
        return textValue;
    }

    public static Intent getDefaultParameters(Intent intent) {
        String parameters = Settings.getParameters();

        if (parameters != null) {
            try {
                Page page = XmlParser.get(parameters);
                for (Group group : page.groups) {
                    for (Parameter parameter : group.parameters) {
                        intent.putExtra(parameter.name, parameter.defaultValue);
                    }
                }
            } catch (Exception e) {
            }
        }
        return intent;
    }
}