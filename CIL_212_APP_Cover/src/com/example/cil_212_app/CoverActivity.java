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
	
	
	
	int[] images = null;//ͼƬ��ԴID
	String[] titles = null;//����
	int main_image_id[]=new int[]{R.id.main_android,R.id.main_java,R.id.main_c,R.id.main_html};
	ImageButton image_but[]=new ImageButton[4];
	ArrayList<ImageView> imageSource = null;//ͼƬ��Դ
	ArrayList<View> dots = null;//��
	TextView title = null;
	ViewPager viewPager;//������ʾͼƬ
	MyPagerAdapter  adapter;//viewPager��������
	private  int currPage = 0;//��ǰ��ʾ��ҳ
	private  int oldPage = 0;//��һ����ʾ��ҳ
	
	
	/*
	 * �����ж��Ƿ��������������...
	 * 
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//�жϵ�ǰ�Ƿ��������������...
		ConnectivityManager manger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo info = manger.getActiveNetworkInfo();  
		if(info!=null&&info.isConnected()){
			
		}else{
			Toast.makeText(CoverActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder dialog = new AlertDialog.Builder(CoverActivity.this);
			dialog.setTitle("������������,�����������������,�ܶ๦�ܽ��޷�����!");
			dialog.setNegativeButton("ȷ��",new DialogInterface.OnClickListener() {
				
				@SuppressLint("NewApi")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO �Զ����ɵķ������
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
				"���ǵ�1��ͼƬ",
				"���ǵ�2��ͼƬ",
				"���ǵ�3��ͼƬ",
				"���ǵ�4��ͼƬ",
				"���ǵ�5��ͼƬ"
		};
		//��ȡ��������������ͼƬid����Ϊ���ĸ�ͼƬ��ť�󶨼���...
		for(int i=0;i<4;i++){
			image_but[i]=(ImageButton) findViewById(main_image_id[i]);
			image_but[i].setOnClickListener(this);
			image_but[i].setOnTouchListener(this);
		}
		//��Ҫ��ʾ��ͼƬ�ŵ�list������
		imageSource = new ArrayList<ImageView>();
		for(int i = 0; i < images.length;i++){
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
		}
		
		//��ȡ��ʾ�ĵ㣨�������·��ĵ㣬��ʾ��ǰ�ǵڼ��ţ�
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));
		
		//ͼƬ�ı���
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);
		//��ʾͼƬ��VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		//ΪviewPager����������
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		//ΪviewPager��Ӽ��������ü��������ڵ�ͼƬ�任ʱ������͵�Ҳ���ű仯
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);
		
		//������ʱ����ÿ��2���Զ�������һ�ţ�ͨ�������߳�ʵ�֣�����Timer���ƣ���ʹ��Timer���棩
		ScheduledExecutorService scheduled =  Executors.newSingleThreadScheduledExecutor();
		//����һ���̣߳����߳�����֪ͨUI�̱߳任ͼƬ
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}
	
//	ViewPagerÿ�ν�����������ͼƬ�������ڷ�ֹ�����ڴ������
	private class MyPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
//			System.out.println("getCount");
			return images.length;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			//�жϽ�Ҫ��ʾ��ͼƬ�Ƿ��������ʾ��ͼƬ��ͬһ��
			//arg0Ϊ��ǰ��ʾ��ͼƬ��arg1�ǽ�Ҫ��ʾ��ͼƬ
//			System.out.println("isViewFromObject");
			return arg0 == arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			System.out.println("destroyItem==" + position);
			//���ٸ�ͼƬ
			container.removeView(imageSource.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//��ʼ����Ҫ��ʾ��ͼƬ������ͼƬ���뵽container�У���viewPager��
			container.addView(imageSource.get(position));
//			System.out.println("instantiateItem===" + position +"===="+container.getChildCount());
			return imageSource.get(position);
		}
	}

	//����ViewPager�ı仯
	private class MyPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			//����ʾ��ͼƬ�����仯֮��
			//���ñ���
			title.setText(titles[position]);
			//�ı���״̬
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			//��¼��ҳ��
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable{
		@Override
		public void run() {
			//�ı䵱ǰҳ���ֵ
			currPage =(currPage+ 1)%images.length;
			//������Ϣ��UI�߳�
			handler.sendEmptyMessage(0);
		}
	}
	
	private Handler handler= new Handler(){
		public void handleMessage(Message msg) {
			//���յ���Ϣ�󣬸���ҳ��
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
			Toast.makeText(CoverActivity.this, "���޹���", Toast.LENGTH_LONG).show();
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
