package ru.toucan.example.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 18.07.12
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class Logger {

    private static String TAG = "2CAN";

    private static boolean isLogging = true;

    static SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        if (isLogging) {
            Log.d(TAG, message);
            writeToFile(message);
        }
    }

    public static void log(Exception e) {
        if (isLogging) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log.e(TAG, errors.toString());
            writeToFile(errors.toString());
        }
    }

    public static void e(String message) {
        if (isLogging) {
            Log.e(TAG, message);
            writeToFile(message);
        }
    }


    public static void e(Thread thread, Throwable ex) {
        if (isLogging) {

            String message = (thread != null ? "thread name: " + thread.getName() : "thread is null") +
                    (ex != null ? "; server_error: " + ex.getLocalizedMessage() : "; server_error is null");
            Log.e(TAG, message);

            writeToFile(message);
        }
    }
    
    public static void printStackTrace(Exception e) {
    	if (e == null) return;

        if (isLogging) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log(sw.toString());
        }
    }

    private static String filename = "logfile.txt";
    public static void writeToFile(String message) {
        try {

            File file = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
            if (!file.exists())
                file.createNewFile();
            if (file.exists()) {

                BufferedWriter bW = new BufferedWriter(new FileWriter(file, true));
                bW.append(stf.format(new Date()) + ": " + message + "\n");

                bW.flush();
                bW.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
