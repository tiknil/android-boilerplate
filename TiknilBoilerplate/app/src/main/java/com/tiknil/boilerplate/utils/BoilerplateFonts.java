package com.tiknil.boilerplate.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

/**
 * Classe di utilità per recuperare da ovunque i font designati
 *
 * @TiKnil
 */
public class BoilerplateFonts {

    /**
     * @return il font dell'app di tipo "Regular"
     */
    public static Typeface getRegularTypeface(@NonNull Context context) {
        String type = "fonts/SourceSansPro-Regular.ttf";
        return Typeface.createFromAsset(context.getAssets(), type);
    }

    /**
     * @return il font dell'app di tipo "SemiBold"
     */
    public static Typeface getSemiBoldTypeface(@NonNull Context context) {
        String type = "fonts/SourceSansPro-Semibold.ttf";
        return Typeface.createFromAsset(context.getAssets(), type);
    }


    /**
     * @return il font dell'app di tipo "Bold"
     */
    public static Typeface getBoldTypeface(@NonNull Context context) {
        String type = "fonts/SourceSansPro-Bold.ttf";
        return Typeface.createFromAsset(context.getAssets(), type);
    }
}
