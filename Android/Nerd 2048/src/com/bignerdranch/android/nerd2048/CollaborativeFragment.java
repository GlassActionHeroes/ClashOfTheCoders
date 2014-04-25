package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import com.emorym.android_pusher.Pusher;
import com.emorym.android_pusher.PusherCallback;
import org.json.JSONObject;

public class CollaborativeFragment extends Fragment {

	private static final String TAG = "CollaborativeFragment";

	private static final String PUSHER_APP_KEY = "514e04bbf50ba9b0b0b6";
	private static final String PUSHER_APP_SECRET = "b3eb40f3b6fd5e4bee2d";
	private static final String PRIVATE_CHANNEL = "private-bnr_2048_channel";
	private static final String EVENT_NAME = "client-send_direction";
	private static final String DEFAULT_USERNAME = "Unknown Android Nerd";

	private enum Move {
		none, up, down, left, right;
	}

	private Pusher mPusher;
	private GestureDetector mDetector;
	private Move mMove;

	private EditText mUserName;
	private ImageView mArrow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMove = Move.none;
		mPusher = new Pusher(PUSHER_APP_KEY, PUSHER_APP_SECRET);
		mPusher.bindAll(new PusherCallback() {
			@Override
			public void onEvent(String eventName, JSONObject eventData, String channelName) {
				Log.d(TAG, "Received " + eventData.toString() + " for event '" + eventName + "' on channel '" + channelName + "'.");
			}
		});
		new PusherInitTask().execute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mDetector = new GestureDetector(activity, mOnGestureListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_collaborative, container, false);

		mUserName = (EditText) view.findViewById(R.id.username);
		mArrow = (ImageView) view.findViewById(R.id.arrow);

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
		switch (mMove) {
			default:
			case none:
				mArrow.setImageResource(R.drawable.arrow_none);
				break;
			case up:
				mArrow.setImageResource(R.drawable.arrow_up);
				break;
			case down:
				mArrow.setImageResource(R.drawable.arrow_down);
				break;
			case left:
				mArrow.setImageResource(R.drawable.arrow_left);
				break;
			case right:
				mArrow.setImageResource(R.drawable.arrow_right);
				break;
		}
	}

	private void sendEvent() {
		try {
			String username = mUserName.getText().toString();
			username = TextUtils.isEmpty(username) ? DEFAULT_USERNAME : username;
			JSONObject eventData = new JSONObject("{ \"" + mMove + "\" : \"" + username + "\" }");
			mPusher.sendEvent(EVENT_NAME, eventData, PRIVATE_CHANNEL);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float absVelocityX = Math.abs(velocityX);
			float absVelocityY = Math.abs(velocityY);

			if (absVelocityX > absVelocityY) {
				// horizontal scrolling
				if (velocityX < 0 ) {
					mMove = Move.left;
				} else {
					mMove = Move.right;
				}
			} else {
				// vertical scrolling
				if (velocityY < 0) {
					mMove = Move.up;
				} else {
					mMove = Move.down;
				}
			}

			new SendEventTask().execute();

			updateUI();
			return false;
		}
	};

	private class PusherInitTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			mPusher.connect();
			mPusher.subscribe(PRIVATE_CHANNEL);
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Log.d(TAG, "Subscribed.");
			super.onPostExecute(aVoid);
		}
	}

	private class SendEventTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			sendEvent();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Log.d(TAG, "Sent event.");
			super.onPostExecute(aVoid);
		}
	}
}
