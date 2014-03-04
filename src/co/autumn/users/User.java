package co.autumn.users;

import org.json.JSONException;

import com.facebook.model.GraphUser;

import android.content.Context;
import android.util.Log;

import co.autmn.utils.AsyncRequest;
import co.autmn.utils.UserJSON;
import static co.autumn.android.Constants.*;

public class User{
	
	private String email;
	private String name;
	private String authToken;
	private String dob;
	private String profilePicURL;
	private String maretialStatus;
	private String gender;
	private String fbId;
	private String linkedinId;
	private String profilePicUrl;
	
	public User(GraphUser profile) {
		email = (String) profile.getProperty("email");
		name = profile.getName();
		dob = profile.getBirthday();
		profilePicUrl = profile.getId();
		gender = (String) profile.getProperty("gender");
		maretialStatus = (String) profile.getProperty("relationship_status");
		fbId = profile.getId();
		linkedinId = "";
	}
	
	public User(){
		email="pankaj@engineerinme.com";
		name="Pankaj Sharma";
		dob = "07-07-1992";
		profilePicURL = "abc.png";
		maretialStatus = "Single";
		gender = "male";
		fbId = "adsas";
		linkedinId = "as";
		authToken = "";
	}
	

	public String create(Context context) throws JSONException{
		Log.d("Create", "User create");
		UserJSON json = new UserJSON(this);
		String url = SERVER_ADDRESS+"users.json";
		AsyncRequest request = new AsyncRequest(context, url, "POST", json.getJSON());
		return request.request();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getProfilePicURL() {
		return profilePicURL;
	}

	public void setProfilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}

	public String getMaretialStatus() {
		return maretialStatus;
	}

	public void setMaretialStatus(String maretialStatus) {
		this.maretialStatus = maretialStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getLinkedinId() {
		return linkedinId;
	}

	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	
}
