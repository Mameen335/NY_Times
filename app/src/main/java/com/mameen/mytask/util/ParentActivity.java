package com.mameen.mytask.util;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mameen.mytask.R;
import com.mameen.mytask.app.Constants;

import java.util.ArrayList;
import java.util.List;

//import android.support.design.widget.Snackbar;


/**
 * This is the parent class for making
 * the common behaviors for all activities.
 *
 * @version 1.0
 */
public class ParentActivity extends AppCompatActivity {

    private final String TAG = ParentActivity.class.getSimpleName();

    boolean doubleBackToExitPressedOnce = false;
    private final int BACK_LENGTH = 2000;

    private static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected boolean checkPermissions(@NonNull String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;

                        }

                    }
                    Log.e(TAG, "permissionsDenied: " + permissionsDenied);
                }
                return;
            }
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
        }
    }

    protected void getFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    protected void gotoActivity(Class<?> activity){
        startActivity(new Intent(getApplicationContext(), activity));
    }

    protected void gotoActivityWithIntent(Intent intent){
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (Constants.isFirstScreen) {
            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this
                    , getResources().getString(R.string.close_app)
                    , Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, BACK_LENGTH);
        } else {
            super.onBackPressed();
        }
    }

    protected void hideInputType() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    /*protected void showProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
    }


    protected void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
    }*/

    protected void showLongToast(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongSnackbar(int parentId, String stringId) {

        Snackbar.make(findViewById(parentId), stringId, Snackbar.LENGTH_LONG).show();
    }

    protected void showShortSnackbar(int parentId, String stringId) {

        Snackbar.make(findViewById(parentId), stringId, Snackbar.LENGTH_SHORT).show();
    }


}
