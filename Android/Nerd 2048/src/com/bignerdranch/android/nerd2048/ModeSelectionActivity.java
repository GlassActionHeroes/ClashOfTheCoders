package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModeSelectionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_selection);
	}

	public void singlePlayerClicked(View v) {
		startGameActivity(GameMode.SINGLE_PLAYER);
	}

	public void collaborativeClicked(View v) {
		startGameActivity(GameMode.COLLABORATIVE);
	}

	private void startGameActivity(GameMode gameMode) {
		PreferencesManager.setGameMode(this, gameMode);
		Intent intent = GameActivity.newIntent(this, gameMode);
		startActivity(intent);
		finish();
	}
}
