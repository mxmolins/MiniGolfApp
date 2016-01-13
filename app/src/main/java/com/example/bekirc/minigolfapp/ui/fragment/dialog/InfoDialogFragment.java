package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bekirc.minigolfapp.R;

/**
 * @author bekirc on 03.12.15.
 */
public class InfoDialogFragment extends BaseDialogFragment {

    public static final String TAG = "InfoDialogFragment";

    public static InfoDialogFragment newInstance() {
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        return infoDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_info, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setNegativeButton(R.string.BACK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
}
