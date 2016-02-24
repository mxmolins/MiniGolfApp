package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bekirc.minigolfapp.R;

/**
 * @author bekirc on 24.02.16.
 */
public class SuggestionsDialogFragment extends BaseDialogFragment {

    public static final String TAG = "suggestionsDialogFragment";

    private SuggestionsDialogFragmentListener listener;

    public interface SuggestionsDialogFragmentListener {
        void onSuggestionSendButtonPressed(String suggestion);

        void onDialogCanceled();
    }

    public static SuggestionsDialogFragment newInstance() {
        return new SuggestionsDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.edit_text, null);
        final EditText suggestionEditText = (EditText) view.findViewById(R.id.edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setMessage(getString(R.string.RECOMMEND_ON_PLAY_TEXT));
        builder.setNegativeButton(R.string.CANCEL_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton(R.string.SEND, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSuggestionSendButtonPressed(suggestionEditText.getText().toString());
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
        if (activity instanceof SuggestionsDialogFragmentListener) {
            listener = (SuggestionsDialogFragmentListener) activity;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        listener.onDialogCanceled();
    }
}
