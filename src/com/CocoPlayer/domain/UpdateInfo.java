package com.CocoPlayer.domain;


public class UpdateInfo {
	//�������������ģ��
	private String mVersion;
	private String mDescription;
	private String mApkUrl;
	
	public String getVersion() {
		return mVersion;
	}
	
	public void setVersion(String version) {
		mVersion=version;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public void setDescription(String description) {
		mDescription=description;
	}
	
	public String getApkUrl() {
		return mApkUrl;
	}
	
	public void setApkUrl(String apkUrl) {
		mApkUrl=apkUrl;
	}

}
