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

    private GridView mGridView;
    private GameAdapter mGameAdapter;
    private GestureDetector mGestureDetector;

    private TextView mScoreTextView;
    private TextView mBestTextView;
    private TextView mGameOverTextView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        setImmersive(true);

        mGameAdapter = new GameAdapter(this);
        mGestureDetector = createGestureDetector(this);

        mGridView = (GridView) findViewById(R.id.view_grid);
        mGridView.setAdapter(mGameAdapter);

        mScoreTextView = (TextView) findViewById(R.id.text_score);
        mBestTextView = (TextView) findViewById(R.id.text_best);
        mGameOverTextView = (TextView) findViewById(R.id.text_gameover);

        mNerd_2_TextView = (TextView) findViewById(R.id.text_nerd_2);
        mNerd_4_TextView = (TextView) findViewById(R.id.text_nerd_4);
        mNerd_8_TextView = (TextView) findViewById(R.id.text_nerd_8);
        mNerd_16_TextView = (TextView) findViewById(R.id.text_nerd_16);
        mNerd_32_TextView = (TextView) findViewById(R.id.text_nerd_32);
        mNerd_64_TextView = (TextView) findViewById(R.id.text_nerd_64);
        mNerd_128_TextView = (TextView) findViewById(R.id.text_nerd_128);
        mNerd_256_TextView = (TextView) findViewById(R.id.text_nerd_256);
        mNerd_512_TextView = (TextView) findViewById(R.id.text_nerd_512);
        mNerd_1024_TextView = (TextView) findViewById(R.id.text_nerd_1024);
        mNerd_2048_TextView = (TextView) findViewById(R.id.text_nerd_2048);

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

    private void gameover() {
        mGameOverTextView.setVisibility(View.VISIBLE);
    }

    private void setupNewGame() {
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
            return R.drawable.image_4;
        } else {
            return R.drawable.image_2;
        }
    }

    private boolean isEmpty(int position) {
        if (mGameAdapter.getItemIdInt(position) == R.drawable.image_none) {
            return true;
        }
        return false;
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
        if (position % 4 == 0 || mGameAdapter.getItemIdInt(position) == R.drawable.image_none) {
            return false;
        } else {
            int leftPosition = position - 1;
            if (mGameAdapter.getItemIdInt(leftPosition) == R.drawable.image_none || mGameAdapter.getItemIdInt(leftPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveLeft(leftPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(int position) {
        if (position % 4 == 3 || mGameAdapter.getItemIdInt(position) == R.drawable.image_none) {
            return false;
        } else {
            int rightPosition = position + 1;
            if (mGameAdapter.getItemIdInt(rightPosition) == R.drawable.image_none || mGameAdapter.getItemIdInt(rightPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveRight(rightPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveUp(int position) {
        if (position <= 3 || mGameAdapter.getItemIdInt(position) == R.drawable.image_none) {
            return false;
        } else {
            int upPosition = position - 4;
            if (mGameAdapter.getItemIdInt(upPosition) == R.drawable.image_none || mGameAdapter.getItemIdInt(upPosition) == mGameAdapter.getItemIdInt(position)) {
                return true;
            }
            if (canMoveUp(upPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveDown(int position) {
        if (position >= 12 || mGameAdapter.getItemIdInt(position) == R.drawable.image_none) {
            return false;
        } else {
            int downPosition = position + 4;
            if (mGameAdapter.getItemIdInt(downPosition) == R.drawable.image_none || mGameAdapter.getItemIdInt(downPosition) == mGameAdapter.getItemIdInt(position)) {
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
        if (mGameAdapter.getItemIdInt(leftPosition) == R.drawable.image_none) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(leftPosition, image);
            return moveLeft(leftPosition);
        } else if (mGameAdapter.getItemIdInt(leftPosition) == image) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(leftPosition, getNextFromDrawable(image));
            int points = getNumberFromDrawable(image) * 2;
            return points;
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
        if (mGameAdapter.getItemIdInt(rightPosition) == R.drawable.image_none) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(rightPosition, image);
            return moveRight(rightPosition);
        } else if (mGameAdapter.getItemIdInt(rightPosition) == image) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(rightPosition, getNextFromDrawable(image));
            int points = getNumberFromDrawable(image) * 2;
            return points;
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
        if (mGameAdapter.getItemIdInt(upPosition) == R.drawable.image_none) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(upPosition, image);
            return moveUp(upPosition);
        } else if (mGameAdapter.getItemIdInt(upPosition) == image) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(upPosition, getNextFromDrawable(image));
            int points = getNumberFromDrawable(image) * 2;
            return points;
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
        if (mGameAdapter.getItemIdInt(downPosition) == R.drawable.image_none) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(downPosition, image);
            return moveDown(downPosition);
        } else if (mGameAdapter.getItemIdInt(downPosition) == image) {
            mGameAdapter.setImage(position, R.drawable.image_none);
            mGameAdapter.setImage(downPosition, getNextFromDrawable(image));
            int points = getNumberFromDrawable(image) * 2;
            return points;
        }
        return 0;
    }

    private int getNumberFromDrawable(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192:
                return 8192;
            case R.drawable.image_4096:
                return 4096;
            case R.drawable.image_2048:
                return 2048;
            case R.drawable.image_1024:
                return 1024;
            case R.drawable.image_512:
                return 512;
            case R.drawable.image_256:
                return 256;
            case R.drawable.image_128:
                return 128;
            case R.drawable.image_64:
                return 64;
            case R.drawable.image_32:
                return 32;
            case R.drawable.image_16:
                return 16;
            case R.drawable.image_8:
                return 8;
            case R.drawable.image_4:
                return 4;
            case R.drawable.image_2:
                return 2;
            default:
                return 0;
        }
    }

    private int getNextFromDrawable(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192:
                return R.drawable.image_8192;
            case R.drawable.image_4096:
                return R.drawable.image_8192;
            case R.drawable.image_2048:
                return R.drawable.image_4096;
            case R.drawable.image_1024:
                return R.drawable.image_2048;
            case R.drawable.image_512:
                return R.drawable.image_1024;
            case R.drawable.image_256:
                return R.drawable.image_512;
            case R.drawable.image_128:
                return R.drawable.image_256;
            case R.drawable.image_64:
                return R.drawable.image_128;
            case R.drawable.image_32:
                return R.drawable.image_64;
            case R.drawable.image_16:
                return R.drawable.image_32;
            case R.drawable.image_8:
                return R.drawable.image_16;
            case R.drawable.image_4:
                return R.drawable.image_8;
            case R.drawable.image_2:
                return R.drawable.image_4;
            default:
                return R.drawable.image_none;
        }
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
        switch (nerd) {
            case R.drawable.image_2048:
                mNerd_2048_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_1024:
                mNerd_1024_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_512:
                mNerd_512_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_256:
                mNerd_256_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_128:
                mNerd_128_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_64:
                mNerd_64_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_32:
                mNerd_32_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_16:
                mNerd_16_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_8:
                mNerd_8_TextView.setVisibility(View.VISIBLE);
                break;
            case R.drawable.image_4:
                mNerd_4_TextView.setVisibility(View.VISIBLE);
            case R.drawable.image_2:
                mNerd_2_TextView.setVisibility(View.VISIBLE);
            default:
                break;
        }
    }

    private int getHighestRankingNerd() {
        int nerd = R.drawable.image_none;
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            nerd = getBiggerNerd(nerd, (int) mGameAdapter.getItemIdInt(i));
        }
        return nerd;
    }

    private int getBiggerNerd(int nerd1, int nerd2) {
        if (nerd1 == nerd2) {
            return nerd1;
        }
        if (nerd1 == R.drawable.image_8192) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_8192) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_4096) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_4096) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_2048) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_2048) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_1024) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_1024) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_512) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_512) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_256) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_256) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_128) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_128) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_64) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_64) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_32) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_32) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_16) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_16) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_8) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_8) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_4) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_4) {
            return nerd2;
        }
        if (nerd1 == R.drawable.image_2) {
            return nerd1;
        } else if (nerd2 == R.drawable.image_2) {
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
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }

}