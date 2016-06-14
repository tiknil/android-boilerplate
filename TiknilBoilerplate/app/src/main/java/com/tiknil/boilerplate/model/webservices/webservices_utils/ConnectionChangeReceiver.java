package com.tiknil.boilerplate.model.webservices.webservices_utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tiknil.boilerplate.model.events.InternetConnectionChangeEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Riceve dal sistema operativo i cambi di connettività
 *
 * @TiKnil
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    private final static String TAG = ConnectionChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d(TAG, "Received connection change");
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        EventBus.getDefault().post(new InternetConnectionChangeEvent(isConnected));
    }
}
