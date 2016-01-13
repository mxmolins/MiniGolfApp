package com.example.bekirc.minigolfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bekirc.minigolfapp.R;
import com.example.bekirc.minigolfapp.ui.fragment.dialog.InfoDialogFragment;

import butterknife.OnClick;

/**
 * @author bekirc on 08.11.15.
 */
public class HomeActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
    }

    @OnClick(R.id.new_game_button)
    void onNewGameButtonClicked() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.continue_button)
    void onContinueGameButtonClicked() {
        Intent intent = new Intent(this, OldGameListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.info_button)
    void onInfoButtonClicked() {
        InfoDialogFragment fragment = InfoDialogFragment.newInstance();
        showDialogFragment(fragment, InfoDialogFragment.TAG);
    }

    @Override
    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}