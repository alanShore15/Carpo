package co.autumn.android;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.autmn.utils.Chat;
import co.autmn.utils.ImageRequest;
import co.autumn.users.CurrentUser;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static co.autumn.android.Constants.*;



public class ChatActivity extends Activity implements OnClickListener{
	
	ListView chatListView;
	Button sendMessageButton;
	EditText messageBox;
	String profilePicURL;
	
	LayoutInflater inflator;
	ChatAdapter chatAdapter;
	int otherUserID;
	int chatTilID = 0;
	CurrentUser currentUser;
	Chat userChat;
	
	JSONArray userChats;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatAdapter = new ChatAdapter();
		setContentView(R.layout.chat_view);
		chatListView = (ListView) findViewById(R.id.chat_list_view);
		sendMessageButton = (Button) findViewById(R.id.send_message_btn);
		messageBox = (EditText) findViewById(R.id.message_edt_box);
		
		otherUserID = getIntent().getExtras().getInt(CHAT_USER_ID);
		profilePicURL = getIntent().getExtras().getString(CHAT_USER_PROFILE_PIC);
		
		currentUser = new CurrentUser(getApplicationContext());
		if(!currentUser.exists()){
			currentUser.setAuthToken("l1IyZsWP-c-5Y4wT3qe6jA");
			currentUser.save();
		}
		
		setUpPrevChat();
		
		sendMessageButton.setOnClickListener(this);
		chatListView.setAdapter(chatAdapter);
		Timer loadChatsTimer = new Timer();
		loadChatsTimer.scheduleAtFixedRate(new ChatTask(), 10000, 20000);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private class ChatAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return userChats.length();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("finally")
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			JSONObject chat;
			try {
				chat = userChats.getJSONObject(position);
				boolean isOtherUserMessage = chat.getInt("sender_id") == otherUserID;
				ViewHolder holder = new ViewHolder();
				if(isOtherUserMessage){
					view = inflator.inflate(R.layout.other_chat_list, null);
					holder.chat = (TextView) view.findViewById(R.id.chat);
					holder.chat.setText(chat.getString("message"));
					
					holder.profilePic = (ImageView) view.findViewById(R.id.profile_pic);
					ImageRequest req = new ImageRequest();
					holder.profilePic.setImageDrawable(req.getImage(profilePicURL));
				}
				else{
					view = inflator.inflate(R.layout.self_chat_list, null);
					
					holder.chat = (TextView) view.findViewById(R.id.chat);
					holder.chat.setText(chat.getString("message"));				
				}	
				
			} catch (JSONException e) {
				Log.d("Image", e.toString());
				e.printStackTrace();
			}
			finally{
				return view;
			}
			
		}
		
		class ViewHolder {
			TextView chat;
			ImageView profilePic;
		}
		
	}
	
	private class ChatTask extends TimerTask {

		@Override
		public void run() {
			JSONObject chats;
			try {
				chats = new JSONObject(userChat.show(getApplicationContext(), chatTilID));
				JSONArray newChats = chats.getJSONArray(CHATS_STRING);
				if(newChats.length() > 0){
					chatTilID = chats.getInt(CHATS_TIL_STRING);
					JSONAdd(userChats, newChats);
					runOnUiThread(new Runnable() {
		
					    @Override
					    public void run() {
					    	chatAdapter.notifyDataSetChanged();
					    }
					});
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void JSONAdd(JSONArray userChats, JSONArray newChats) throws JSONException {
		for(int i=0;i<newChats.length();i++)
			userChats.put(newChats.get(i));
	}
	
	private void setUpPrevChat() {
		userChat = new Chat(otherUserID, currentUser.getAuthToken());
		JSONObject chats;
		try {
			chats = new JSONObject(userChat.show(getApplicationContext(), 0));
			userChats = chats.getJSONArray(CHATS_STRING);
			chatTilID = chats.getInt(CHATS_TIL_STRING);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if(messageBox.getText().toString().length() < 2)
			Toast.makeText(getApplicationContext(), "Too Short to be message!", Toast.LENGTH_SHORT).show();
		else{
			userChat= new Chat(otherUserID, currentUser.getAuthToken(), messageBox.getText().toString());
			JSONObject newChat;
			try {
				newChat = new JSONObject(userChat.create(getApplicationContext(), chatTilID));
				if(newChat.getString("status").equalsIgnoreCase("success")) {
					JSONArray newChats = newChat.getJSONArray(CHATS_STRING);
					if(newChats.length() > 0){
						chatTilID = newChat.getInt(CHATS_TIL_STRING);
						JSONAdd(userChats, newChats);
						chatAdapter.notifyDataSetChanged();
					}
					messageBox.setText("");
				}
				else
					Toast.makeText(getApplicationContext(), "Oops. Please try again!", Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Oops. Please try again!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}
}
