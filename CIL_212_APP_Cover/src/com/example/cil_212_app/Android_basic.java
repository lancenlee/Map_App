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
		//�������ڰ汾��Android���������߳��н����������...���ʹ�� StrictMode...
		//��ʵ��ȫ����ʹ��AsyncTask�첽�߳̽���������...����StrictMode������...
		//������Ҫ���ж�����...��������Ч��...
		if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		
		//ͨ��Intent�еĶ���������������ͬ��XML�ĵ�...
	    Intent intent=this.getIntent();
	    Bundle bundle=intent.getExtras();
	    String url=bundle.getString("url");
	    final String activity_name=bundle.getString("name");
	    
	    ArrayList<HashMap<String, String>>contentlist=new ArrayList<HashMap<String,String>>();
	    //����XML...
	    XMLParser parser=new XMLParser();
	    String xml=parser.getXmlFromUrl(url);
	    //�Խ������DOM���ڵ�Ļ�ȡ...
	    Document doc=parser.getDomElement(xml);
    
	  
	    NodeList nl=doc.getElementsByTagName(KEY_CONTENT);
	    //ѭ���������еĽڵ�...
	    for(int i=0;i<nl.getLength();i++){
	    	HashMap<String, String>map=new HashMap<String, String>();
	    	Element e=(Element) nl.item(i);
	    	//��ÿһ�����ڵ���ӽڵ�ȫ�����浽Map����...
	    	map.put(KEY_ID, parser.getValue(e, KEY_ID));  
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));  
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));  
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));  
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));  
            contentlist.add(map);
            
	        }
	    
	    list=(ListView)findViewById(R.id.android_content_list);  
        //ΪListView��������...    
      adapter=new LazyAdapter(this, contentlist);     
      
      list.setAdapter(adapter);  
        

      //Ϊ��һ�б�����ӵ����¼�  
      
      /*
       * ���һ��:�����¼�+���ݿ����ݵĻ�ȡ...
       * 
       * */

      list.setOnItemClickListener(new OnItemClickListener() {  

          @Override  
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
        	  //ʹ���е��б���һ������...ͨ�������ݿ��ȡ������ʾ�����ֵ���...
        	 
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
