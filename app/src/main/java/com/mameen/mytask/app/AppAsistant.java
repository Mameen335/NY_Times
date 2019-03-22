package com.mameen.mytask.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


//import android.support.design.widget.Snackbar;


/**
 * Created by mahmoudameen on 07/05/18.
 */

public class AppAsistant {

    public static boolean isConnected(Activity activity) {
        ConnectivityManager
                cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

}
