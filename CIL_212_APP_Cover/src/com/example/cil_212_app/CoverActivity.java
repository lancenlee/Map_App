package com.example.cil_212_app;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CoverActivity extends Activity implements View.OnClickListener, View.OnTouchListener{
	
	
	
	int[] images = null;//图片资源ID
	String[] titles = null;//标题
	int main_image_id[]=new int[]{R.id.main_android,R.id.main_java,R.id.main_c,R.id.main_html};
	ImageButton image_but[]=new ImageButton[4];
	ArrayList<ImageView> imageSource = null;//图片资源
	ArrayList<View> dots = null;//点
	TextView title = null;
	ViewPager viewPager;//用于显示图片
	MyPagerAdapter  adapter;//viewPager的适配器
	private  int currPage = 0;//当前显示的页
	private  int oldPage = 0;//上一次显示的页
	
	
	/*
	 * 首先判断是否进行了联网操作...
	 * 
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//判断当前是否进行了网络联接...
		ConnectivityManager manger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo info = manger.getActiveNetworkInfo();  
		if(info!=null&&info.isConnected()){
			
		}else{
			Toast.makeText(CoverActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder dialog = new AlertDialog.Builder(CoverActivity.this);
			dialog.setTitle("请检查网络连接,如果不进行网络联接,很多功能将无法运行!");
			dialog.setNegativeButton("确定",new DialogInterface.OnClickListener() {
				
				@SuppressLint("NewApi")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					if(android.os.Build.VERSION.SDK_INT > 10 ){
					    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
					} else {
					    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					} 
				}
			});
			dialog.show();
		}
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		init();
	}
	
	public void init(){
		images = new int[]{
				R.drawable.a,
				R.drawable.b,
				R.drawable.c,
				R.drawable.d,
				R.drawable.e,
		};
		titles = new String[]{
				"这是第1张图片",
				"这是第2张图片",
				"这是第3张图片",
				"这是第4张图片",
				"这是第5张图片"
		};
		//获取主界面的主界面的图片id，并为这四个图片按钮绑定监听...
		for(int i=0;i<4;i++){
			image_but[i]=(ImageButton) findViewById(main_image_id[i]);
			image_but[i].setOnClickListener(this);
			image_but[i].setOnTouchListener(this);
		}
		//将要显示的图片放到list集合中
		imageSource = new ArrayList<ImageView>();
		for(int i = 0; i < images.length;i++){
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
		}
		
		//获取显示的点（即文字下方的点，表示当前是第几张）
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));
		
		//图片的标题
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);
		//显示图片的VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		//为viewPager设置适配器
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		//为viewPager添加监听器，该监听器用于当图片变换时，标题和点也跟着变化
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);
		
		//开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
		ScheduledExecutorService scheduled =  Executors.newSingleThreadScheduledExecutor();
		//设置一个线程，该线程用于通知UI线程变换图片
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}
	
//	ViewPager每次仅最多加载三张图片（有利于防止发送内存溢出）
	private class MyPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
//			System.out.println("getCount");
			return images.length;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			//判断将要显示的图片是否和现在显示的图片是同一个
			//arg0为当前显示的图片，arg1是将要显示的图片
//			System.out.println("isViewFromObject");
			return arg0 == arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			System.out.println("destroyItem==" + position);
			//销毁该图片
			container.removeView(imageSource.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//初始化将要显示的图片，将该图片加入到container中，即viewPager中
			container.addView(imageSource.get(position));
//			System.out.println("instantiateItem===" + position +"===="+container.getChildCount());
			return imageSource.get(position);
		}
	}

	//监听ViewPager的变化
	private class MyPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			//当显示的图片发生变化之后
			//设置标题
			title.setText(titles[position]);
			//改变点的状态
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			//记录的页面
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable{
		@Override
		public void run() {
			//改变当前页面的值
			currPage =(currPage+ 1)%images.length;
			//发送消息给UI线程
			handler.sendEmptyMessage(0);
		}
	}
	
	private Handler handler= new Handler(){
		public void handleMessage(Message msg) {
			//接收到消息后，更新页面
			viewPager.setCurrentItem(currPage);
		};
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.main_android:
			Intent intent_android=new Intent(CoverActivity.this,AndroidActivity.class);
			startActivity(intent_android);
			this.finish();
			break;
		case R.id.main_java:
			Intent intent_java=new Intent(CoverActivity.this,JavaActivity.class);
			startActivity(intent_java);
			this.finish();
			break;
		case R.id.main_c:
			Toast.makeText(CoverActivity.this, "暂无功能", Toast.LENGTH_LONG).show();
			Intent intent_c=new Intent(CoverActivity.this,CActivity.class);
			startActivity(intent_c);
			this.finish();
			break;
		case R.id.main_html:
			Intent intent_web=new Intent(CoverActivity.this,Web.class);
			startActivity(intent_web);
			this.finish();
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			switch(v.getId()){
			case R.id.main_android:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.android_1));
				break;
			case R.id.main_c:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.cmanual));
				break;
			case R.id.main_html:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.html_1));
				break;
			case R.id.main_java:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.java_1));
				break;
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			switch(v.getId()){
			case R.id.main_android:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.android));
				break;
			case R.id.main_c:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.c_));
				break;
			case R.id.main_html:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.html_4));
				break;
			case R.id.main_java:
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.java_3));
				break;
			}
		}
		return false;
	}

}
