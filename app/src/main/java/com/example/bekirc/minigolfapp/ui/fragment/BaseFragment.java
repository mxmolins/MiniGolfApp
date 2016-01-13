package com.example.bekirc.minigolfapp.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.example.bekirc.minigolfapp.ui.activity.BaseActionBarActivity;

/**
 * @author bekirc on 03.12.15.
 */
public abstract class BaseFragment extends Fragment{

    private BaseActionBarActivity mainActivity;

    protected BaseActionBarActivity getBaseActivity() {
        if (mainActivity == null) {
            mainActivity = (BaseActionBarActivity) getActivity();
        }
        return mainActivity;
    }

    protected void setTitle(String title) {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
