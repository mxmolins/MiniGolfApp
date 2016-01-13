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

/**
 * @author bekirc on 03.12.15.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Player> itemList;

    public ResultAdapter(Context context, ArrayList<Player> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);
        return new ResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Player item = itemList.get(position);
        String playerNoText = (position + 1) + "";
        holder.playerNoText.setText(playerNoText);
        holder.playerNameText.setText(item.getName());
        String playerScoreText = item.getScore() + "";
        holder.playerScoreText.setText(playerScoreText);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.player_no)
        TextView playerNoText;
        @InjectView(R.id.player_name)
        TextView playerNameText;
        @InjectView(R.id.player_score)
        TextView playerScoreText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
