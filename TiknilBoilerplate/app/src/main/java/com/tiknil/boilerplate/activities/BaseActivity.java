package com.tiknil.boilerplate.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tiknil.boilerplate.utils.BoilerplateFonts;
import com.tiknil.boilerplate.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Base activity
 * <p>
 * Activity di base ereditata da tutte le altre per modificare gli aspetti generici delle activity
 * <p>
 * Created by Tribbia Riccardo on 03/08/15.
 *
 * @TiKnil
 */
public class BaseActivity extends FragmentActivity {

    //region Inner enums
    //endregion


    //region Constants
    private final static String TAG = BaseActivity.class.getSimpleName();
    //endregion


    //region Instance Fields
    protected Typeface regular = null;
    protected Typeface bold = null;
    protected Typeface semiBold = null;

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bold = BoilerplateFonts.getBoldTypeface(this);
        regular = BoilerplateFonts.getRegularTypeface(this);
        semiBold = BoilerplateFonts.getSemiBoldTypeface(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        ViewUtils.instance().destroyProgress();
        super.onDestroy();
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public

    /**
     * Metodo generico per gestire gli eventi EventBus
     *
     * @param event
     */
    @Subscribe
    public void onEvent(Object event) {
    }

    //endregion

    //region Protected, without modifier

    /**
     * Metodo per recuperare gli elementi grafici (bind) senza doverne specificare la classe
     *
     * @param id della risorsa da recuperare (R.id.layout_name_resource_name)
     * @return il riferimento della risorsa già castato alla classe relativa. null se assente.
     */
    protected <T> T getViewById(int id) {
        return (T) findViewById(id);
    }


    /*
     * Fix Crashltyics #11
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion

}
