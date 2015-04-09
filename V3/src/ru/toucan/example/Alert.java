package ru.toucan.example;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.toucan.example2.R;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 18.07.12
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class Alert {

    public static void show(String title, String message, String okLabel,
                            final Handler okHandler,
                            Context context) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog);

        ((TextView) dialog.findViewById(R.id.titleView)).setText(title + "                                           ");
        ((TextView) dialog.findViewById(R.id.messageView)).setText(message);

        if (okLabel != null)
            ((Button) dialog.findViewById(R.id.btnOk)).setText(okLabel);
        dialog.findViewById(R.id.btnOk).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();

                if (okHandler != null) okHandler.sendEmptyMessage(0);
            }
        });
        dialog.show();
    }
}

