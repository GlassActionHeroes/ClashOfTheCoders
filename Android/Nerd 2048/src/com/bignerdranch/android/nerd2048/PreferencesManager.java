package com.bignerdranch.android.nerd2048;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

	private static final String PREFS_NAME = "com.bignerdranch.android.nerd2048";
	private static final String PREF_GAME_MODE = "InitActivity.GameMode";

	public static GameMode getGameMode(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String gameMode = settings.getString(PREF_GAME_MODE, null);

		return GameMode.fromString(gameMode);
	}

	public static void setGameMode(Context context, GameMode gameMode) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_GAME_MODE, gameMode.toString());
		editor.commit();
	}
}
