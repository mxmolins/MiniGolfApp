package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.misc.GameSaveSharePrefUtil;
import com.example.bekirc.minigolfapp.ui.adapter.OldGameAdapter;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * @author bekirc on 22.11.15.
 */
public class OldGameListActivity extends BaseActionBarActivity implements OldGameAdapter.OnItemClickListener {

    private ArrayList<Game> gameList;
    private OldGameAdapter adapter;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_old_game_list);

        setTitle(R.string.CONTINUE_GAME);

        gameList = GameSaveSharePrefUtil.getGameList(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OldGameAdapter(this, gameList, this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_white);
            //toolbar.setLogo(R.drawable.ic_logo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Game game = gameList.get(position);
        if (game.getCurrentTurn() >= game.getTotalTurnNumber()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(ContinueGameActivity.GAME, game);
        Intent intent = new Intent(this, ContinueGameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        GameSaveSharePrefUtil.deleteGame(this, gameList.get(position));
        gameList.remove(position);
        adapter.notifyDataSetChanged();
    }
}
