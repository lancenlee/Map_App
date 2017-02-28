package com.example.cil_212_app;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


interface factory_startactivity {
	public abstract void startactivity(Activity activity,String name);
}
	/*  
	 *  以下每一个类决定了每一个按钮被点击后所触发的效果...
	 *  分别用来传递不同的信息...
	 * */

class BasicActivity implements factory_startactivity {
	
	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android.xml",name));
	}
}

class UIActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_ui.xml",name));
	}
	
}



class EventActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_event.xml",name));
	}
	
}

class AdvancedActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_advanced_view.xml",name));
	}
	
}

class CommunicateActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_com.xml",name));
	}
	
}

class Multi_MediaActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_multi.xml",name));
	}
	
}

class DataActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_data.xml",name));
	}
	
}

class NetWorkActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_net.xml",name));
	}
	
}

class GameActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_game.xml",name));
	}
	
}

class interviewActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/android_interview.xml",name));
		
	}
	
}

class AdvanceinventActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
//		intent i=new intent();	
//		context.startActivity(i.getIntent(context, "AdvanceinvnetActivity"));
		Toast.makeText(activity, "暂无内容",Toast.LENGTH_LONG).show();
	}
	
}

class Android_Acticity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		
	}
	
}

class Java_Activity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		activity.startActivity(new Intent(activity,JavaActivity.class));
		activity.finish();
	}
	
}

class C_Activity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		activity.startActivity(new Intent(activity,CActivity.class));
		activity.finish();
		
	}
	
}

class Web_Activity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		activity.startActivity(new Intent(activity,Web.class));
		activity.finish();
	}
}

class JavaBasicActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		try {
			intent i=new intent();	
			activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_basic.xml",name));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("异常...");
		}
		
		
	}
	
}

class JavaArithmeticActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_arithmetic.xml",name));
		
	}
	
}

class DataBaseAcitvity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_database.xml",name));
		
	}
	
}

class JavaWebActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_web.xml",name));
		
	}
	
}

class DesignActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_design.xml",name));
		
	}
	
}

class JavaFrameActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_frame.xml",name));
		
	}
	
}

class JavaEEActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_ee.xml",name));
		
	}
	
}

class JavaAdvancedActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_advanced.xml",name));
		
	}
	
}

class JavaInterviewActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_interview.xml",name));
		
	}
	
}

class JavaAPIActivity implements factory_startactivity{

	@Override
	public void startactivity(Activity activity,String name) {
		// TODO Auto-generated method stub
		intent i=new intent();	
		activity.startActivity(i.getIntent(activity, "http://www.iitbox.com/admin/java_api.xml",name));
		
	}
	
}
class Factory{
	/*
	 * 工厂模式... 
	 * */
	public static factory_startactivity getInstance(String name){
		factory_startactivity fs=null;
		try {
			Class clazz=Class.forName(name);
			fs=(factory_startactivity) clazz.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fs;
	}
}

class reflect{
	public void getReflectname(String name,Activity activity){
		//获取每一个类名...
		String reflectname="com.example.cil_212_app"+"."+name;
		factory_startactivity f=Factory.getInstance(reflectname);	
		if(f!=null){	
			
			f.startactivity(activity,name);
		}
	}
}