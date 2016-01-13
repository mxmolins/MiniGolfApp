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
 * @author bekirc on 03.12.15.
 */
public class PreviousGameDialogFragment extends BaseDialogFragment {

    private static final String PREVIOUS_TURN = "previousTurn";
    public static final String TAG = "previousGameDialogFragment";

    private int previousTurn;
    private PreviousGameDialogFragmentListener listener;

    public interface PreviousGameDialogFragmentListener {
        void onOkayButtonClicked();
    }

    public static PreviousGameDialogFragment newInstance(int previousTurn) {
        Bundle bundle = new Bundle();
        bundle.putInt(PREVIOUS_TURN, previousTurn + 1);
        PreviousGameDialogFragment previousGameDialogFragment = new PreviousGameDialogFragment();
        previousGameDialogFragment.setArguments(bundle);
        return previousGameDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        previousTurn = bundle.getInt(PREVIOUS_TURN, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.SURE_GO_BACK, previousTurn));
        builder.setNegativeButton(R.string.CANCEL_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string.OK_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onOkayButtonClicked();
                dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PreviousGameDialogFragmentListener) {
            listener = (PreviousGameDialogFragmentListener) activity;
        }
    }
}
