package com.bignerdranch.glass.nerd2048;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.GridView;
import android.widget.TextView;
import com.bignerdranch.glass.nerd2048.GameAdapter.Mode;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.util.Random;

public class GameActivity extends Activity {

    private static final String TAG = "GameActivity";
    private static final String KEY = "com.bignerdranch.glass";
    private static final String KEY_BEST = "com.bignerdranch.glass.best";
    private static final String KEY_MODE = "com.bignerdranch.glass.mode";

    private Random random = new Random();

    private GameAdapter mGameAdapter;
    private GestureDetector mGestureDetector;

    private TextView mScoreTextView;
    private TextView mBestTextView;
    private TextView mGameOverTextView;

    private TextView mValuesTextView;
    private TextView mNerd_2_TextView;
    private TextView mNerd_4_TextView;
    private TextView mNerd_8_TextView;
    private TextView mNerd_16_TextView;
    private TextView mNerd_32_TextView;
    private TextView mNerd_64_TextView;
    private TextView mNerd_128_TextView;
    private TextView mNerd_256_TextView;
    private TextView mNerd_512_TextView;
    private TextView mNerd_1024_TextView;
    private TextView mNerd_2048_TextView;

    private int mCurrentScore;
    private int mBestScore;
    private boolean isNerdMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        setImmersive(true);

        loadMode();
        if (isNerdMode) {
            mGameAdapter = new GameAdapter(this, Mode.NERD);
        } else {
            mGameAdapter = new GameAdapter(this, Mode.NUMBER);
        }

        GridView gridView = (GridView) findViewById(R.id.view_grid);
        gridView.setAdapter(mGameAdapter);
        mGestureDetector = createGestureDetector(this);

        mScoreTextView = (TextView) findViewById(R.id.text_score);
        mBestTextView = (TextView) findViewById(R.id.text_best);
        mGameOverTextView = (TextView) findViewById(R.id.text_gameover);

        mValuesTextView = (TextView) findViewById(R.id.text_values);
        mNerd_2_TextView = (TextView) findViewById(R.id.text_number_2);
        mNerd_4_TextView = (TextView) findViewById(R.id.text_number_4);
        mNerd_8_TextView = (TextView) findViewById(R.id.text_number_8);
        mNerd_16_TextView = (TextView) findViewById(R.id.text_number_16);
        mNerd_32_TextView = (TextView) findViewById(R.id.text_number_32);
        mNerd_64_TextView = (TextView) findViewById(R.id.text_number_64);
        mNerd_128_TextView = (TextView) findViewById(R.id.text_number_128);
        mNerd_256_TextView = (TextView) findViewById(R.id.text_number_256);
        mNerd_512_TextView = (TextView) findViewById(R.id.text_number_512);
        mNerd_1024_TextView = (TextView) findViewById(R.id.text_number_1024);
        mNerd_2048_TextView = (TextView) findViewById(R.id.text_number_2048);

        setupNewGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveBestScore();
        saveMode();
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
            case R.id.menu_mode:
                switchMode();
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
        saveMode();
        setupNewGame();
    }

    private void quit() {
        finish();
    }

    private void switchMode() {
        isNerdMode = !isNerdMode;
        if (isNerdMode) {
            mGameAdapter.setMode(Mode.NERD);
        } else {
            mGameAdapter.setMode(Mode.NUMBER);
        }
        mGameAdapter.convertMode();
        updateTextViews();
    }

    private void updateTextViews() {
        if (isNerdMode == false) {
            mValuesTextView.setText(R.string.values);
            mNerd_2_TextView.setText(R.string.number_2);
            mNerd_4_TextView.setText(R.string.number_4);
            mNerd_8_TextView.setText(R.string.number_8);
            mNerd_16_TextView.setText(R.string.number_16);
            mNerd_32_TextView.setText(R.string.number_32);
            mNerd_64_TextView.setText(R.string.number_64);
            mNerd_128_TextView.setText(R.string.number_128);
            mNerd_256_TextView.setText(R.string.number_256);
            mNerd_512_TextView.setText(R.string.number_512);
            mNerd_1024_TextView.setText(R.string.number_1024);
            mNerd_2048_TextView.setText(R.string.number_2048);
        } else {
            mValuesTextView.setText(R.string.nerds);
            mNerd_2_TextView.setText(R.string.nerd_2);
            mNerd_4_TextView.setText(R.string.nerd_4);
            mNerd_8_TextView.setText(R.string.nerd_8);
            mNerd_16_TextView.setText(R.string.nerd_16);
            mNerd_32_TextView.setText(R.string.nerd_32);
            mNerd_64_TextView.setText(R.string.nerd_64);
            mNerd_128_TextView.setText(R.string.nerd_128);
            mNerd_256_TextView.setText(R.string.nerd_256);
            mNerd_512_TextView.setText(R.string.nerd_512);
            mNerd_1024_TextView.setText(R.string.nerd_1024);
            mNerd_2048_TextView.setText(R.string.nerd_2048);
        }
    }

    private void gameover() {
        mGameOverTextView.setVisibility(View.VISIBLE);
    }

    private void setupNewGame() {
        if (isNerdMode) {
            switchMode();
        }

        mGameOverTextView.setVisibility(View.INVISIBLE);

        mCurrentScore = 0;
        mBestScore = 0;
        loadBestScore();

        mScoreTextView.setText(getString(R.string.score, mCurrentScore));
        mBestTextView.setText(getString(R.string.best, mBestScore));

        setupStartingBoard();
        clearNerds();
        updateNerds();
    }

    private void setupStartingBoard() {
        mGameAdapter.clearImages();
        mGameAdapter.setImage(getRandomPosition(), getRandomImage());
        mGameAdapter.setImage(getRandomPosition(), getRandomImage());
    }

    private int getRandomPosition() {
        int r;
        do {
            r = random.nextInt(16);
        } while (!isEmpty(r));
        return r;
    }

    private int getRandomImage() {
        int quartile = random.nextInt(4);
        if (quartile == 0) {
            return mGameAdapter.getDrawable_4();
        } else {
            return mGameAdapter.getDrawable_2();
        }
    }

    private boolean isEmpty(int position) {
        return mGameAdapter.getItemIdInt(position) == mGameAdapter.getDrawable_None();
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

    private void loadMode() {
        SharedPreferences prefs = this.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String modeKey = KEY_MODE;
        isNerdMode = prefs.getBoolean(modeKey, false);
    }

    private void saveMode() {
        SharedPreferences prefs = this.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_MODE, isNerdMode).commit();
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.SWIPE_DOWN) {
                    if (mGameOverTextView.getVisibility() == View.VISIBLE) {
                        quit();
                    }
                    if (isDownValid()) {
                        actionDown();
                    }
                } else if (gesture == Gesture.SWIPE_UP) {
                    if (isUpValid()) {
                        actionUp();
                    }
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    if (isRightValid()) {
                        actionRight();
                    }
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    if (isLeftValid()) {
                        actionLeft();
                    }
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

    private void actionLeft() {
        int points = moveLeft();
        endTurn(points);
    }

    private void actionRight() {
        int points = moveRight();
        endTurn(points);
    }

    private void actionUp() {
        int points = moveUp();
        endTurn(points);
    }

    private void actionDown() {
        int points = moveDown();
        endTurn(points);
    }

    private void endTurn(int scoreUpdate) {
        updateScore(scoreUpdate);
        mGameAdapter.setImage(getRandomPosition(), getRandomImage());
        updateNerds();
        checkForValidMoves();
    }

    private void checkForValidMoves() {
        if (!isLeftValid() && !isRightValid() && !isUpValid() && !isDownValid()) {
            gameover();
        }
    }

    private boolean isLeftValid() {
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            if (canMoveLeft(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightValid() {
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            if (canMoveRight(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpValid() {
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            if (canMoveUp(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDownValid() {
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            if (canMoveDown(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveLeft(int position) {
        if (position % 4 == 0 || mGameAdapter.getItemIdInt(position) == mGameAdapter.getDrawable_None()) {
            return false;
        } else {
            int leftPosition = position - 1;
            if (mGameAdapter.getItemIdInt(leftPosition) == mGameAdapter.getDrawable_None() || mGameAdapter.getItemIdInt(leftPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveLeft(leftPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(int position) {
        if (position % 4 == 3 || mGameAdapter.getItemIdInt(position) == mGameAdapter.getDrawable_None()) {
            return false;
        } else {
            int rightPosition = position + 1;
            if (mGameAdapter.getItemIdInt(rightPosition) == mGameAdapter.getDrawable_None() || mGameAdapter.getItemIdInt(rightPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveRight(rightPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveUp(int position) {
        if (position <= 3 || mGameAdapter.getItemIdInt(position) == mGameAdapter.getDrawable_None()) {
            return false;
        } else {
            int upPosition = position - 4;
            if (mGameAdapter.getItemIdInt(upPosition) == mGameAdapter.getDrawable_None() || mGameAdapter.getItemIdInt(upPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveUp(upPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveDown(int position) {
        if (position >= 12 || mGameAdapter.getItemIdInt(position) == mGameAdapter.getDrawable_None()) {
            return false;
        } else {
            int downPosition = position + 4;
            if (mGameAdapter.getItemIdInt(downPosition) == mGameAdapter.getDrawable_None() || mGameAdapter.getItemIdInt(downPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveDown(downPosition)) {
                return true;
            }
        }
        return false;
    }

    private int moveLeft() {
        int points = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int position = j * 4 + i;
                points += moveLeft(position);
            }
        }
        return points;
    }

    private int moveLeft(int position) {
        if (!canMoveLeft(position)) {
            return 0;
        }
        int image = mGameAdapter.getItemIdInt(position);
        int leftPosition = position - 1;
        if (mGameAdapter.getItemIdInt(leftPosition) == mGameAdapter.getDrawable_None()) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(leftPosition, image);
            return moveLeft(leftPosition);
        } else if (mGameAdapter.getItemIdInt(leftPosition) == image) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(leftPosition, mGameAdapter.getNextFromDrawable(image));
            return mGameAdapter.getNumberFromDrawable(image) * 2;
        }
        return 0;
    }

    private int moveRight() {
        int points = 0;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                int position = j * 4 + i;
                points += moveRight(position);
            }
        }
        return points;
    }

    private int moveRight(int position) {
        if (!canMoveRight(position)) {
            return 0;
        }
        int image = mGameAdapter.getItemIdInt(position);
        int rightPosition = position + 1;
        if (mGameAdapter.getItemIdInt(rightPosition) == mGameAdapter.getDrawable_None()) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(rightPosition, image);
            return moveRight(rightPosition);
        } else if (mGameAdapter.getItemIdInt(rightPosition) == image) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(rightPosition, mGameAdapter.getNextFromDrawable(image));
            return mGameAdapter.getNumberFromDrawable(image) * 2;
        }
        return 0;
    }

    private int moveUp() {
        int points = 0;
        for (int i = 0; i < 16; i++) {
            points += moveUp(i);
        }
        return points;
    }

    private int moveUp(int position) {
        if (!canMoveUp(position)) {
            return 0;
        }
        int image = mGameAdapter.getItemIdInt(position);
        int upPosition = position - 4;
        if (mGameAdapter.getItemIdInt(upPosition) == mGameAdapter.getDrawable_None()) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(upPosition, image);
            return moveUp(upPosition);
        } else if (mGameAdapter.getItemIdInt(upPosition) == image) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(upPosition, mGameAdapter.getNextFromDrawable(image));
            return mGameAdapter.getNumberFromDrawable(image) * 2;
        }
        return 0;
    }

    private int moveDown() {
        int points = 0;
        for (int i = 15; i >= 0; i--) {
            points += moveDown(i);
        }
        return points;
    }

    private int moveDown(int position) {
        if (!canMoveDown(position)) {
            return 0;
        }
        int image = mGameAdapter.getItemIdInt(position);
        int downPosition = position + 4;
        if (mGameAdapter.getItemIdInt(downPosition) == mGameAdapter.getDrawable_None()) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(downPosition, image);
            return moveDown(downPosition);
        } else if (mGameAdapter.getItemIdInt(downPosition) == image) {
            mGameAdapter.setImage(position, mGameAdapter.getDrawable_None());
            mGameAdapter.setImage(downPosition, mGameAdapter.getNextFromDrawable(image));
            return mGameAdapter.getNumberFromDrawable(image) * 2;
        }
        return 0;
    }

    private void clearNerds() {
        mNerd_2048_TextView.setVisibility(View.INVISIBLE);
        mNerd_1024_TextView.setVisibility(View.INVISIBLE);
        mNerd_512_TextView.setVisibility(View.INVISIBLE);
        mNerd_256_TextView.setVisibility(View.INVISIBLE);
        mNerd_128_TextView.setVisibility(View.INVISIBLE);
        mNerd_64_TextView.setVisibility(View.INVISIBLE);
        mNerd_32_TextView.setVisibility(View.INVISIBLE);
        mNerd_16_TextView.setVisibility(View.INVISIBLE);
        mNerd_8_TextView.setVisibility(View.INVISIBLE);
        mNerd_4_TextView.setVisibility(View.INVISIBLE);
        mNerd_2_TextView.setVisibility(View.INVISIBLE);
    }

    private void updateNerds() {
        int nerd = getHighestRankingNerd();
        if (nerd == mGameAdapter.getDrawable_2048()) {
            mNerd_2048_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_1024()) {
            mNerd_1024_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_512()) {
            mNerd_512_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_256()) {
            mNerd_256_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_128()) {
            mNerd_128_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_64()) {
            mNerd_64_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_32()) {
            mNerd_32_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_16()) {
            mNerd_16_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_8()) {
            mNerd_8_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_4()) {
            mNerd_4_TextView.setVisibility(View.VISIBLE);
            mNerd_2_TextView.setVisibility(View.VISIBLE);
        } else if (nerd == mGameAdapter.getDrawable_2()) {
            mNerd_2_TextView.setVisibility(View.VISIBLE);
        }
    }

    private int getHighestRankingNerd() {
        int nerd = mGameAdapter.getDrawable_None();
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            nerd = getBiggerNerd(nerd, mGameAdapter.getItemIdInt(i));
        }
        return nerd;
    }

    private int getBiggerNerd(int nerd1, int nerd2) {
        if (nerd1 == nerd2) {
            return nerd1;
        }
        if (nerd1 == mGameAdapter.getDrawable_8192()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_8192()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_4096()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_4096()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_2048()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_2048()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_1024()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_1024()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_512()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_512()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_256()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_256()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_128()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_128()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_64()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_64()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_32()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_32()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_16()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_16()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_8()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_8()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_4()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_4()) {
            return nerd2;
        }
        if (nerd1 == mGameAdapter.getDrawable_2()) {
            return nerd1;
        } else if (nerd2 == mGameAdapter.getDrawable_2()) {
            return nerd2;
        }
        return nerd1;
    }

    private void updateScore(int addition) {
        mCurrentScore += addition;
        mScoreTextView.setText(Integer.toString(mCurrentScore));
        if (mCurrentScore > mBestScore) {
            mBestScore = mCurrentScore;
            mBestTextView.setText(Integer.toString(mBestScore));
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector != null && mGestureDetector.onMotionEvent(event);
    }

}