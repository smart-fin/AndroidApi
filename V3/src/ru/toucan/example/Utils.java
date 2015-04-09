package ru.toucan.example;

import android.os.Bundle;
import android.util.Log;

public class Utils {
    public static final String TAG = "2CAN";

    public static int parseSum (String text) {
        int result = 0;
        try {
            String[] parts = text.split("[,.]");
            result = Integer.decode(parts[0]) * 100;
            if (parts.length > 1) {
                result += Integer.decode((parts[1] + "00").substring(0, 2));
            }
        } catch (Exception e) {}
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
        } catch (Exception e) {}
        return result;
    }


    public static void dumpBundle (Bundle bundle) {
        Log.d(TAG, "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.d(TAG, String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }
        Log.d(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}
