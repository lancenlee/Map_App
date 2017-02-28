/************************************************************************************/
/* File dir=new File(DATABASE_PATH);                                                */
/*	  if(!dir.exists()){                                                            */
/*	  	dir.mkdirs();                                                               */
/*	  }                                                                             */
/* String path=Environment.getExternalStorageDirectory()+File.separator+""          */
/* +File.separator+"android"+".db";                                                 */
/*                                                                                  */
/*	  File file=new File(path);                                                     */
/*	  if(!file.exists()){                                                           */
/*    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();        */
/*	  	new Thread(new updatedatabase(database_url,path)).start();                  */
/*	  }                                                                             */
/*                                                                                  */
/*                                                                                  */
/*                                                                                  */
/************************************************************************************/

package com.example.cil_212_app;


import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ImageButton;

public class AndroidActivity extends Activity {
	
	Context c;
	Activity activity;
	ArrayList<Integer> integer=new ArrayList<Integer>();
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, String>activity_map=new HashMap<Integer, String>();
	private ImageButton img_android_main[]= new ImageButton[15];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    activity=this;
		setContentView(R.layout.activity_android);	
					
		//获取当前的Context对象...
	    c=ContextUtil.getInstance();
		imp i=new imp(activity,c);
		
		//封装findViewById方法....
		i.getid(integer, "Android");
		i.findImgview(img_android_main, integer);
		
		//获取ID信息...
		IdProperties id=new IdProperties(c, activity);
		id.idproperties(activity_map, "id.properties", integer);
				
		i.set_activity_map(activity_map);
		i.setClick(img_android_main);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent=new Intent(AndroidActivity.this,CoverActivity.class);
			startActivity(intent);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android, menu);
		return true;
	}

}
