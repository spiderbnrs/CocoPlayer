package com.CocoPlayer.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CocoPlayer.domain.UpdateInfo;
import com.CocoPlayer.engine.UpdateInfoService;
import com.example.cocoplayer.R;

public class WelcomeActivity extends Activity {
	
	private TextView verTV;
	private LinearLayout mLinearLayout;
	
	private UpdateInfo mUpdateInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//hide the title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set the version text
		verTV = (TextView)findViewById(R.id.welcome_version);
		String version = getVersion();
		verTV.setText("�汾��"+version);
		
		//add animation
		mLinearLayout = (LinearLayout)findViewById(R.layout.activity_player);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(2000);
		mLinearLayout.startAnimation(alphaAnimation);
		
		if (isNeedUpdate(version)) {
			showUpdateDialog();
		}
	}
	
	private void showUpdateDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this).
				setTitle("��������").
				setMessage(mUpdateInfo.getDescription()).
				setCancelable(false);
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.create().show();
	}
	
	private boolean isNeedUpdate(String version) {
		
		UpdateInfoService updateInfoService = new UpdateInfoService(this);
		try {
			mUpdateInfo = updateInfoService.getUpdateInfo(R.string.serverUrl);
			String serverVersion = mUpdateInfo.getVersion();
			if (serverVersion.equals(version)) {
				Log.v("update", "no need to update");
				return false;
			}
			else {
				Log.v("update", "need to update");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(this, "��ȡ������Ϣʧ�ܣ����Ժ���", Toast.LENGTH_SHORT).show();
			return false;
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
			return "�汾��δ֪";
		}
	}
	

}
