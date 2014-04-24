package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class NerdActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/2048/index.html");
	}
}
