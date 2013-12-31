package com.CocoPlayer.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "cocoAlbum_db";
	private final static int DATABASE_INIT_VERSION = 1;
	private final static String TABLE_ALBUM = "album";
	
	private final static String SQL_CREATE_ALBUM = "Create table "+TABLE_ALBUM+"(_id integer primary key autoincrement," +
			"name TEXT, cover_uri TEXT";
	
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_INIT_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ALBUM);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
		// TODO Auto-generated method stub
		String sql=" DROP TABLE IF EXISTS "+TABLE_ALBUM;
        db.execSQL(sql);
        onCreate(db);
        
	}
	
}
