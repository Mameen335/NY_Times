package com.mameen.mytask.ui.activities;

import android.os.Bundle;
import com.mameen.mytask.R;
import com.mameen.mytask.ui.fragments.NewsFragment;
import com.mameen.mytask.util.ParentActivity;


public class MainActivity extends ParentActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getString(R.string.app_name) + " "
                +getResources().getString(R.string.mostpopular));

        getFragment(R.id.frg, new NewsFragment());
    }
}
