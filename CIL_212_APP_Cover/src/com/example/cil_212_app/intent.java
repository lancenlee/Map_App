package com.example.cil_212_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class intent {
	
	public Intent getIntent(Activity activity,String url,String name){
		
		/*
		 * 1.定义Intent对象...启动Activity
		 * 2.在Bundle对象中放入数据..通过Intent进行传递...新的Activity可以从中获取数据信息..
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
