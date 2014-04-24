package com.bignerdranch.android.nerd2048;

public enum GameMode {
	NONE, SINGLE_PLAYER, COLLABORATIVE;

	public static GameMode fromString(String gameMode) {
		if (gameMode == null) {
			return NONE;
		}

		if (gameMode.equals(SINGLE_PLAYER.toString())) {
			return SINGLE_PLAYER;
		} else if (gameMode.equals(COLLABORATIVE.toString())) {
			return COLLABORATIVE;
		} else {
			return NONE;
		}
	}
}
