package com.bignerdranch.android.nerd2048;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

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

	private GestureDetector mDetector;
	private Move mMove;
	private Pusher mPusher;
	private PrivateChannel mChannel;

	private EditText mUserName;
	private ImageView mArrow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		mMove = Move.none;

		HttpAuthorizer authorizer = new HttpAuthorizer("http://mysterious-forest-1989.herokuapp.com/pusher/auth");
		PusherOptions options = new PusherOptions().setAuthorizer(authorizer);
		mPusher = new Pusher(PUSHER_APP_KEY, options);

		mPusher.connect(new ConnectionEventListener() {
			@Override
			public void onConnectionStateChange(ConnectionStateChange change) {
				Log.d(TAG, "State changed to " + change.getCurrentState() +
						" from " + change.getPreviousState());
			}

			@Override
			public void onError(String message, String code, Exception e) {
				Log.d(TAG, "There was a problem connecting!");
			}
		}, ConnectionState.ALL);

		mChannel = mPusher.subscribePrivate(PRIVATE_CHANNEL,
				new PrivateChannelEventListener() {
					@Override
					public void onAuthenticationFailure(String message, Exception e) {
						Log.d(TAG, String.format("Authentication failure due to [%s], exception was [%s]", message, e));
					}

					@Override
					public void onSubscriptionSucceeded(String channelName) {

					}

					@Override
					public void onEvent(String channelName, String eventName, String data) {
						Log.d(TAG, "Received event: " + eventName + " with data: " + data);
					}
				});
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

			String username = mUserName.getText().toString();
			username = TextUtils.isEmpty(username) ? DEFAULT_USERNAME : username;
			mChannel.trigger(EVENT_NAME, "{ \"" + mMove + "\" : \"" + username + "\" }");

			updateUI();
			return false;
		}
	};
}
