package com.CocoPlayer.activity;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.CocoPlayer.model.Album;
import com.CocoPlayer.provider.DBHelper;
import com.CocoPlayer.provider.Storage;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.cocoplayer.R;

public class AlbumCollection extends SherlockActivity {
	
	
	private MenuDrawer mDrawer;
	
	private AlbumCollectionHandler mHandler = new AlbumCollectionHandler();
	private AlbumAdapter mAlbumAdapter;
	
	
	class AlbumCollectionHandler extends Handler {
		
		public void newAlbum(final Album newAlbum) {
			
			runOnUiThread(new Runnable() {
				public void run() {
					mAlbumAdapter.mAlbums.add(newAlbum);
					mHandler.dataChanged();
				}
			});
			
		}
		
		public void dataChanged() {
			runOnUiThread(new Runnable() {
				public void run() {
					mAlbumAdapter.notifyDataSetChanged();
				}
			});
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		setContentView(R.layout.view_album_collection);
		
		GridView albumGridView =(GridView)findViewById(R.id.gridView_Album);
		
		
		try {
			mAlbumAdapter = new AlbumAdapter(this,Storage.getInstance(this).getAlbums());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		albumGridView.setAdapter(mAlbumAdapter);
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_album_add:
			onNewAlbum("");
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onNewAlbum(String name) {
		
		Album newAlbum = new Album();
		//通过handler，异步告诉adpater添加了一个新的album
		newAlbum.setName("first album");
		newAlbum.setDescription("let's check out if database works");
		Storage.getInstance(this).addNewAlbumIntoDatabase(newAlbum);
		mHandler.newAlbum(newAlbum);
	}


	private class AlbumAdapter extends BaseAdapter {
		
		private Context mContext;
		private LayoutInflater mInflater;
		
		
		ArrayList<Album> mAlbums;
		
		public AlbumAdapter(Context context,ArrayList<Album> albums) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mAlbums = albums;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			if (null!=mAlbums) {
				return mAlbums.size();
			}else {
				return 0;
			}
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAlbums.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			AlbumViewHolder holder;		
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_album, null);
				holder = new AlbumViewHolder();
				holder.imageView = (ImageView)convertView.findViewById(R.id.image_album);
				holder.textView = (TextView)convertView.findViewById(R.id.text_album_name);
				
				convertView.setTag(holder);
			}
			else {
				holder = (AlbumViewHolder)convertView.getTag();
			}
			
			holder.textView.setText(mAlbums.get(position).getName());
			if (mAlbums.get(position).getIconUri()== null) {
				holder.imageView.setImageResource(R.drawable.albums);
			}else {
				holder.imageView.setImageURI(Uri.parse(mAlbums.get(position).getIconUri()));
			}
			return convertView;
		}
		
	}
	
	private class AlbumViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
