package co.autumn.android;

//import java.lang.reflect.Array;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import co.autumn.users.CurrentUser;
import co.autumn.users.User;

import static co.autumn.android.Constants.*;

import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends FragmentActivity {
	
	private UiLifecycleHelper uiHelper;

	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CurrentUser currentUser = new CurrentUser(this);
    if(currentUser.exists()){
    	Intent peopleIntent = new Intent(MainActivity.this, PeopleNearbyActivity.class);
		startActivity(peopleIntent);
    }
    setContentView(R.layout.activity_main);
    uiHelper = new UiLifecycleHelper(this, callback);
    uiHelper.onCreate(savedInstanceState);
    Button login_btn = (Button) findViewById(R.id.btn_login);
    login_btn.setText("Login with LinkedIn");
    login_btn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent peopleIntent = new Intent(MainActivity.this, ChatActivity.class);
			peopleIntent.putExtra(CHAT_USER_ID, 2);
			peopleIntent.putExtra(CHAT_USER_PROFILE_PIC, "http://marakana.com/user/photo/b0f6e2fd95af8081ae682cb646ae4e09.jpg");
			startActivity(peopleIntent);
		}
	});
    
    LoginButton button = (LoginButton) findViewById(R.id.login_button);
    button.setBackgroundResource(R.drawable.facebook);
    button.setReadPermissions(Arrays.asList("user_likes", "user_status", "email", "user_relationships"));
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      uiHelper.onActivityResult(requestCode, resultCode, data);
  }

	private void onSessionStateChange(Session session, SessionState state,
				Exception exception) {
		if(session != null && session.isOpened()){
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		        @Override
		        public void onCompleted(GraphUser user, Response response) {
		            if (user != null) {
		                User newUser = new User(user);
		                JSONObject authToken = null;
		                try {
							authToken = new JSONObject(newUser.create(getApplicationContext()));
						} catch (Exception e) {
							e.printStackTrace();
						}
		                if(authToken != null && authToken.has(AUTH_TOKEN_STRING)){
		                	CurrentUser currentUser = null;
							try {
								currentUser = new CurrentUser(getApplicationContext(), authToken.getString(AUTH_TOKEN_STRING));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                	currentUser.save();
		                }
		            }
		        }

				
		    });
		}
			
	}

}