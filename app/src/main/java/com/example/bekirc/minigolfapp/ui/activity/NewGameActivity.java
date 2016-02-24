package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Game;
import com.example.bekirc.minigolfapp.data.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author bekirc on 22.11.15.
 */
public class NewGameActivity extends BaseActionBarActivity {

    private static final int MIN_PLAYER_NUMBER = 1;
    private static final int DEFAULT_PLAYER_NUMBER = 4;
    private static final int DEFAULT_HOLE_NUMBER = 15;
    private static final int MIN_HOLE_NUMBER = 1;

    private static final String PLAYER_NUMBER = "playerNumber";
    private static final String HOLE_NUMBER = "holeNumber";

    private int playerNumber = DEFAULT_PLAYER_NUMBER;
    private int holeNumber = DEFAULT_HOLE_NUMBER;

    @InjectView(R.id.player_number_text)
    TextView playerNumberText;
    @InjectView(R.id.hole_number_text)
    TextView holeNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        if (savedInstanceState != null) {
            playerNumber = savedInstanceState.getInt(PLAYER_NUMBER, DEFAULT_PLAYER_NUMBER);
            holeNumber = savedInstanceState.getInt(HOLE_NUMBER, DEFAULT_HOLE_NUMBER);
        }

        setTitle(R.string.NEW_GAME_SETTINGS);

        playerNumberText.setText(playerNumber + "");
        holeNumberText.setText(holeNumber + "");
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
    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_white);
            //toolbar.setLogo(R.drawable.ic_logo);
        }
    }

    @OnClick(R.id.player_number_up_button)
    void onIncreasePlayerNumberclicked() {
        playerNumber = playerNumber + 1;
        playerNumberText.setText(playerNumber + "");
    }

    @OnClick(R.id.player_number_down_button)
    void onDecreasePlayerNumberclicked() {
        if (playerNumber <= MIN_PLAYER_NUMBER) {
            return;
        }
        playerNumber = playerNumber - 1;
        playerNumberText.setText(playerNumber + "");
    }

    @OnClick(R.id.hole_number_up_button)
    void onIncreaseHoleNumberclicked() {
        holeNumber = holeNumber + 1;
        holeNumberText.setText(holeNumber + "");
    }

    @OnClick(R.id.hole_number_down_button)
    void onDecreaseHoleNumberclicked() {
        if (holeNumber <= MIN_HOLE_NUMBER) {
            return;
        }
        holeNumber = holeNumber - 1;
        holeNumberText.setText(holeNumber + "");
    }

    @OnClick(R.id.continue_to_new_game)
    void onContinueButtonClicked() {
        Intent intent = new Intent(this, PlayerListActivity.class);
        Bundle bundle = new Bundle();
        Game game = createNewGame();
        bundle.putParcelable(PlayerListActivity.GAME, game);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private Game createNewGame() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        long time = System.currentTimeMillis();
        Game game = new Game(formattedDate, holeNumber, time);
        int i = 0;
        while (i < playerNumber) {
            game.addPlayer(new Player("", holeNumber));
            i++;
        }
        return game;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYER_NUMBER, playerNumber);
        outState.putInt(HOLE_NUMBER, holeNumber);
    }
}
