package com.example.mapdemo;

import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* HOWTO use:

1. Instantiate the receiver:

public class MapDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      IntentFilter intentFilter = new IntentFilter("com.parse.push.intent.RECEIVE");
      registerReceiver(new MarkerUpdatesReceiver(this), intentFilter);
    }

2. Make sure the Activity extends PushInterface:

public class MapDemoActivity extends AppCompatActivity implements PushInterface {

	PushUtil mPushUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
            mPushUtil = new PushUtil();
        }

        @Override
	public void onMarkerUpdate(PushRequest pushRequest) {
	}
}
*/

public class MarkerUpdatesReceiver extends BroadcastReceiver {

    private static final String TAG = "MarkerUpdatesReceiver";

    public static final String intentAction = "com.parse.push.intent.RECEIVE";

    public interface PushInterface {
        void onMarkerUpdate(PushRequest pushRequest);
    }

    Activity mActivity;

    public MarkerUpdatesReceiver(Activity activity) {
        if (!(activity instanceof PushInterface)) {
            throw new IllegalStateException("activity needs to implement push interface");
        } else {
            mActivity = activity;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(TAG, "Receiver intent null");
        } else {
            // Parse push message and handle accordingly
            processPush(context, intent);
        }
    }

    private void processPush(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "got action " + action);
        if (action.equals(intentAction)) {
            String channel = intent.getExtras().getString("com.parse.Channel");

            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                JSONObject customData = new JSONObject(json.getString("customData"));

                PushRequest pushRequest = new PushRequest(customData);

                // don't update markers from yourself
                if (!pushRequest.userId.equals(ParseUser.getCurrentUser().getObjectId())) {
                    ((PushInterface) mActivity)
                            .onMarkerUpdate(pushRequest);
                }

                Log.d(TAG, "got action " + action + "with " + customData);
            } catch (JSONException ex) {
                Log.d(TAG, "JSON failed!");
                ex.printStackTrace();
            }
        }
    }
}