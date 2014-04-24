package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class GameActivity extends Activity {

	private static final String EXTRA_GAME_MODE = "GameActivity.GameMode";

	public static final Intent newIntent(Context context, GameMode gameMode) {
		Intent intent = new Intent(context, GameActivity.class);
		intent.putExtra(EXTRA_GAME_MODE, gameMode);

		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Toast.makeText(this, getIntent().getSerializableExtra(EXTRA_GAME_MODE).toString(), Toast.LENGTH_SHORT).show();

		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/doge2048/index.html");
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
				Toast.makeText(this, GameMode.SINGLE_PLAYER.toString(), Toast.LENGTH_SHORT).show();
				return true;
			case R.id.collaborative:
				Toast.makeText(this, GameMode.COLLABORATIVE.toString(), Toast.LENGTH_SHORT).show();
				return true;
		}
	}
}