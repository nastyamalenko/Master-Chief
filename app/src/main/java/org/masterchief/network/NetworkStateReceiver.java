package org.masterchief.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.masterchief.R;


public class NetworkStateReceiver extends BroadcastReceiver {


    public void onReceive(final Context context, final Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            boolean isConnected = networkInfo.isConnectedOrConnecting();

            if (isConnected) {
                boolean isWiFi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
                boolean isMobile = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                if (isWiFi) {
                    Log.i("APP_TAG", "Wi-Fi - CONNECTED");
                } else if (isMobile) {
                    Log.i("APP_TAG", "Mobile - CONNECTED");
                } else {
                    Log.i("APP_TAG", networkInfo.getTypeName() + " - CONNECTED");
                }
            } else {
                Log.i("APP_TAG", "NOT CONNECTED TO INTERNET");
                new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.no_network)
                        .setMessage(R.string.reload)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reload(context, intent);
                            }
                        }).setNegativeButton(R.string.no, null).show();

            }
        } else {
            Log.i("APP_TAG", "NO NETWORK");
            new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.no_network)
                    .setMessage(R.string.reload)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reload(context, intent);
                        }
                    }).setNegativeButton(R.string.no, null).show();
        }
    }

    public void reload(Context context, Intent intent) {
        Intent i = new Intent(context, context.getClass());
        context.startActivity(i);
    }
}
