package com.sample.listview_localjson;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterView extends BaseAdapter {

	Context context;
	ArrayList<GetSetListData> rowItem;

	public ListAdapterView(Context context, ArrayList<GetSetListData> rowItem) {
		this.context = context;
		this.rowItem = rowItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rowItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return rowItem.indexOf(getItem(position));
	}

	public class viewHolder {
		TextView userName;
		TextView screenName;
		ImageView thumbPic;
		// TextView description;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		viewHolder holder = null;
		LayoutInflater layout = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = layout.inflate(R.layout.listadapter_view, null);
			holder = new viewHolder();
			holder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			holder.screenName = (TextView) convertView
					.findViewById(R.id.userId);
			holder.thumbPic = (ImageView) convertView
					.findViewById(R.id.userImg);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		GetSetListData rowItem = (GetSetListData) getItem(position);

		holder.userName.setText(rowItem.getUserName());
		holder.screenName.setText(rowItem.getScreenName());
		Log.d("rowItemgetImage()-->", rowItem.getImage());

		if (holder.thumbPic != null) {
			new ImageDownloaderTask(holder.thumbPic)
					.execute(rowItem.getImage());
		}

		return convertView;
	};

	// private Bitmap GetImageBitmapFromUrl3(string url3)
	// {
	//
	// using (var webClient = new WebClient())
	// {
	// var imageBytes = webClient.DownloadData(url3);
	// if (imageBytes != null && imageBytes.Length > 0)
	// {
	// ActiveIcon = BitmapFactory.DecodeByteArray(imageBytes, 0,
	// imageBytes.Length);
	// }
	// }
	//
	// return ActiveIcon;
	// }
}
