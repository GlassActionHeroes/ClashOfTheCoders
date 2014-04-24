package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GameMode gameMode = PreferencesManager.getGameMode(this);

		switch (gameMode) {
			default:
			case NONE:
				Intent modeSelectionIntent = new Intent(this, ModeSelectionActivity.class);
				startActivity(modeSelectionIntent);
				break;
			case SINGLE_PLAYER:
			case COLLABORATIVE:
				Intent gameIntent = GameActivity.newIntent(this, gameMode);
				startActivity(gameIntent);
				break;
		}

		finish();
	}
}