package co.autmn.utils;



import java.net.URL;
import java.net.URLConnection;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class ImageRequest extends AsyncTask<String, Void, Drawable>{

	@Override
	protected Drawable doInBackground(String... urls) {
		Drawable image = null;
		URL url;
		Log.d("in doinbg", urls[0]);
		try {
			url = new URL(urls[0]);
			URLConnection connection = url.openConnection();
			connection.setUseCaches(true);
			image = Drawable.createFromStream(connection.getInputStream(), "sdsd");			 
		} catch (Exception e) {
			Log.d("error - bg", e.toString());
			e.printStackTrace();
		}
		return image;
	}
	
	public Drawable getImage(String imageURL){
		return doInBackground(imageURL);
	}

	@Override
	protected void onCancelled() {
		Log.d("1","3");
		super.onCancelled();
	}

	@Override
	protected void onCancelled(Drawable result) {
		Log.d("2","3");
		// TODO Auto-generated method stub
//		super.onCancelled(result);
	}

	@Override
	protected void onPreExecute() {
		Log.d("3","3");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		Log.d("4","3");
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Drawable result) {
		Log.d("on post exexcute", "adsasd");
		super.onPostExecute(result);
	}

}
