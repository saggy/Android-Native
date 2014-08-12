package com.sample.googlemap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements
		OnMyLocationChangeListener, OnCameraChangeListener,
		OnLocationChangedListener {
	private GoogleMap map;
	private LocationManager locManager;
	private Location location;
	public MarkerOptions mark;
	private TextView myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	public void init() {
		try {
			if (map == null) {
				map = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.mapView)).getMap();
				map.setMyLocationEnabled(true);
				map.getUiSettings().setZoomGesturesEnabled(true);
				map.getUiSettings().setRotateGesturesEnabled(true);
				map.getUiSettings().setZoomGesturesEnabled(true);

				locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Criteria c = new Criteria();
				if (locManager.isProviderEnabled(locManager.GPS_PROVIDER)) {

				} else {
					buildAlertMessageNoGps();
				}
				;
				String provider = locManager.getBestProvider(c, false);
				location = locManager.getLastKnownLocation(provider);
				Log.d("best cur lat-->", location.getLatitude() + "");
				Log.d("best cur lon-->", location.getLongitude() + "");

				LatLng myLoc = new LatLng(location.getLatitude(),
						location.getLongitude());

				// Showing the current location in Google Map
				map.moveCamera(CameraUpdateFactory.newLatLng(myLoc));

				// Zoom in the Google Map
				map.animateCamera(CameraUpdateFactory.zoomTo(15));

				myLocation = (TextView) findViewById(R.id.latValue);
				myLocation.setText(location.getLatitude() + ", Longitude: "
						+ location.getLongitude());

			}
		} catch (Exception e) {
			Log.d("Exception Map", e.toString());
			Toast.makeText(getApplicationContext(),
					"Google play service need to be updated", Toast.LENGTH_LONG)
					.show();
		}
	};

	@Override
	public void onMyLocationChange(Location loc) {
		TextView myLocation = (TextView) findViewById(R.id.latValue);
		// TODO Auto-generated method stub
		double latitude = loc.getLongitude();
		double longitude = loc.getLatitude();
		Log.d("Lat->", latitude + "");
		Log.d("lon-->", longitude + "");
		Toast.makeText(getApplicationContext(),
				"Lat--> " + latitude + "Lon--> " + longitude, Toast.LENGTH_LONG)
				.show();
		LatLng myLoc = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		map.moveCamera(CameraUpdateFactory.newLatLng(myLoc));

		// Zoom in the Google Map
		map.animateCamera(CameraUpdateFactory.zoomTo(15));

		// Setting latitude and longitude in the TextView tv_location
		myLocation.setText(latitude + ", Longitude: " + longitude);

	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		double lat = loc.getLatitude();
		double lon = loc.getLongitude();

		Log.d("On change map latitude--->", lat + "");
		Log.d("On change map longitude-->", lon + "");

	}

	@Override
	public void onCameraChange(CameraPosition pos) {
		// TODO Auto-generated method stub

	}

	// To show Alert, When GPS in Switch-Off mode.
	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alert");
		builder.setMessage(
				"GPS on your phone is not enabled. This requires GPS for few features like Map.Do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.dismiss();
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.dismiss();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

}
