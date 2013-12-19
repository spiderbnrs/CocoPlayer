package com.CocoPlayer.engine;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.CocoPlayer.domain.UpdateInfo;

public class UpdateInfoPraser {
	
	public static UpdateInfo getUpdateInfo(InputStream in) throws Exception{
		
		UpdateInfo updateInfo = new UpdateInfo();
		//����һ��xmlpraser�������������е�xml�ļ�����
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "GB2312");
		int type = pullParser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			
			switch (type) {
			case XmlPullParser.START_TAG:
				//get the version
				if (pullParser.getName().equals("version")) {
					updateInfo.setVersion(pullParser.nextText());
				}
				//get the description
				if (pullParser.getName().equals("description")) {
					updateInfo.setDescription(pullParser.nextText());
				}
				//get the apkUrl
				if (pullParser.getName().equals("apkUrl")) {
					updateInfo.setApkUrl(pullParser.nextText());
				}
				break;
				
			default:
				break;
			}
			type = pullParser.next();
		}
		
		return updateInfo;
	}

}
