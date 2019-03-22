package com.mameen.mytask.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

//import static com.android.volley.VolleyLog.TAG;
//import static com.google.android.gms.internal.zzt.TAG;

public class PermissionUtils {

    public static boolean isPermissionGranted(Activity activity, String permissionName) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permissionName)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG, "Permission is granted");
                return true;
            } else {
//                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(activity,
                        new String[]{permissionName}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public static final String[] IMAGE_PERMISSIONS =
            {Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] LOCATION_PERMISSIONS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String[] DOWNLOAD_PERMISSIONS =
            {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] VOICE_PERMISSIONS =
            {Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAPTURE_AUDIO_OUTPUT};

    public static final String[] ALL_PERMISSIONS =
            {Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAPTURE_AUDIO_OUTPUT,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] CALL_PERMISSIONS =
            {Manifest.permission.CALL_PHONE};

    public static final int IMAGE_PERMISSION_RESPONSE = 1;
    public static final int DOWNLOAD_PERMISSION_RESPONSE = 2;
    public static final int LOCATION_PERMISSION_RESPONSE = 3;
    public static final int VOICE_PERMISSION_RESPONSE = 4;
    public static final int ALL_PERMISSION_RESPONSE = 5;
    public static final int CALL_PERMISSION_RESPONSE = 6;

    /**
     * @return whether all the required permission for taking and picking an voice are granted or not
     */
    public static boolean areVoicePermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT < 23)
            return true;
        for (String permission : VOICE_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    /**
     * @return whether all the required permission for taking and picking an image are granted or not
     */
    public static boolean areImagePermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT < 23)
            return true;
        for (String permission : IMAGE_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    /**
     * @return whether all the required permission for taking and picking an image are granted or not
     */
    public static boolean areLocationPermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT < 23)
            return true;
        for (String permission : LOCATION_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    public static boolean isAllPermissionGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("SSSs", permission.toString());
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkPermissionForExternalStorage(Activity activity) {
        int result =
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean requestStoragePermission(Activity activity, int READ_STORAGE_PERMISSION) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE_PERMISSION);
            }
        } else {
        }
        return false;
    }
}