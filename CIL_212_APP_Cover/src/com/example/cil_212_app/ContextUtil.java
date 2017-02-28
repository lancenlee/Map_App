package com.example.cil_212_app;
/* 
 * @param
 * 获取当前的Activity对象...
 * */
import android.app.Application;

public class ContextUtil extends Application{

	private static ContextUtil instance;
	
	public static ContextUtil getInstance(){
		return instance;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		instance=this;
	}

}
