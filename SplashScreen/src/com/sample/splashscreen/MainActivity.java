package com.sample.splashscreen;

import com.sample.splashscreen.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// public String initJsonLoad() {
	// String json = null;
	// try {
	// InputStream is = getAssets().open("data.json");
	// int size = is.available();
	// byte[] buffer = new byte[size];
	// is.read(buffer);
	// is.close();
	// json = new String(buffer, "UTF-8");
	// Log.d("Check Json-->", json + "");
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// Log.d("JSON Exception-->", e + "");
	// return null;
	// }
	// return json;
	// }

	// public void listViewAdapter() {
	// if(initJsonLoad() != null){
	//
	// }else{
	//
	// }
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
