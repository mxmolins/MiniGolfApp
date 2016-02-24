package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.example.bekirc.minigolfapp.R;

/**
 * @author bekirc on 24.02.16.
 */
public class RecommendOnPlayDialogFragment extends BaseDialogFragment {

    public static final String TAG = "recommendOnPlayDialogFragment";

    private RecommendOnPlayDialogFragmentListener listener;

    public interface RecommendOnPlayDialogFragmentListener {
        void onRecommendOnPlayPressed();

        void onDialogCanceled();
    }

    public static RecommendOnPlayDialogFragment newInstance() {
        return new RecommendOnPlayDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.RECOMMEND_ON_PLAY_TEXT));
        builder.setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onRecommendOnPlayPressed();
                dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (getActivity() != null) {
                    Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(getResources().getColor(R.color.c_aeaeae));
                    negativeButton.invalidate();
                }
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RecommendOnPlayDialogFragmentListener) {
            listener = (RecommendOnPlayDialogFragmentListener) activity;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        listener.onDialogCanceled();
    }
}
