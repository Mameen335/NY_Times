package com.mameen.mytask.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.mameen.mytask.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ParentFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
        }
    }

    protected void changeActivityViewBackground(@IdRes int id, @DrawableRes int background) {
        getActivity().findViewById(id).setBackground(getActivity().getDrawable(background));
    }

    protected void getFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    protected void setFullScreen() {
        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void hideInputType() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

    }

    protected void gotoActivity(Class<?> activity){
        startActivity(new Intent(getActivity(), activity));
    }

    protected void gotoActivityWithIntent(Intent intent){
        startActivity(intent);
    }

   /* protected void showProgressBar() {
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
    }


    protected void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
    }
*/
    protected void showLongToast(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongSnackbar(int parentId, String stringId) {

        Snackbar.make(getActivity().findViewById(parentId), stringId, Snackbar.LENGTH_LONG).show();
    }

    protected void showShortSnackbar(int parentId, String stringId) {

        Snackbar.make(getActivity().findViewById(parentId), stringId, Snackbar.LENGTH_SHORT).show();
    }

}
