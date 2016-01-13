package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.bekirc.minigolfapp.R;

/**
 * @author bekirc on 13.01.16.
 */
public class PreviousScreenDialogFragment extends BaseDialogFragment {

    public static final String TAG = "previousScreenDialogFragment";

    private PreviousScreenDialogFragmentListener listener;

    public interface PreviousScreenDialogFragmentListener {
        void onPreviousScreenOkayButtonClicked();
    }

    public static PreviousScreenDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        PreviousScreenDialogFragment previousScreenDialogFragment = new PreviousScreenDialogFragment();
        previousScreenDialogFragment.setArguments(bundle);
        return previousScreenDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.SURE_GO_PREVIOUS_SCREEN));
        builder.setNegativeButton(R.string.CANCEL_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string.OK_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPreviousScreenOkayButtonClicked();
                dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PreviousScreenDialogFragmentListener) {
            listener = (PreviousScreenDialogFragmentListener) activity;
        }
    }
}
