package com.tone.coast.movie.util;

import android.content.Context;
import android.content.res.Resources;


public class PixelUtil {

    /**
     * dp转 px.
     *
     * @param value the value
     * @return the int
     */
    public static float dp2px(Context context,float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return value * scale / 160 + 0.5f;
    }



    /**
     * px转dp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static float px2dp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return value * 160 / scale + 0.5f;
    }



    /**
     * sp转px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int sp2px(float value, Context context) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }



    /**
     * px转sp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2sp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }

}
