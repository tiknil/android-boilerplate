package com.tiknil.boilerplate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.tiknil.boilerplate.R;

/**
 * Classe che accentra tutti i controlli per la visualizzazione dei dialog principalmente utilizzati
 *
 * @TiKnil
 */
public class DialogFactory {

    /**
     * Visualizza un popup con titolo, messaggio, etichetta del pulsante positivo e del pulsante negativo e listener associati
     * Specifica l'activity relativa alla visualizzazione del popup
     *
     * @param activity              activity relativa alla visualizzazione del popup
     * @param title                 titolo del popup, non può essere null
     * @param message               messaggio del popup, non può essere null
     * @param rightBtnLbl           etichetta del pulsante destro, può essere null
     * @param rightBtnClickListener listener relativo al click sul pulsante destro, può essere null
     * @param leftBtnLbl            etichetta del pulsante sinistro, può essere null
     * @param leftBtnClickListener  listener relativo al click sul pulsante sinistro, può essere null
     */
    public static void showAlertDialog(@NonNull final Activity activity,
                                       @NonNull final String title,
                                       @NonNull final String message,
                                       final String rightBtnLbl,
                                       final DialogInterface.OnClickListener rightBtnClickListener,
                                       final String leftBtnLbl,
                                       final DialogInterface.OnClickListener leftBtnClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setTitle(title)
                            .setMessage(message).create();

                    if (rightBtnLbl != null) {
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, rightBtnLbl, rightBtnClickListener);
                    } else {
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.generic_ok), rightBtnClickListener);
                    }

                    if (leftBtnLbl != null) {
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, leftBtnLbl, leftBtnClickListener);
                    }

                    alertDialog.show();
                }
            }
        });
    }

}
