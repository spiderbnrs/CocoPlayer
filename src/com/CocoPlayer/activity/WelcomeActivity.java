package com.CocoPlayer.activity;


import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CocoPlayer.domain.UpdateInfo;
import com.CocoPlayer.engine.DownloadTask;
import com.CocoPlayer.engine.UpdateInfoService;
import com.example.cocoplayer.R;

public class WelcomeActivity extends Activity {
	
	public static final String NEED_UPDATE="need to update";
	public static final String NO_NEED_UPDATE="no need to update";
	public static final String CONNECTION_FAILED="connection fialed";
	
	private TextView verTV;
	private LinearLayout mLinearLayout;
	private ProgressDialog mProgressDialog;
	
	private String mVersion;
	private UpdateInfo mUpdateInfo;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//hide the title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set the version text
		verTV = (TextView)findViewById(R.id.welcome_versiontext);
		mVersion = getVersion();
		verTV.setText("版本号"+mVersion);
		
		//add animation
		mLinearLayout = (LinearLayout)findViewById(R.id.welcome_version);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(2000);
		mLinearLayout.startAnimation(alphaAnimation);
		
		Log.v("server_address", getString(R.string.serverUrl));
		//set progressDialog
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMessage("正在下载...");
		
		//async to handle http request
		mHandler = new Handler() {
			
			Bundle result;
			public void handleMessage(Message msg) {
				result = msg.getData();
				if (result.getString("update").equals(NEED_UPDATE)) {
					showUpdateDialog();
				}else if(result.getString("update").equals(CONNECTION_FAILED)){
					Toast.makeText(getApplicationContext(), "连接出现问题，升级失败", Toast.LENGTH_SHORT).show();
					loadMainUI();
				}else {
					loadMainUI();
				}
			}
		};
		new Thread() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					//sleep for a while
					sleep(3000);
					//check if need update and send message to handler
					Message message = new Message();
					Bundle result = checkNeedUpdate(mVersion);
					message.setData(result);
					mHandler.sendMessage(message);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}.start();
	}
	private void showUpdateDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this).
				setTitle("升级提醒").
				setMessage(mUpdateInfo.getDescription()).
				setCancelable(false);
		
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
					File dir = new File(Environment.getExternalStorageDirectory(), "/CocoPlayer/update");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String apkPath = Environment.getExternalStorageState() + "/CocoPlayer/update/new.apk";
					UpdateTask task = new UpdateTask(mUpdateInfo.getApkUrl(), apkPath);
					mProgressDialog.show();
					new Thread(task).start();
				}
				else {
					
					Toast.makeText(WelcomeActivity.this, "SD卡不可用，请插入SD卡", Toast.LENGTH_SHORT).show();
					loadMainUI();
				}
				
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				loadMainUI();
			}
		});
		
		builder.create().show();
	}
	
	private Bundle checkNeedUpdate(String version) {
		
		Bundle result = new Bundle();
		UpdateInfoService updateInfoService = new UpdateInfoService(this);
		try {
			mUpdateInfo = updateInfoService.getUpdateInfo(R.string.serverUrl);
			String serverVersion = mUpdateInfo.getVersion();
			if (serverVersion.equals(version)) {
				Log.v("update", "no need to update");
				result.putString("update", NO_NEED_UPDATE);
				return result;
			}
			else {
				Log.v("update", "need to update");
				result.putString("update", NEED_UPDATE);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.putString("update", CONNECTION_FAILED);
			return result;
		}		
	}
	
	public String getVersion() {
		
		try {
			
			PackageManager packageManager = getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			return packageInfo.versionName;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "版本号未知";
		}
	}
	
	/**
	 * install apk
	 * @param file where apk exsits
	 */
	private void install(File file) {
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		finish();
		startActivity(intent);
	}
	
	private void loadMainUI() {
		
	}
	
	class UpdateTask implements Runnable {
		
		private String mPath;
		private String mFilePath;
		
		public UpdateTask(String path, String filePath) {
			this.mPath = path;
			this.mFilePath = filePath;
		}
		public void run() {
			// TODO Auto-generated method stub
			try {
				
				File file = DownloadTask.getFile(mPath, mFilePath, mProgressDialog);
				mProgressDialog.dismiss();
				install(file);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				mProgressDialog.dismiss();
				Toast.makeText(WelcomeActivity.this, "更新失败", Toast.LENGTH_LONG).show();
				loadMainUI();
			}
		}
		
	}
	

}
