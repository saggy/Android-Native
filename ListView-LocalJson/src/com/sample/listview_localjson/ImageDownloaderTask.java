package com.sample.listview_localjson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	public WeakReference imageViewRefrence;

	public ImageDownloaderTask(ImageView imageView) {
		imageViewRefrence = new WeakReference(imageView);

	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.d("params[0]-->", params[0]);

		return downloadBitmap(params[0]);
	}

	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}
		if (imageViewRefrence != null) {
			ImageView imageView = (ImageView) imageViewRefrence.get();
			if (imageView != null) {
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView
							.setImageDrawable(imageView.getContext()
									.getResources()
									.getDrawable(R.drawable.ic_launcher));
				}
			}
		}
	}

	// // Creates Bitmap from InputStream and returns it
	// private Bitmap downloadImage(String url) {
	// Bitmap bitmap = null;
	// InputStream stream = null;
	// BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	// bmOptions.inSampleSize = 1;
	//
	// try {
	// stream = getHttpConnection(url);
	// bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
	// stream.close();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// return bitmap;
	// }

	// Makes HttpURLConnection and returns InputStream
	// private InputStream getHttpConnection(String urlString) throws
	// IOException {
	// InputStream stream = null;
	// URL url = new URL(urlString);
	// URLConnection connection = url.openConnection();
	//
	// try {
	// HttpURLConnection httpConnection = (HttpURLConnection) connection;
	// httpConnection.setRequestMethod("GET");
	// httpConnection.connect();
	//
	// if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	// stream = httpConnection.getInputStream();
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return stream;
	// }

	static Bitmap downloadBitmap(String url) {
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("android");
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			Log.d("statusCode-->", statusCode + "");
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			getRequest.abort();
			Log.w("ImageDownloader ", "Error while retrieving bitmap from "
					+ url);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

}
