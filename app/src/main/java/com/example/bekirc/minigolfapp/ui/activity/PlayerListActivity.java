package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;
import com.example.bekirc.minigolfapp.misc.Util;
import com.example.bekirc.minigolfapp.ui.adapter.PlayerListNameAdapter;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author bekirc on 04.12.15.
 */
public class PlayerListActivity extends BaseActionBarActivity {

    public static final String GAME = "game";

    private Game game;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player_list);

        setTitle(R.string.NEW_GAME_SETTINGS);

        Bundle bundle = getIntent().getExtras();
        game = bundle.getParcelable(GAME);
        ArrayList<Player> playerList = game.getPlayerList();

        SpacesItemDecoration decoration = new SpacesItemDecoration(Util.dpToPx(8), 0, false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlayerListNameAdapter adapter = new PlayerListNameAdapter(this, playerList);
        recyclerView.setAdapter(adapter);
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

    @OnClick(R.id.continue_to_new_game)
    void onContinueButtonClicked() {
        if (playerNamesAreReady()) {
            Intent intent = new Intent(this, ContinueGameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ContinueGameActivity.GAME, game);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private boolean playerNamesAreReady() {
        ArrayList<Player> playerList = game.getPlayerList();
        for (Player player : playerList) {
            if (TextUtils.isEmpty(player.getName())) {
                Toast.makeText(this, getString(R.string.PROVIDE_NAME_FOR_PLAYER), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int verticalSpace;
        private int horizontalSpace;
        private boolean rightToLeft;

        public SpacesItemDecoration(int verticalSpace, int horizontalSpace, boolean rightToLeft) {
            this.verticalSpace = verticalSpace;
            this.horizontalSpace = horizontalSpace;
            this.rightToLeft = rightToLeft;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpace;
            int position = parent.getChildAdapterPosition(view);
            if (position % 2 == 0) {
                if (rightToLeft) {
                    outRect.left = horizontalSpace;
                } else {
                    outRect.right = horizontalSpace;
                }
            } else {
                if (rightToLeft) {
                    outRect.right = horizontalSpace;
                } else {
                    outRect.left = horizontalSpace;
                }
            }
        }
    }
}
