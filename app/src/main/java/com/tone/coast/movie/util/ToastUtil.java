package com.tone.coast.movie.util;

import android.content.Context;
import android.widget.Toast;

import com.tone.coast.movie.App;


public class ToastUtil {

    private static ToastUtil instance;
    private Context mContext;
    private Toast mToast;


    public static ToastUtil getInstance() {
        if (instance == null)
            synchronized (ToastUtil.class) {
                if (instance == null)
                    instance = new ToastUtil();
            }
        return instance;
    }

    private ToastUtil() {
        init();
    }

    private void init() {
        mContext = App.getInstance().getApplicationContext();
    }

    public void showToast(String text) {

        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
