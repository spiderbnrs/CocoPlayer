package com.CocoPlayer.model;

import java.util.UUID;


public class Album {
	
	//Uuid 在全局标识
	private String mUuid;
	
	private String mName;
	private String mDescription;
	private String mIconUri;
	
	public Album() {
		
		mUuid = UUID.randomUUID().toString();
		mName = "";
		mIconUri = null;
		mDescription = "";
		
	}
	
	public String getUuid() {
		return mUuid;
	}
	
	public void setUuid(String Uuid){
		mUuid = Uuid;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public void setDescription(String description) {
		mDescription =description;
	}
	
	public String getIconUri() {
		return mIconUri;
	}
	
	public void setIconUri(String iconUri) {
		mIconUri = iconUri;
	}
	

}
