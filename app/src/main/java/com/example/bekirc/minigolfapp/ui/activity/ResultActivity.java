package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;
import com.example.bekirc.minigolfapp.ui.adapter.ResultAdapter;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.GameFinishedDialogFragment;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.RecommendOnPlayDialogFragment;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.SuggestionsDialogFragment;
import com.example.bekirc.minigolfapp.ui.misc.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * @author bekirc on 03.12.15.
 */
public class ResultActivity extends BaseActionBarActivity implements GameFinishedDialogFragment.GameFinishedDialogFragmentListener,
        RecommendOnPlayDialogFragment.RecommendOnPlayDialogFragmentListener,
        SuggestionsDialogFragment.SuggestionsDialogFragmentListener {

    public static final String GAME = "game";

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerview;
    @InjectView(R.id.winner_text)
    TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setTitle(R.string.app_name);

        Bundle bundle = getIntent().getExtras();
        Game game = bundle.getParcelable(GAME);
        ArrayList<Player> playerList = game.getPlayerList();

        winnerText.setText(getWinnerName(playerList));

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, null));
        ResultAdapter adapter = new ResultAdapter(this, playerList, game.getTotalTurnNumber());
        recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_close) {
            showDialogFragment(GameFinishedDialogFragment.newInstance(), GameFinishedDialogFragment.TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getWinnerName(ArrayList<Player> playerList) {
        String winnerName = "";
        int winnerScore = Integer.MAX_VALUE;
        for (Player player : playerList) {
            if (player.getScore() < winnerScore) {
                winnerScore = player.getScore();
                winnerName = player.getName();
            }
        }
        return winnerName;
    }

    @Override
    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_white);
            //toolbar.setLogo(R.drawable.ic_logo);
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onUserNotEnjoyedPressed() {
        showDialogFragment(SuggestionsDialogFragment.newInstance(), SuggestionsDialogFragment.TAG);
    }

    @Override
    public void onUserEnjoyedPressed() {
        showDialogFragment(RecommendOnPlayDialogFragment.newInstance(), RecommendOnPlayDialogFragment.TAG);
    }

    @Override
    public void onDialogCanceled() {
        goToHomeActivity();
    }

    @Override
    public void onRecommendOnPlayPressed() {
        //todo
        goToHomeActivity();
    }

    @Override
    public void onSuggestionSendButtonPressed(String suggestion) {
        //todo
        goToHomeActivity();
    }
}
