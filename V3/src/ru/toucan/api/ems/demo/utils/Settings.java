package ru.toucan.api.ems.demo.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.toucan.api.ems.demo.App;
import ru.toucan.merchant.common.Extras;

/**
 * Created by Nastya on 17.04.2015.
 */
public class Settings {

    private static SharedPreferences mSharedPreferences;

    private static SharedPreferences getPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(App.getContext());
        }
        return mSharedPreferences;
    }

    private static void put(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    private static String get(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static String getParameters() {
        return get(Extras.tableParameters, null);
    }

    public static void setParameters(String tableParameters) {
        put(Extras.tableParameters, tableParameters);
    }
}
