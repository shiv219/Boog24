package com.boog24.extra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;

import com.boog24.R;


public class NetworkAlertUtility {
    static AlertDialog alert;
    public static boolean isConnectingToInternet(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
            }
        }

        return false;
    }


    public static void showNetworkFailureAlert(Context context) {
        if (context != null  && !((Activity)(context)).isFinishing()  && (alert==null ||  (alert != null && !alert.isShowing()))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.color.white);
            builder.setMessage(context.getResources().getString(R.string.no_network_message)).setTitle(context.getResources().getString(R.string.no_internet))
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            alert = builder.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

}
