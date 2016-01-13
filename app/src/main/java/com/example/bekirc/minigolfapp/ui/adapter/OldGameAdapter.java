package com.example.bekirc.minigolfapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author bekirc on 03.12.15.
 */
public class OldGameAdapter extends RecyclerView.Adapter<OldGameAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Game> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public OldGameAdapter(Context context,
                          ArrayList<Game> itemList,
                          OnItemClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public OldGameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_old_game, parent, false);
        ViewHolder.OnClickListener listener = new ViewHolder.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                if (OldGameAdapter.this.listener != null) {
                    OldGameAdapter.this.listener.onItemClick(position);
                }
            }

            @Override
            public void onItemLongClick(int position) {
                if (OldGameAdapter.this.listener != null) {
                    OldGameAdapter.this.listener.onItemLongClick(position);
                }
            }
        };
        return new OldGameAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Game item = itemList.get(position);
        holder.statusText.setText(item.getStatus());
        holder.dateText.setText(item.getDate());
        ArrayList<Player> playerList = item.getPlayerList();
        String playersName = "";
        for (Player player : playerList) {
            playersName = playersName + (TextUtils.isEmpty(playersName) ? "" : ", ") + player.getName();
        }
        holder.playersText.setText(playersName);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @InjectView(R.id.players_text)
        TextView playersText;
        @InjectView(R.id.date_text)
        TextView dateText;
        @InjectView(R.id.status_text)
        TextView statusText;

        private final OnClickListener listener;
        private int position;

        public ViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                listener.onItemLongClick(position);
                return true;
            }
            return false;
        }

        public interface OnClickListener {
            void onItemClick(int position);

            void onItemLongClick(int position);
        }
    }
}
