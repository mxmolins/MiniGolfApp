package com.example.bekirc.minigolfapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Player;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author bekirc on 04.12.15.
 */
public class PlayerListNameAdapter extends RecyclerView.Adapter<PlayerListNameAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Player> playerList;

    public PlayerListNameAdapter(Context context, ArrayList<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player_name, parent, false);

        return new PlayerListNameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String playerPositionText = (position + 1) + "";
        holder.playerNoText.setText(playerPositionText);
        holder.playerNameEdit.setTag(position);
        String playerNoHint = context.getString(R.string.PLAYER_NO, (position + 1));
        holder.playerNameEdit.setHint(playerNoHint);
        holder.playerNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (holder.textWatcher != null) {
                        holder.playerNameEdit.removeTextChangedListener(holder.textWatcher);
                        holder.textWatcher = null;
                    }
                } else {
                    holder.textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            int etPosition = (int) v.getTag();
                            EditText et = (EditText) v;
                            playerList.get(etPosition).setName(et.getText().toString());
                        }
                    };
                    holder.playerNameEdit.addTextChangedListener(holder.textWatcher);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.player_no_text)
        TextView playerNoText;
        @InjectView(R.id.player_name_edit)
        EditText playerNameEdit;

        TextWatcher textWatcher;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
