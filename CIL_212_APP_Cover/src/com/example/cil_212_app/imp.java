package com.example.cil_212_app;

import java.util.ArrayList;
import java.util.HashMap;
import dao.findviewdao;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;


class imp implements findviewdao, View.OnClickListener{

	/*
	 * 此类用于实现imp DAO...
	 * 1.封装findViewById方法...
	 * 2.由于普通类是无法直接调用Activity方法的...因此需要传递当前的Activity对象和Context对象..    
	 *     
	 * */

	Activity act;
	Context con;
	HashMap<Integer, String>activity_map;
	imp(Activity activity,Context context){
		this.act=activity;
		this.con=context;
	}
	
	public void set_activity_map(HashMap<Integer, String>map){
		this.activity_map=map;
	}
	
	public void setClick(ImageButton[] view){
		for(int j=0;j<view.length;j++){
			try {
				view[j].setOnClickListener(this);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("非常异常！");
			}
			
		}
	}
	
	public void findjavaImgview(ImageButton[] view, ArrayList<Integer> array,View v) {
		// TODO Auto-generated method stub
		for(int i=0;i<array.size();i++){			
				view[i]=(ImageButton) v.findViewById(array.get(i));
				view[i].setOnClickListener(this);
		}
	}
	
	@Override
	public void findImgview(ImageButton[] view, ArrayList<Integer> array) {
		// TODO Auto-generated method stub
		for(int i=0;i<array.size();i++){			
				view[i]=(ImageButton) act.findViewById(array.get(i));
		}
	}
	
	public ArrayList<Integer> getid(ArrayList<Integer> array,String name){

		int array_size=0;
		String nameid="";
		if(name.equals("Android")){
		    array_size=15;
		    nameid="android"+"_"+"main"+"_";	
			
		}else if(name.equals("java")){
		    array_size=5;
		    nameid="java"+"_";

		}else if(name.equals("c/c++")){
			array_size=6;
			nameid="c/c++";

		}else if(name.equals("web")){
			array_size=6;
			nameid="web";
			
		}else if(name.equals("java_next")){
			array_size=5;
			nameid="java"+"_"+"next"+"_";
		}
		getresid(array, array_size, nameid);
		return array;
	}

	public ArrayList<Integer> getresid(ArrayList<Integer>array_res,int array_size,String id){
		for(int j=0;j<array_size;j++){
			int o=j+1;
			String nameid=id+o;
			int resid=con.getResources().getIdentifier(nameid, "id", "com.example.cil_212_app");
			array_res.add(resid);
		}
		return array_res;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer resid=v.getId();
		String activityname=activity_map.get(resid);
		System.out.println(activityname);
		/*
		 * 使用发射机制来消除switch case语句的判断...
		 * 
		 * */
		reflect r=new reflect();
		r.getReflectname(activityname,act);
	}
		
}
