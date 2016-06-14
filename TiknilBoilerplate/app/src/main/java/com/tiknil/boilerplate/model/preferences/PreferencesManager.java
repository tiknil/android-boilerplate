package com.tiknil.boilerplate.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

/**
 * Created by Tribbia Riccardo on 28/05/16.
 *
 * @TiKnil
 */
public class PreferencesManager {

    //region Inner enums
    //endregion


    //region Constants
    private static final String SHARED_PREFERENCES_FILE_NAME = "com.tiknil.boilerplate.sharedpreferences";

    //TODO: Aggiungere qui le chiavi delle preferenze
//    private static final String LAST_USEREMAIL_SHARED_PREFS_KEY = "com.tiknil.boilerplate.lastuseremail";

    //endregion


    //region Instance Fields
    private SharedPreferences sharedPreferences = null;

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    public PreferencesManager(Context context) {
        try {
            sharedPreferences = new SecurePreferences(context);
        } catch (IllegalStateException exception) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        } catch (RuntimeException exception) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        } catch (Exception exception) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        }
    }
    //endregion


    //region Custom accessors

    //Impostare i metodi accessori per salvare e recuperare le preferenze utente
//    /**
//     * @return l'ultimo indirizzo mail utilizzato con cui l'accesso è avvenuto con successo
//     */
//    public String getLastUserEmail() {
//        String result = null;
//        if (sharedPreferences != null) {
//            result = sharedPreferences.getString(LAST_USEREMAIL_SHARED_PREFS_KEY, null);
//        }
//        return result;
//    }
//
//    /**
//     * @param userEmail da salvare come ultima mail utiizzata
//     */
//    public void setLastUserEmail(String userEmail) {
//        if (sharedPreferences != null) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(LAST_USEREMAIL_SHARED_PREFS_KEY, userEmail);
//            editor.commit();
//        }
//    }

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
