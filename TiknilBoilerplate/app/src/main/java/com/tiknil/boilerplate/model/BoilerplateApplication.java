package com.tiknil.boilerplate.model;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tiknil.boilerplate.BuildConfig;
import com.tiknil.boilerplate.model.preferences.PreferencesManager;
import com.tiknil.boilerplate.model.webservices.WebServicesManager;
import com.tiknil.boilerplate.utils.ViewUtils;

import io.fabric.sdk.android.Fabric;

/**
 * Classe che identifica l'applicazione stessa
 *
 * @TiKnil
 */
public class BoilerplateApplication extends Application {

    //region Inner enums
    //endregion


    //region Constants
    public static final String GOOGLE_ANALYTICS_TAG = "CHANGE_WITH_ANALYTICS_TAG";
    //endregion


    //region Instance Fields
    private WebServicesManager wsManager = null;
    private PreferencesManager preferencesManager = null;
    private Tracker mTracker;
    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        preferencesManager = new PreferencesManager(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        ViewUtils.initialize(this);

        //TODO: Se serve: impostare l'ultimo access token salvato
//        wsManager = new WebServicesManager(this);
//        wsManager.setAccessToken(preferencesManager.getLastToken());
    }
    //endregion


    //region Custom accessors

    /**
     * @return il manager dei web services
     */
    public WebServicesManager getWsManager() {
        return wsManager;
    }

    /**
     * @return il gestore delle preferenze utente
     */
    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }


    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            //TODO: aggiungere file di configurazione collegato all'account che ha l'app in developer console
            //TODO: vedi: https://developers.google.com/analytics/devguides/collection/android/v4/#get-config
            // mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
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
