package com.saggy.torch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class FlashActivity extends Activity {

	MediaPlayer mp;
	Parameters params;
	private Camera camera;
	private Boolean hasFlash;
	private Boolean isFlashOn = false;
	public ToggleButton FlashBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		FlashBtn = (ToggleButton) findViewById(R.id.torch_Btn);

		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

		if (!hasFlash) {
			// device doesn't support flash
			// Show alert message and close the application
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
					FlashActivity.this);

			alertBuilder.setTitle("ALERT");
			alertBuilder
					.setMessage("Sorry, your device doesn't support flash light!");
			alertBuilder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// if this button is clicked, close
							// current activity
							FlashActivity.this.finish();
						}
					});
			AlertDialog alert = alertBuilder.create();

			alert.show();
			return;
		}

		// get all the camera information
		getCamera();

		// click event on toggle button;
		FlashBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton toggleButton,
					boolean isChecked) {

				if (FlashBtn.isChecked()) {
					Log.d("check status -->", isChecked + "");
					turnOffTorch();
				} else {
					Log.d("check status false-->", isChecked + "");
					turnOnTorch();
				}
			}
		});
	};

	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera Error Exception-->", e.getMessage());
			}

		}
	}

	// Playing sound
	// will play button toggle sound on flash on / off
	private void playSound() {
		if (isFlashOn) {
			mp = MediaPlayer.create(FlashActivity.this, R.raw.light_switch_off);
		} else {
			mp = MediaPlayer.create(FlashActivity.this, R.raw.light_switch_on);
		}
		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
		mp.start();
	}

	public void turnOffTorch() {
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;
		}
	}

	public void turnOnTorch() {
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// on pause turn off the flash
		turnOffTorch();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// on resume turn on the flash
		// if (isFlashOn)
		turnOnTorch();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// on starting the app get the camera params
		getCamera();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// on stop release the camera
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}
}
