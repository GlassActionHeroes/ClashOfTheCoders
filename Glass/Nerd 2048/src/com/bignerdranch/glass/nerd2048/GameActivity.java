package com.bignerdranch.glass.nerd2048;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class GameActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/2048/index.html");
	}
}