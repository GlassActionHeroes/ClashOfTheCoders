package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends Activity {

	private static final String EXTRA_GAME_MODE = "GameActivity.ExtraGameMode";
	private static final String SAVED_GAME_MODE = "GameActivity.SavedGameMode";
	private static final String TAG_SINGLE_PLAYER_FRAGMENT = "GameActivity.TagSinglePlayerFragment";
	private static final String TAG_COLLABORATIVE_FRAGMENT = "GameActivity.CollaborativeFragment";

	public static final Intent newIntent(Context context, GameMode gameMode) {
		Intent intent = new Intent(context, GameActivity.class);
		intent.putExtra(EXTRA_GAME_MODE, gameMode);

		return intent;
	}

	private GameMode mGameMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		mGameMode = (GameMode) getIntent().getExtras().get(EXTRA_GAME_MODE);

		if (savedInstanceState != null) {
			mGameMode = (GameMode) savedInstanceState.get(SAVED_GAME_MODE);
		}

		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
			case R.id.single_player:
				if (mGameMode != GameMode.SINGLE_PLAYER) {
					mGameMode = GameMode.SINGLE_PLAYER;
					updateUI();
				}
				return true;
			case R.id.collaborative:
				if (mGameMode != GameMode.COLLABORATIVE) {
					mGameMode = GameMode.COLLABORATIVE;
					updateUI();
				}
				return true;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(SAVED_GAME_MODE, mGameMode);
	}

	private void updateUI() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		Fragment singlePlayerFragment = getFragmentManager().findFragmentByTag(TAG_SINGLE_PLAYER_FRAGMENT);
		Fragment collaborativeFragment = getFragmentManager().findFragmentByTag(TAG_COLLABORATIVE_FRAGMENT);

		if (mGameMode == GameMode.SINGLE_PLAYER) {
			if (singlePlayerFragment == null) {
				singlePlayerFragment = new SinglePlayerFragment();
				ft.add(R.id.container, singlePlayerFragment, TAG_SINGLE_PLAYER_FRAGMENT);
			}
			if (collaborativeFragment != null) {
				ft.hide(collaborativeFragment);
			}
			ft.show(singlePlayerFragment);
		} else if (mGameMode == GameMode.COLLABORATIVE) {
			if (collaborativeFragment == null) {
				collaborativeFragment = new CollaborativeFragment();
				ft.add(R.id.container, collaborativeFragment, TAG_COLLABORATIVE_FRAGMENT);
			}
			if (singlePlayerFragment != null) {
				ft.hide(singlePlayerFragment);
			}
			ft.show(collaborativeFragment);
		}

		ft.commit();
	}
}