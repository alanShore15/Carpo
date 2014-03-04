package co.autmn.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class AsyncRequest extends AsyncTask<String, Void, String> {
	
	private Context context;
	private String URL;
	private String method;
	private String params;
	private final static int NETWORK_STATUS_OK = 0;

	public AsyncRequest(Context context, String url, String method, String params){
		this.context = context;
		this.method = method;
		this.URL = url;
		this.params = params;
	}
	
	private int networkStatus(){
		int networkStatus = 1;
		ConnectivityManager connMgr = (ConnectivityManager) 
	        context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) 
		    networkStatus = 0;
		   return networkStatus;
	}

	@Override
	protected String doInBackground(String... urls) {
		HttpClient httpClient = new DefaultHttpClient();
//		Log.d("inbgactivity", urls[0]);
		if(method.equalsIgnoreCase("POST")){
			try{
				HttpPost request = new HttpPost(urls[0]);
//				Log.d("asd", this.params);
				StringEntity parameters = new StringEntity(params);
				request.addHeader("content-type", "application/json");
		        request.setEntity(parameters);
		        HttpResponse response = httpClient.execute(request);
		        return EntityUtils.toString(response.getEntity());
			}
			catch(Exception e){
//				Log.d("bg exception", e.toString());
				return null;
			}
		}
		else 
			return null;
	}

	
	public String request(){
		if(networkStatus() != NETWORK_STATUS_OK)
			return "Internet Error";
		else{
			return doInBackground(URL);
		}
	}
	
}
