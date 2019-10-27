package com.example.fisrtapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.view.View;

import com.example.fisrtapplication.R;
import com.google.android.material.snackbar.Snackbar;


public class ConnectionChangeReceiver extends BroadcastReceiver {
    private View view;

    public ConnectionChangeReceiver(View view) {
        super();
        this.view = view;
    }

    public void onReceive(Context context, Intent intent) {
        if (!isOnline(context)) {
            Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        }
        return false;
    }
}
