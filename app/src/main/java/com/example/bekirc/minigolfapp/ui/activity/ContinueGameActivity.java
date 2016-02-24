package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;
import com.example.bekirc.minigolfapp.misc.GameSaveSharePrefUtil;
import com.example.bekirc.minigolfapp.ui.fragment.BaseFragment;
import com.example.bekirc.minigolfapp.ui.fragment.GameFragment;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.PreviousGameDialogFragment;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.PreviousScreenDialogFragment;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.ResultDialogFragment;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * @author bekirc on 03.12.15.
 */
public class ContinueGameActivity extends BaseActionBarActivity implements GameFragment.GameFragmentListener,
        PreviousGameDialogFragment.PreviousGameDialogFragmentListener,
        PreviousScreenDialogFragment.PreviousScreenDialogFragmentListener {

    public static final String GAME = "game";

    private Game game;
    private int currentGameTurn = Game.MIN_GAME_TURN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_continue_game);

        setTitle(R.string.app_name);

        Bundle bundle = getIntent().getExtras();
        game = bundle.getParcelable(GAME);

        if (savedInstanceState != null) {
            game = savedInstanceState.getParcelable(GAME);
        }

        if (game != null) {
            currentGameTurn = game.getCurrentTurn();
            game.setStatus(getString(R.string.TURN_STATUS, currentGameTurn));
        }

        goToCorrectGameTurnPage(currentGameTurn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_continue_game, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_result) {
            showResultDialog();
            return true;
        }

        return true;
    }

    private void showResultDialog() {
        ResultDialogFragment fragment = ResultDialogFragment.newInstance(game.getPlayerList(), currentGameTurn);
        showDialogFragment(fragment, ResultDialogFragment.TAG);
    }

    private void goToCorrectGameTurnPage(int targetGameTurn) {
        int turnController = 0;
        while (turnController <= targetGameTurn) {
            onSwitchFragment(GameFragment.newInstance(turnController, game.getTotalTurnNumber()), GameFragment.TAG + (turnController + ""));
            turnController++;
        }
    }

    @Override
    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_white);
            //toolbar.setLogo(R.drawable.ic_logo);
        }
    }

    @Override
    public ArrayList<Player> getPlayerList() {
        return game.getPlayerList();
    }

    @Override
    public void setPlayerScores(int turn, ArrayList<Player> playerListFromFragment) {
        ArrayList<Player> playerList = game.getPlayerList();
        int i = 0;
        for (Player player : playerListFromFragment) {
            playerList.get(i).setScoreByTurnNumber(turn, player.getScoreByTurnNumber(turn));
            i++;
        }
    }

    private void onSwitchFragment(BaseFragment fragment, String fragmentTag) {
        onSwitchFragment(fragment, fragmentTag, true);
    }

    private void onSwitchFragment(BaseFragment fragment, String fragmentTag, Boolean addToBackStack) {
        showFragment(R.id.game_fragment_container, fragment, fragmentTag, addToBackStack);
    }

    @OnClick(R.id.continue_to_next_hole)
    void onContinueButtonClicked() {
        currentGameTurn++;
        game.setCurrentTurn(currentGameTurn);
        if (currentGameTurn > game.getTotalTurnNumber()) {
            game.setStatus(getString(R.string.GAME_FINISHED));
            Intent intent = new Intent(this, ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ResultActivity.GAME, game);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            game.setStatus(getString(R.string.TURN_STATUS, currentGameTurn));
            onSwitchFragment(GameFragment.newInstance(currentGameTurn, game.getTotalTurnNumber()), GameFragment.TAG + (currentGameTurn + ""));
        }
    }

    @Override
    public void onBackPressed() {
        if (currentGameTurn > Game.MIN_GAME_TURN) {
            int previousGameTurn = currentGameTurn - 1;
            showDialogFragment(PreviousGameDialogFragment.newInstance(previousGameTurn), PreviousGameDialogFragment.TAG);
        } else {
            showDialogFragment(PreviousScreenDialogFragment.newInstance(), PreviousScreenDialogFragment.TAG);
        }
    }

    @Override
    public void onOkayButtonClicked() {
        currentGameTurn--;
        game.setCurrentTurn(currentGameTurn);
        onSwitchFragment(GameFragment.newInstance(currentGameTurn, game.getTotalTurnNumber()), GameFragment.TAG + (currentGameTurn + ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameSaveSharePrefUtil.setGameList(this, game);
    }

    @Override
    public void onPreviousScreenOkayButtonClicked() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(GAME, game);
    }
}
