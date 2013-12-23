package com.CocoPlayer.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;

public class DownloadTask {
	
	public static File getFile(String path,String filePath,ProgressDialog progressDialog) throws Exception {
		
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setConnectTimeout(2000);
		httpURLConnection.setRequestMethod("GET");
		if (httpURLConnection.getResponseCode() == 200) {
			
			int total = httpURLConnection.getContentLength();
			progressDialog.setMax(total);
			
			InputStream in = httpURLConnection.getInputStream();
			File file = new File(filePath);
			FileOutputStream fout = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			int process = 0;
			while ((len = in.read(buffer))!=-1) {
				
				fout.write(buffer,0,len);
				process += len;
				progressDialog.setProgress(process);
			}
			fout.flush();
			fout.close();
			in.close();
			return file;
		}
		return null;
	}

}
