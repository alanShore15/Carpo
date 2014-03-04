package co.autmn.utils;

import static co.autumn.android.Constants.SERVER_ADDRESS;

import org.json.JSONException;

import android.content.Context;

public class Chat {
	
	private int chatUserID;
	private String authToken;
	private String message;
	
	public Chat(int userId, String authToken, String message){
		this.chatUserID = userId;
		this.message = message;
		this.authToken = authToken;
	}
	
	public Chat(int otherUserID, String authToken) {
		this.chatUserID = otherUserID;
		this.authToken = authToken;
	}

	public String create(Context context, int start) throws JSONException{
		ChatJSON json = new ChatJSON(this);
		String url = SERVER_ADDRESS+"chats.json";
		AsyncRequest request = new AsyncRequest(context, url, "POST", json.getJSON(start));
		return request.request();
	}
	
	public String show(Context context, int start) throws JSONException{
		String url = SERVER_ADDRESS+"chats/"+chatUserID+".json";
		ChatJSON json = new ChatJSON();
		AsyncRequest request = new AsyncRequest(context, url, "POST", json.getJSONForShow(authToken, start));
		return request.request();
	}

	public int getChatUserID() {
		return chatUserID;
	}

	public void setChatUserID(int chatUserID) {
		this.chatUserID = chatUserID;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
