package com.tiknil.boilerplate.model.analytics;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tiknil.boilerplate.model.BoilerplateApplication;

/**
 * Created by Tribbia Riccardo on 13/06/16.
 *
 * @TiKnil
 */
public class Analytics {

    //region Inner enums
    //endregion


    //region Constants
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = Analytics.class.getSimpleName();
    //endregion


    //region Instance Fields
    //endregion


    //region Class methods

    /**
     * Obtain the shared Tracker instance.
     *
     * @return GoogleAnalytics Tracker
     */
    public static Tracker getGoogleAnalyticsTracker(Application application) {
        return (application != null ? ((BoilerplateApplication) application).getDefaultTracker() : null);
    }

    /**
     * Controlla la presenza dei Google Play Service. Se sono attivabili mostra all'utente la schermata di sistema per farlo
     *
     * @return true se sono presenti i Google Play Service sul device, false altrimenti
     */
    public static boolean checkPlayServices(@NonNull Activity activity) {
        boolean result = false;
        try {
            int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Log.d(TAG, "This device is not supported - Google Play Services.");
                }
            } else {
                result = true;
            }
        } catch (Exception exception) {
            Log.w("Play Services", "Errore durante il check della presenza dei Google Play Services", exception);
        }
        return result;
    }

    //endregion


    //region Constructors / Lifecycle
    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}
