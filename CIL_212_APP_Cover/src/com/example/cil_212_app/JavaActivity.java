package com.example.cil_212_app;


import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


/*
 * Javaҳ��
 * */

public class JavaActivity extends Activity implements View.OnClickListener  {

	Context context_java;
	Activity activity_java;
	ArrayList<Integer>integer_java=new ArrayList<Integer>();
	ArrayList<Integer>integer_java_next=new ArrayList<Integer>();
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, String>java_activity=new HashMap<Integer, String>();
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, String>java_next_activity=new HashMap<Integer, String>();
	ImageButton img_java_main[]=new ImageButton[5];
	ImageButton img_java_next[]=new ImageButton[5];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		activity_java=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_java_main);	
		initViewPager();
		
	}
	
	/*
	 *  ��ʼ������....
	 * */
	private void initViewPager(){
		ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);
		View view_java=LayoutInflater.from(this).inflate(R.layout.activity_java, null);
		
		//����activity��context
		context_java=ContextUtil.getInstance();
		imp i=new imp(activity_java, context_java);
		
		//��ȡ��Ӧ��ID��Ϣ..
		i.getid(integer_java,"java");
		i.findjavaImgview(img_java_main, integer_java, view_java);
		
		//��ȡProperties�ļ�....
		IdProperties id=new IdProperties(context_java, activity_java);
		id.idproperties(java_activity, "Java_id.properties", integer_java);
		//����map�е�ֵ��Ŀ����ʵ��OnClick�����ĵ���...
		i.set_activity_map(java_activity);
		
		View view_java_next=LayoutInflater.from(this).inflate(R.layout.activity_java_next, null);
		
		imp i_next=new imp(activity_java, context_java);
		i_next.getid(integer_java_next, "java_next");
	
		i_next.findjavaImgview(img_java_next, integer_java_next, view_java_next);
		
		IdProperties next_id=new IdProperties(context_java,activity_java);
		next_id.idproperties(java_next_activity, "Java_next_id.properties", integer_java_next);	
		i_next.set_activity_map(java_next_activity);
		
		//����һ��������ͼ�ļ���...
		ArrayList<View> views=new ArrayList<View>();
		views.add(view_java);
		views.add(view_java_next);
		
		//����������...
		MyViewPagerAdapter adapter=new MyViewPagerAdapter();
		adapter.setViews(views);
		viewPager.setAdapter(adapter);   
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent=new Intent(JavaActivity.this,CoverActivity.class);
			startActivity(intent);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.java, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_LONG).show();
	}

	

}
