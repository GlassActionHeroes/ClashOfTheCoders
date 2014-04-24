package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		webView.loadUrl("file:///android_asset/2048/index.html");
	}
}