package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class CollaborativeFragment extends Fragment {

	private static final int MIN_DELTA = 16;

	private enum Move {
		None, Up, Down, Left, Right;
	}

	private GestureDetector mDetector;
	private Move mNextMove;
	private RoundState mRoundState;

	private ImageView mArrow;
	private TextView mMoveDescription;
	private RoundTimerView mRoundTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mNextMove = Move.None;
		mRoundState = RoundState.IN_PROGRESS;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mDetector = new GestureDetector(activity, mOnGestureListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_collaborative, container, false);

		mArrow = (ImageView) view.findViewById(R.id.arrow);
		mMoveDescription = (TextView) view.findViewById(R.id.move_description);
		mRoundTimer = (RoundTimerView) view.findViewById(R.id.round_timer);

		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mDetector.onTouchEvent(event);
				return true;
			}
		});

		updateUI();

		return view;
	}

	private void updateUI() {
		mMoveDescription.setText(getString(R.string.next_move) + " " + mNextMove);

		int visibility = mRoundState == RoundState.IN_PROGRESS ? View.VISIBLE : View.GONE;
		mMoveDescription.setVisibility(visibility);

		switch (mNextMove) {
			default:
			case None:
				mArrow.setImageResource(R.drawable.arrow_none);
				break;
			case Up:
				mArrow.setImageResource(R.drawable.arrow_up);
				break;
			case Down:
				mArrow.setImageResource(R.drawable.arrow_down);
				break;
			case Left:
				mArrow.setImageResource(R.drawable.arrow_left);
				break;
			case Right:
				mArrow.setImageResource(R.drawable.arrow_right);
				break;
		}

		mRoundTimer.setRoundState(mRoundState);
	}

	private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			float absDistanceX = Math.abs(distanceX);
			float absDistanceY = Math.abs(distanceY);

			if (absDistanceX > absDistanceY) {
				if (absDistanceX < MIN_DELTA) {
					return false;
				}

				// horizontal scrolling
				if (distanceX > 0 ) {
					mNextMove = Move.Left;
				} else {
					mNextMove = Move.Right;
				}
			} else {
				if (absDistanceY < MIN_DELTA) {
					return false;
				}

				// vertical scrolling
				if (distanceY > 0) {
					mNextMove = Move.Up;
				} else {
					mNextMove = Move.Down;
				}
			}

			updateUI();
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}
	};
}
