package com.CocoPlayer.engine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

import com.CocoPlayer.domain.UpdateInfo;

public class UpdateInfoService {
	
	private Context mContext;
	
	public UpdateInfoService(Context context) {
		mContext=context;
	}
	
	public UpdateInfo getUpdateInfo (int urlId) throws Exception {
		String path = mContext.getResources().getString(urlId);
		URL serverUrl=new URL(path);
		HttpURLConnection httpURLConnection;
		httpURLConnection = (HttpURLConnection)serverUrl.openConnection();
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setRequestMethod("GET");
		InputStream in=httpURLConnection.getInputStream();
		return UpdateInfoPraser.getUpdateInfo(in);
		
	}
	
}
