package com.bignerdranch.glass.nerd2048;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class GameActivity extends Activity {

    private static final String KEY = "com.bignerdranch.glass";
    private static final String KEY_BEST = "com.bignerdranch.glass.best";

    private ImageAdapter mImageAdapter;
    private GestureDetector mGestureDetector;

    private TextView mScoreTextView;
    private TextView mBestTextView;

    private int mBestScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        mImageAdapter = new ImageAdapter(this);
        mGestureDetector = createGestureDetector(this);

        GridView gridview = (GridView) findViewById(R.id.view_grid);
        gridview.setAdapter(mImageAdapter);

        mScoreTextView = (TextView) findViewById(R.id.text_score);
        mScoreTextView.setText(getString(R.string.score, 0));
        mBestTextView = (TextView) findViewById(R.id.text_best);
        mBestTextView.setText(getString(R.string.best, mBestScore));

        setImmersive(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBestScore();
    }

    @Override protected void onPause() {
        super.onPause();
        saveBestScore();
    }

    private void loadBestScore() {
        SharedPreferences prefs = this.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String bestScoreKey = KEY_BEST;
        mBestScore = prefs.getInt(bestScoreKey, 0);
    }


    private void saveBestScore() {
        SharedPreferences prefs = this.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_BEST, mBestScore).commit();
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.SWIPE_DOWN) {
                    mImageAdapter.actionDown();
                    return true;
                } else if (gesture == Gesture.SWIPE_UP) {
                    mImageAdapter.actionUp();
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    mImageAdapter.actionRight();
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    mImageAdapter.actionLeft();
                    return true;
                } else if (gesture == Gesture.TWO_SWIPE_DOWN) {
                    return true;
                }
                return true;
            }
        });
        return gestureDetector;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }

}