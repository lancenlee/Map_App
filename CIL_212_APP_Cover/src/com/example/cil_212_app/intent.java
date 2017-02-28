package com.example.cil_212_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class intent {
	
	public Intent getIntent(Activity activity,String url,String name){
		
		/*
		 * 1.����Intent����...����Activity
		 * 2.��Bundle�����з�������..ͨ��Intent���д���...�µ�Activity���Դ��л�ȡ������Ϣ..
		 *  
		 * */
		Intent it=new Intent();
		it.setClass(activity, Android_basic.class);
		Bundle bundle=new Bundle();
		bundle.putString("url", url);
		bundle.putString("name", name);
		it.putExtras(bundle);
		return it;
	}

}
