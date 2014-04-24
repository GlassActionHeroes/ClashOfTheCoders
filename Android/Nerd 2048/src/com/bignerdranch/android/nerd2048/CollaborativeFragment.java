package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class CollaborativeFragment extends Fragment {

	private static final int MIN_DELTA = 16;

	private enum Move {
		None, Up, Down, Left, Right;
	}

	private GestureDetector mDetector;
	private Move mNextMove;

	private TextView mMoveDescription;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mNextMove = Move.None;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mDetector = new GestureDetector(activity, mOnGestureListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_collaborative, container, false);

		mMoveDescription = (TextView) view.findViewById(R.id.move_description);

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
