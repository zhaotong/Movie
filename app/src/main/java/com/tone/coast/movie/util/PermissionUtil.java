package com.tone.coast.movie.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class PermissionUtil {

    // READ_PHONE_STATE
    public static final int REQUEST_PERMISSION_READ_PHONE_STATE = 7000;
    public static final int REQUEST_PERMISSION_CALL_PHONE = 7001;
    public static final int REQUEST_PERMISSION_LOCATION = 7002;
    public static final int REQUEST_PERMISSION_CAMERA = 7003;
    public static final int REQUEST_PERMISSION_EXTERNAL_STORAGE = 7004;


    /**
     * READ_PHONE_STATE权限是否满足
     *
     * @param context
     * @return
     */
    public static boolean checkPermissionReadPhoneState(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }


    /**
     * 定位权限
     *
     * @param context
     * @return
     */
    public static boolean checkPermissionLocation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }


    /**
     * WRITE_EXTERNAL_STORAGE权限是否满足
     *
     * @param context
     * @return
     */
    public static boolean checkPermissionExternalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }


    public static void requestPermissionsExternalStorage(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (!checkPermissionExternalStorage(activity)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_EXTERNAL_STORAGE);
        }

    }

    /**
     * CAMERA权限是否满足
     *
     * @param context
     * @return
     */
    public static boolean checkSelfPermissionCamera(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static boolean checkSelfPermissionCameraAndRecord(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static void requestPermissionsCamera(Activity activity) {
        checkPermission(activity, Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
    }


    /**
     * 检验READ_PHONE_STATE权限
     *
     * @param activity
     */
    public static void requestPermissionsReadPhoneState(Activity activity) {
        checkPermission(activity, Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_READ_PHONE_STATE);
    }

    /**
     * 检验ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION权限
     *
     * @param activity
     */
    public static void requestPermissionsLocation(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (!PermissionUtil.checkPermissionLocation(activity)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }

    }


    public static boolean checkPermission(Activity activity, @Nullable String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    /**
     * 处理权限
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static boolean checkPermission(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            if (!checkPermission(activity, permission)) {
                list.add(permission);
            }
        }
        if (list.size() > 0) {
            String[] a = new String[list.size()];
            list.toArray(a);
            ActivityCompat.requestPermissions(activity, a, requestCode);
        }
        return list.size() == 0;
    }

    /**
     * 处理权限
     *
     * @param activity
     * @param permission
     * @param requestCode
     */
    private static void checkPermission(Activity activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (TextUtils.isEmpty(permission)) {
            return;
        }

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }


    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }


}
