package com.sample.splashscreen;

import com.sample.splashscreen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// Intent to open the  new activity after specific time interval
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// close current activity
				finish();
			}

		}, SPLASH_TIME_OUT);
	}
}
