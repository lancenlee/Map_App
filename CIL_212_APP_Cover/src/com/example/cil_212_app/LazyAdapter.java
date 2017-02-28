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
	
	//��DOM�������Ľڵ㱣����Activity�е�ArrayList��...
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
		 * �Զ����б���...
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
		
		//Ϊÿһ���б���������ʾ������...��ʾ������ΪXML�ĵ�������...
		title.setText(content.get(Android_basic.KEY_TITLE));
		artist.setText(content.get(Android_basic.KEY_ARTIST));
		duration.setText(content.get(Android_basic.KEY_DURATION));
		imageLoader.DisplayImage(content.get(Android_basic.KEY_THUMB_URL), thumb_image);  //��ͼƬ����Դ���и���...
		
		return v;
	}

}
