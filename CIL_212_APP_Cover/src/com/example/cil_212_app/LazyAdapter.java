package com.example.cil_212_app;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter{

	private Activity activity;
	private ArrayList<HashMap<String, String>>android_basic_data;
	private static LayoutInflater inflater;
	public ImageLoader imageLoader;
	
	//将DOM解析树的节点保存在Activity中的ArrayList中...
	public LazyAdapter(Activity a,ArrayList<HashMap<String, String>>android_basic){
		this.activity=a;
		this.android_basic_data=android_basic;
		inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return android_basic_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/* 
		 * 自定义列表布局...
		 * 
		 * */
		View v=convertView;
		v=inflater.inflate(R.layout.android_list_raw, null);
		TextView title=(TextView) v.findViewById(R.id.title);
		TextView artist=(TextView) v.findViewById(R.id.artist);
		TextView duration=(TextView) v.findViewById(R.id.duration);
		ImageView thumb_image=(ImageView) v.findViewById(R.id.list_image);
		
		
		HashMap<String, String>content=new HashMap<String, String>();
		content=android_basic_data.get(position);
		
		//为每一个列表项设置显示的内容...显示的内容为XML文档的内容...
		title.setText(content.get(Android_basic.KEY_TITLE));
		artist.setText(content.get(Android_basic.KEY_ARTIST));
		duration.setText(content.get(Android_basic.KEY_DURATION));
		imageLoader.DisplayImage(content.get(Android_basic.KEY_THUMB_URL), thumb_image);  //将图片的资源进行更改...
		
		return v;
	}

}
