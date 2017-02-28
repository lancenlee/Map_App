package com.example.cil_212_app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;

public class IdProperties {

	Context context;
	Activity activity;
	public IdProperties(Context context,Activity activity){
		this.context=context;
		this.activity=activity;
	}
	public HashMap<Integer, String> idproperties(HashMap<Integer, String>idmap,String name,ArrayList<Integer>list){
		Properties properties=new Properties();
		try {
			properties.load(activity.getAssets().open(name));
			for(int i=0;i<list.size();i++){
				idmap.put(list.get(i), properties.getProperty("0x"+Integer.toHexString(list.get(i))));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idmap;
	}
}
