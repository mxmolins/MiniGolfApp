package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.support.v4.app.DialogFragment;

/**
 * @author bekirc on 03.12.15.
 */
public class BaseDialogFragment extends DialogFragment {
    @Override
    public void dismissAllowingStateLoss() {
        if (isAdded() && isResumed()) {
            super.dismissAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        if (isAdded() && isResumed()) {
            super.dismiss();
        }
    }
}
