package com.bignerdranch.glass.nerd2048;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.GridView;
import android.widget.TextView;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.util.Random;

public class GameActivity extends Activity {

    private static final String TAG = "GameActivity";
    private static final String KEY = "com.bignerdranch.glass";
    private static final String KEY_BEST = "com.bignerdranch.glass.best";

    private Random random = new Random();

    private GameAdapter mGameAdapter;
    private GestureDetector mGestureDetector;

    private TextView mScoreTextView;
    private TextView mBestTextView;

    private int mCurrentScore;
    private int mBestScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        setImmersive(true);

        mGameAdapter = new GameAdapter(this);
        mGestureDetector = createGestureDetector(this);

        GridView gridview = (GridView) findViewById(R.id.view_grid);
        gridview.setAdapter(mGameAdapter);

        mScoreTextView = (TextView) findViewById(R.id.text_score);
        mBestTextView = (TextView) findViewById(R.id.text_best);

        setupNewGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveBestScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_restart:
                restart();
                return true;
            case R.id.menu_quit:
                quit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void restart() {
        saveBestScore();
        setupNewGame();
    }

    private void quit() {
        finish();
    }

    private void setupNewGame() {
        mCurrentScore = 0;
        mBestScore = 0;
        loadBestScore();

        mScoreTextView.setText(getString(R.string.score, mCurrentScore));
        mBestTextView.setText(getString(R.string.best, mBestScore));

        setupStartingBoard();
    }

    private void setupStartingBoard() {
        mGameAdapter.clearImages();
        int r1 = getRandomPosition();
        int r2;
        do {
            r2 = getRandomPosition();
        } while (r1 == r2);
        mGameAdapter.setImage(r1, getRandomImage());
        mGameAdapter.setImage(r2, getRandomImage());
    }

    private int getRandomPosition() {
        return random.nextInt(16);
    }

    private int getRandomImage() {
        int quartile = random.nextInt(4);
        if (quartile == 0) {
            return R.drawable.image_4;
        } else {
            return R.drawable.image_2;
        }
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
                    actionDown();
                } else if (gesture == Gesture.SWIPE_UP) {
                    actionUp();
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    actionRight();
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    actionLeft();
                } else if (gesture == Gesture.TWO_SWIPE_DOWN) {
                    quit();
                } else if (gesture == Gesture.TAP) {
                    openOptionsMenu();
                }
                return true;
            }
        });
        return gestureDetector;
    }

    public void actionLeft() {
    }

    public void actionRight() {
    }

    public void actionUp() {
    }

    public void actionDown() {
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }

}