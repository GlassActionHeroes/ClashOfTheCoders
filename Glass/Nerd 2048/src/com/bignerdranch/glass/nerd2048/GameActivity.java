package com.bignerdranch.glass.nerd2048;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        GridView gridview = (GridView) findViewById(R.id.view_grid);
        gridview.setAdapter(new ImageAdapter(this));
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onPause() {
        super.onPause();
    }
}