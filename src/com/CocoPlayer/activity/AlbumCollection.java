package com.CocoPlayer.activity;

import java.util.ArrayList;

import net.simonvt.menudrawer.MenuDrawer;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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
import com.actionbarsherlock.app.SherlockActivity;
import com.example.cocoplayer.R;

public class AlbumCollection extends SherlockActivity {
	
	
	private MenuDrawer mDrawer;
	private ArrayList<Album> mAlbums;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		setContentView(R.layout.view_album_collection);
		
		GridView albumGridView =(GridView)findViewById(R.id.gridView_Album);
		mAlbums = new ArrayList<Album>();
		Album album1 = new Album("hello",null);
		Album album2 = new Album("HI", null);
		mAlbums.add(album1);
		mAlbums.add(album2);
		albumGridView.setAdapter(new AlbumAdapter(this,mAlbums));
		
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
