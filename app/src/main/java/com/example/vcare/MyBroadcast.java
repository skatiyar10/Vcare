package com.example.vcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        {try {

            if (!checkInternet(context)) {

                Intent i = new Intent(context, NoInternet.class);
                context.startActivity(i);
            }

        }
        catch (Exception e)
        {}

        }
    }

    public boolean checkInternet(Context c) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();


            return network.isConnected() && network != null;
        }
        catch (Exception e)
        {}
        return false;
    }
}
