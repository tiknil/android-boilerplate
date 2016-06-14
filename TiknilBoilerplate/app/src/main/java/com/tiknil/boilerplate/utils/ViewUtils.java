package com.tiknil.boilerplate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.tiknil.boilerplate.R;

/**
 * Created by Tribbia Riccardo on 28/05/16.
 *
 * @TiKnil
 */
public class ViewUtils {

    //region Inner enums
    //endregion


    //region Constants
    private static final String TAG = ViewUtils.class.getSimpleName();
    //endregion


    //region Instance Fields
    private ProgressDialog progressDialog = null;
    private static ViewUtils instance = null;
    private Context context = null;
    //endregion


    //region Class methods

    public static ViewUtils instance() {
        if (instance == null) {
            new Exception("Bisogna inizializzare ViewUtils nella classe Application per poterla usare");
        }
        return instance;
    }

    public static void initialize(Context context) {
        instance = new ViewUtils(context);
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int mmToPx(int mm, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm,
                context.getResources().getDisplayMetrics());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    //endregion


    //region Constructors / Lifecycle
    public ViewUtils(Context context) {
        this.context = null;
    }
    //endregion


    //region Custom accessors
    //endregion


    //region Public

    public void destroyProgress() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * Visualizza un popup con progress bar con titolo e messaggio
     *
     * @param title   titolo del popup, può essere null
     * @param message messaggio del popup, può essere null
     */
    public void showProgressDialogWithTitleAndMessage(final String title, final String message) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(context);
                    }
                    if (title != null) {
                        progressDialog.setTitle(title);
                    }
                    if (message != null) {
                        progressDialog.setMessage(message);
                    }
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                } catch (Exception exception) {
                    Log.w(TAG, "Errore durante la visualizzazione del progress dialog", exception);
                }
            }
        });
    }

    /**
     * Nasconde l'eventuale popup con progress bar
     */
    public void hideProgressDialog() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    Log.w(TAG, "Errore durante il nascondimento del progress dialog", exception);
                }
            }
        });
    }

    /**
     * Visualizza un popup con titolo e messaggio passati come parametro con unico pulsante "Ok"
     *
     * @param title   titolo del popup, non può essere null
     * @param message messaggio del popup, non può essere null
     */
    public void showAlertDialog(@NonNull final String title, @NonNull final String message) {
        showAlertDialog(title, message, null, null);
    }

    /**
     * Visualizza un popup con titolo, messaggio, etichetta del pulsante positivo (quello negativo sarà "ok") e listener associato
     *
     * @param title                titolo del popup, non può essere null
     * @param message              messaggio del popup, non può essere null
     * @param leftBtnLbl           etichetta del pulsante sinistro, può essere null
     * @param leftBtnClickListener listener relativo al click sul pulsante sinistro, può essere null
     */
    public void showAlertDialog(@NonNull final String title,
                                @NonNull final String message,
                                final String leftBtnLbl,
                                final DialogInterface.OnClickListener leftBtnClickListener) {
        showAlertDialog(title, message, null, null, leftBtnLbl, leftBtnClickListener);

    }

    /**
     * Visualizza un popup con titolo, messaggio, etichetta del pulsante positivo e del pulsante negativo e listener associati
     *
     * @param title                 titolo del popup, non può essere null
     * @param message               messaggio del popup, non può essere null
     * @param rightBtnLbl           etichetta del pulsante destro, può essere null
     * @param rightBtnClickListener listener relativo al click sul pulsante destro, può essere null
     * @param leftBtnLbl            etichetta del pulsante sinistro, può essere null
     * @param leftBtnClickListener  listener relativo al click sul pulsante sinistro, può essere null
     */
    public void showAlertDialog(@NonNull final String title,
                                @NonNull final String message,
                                final String rightBtnLbl,
                                final DialogInterface.OnClickListener rightBtnClickListener,
                                final String leftBtnLbl,
                                final DialogInterface.OnClickListener leftBtnClickListener) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle(title)
                            .setMessage(message).create();

                    if (rightBtnLbl != null) {
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, rightBtnLbl, rightBtnClickListener);
                    } else {
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.generic_ok), rightBtnClickListener);
                    }

                    if (leftBtnLbl != null) {
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, leftBtnLbl, leftBtnClickListener);
                    }

                    alertDialog.show();
                } catch (Exception exception) {
                    Log.w(TAG, "Errore durante la visualizzaione del dialog", exception);
                }
            }
        });
    }

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
