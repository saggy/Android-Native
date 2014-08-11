package com.sample.listview_localjson;

public class GetSetListData {

	String _screenName, _userName, _desc, _image;

	// Null constructor
	public GetSetListData() {

	}

	public GetSetListData(String userName, String screenName, String image,
			String desc) {
		this._desc = desc;
		this._image = image;
		this._userName = userName;
		this._screenName = screenName;
	};

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		this._screenName = screenName;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		this._userName = userName;
	}

	public String getDesc() {
		return _desc;
	}

	public void setDesc(String desc) {
		this._desc = desc;
	}

	public String getImage() {
		return _image;
	}

	public void setImage(String image) {
		this._image = image;
	}
}
