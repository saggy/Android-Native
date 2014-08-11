package com.sample.googlemap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	private GoogleMap map;
	double lat;
	double lon;
	public MarkerOptions mark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapView)).getMap();
		map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomGesturesEnabled(true);

		// Location currentLoc = map.getMyLocation();
		// Log.d("Lat-->", currentLoc.getLatitude() + "");
		// Log.d("Lon->", currentLoc.getLongitude() + "");
		// LatLng coordinates = new LatLng(currentLoc.getLatitude(),
		// currentLoc.getLongitude());
		// mark = new
		// MarkerOptions().position(coordinates).title("Sagar Is Here");
		// map.addMarker(mark);
	}

	public void init() {

	};
}
