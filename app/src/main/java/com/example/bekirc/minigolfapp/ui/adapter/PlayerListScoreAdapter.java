package com.example.bekirc.minigolfapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Player;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author bekirc on 03.12.15.
 */
public class PlayerListScoreAdapter extends RecyclerView.Adapter<PlayerListScoreAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Player> itemList;
    private OnItemClickListener listener;
    private int turnNumber;

    public PlayerListScoreAdapter(Context context,
                                  ArrayList<Player> itemList,
                                  OnItemClickListener listener, int turnNumber) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
        this.turnNumber = turnNumber;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue_game, parent, false);
        ViewHolder.OnClickListener listener = new ViewHolder.OnClickListener() {
            @Override
            public void onScoreUp(int position) {
                if (PlayerListScoreAdapter.this.listener != null) {
                    PlayerListScoreAdapter.this.listener.onScoreUp(position);
                }
            }

            @Override
            public void onScoreDown(int position) {
                if (PlayerListScoreAdapter.this.listener != null) {
                    PlayerListScoreAdapter.this.listener.onScoreDown(position);
                }
            }
        };
        return new PlayerListScoreAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Player item = itemList.get(position);
        String playerNoText = (position + 1) + "";
        holder.playerNoText.setText(playerNoText);
        holder.playerNameText.setText(item.getName());
        String playerScoreText = item.getScoreByTurnNumber(turnNumber) + "";
        holder.playerScoreText.setText(playerScoreText);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onScoreUp(int position);

        void onScoreDown(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.player_no)
        TextView playerNoText;
        @InjectView(R.id.player_name)
        TextView playerNameText;
        @InjectView(R.id.player_score)
        TextView playerScoreText;

        private final OnClickListener listener;
        private int position;

        private int score;

        public ViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.listener = listener;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @OnClick(R.id.player_score_up)
        public void onScoreUpClicked() {
            score = score + 1;
            String scoreText = score + "";
            playerScoreText.setText(scoreText);
            if (listener != null) {
                listener.onScoreUp(position);
            }
        }

        @OnClick(R.id.player_score_down)
        public void onScoreDownClicked() {
            score = score - 1;
            String scoreText = score + "";
            playerScoreText.setText(scoreText);
            if (listener != null) {
                listener.onScoreDown(position);
            }
        }

        public interface OnClickListener {
            void onScoreUp(int position);

            void onScoreDown(int position);
        }
    }
}
