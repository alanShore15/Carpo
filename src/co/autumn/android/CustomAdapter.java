//
//package co.autumn.android;
//
//import org.brickred.socialauth.android.SocialAuthAdapter;
//import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
//
//import co.autumn.android.R;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//public class CustomAdapter extends BaseAdapter {
//	// Android Components
//	private final LayoutInflater mInflater;
//	private final Context ctx;
//	private Bitmap mIcon;
//
//	// SocialAuth Components
//	SocialAuthAdapter adapter;
//	private final Provider[] providers = new Provider[] { Provider.FACEBOOK, Provider.LINKEDIN };
//	private final int[] images = new int[] { R.drawable.facebook, R.drawable.linkedin };
//
//	public CustomAdapter(Context context, SocialAuthAdapter mAdapter) {
//		// Cache the LayoutInflate to avoid asking for a new one each time.
//		ctx = context;
//		mInflater = LayoutInflater.from(ctx);
//		adapter = mAdapter;
//	}
//
//	/**
//	 * The number of items in the list is determined by the number of speeches
//	 * in our array.
//	 */
//	@Override
//	public int getCount() {
//		return providers.length;
//	}
//
//	/**
//	 * Since the data comes from an array, just returning the index is sufficent
//	 * to get at the data. If we were using a more complex data structure, we
//	 * would return whatever object represents one row in the list.
//	 */
//	@Override
//	public Object getItem(int position) {
//		return position;
//	}
//
//	/**
//	 * Use the array index as a unique id.
//	 */
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	/**
//	 * Make a view to hold each row.
//	 * 
//	 * @see android.widget.ListAdapter#getView(int, android.view.View,
//	 *      android.view.ViewGroup)
//	 */
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		// A ViewHolder keeps references to children views to avoid unneccessary
//		// calls to findViewById() on each row.
//		ViewHolder holder;
//
//		// When convertView is not null, we can reuse it directly, there is no
//		// need to reinflate it. We only inflate a new View when the convertView
//		// supplied by ListView is null.
//		if (convertView == null) {
//			convertView = mInflater.inflate(R.layout.providers_list, null);
//
//			// Creates a ViewHolder and store references to the two children
//			// views
//			// we want to bind data to.
//			holder = new ViewHolder();
//			holder.layout = (RelativeLayout) convertView.findViewById(R.id.provider_layout);
//			holder.text = (TextView) convertView.findViewById(R.id.providerText);
//			holder.icon = (ImageView) convertView.findViewById(R.id.provider);
//			holder.signText = (TextView) convertView.findViewById(R.id.signstatus);
//
//			convertView.setTag(holder);
//		} else {
//			// Get the ViewHolder back to get fast access to the TextView
//			// and the ImageView.
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		mIcon = BitmapFactory.decodeResource(ctx.getResources(), images[position]);
//
//		// Bind the data efficiently with the holder.
//
//		String textCase = providers[position].toString();
//		textCase = String.valueOf(textCase.charAt(0)).toUpperCase() + textCase.substring(1, textCase.length());
//
//		holder.text.setText(textCase);
//		holder.icon.setImageBitmap(mIcon);
//
//		holder.layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(ctx, v.toString(), Toast.LENGTH_LONG).show();
//				adapter.signOut(providers[position].toString());
//				adapter.authorize(ctx, providers[position]);
//
//			}
//		});
//
//		holder.signText.setText("Sign In");
//		holder.signText.setTag(1);
//
//		holder.layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				adapter.authorize(ctx, providers[position]);
//			}
//		});
//
//		return convertView;
//	}
//
//	class ViewHolder {
//		RelativeLayout layout;
//		TextView text;
//		ImageView icon;
//		TextView signText;
//	}
//} // End of customAdapter
