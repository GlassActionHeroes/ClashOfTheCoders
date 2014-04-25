package com.bignerdranch.android.nerd2048;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RoundTimerView extends FrameLayout {

	private RoundState mRoundState;

	private ViewGroup mCountdownBarContainer;
	private ViewGroup mProgressBarContainer;
	private View mCountdownBar;
	private TextView mStatus;

	public RoundTimerView(Context context) {
		this(context, null);
	}

	public RoundTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_round_timer, this);

		mCountdownBarContainer = (ViewGroup) findViewById(R.id.countdown_bar_container);
		mProgressBarContainer = (ViewGroup) findViewById(R.id.progress_bar_container);
		mCountdownBar = findViewById(R.id.countdown_bar);
		mStatus = (TextView) findViewById(R.id.status);

		mRoundState = RoundState.WAITING;

		updateUI();
	}

	private void updateUI() {
		mProgressBarContainer.setVisibility(View.GONE);
		mCountdownBarContainer.setVisibility(View.GONE);

		switch (mRoundState) {
			default:
			case WAITING:
				mProgressBarContainer.setVisibility(View.VISIBLE);
				mStatus.setText(R.string.waiting_message);
				break;
			case IN_PROGRESS:
				mCountdownBarContainer.setVisibility(View.VISIBLE);
				mStatus.setText("");
				startInProgress();
				break;
		}
	}

	private void setProgressBarSize(float px) {
		ViewGroup.LayoutParams params = mCountdownBar.getLayoutParams();
		params.width = (int) px;
		mCountdownBar.setLayoutParams(params);
	}

	public void setRoundState(RoundState roundState) {
		mRoundState = roundState;
		updateUI();
	}

	private void startInProgress() {
		final float progressBarInitWidth = getResources().getDimension(R.dimen.countdown_bar_init_width);

		setProgressBarSize(progressBarInitWidth);

		ValueAnimator animation = ValueAnimator.ofFloat(1f, 0f);
		animation.setInterpolator(new TimeInterpolator() {
			@Override
			public float getInterpolation(float input) {
				return input;
			}
		});
		animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float width = (Float) animation.getAnimatedValue() * progressBarInitWidth;
				setProgressBarSize(width);
			}
		});
		animation.setDuration(10000);
		animation.start();
	}
}
