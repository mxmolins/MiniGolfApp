package com.example.bekirc.minigolfapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.data.Player;
import com.example.bekirc.minigolfapp.ui.adapter.PlayerListScoreAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author bekirc on 03.12.15.
 */
public class GameFragment extends BaseFragment implements PlayerListScoreAdapter.OnItemClickListener {

    public static final String TAG = "gameFragment";
    public static final String TOTAL_TURN_NUMBER = "totalTurnNumber";
    public static final String GAME_TURN = "gameTurn";

    public static final int MIN_SHOT_NUMBER = 1;
    public static final int MAX_SHOT_NUMBER = 7;

    private int gameTurn;
    private int totalTurnNumber;
    private ArrayList<Player> playerList;

    private GameFragmentListener listener;

    @InjectView(R.id.hole_number)
    TextView holeNumberText;
    @InjectView(R.id.player_number)
    TextView playerNumberText;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    public interface GameFragmentListener {
        ArrayList<Player> getPlayerList();

        void setPlayerScores(int turn, ArrayList<Player> playerList);
    }

    public static GameFragment newInstance(int gameTurn, int totalTurnNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt(GAME_TURN, gameTurn);
        bundle.putInt(TOTAL_TURN_NUMBER, totalTurnNumber);
        GameFragment gameFragment = new GameFragment();
        gameFragment.setArguments(bundle);
        return gameFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        gameTurn = bundle.getInt(GAME_TURN, 0);
        totalTurnNumber = bundle.getInt(TOTAL_TURN_NUMBER, 0);
        playerList = listener.getPlayerList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_continue_game, container, false);

        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        PlayerListScoreAdapter adapter = new PlayerListScoreAdapter(getBaseActivity(), playerList, this, gameTurn);
        recyclerView.setAdapter(adapter);

        holeNumberText.setText(getString(R.string.HOLE_INFO, gameTurn, totalTurnNumber));
        playerNumberText.setText(getString(R.string.PLAYER_INFO, getString(R.string.per_number)));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GameFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Game Fragment listener is null!\n" + e);
        }
    }

    @Override
    public void onScoreUp(int position) {
        int playerScore = playerList.get(position).getScoreByTurnNumber(gameTurn);
        if (playerScore >= MAX_SHOT_NUMBER) {
            return;
        }
        playerList.get(position).increaseScoreByTurnNumber(gameTurn);
        listener.setPlayerScores(gameTurn, playerList);
    }

    @Override
    public void onScoreDown(int position) {
        int playerScore = playerList.get(position).getScoreByTurnNumber(gameTurn);
        if (playerScore <= MIN_SHOT_NUMBER) {
            return;
        }
        playerList.get(position).decreaseScoreByTurnNumber(gameTurn);
        listener.setPlayerScores(gameTurn, playerList);
    }
}
