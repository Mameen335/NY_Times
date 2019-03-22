package com.mameen.mytask.app;

import android.app.Activity;
import android.os.Handler;
import android.view.View;


public class MyUiVisibilityChangeListener implements View.OnSystemUiVisibilityChangeListener {

    private boolean checkToUIvisibility = true;
    private Activity activity;

    public MyUiVisibilityChangeListener(Activity activity) {
        this.activity = activity;
        hideSystemUI();
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        if (checkToUIvisibility) {
            checkToUIvisibility = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideSystemUI();
                }
            }, 2000);
        }
    }


    private void hideSystemUI() {
        checkToUIvisibility = true;
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        /*| View.SYSTEM_UI_FLAG_FULLSCREEN*/);





        /*activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);*/
    }


    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
