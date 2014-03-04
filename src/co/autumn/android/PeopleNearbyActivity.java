package co.autumn.android;

import co.autmn.utils.DeviceLocationListener;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PeopleNearbyActivity extends Activity{
	
	String names[] = { "Stephnie", "Kumar Abhijeet", "Niel Mishra" };
	int pics[] = { R.drawable.random_user, R.drawable.abhijeet, R.drawable.niel };
	String distances[] = { "300m away", "100m away", "150m away" };
	String profiles[] = { 
		"Working as Instructor at MITI AITI in India.",
		"Wroking as CEO at Autumn.",
		"Studying at NIT Calicut."
	};
	String statuses[] = { "Just went for hiking. Awesome experience.",
							"Looking for a JS developer. Anyone here?",
							"Flight delayed at Calicut airport. Anyone for coofee?"
						};
	
	LocationManager locationManager;
	DeviceLocationListener locationListener;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.people_nearby);
	    
	    locationListener = new DeviceLocationListener(getApplicationContext());
	    Criteria locationCriteria = new Criteria();
	    locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
	    locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
	    String provider = locationManager.getBestProvider(locationCriteria, true);
	    provider = (provider == null) ? LocationManager.GPS_PROVIDER : provider;
	    
	    locationManager.requestLocationUpdates(provider, 10, 10, locationListener);
	    
	    Toast.makeText(getApplicationContext(), locationManager.getLastKnownLocation(provider)+"", Toast.LENGTH_LONG).show();
	    
	    ListView people_nearby = (ListView) findViewById(R.id.list_people_nearby);
	    people_nearby.setAdapter(new PeopleAdapter());
	    
	}
	
	public class PeopleAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) PeopleNearbyActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(view == null)
				view = inflater.inflate(R.layout.list_view_people_nearby, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.txt_name);
			holder.status = (TextView) view.findViewById(R.id.txt_status);
			holder.profileDetail = (TextView) view.findViewById(R.id.txt_user_detail);
			holder.lastKnownTime = (TextView) view.findViewById(R.id.txt_last_known_time);
			holder.profilePic = (ImageView) view.findViewById(R.id.profile_pic);
			
			holder.name.setText(names[position]);
			holder.status.setText(statuses[position]);
			holder.profileDetail.setText(profiles[position]);
			holder.lastKnownTime.setText(distances[position]);
			holder.profilePic.setImageResource(pics[position]);
			return view;
		}
		
		class ViewHolder {
			TextView name, status, profileDetail, lastKnownTime;
			ImageView profilePic;
		}
		
	}
}
