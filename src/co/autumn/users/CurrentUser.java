package co.autumn.users;

import android.content.Context;
import android.content.SharedPreferences;

import static co.autumn.android.Constants.*;

public class CurrentUser extends User {
	
	protected String authToken;
	protected Context context;
	SharedPreferences userAuthTokenSP;
	
	public CurrentUser(Context context, String auth_token){
		this.context = context;
		this.authToken = auth_token;
		this.userAuthTokenSP = context.getSharedPreferences(AUTH_TOKEN_SP, Context.MODE_PRIVATE);
	}
	
	public CurrentUser(Context context){
		this.context = context;
		this.userAuthTokenSP = context.getSharedPreferences(AUTH_TOKEN_SP, Context.MODE_PRIVATE);
		this.authToken = userAuthTokenSP.getString(AUTH_TOKEN_STRING, "");
	}
	
	public boolean exists(){
		return userAuthTokenSP.contains(AUTH_TOKEN_STRING);
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String auth_token) {
		this.authToken = auth_token;
	}
	
	public boolean save() {
		SharedPreferences.Editor authEditor = userAuthTokenSP.edit();
		authEditor.putString(AUTH_TOKEN_STRING, authToken);
		return authEditor.commit();
	}
}
