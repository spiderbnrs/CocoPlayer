package com.CocoPlayer.model;


public class Album {
	
	private String mName;
	private String mIconUri;
	
	public Album() {
		mName = "unNamed";
		mIconUri = "";
	}
	public Album(String name, String iconUri) {
		mName = name;
		mIconUri = iconUri;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public String getIconUri() {
		return mIconUri;
	}
	
	public void setIconUri(String iconUri) {
		mIconUri = iconUri;
	}
	

}
