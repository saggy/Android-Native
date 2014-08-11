package com.sample.listview_localjson;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
	String json = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			AssetManager is = getAssets();
			InputStream input = is.open("data.json");
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			json = new String(buffer);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d("Exception json fetch-->", ex.toString());
		}

		if (json != null) {
			init(json);
		}

	}

	public void init(String JSON) {
		if (JSON != null) {
			ArrayList<GetSetListData> rowItem = new ArrayList<GetSetListData>();
			String userName, screenName, desc, thumbPic;
			ListAdapterView adapter;
			ListView listView = (ListView) findViewById(R.id.jsonListView);

			try {
				JSONObject newJson = new JSONObject(JSON);
				Log.d("New JSON OBJ-->", newJson + "");
				JSONArray listArray = newJson.getJSONArray("List");
				Log.d("listArray--->", listArray + "");

				for (int i = 0, l = listArray.length(); i < l; i++) {
					JSONObject listObj = listArray.getJSONObject(i);
					Log.d("listObj-->", i + "-->" + listObj);

					userName = listObj.getString("Name");
					thumbPic = listObj.getString("image");
					desc = listObj.getString("description");
					screenName = listObj.getString("screenname");

					rowItem.add(new GetSetListData(userName, screenName,
							thumbPic, desc));
				}
				adapter = new ListAdapterView(getApplicationContext(), rowItem);
				listView.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("JSON Exception-->", e + "");
			}
		}
	};
}
