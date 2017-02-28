package com.example.cil_212_app;


import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Android_basic extends Activity {

	
	static final String KEY_CONTENT="content";
	static final String KEY_ID="id";
	static final String KEY_TITLE="title";
	static final String KEY_ARTIST="artist";
	static final String KEY_DURATION="duration";
	static final String KEY_THUMB_URL="thumb_url";
	ListView list;
	LazyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_content);
		//由于现在版本的Android不能在主线程中进行网络操作...因此使用 StrictMode...
		//其实完全可以使用AsyncTask异步线程解决这个问题...不过StrictMode更方便...
		//这里需要加判断条件...否则是无效的...
		if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		
		//通过Intent中的额外属性来解析不同的XML文档...
	    Intent intent=this.getIntent();
	    Bundle bundle=intent.getExtras();
	    String url=bundle.getString("url");
	    final String activity_name=bundle.getString("name");
	    
	    ArrayList<HashMap<String, String>>contentlist=new ArrayList<HashMap<String,String>>();
	    //解析XML...
	    XMLParser parser=new XMLParser();
	    String xml=parser.getXmlFromUrl(url);
	    //对解析后的DOM树节点的获取...
	    Document doc=parser.getDomElement(xml);
    
	  
	    NodeList nl=doc.getElementsByTagName(KEY_CONTENT);
	    //循环遍历所有的节点...
	    for(int i=0;i<nl.getLength();i++){
	    	HashMap<String, String>map=new HashMap<String, String>();
	    	Element e=(Element) nl.item(i);
	    	//将每一个父节点的子节点全部保存到Map当中...
	    	map.put(KEY_ID, parser.getValue(e, KEY_ID));  
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));  
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));  
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));  
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));  
            contentlist.add(map);
            
	        }
	    
	    list=(ListView)findViewById(R.id.android_content_list);  
        //为ListView绑定适配器...    
      adapter=new LazyAdapter(this, contentlist);     
      
      list.setAdapter(adapter);  
        

      //为单一列表行添加单击事件  
      
      /*
       * 最后一步:触发事件+数据库内容的获取...
       * 
       * */

      list.setOnItemClickListener(new OnItemClickListener() {  

          @Override  
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
        	  //使所有的列表共用一个布局...通过从数据库获取数据显示到布局当中...
        	 
        	  Intent intent_content=new Intent(Android_basic.this,ContentActivity.class);
        	  Bundle bundle=new Bundle();
        	  bundle.putString("name", activity_name);
        	  bundle.putInt("position", position);
        	  intent_content.putExtras(bundle);
        	  startActivity(intent_content);
        	  }  
      });       
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_basic, menu);
		return true;
	}

}
