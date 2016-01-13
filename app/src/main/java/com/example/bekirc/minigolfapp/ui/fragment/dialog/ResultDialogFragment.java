package com.example.bekirc.minigolfapp.ui.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Player;
import com.example.bekirc.minigolfapp.misc.Util;
import com.example.bekirc.minigolfapp.ui.adapter.ResultAdapter;
import com.example.bekirc.minigolfapp.ui.misc.DividerItemDecoration;

import java.util.ArrayList;

/**
 * @author bekirc on 13.01.16.
 */
public class ResultDialogFragment extends BaseDialogFragment {

    public static final String TAG = "ResultDialogFragment";
    public static final String PLAYER_LIST = "playerList";

    private ArrayList<Player> playerList;

    public static ResultDialogFragment newInstance(ArrayList<Player> playerList) {
        ResultDialogFragment resultDialogFragment = new ResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PLAYER_LIST, playerList);
        resultDialogFragment.setArguments(bundle);
        return resultDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playerList = getArguments().getParcelableArrayList(PLAYER_LIST);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_result, null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), null);
        decoration.setVerticalSpace(Util.dpToPx(8));
        recyclerView.addItemDecoration(decoration);
        ResultAdapter adapter = new ResultAdapter(getContext(), playerList);
        recyclerView.setAdapter(adapter);

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
