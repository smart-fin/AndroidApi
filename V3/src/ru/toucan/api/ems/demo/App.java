package ru.toucan.api.ems.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Nastya on 17.04.2015.
 */
public class App extends Application {

    private static Context context;

    public static void setContext(Context cxt) {
        context = cxt;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setContext(getApplicationContext());
    }
}
