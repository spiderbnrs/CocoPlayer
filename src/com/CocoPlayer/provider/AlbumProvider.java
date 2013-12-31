package com.CocoPlayer.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class AlbumProvider extends ContentProvider {
	
	private final static Uri CONTENT_URI = Uri.parse("com.CocoPlayer.provider.AlbumProvider/album");

	//µ÷ÊÔÐÅÏ¢
	private final static String TAG = "Debug";
	private SQLiteDatabase db;
	private DBHelper mHelper;
	
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "query uri - " + uri);
        }
		db = mHelper.getReadableDatabase(); 
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
