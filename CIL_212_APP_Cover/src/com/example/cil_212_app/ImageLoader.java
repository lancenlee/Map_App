package com.example.cil_212_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageLoader {

	final int stub_id=R.drawable.no_image;
	MemoryCache memoryCache=new MemoryCache();
	FileCache fileCache;
	private Map<ImageView,String>imageviews=Collections.synchronizedMap(new HashMap<ImageView, String>());
	ExecutorService executorService;  //ʹ���̳߳�...
	
	/*
	 *  ʹ���̼߳���ͼƬ����...
	 *  
	 * */
	public ImageLoader(Context context){
		fileCache=new FileCache(context);
		executorService=Executors.newFixedThreadPool(5);  //���������̵߳��������...
	}
	
	/*
	 * ��ͼƬչʾ��ListView��...
	 * 
	 * */
	public void DisplayImage(String url,ImageView imageView){
		imageviews.put(imageView, url);
		Bitmap bitmap=memoryCache.get(url);
		if(bitmap!=null){
			imageView.setImageBitmap(bitmap);
		}else{
			queuePhoto(url,imageView);   //��ֹ���������ʱ�򲿷�����û�б�����...��˷���һ�����������..
			imageView.setImageResource(stub_id);  //����ͼƬ��ʱ��������ʾ...
		}
	}
	
	/*
	 * ����һ��ͼƬ���ض���...
	 * 
	 * */
	public void queuePhoto(String url, ImageView imageView){
		// TODO Auto-generated method stub
		PhotoToLoad p=new PhotoToLoad(url,imageView);  
		executorService.submit(new  PhotosLoader(p));  //�����̲߳��ύ�������ݶ���...
	}
	
	/*
	 * ��ȡͼƬ����...
	 * 
	 * */
	private Bitmap getBitmap(String url){  //������ȡ��url�ǲ����ģ�����Ҫʵ��ͼƬ������...
		
		File f=fileCache.getFile(url);  //���ļ������л�ȡurl SD��
		
		
		//��sd����ȡ...
		Bitmap b=decodeFile(f);
		if(b!=null)
			return b;
		
		//���sd��û��ͼƬ��Դ�Ļ���,��ô�������Ͻ�������...
		try {
			 Bitmap bitmap=null;
			 URL imageurl=new URL(url);
			
			 HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();   
			 
	         conn.setConnectTimeout(30000);   
	         conn.setReadTimeout(30000);   
	         conn.setInstanceFollowRedirects(true);   
	         
	         InputStream is=conn.getInputStream();  //��ȡ���ص�������... 
	         OutputStream os = new FileOutputStream(f);  //���ļ��������... 
	         Utils.CopyStream(is, os);   
	         os.close();   
	         is.close();
	         bitmap = decodeFile(f);   
	         return bitmap;   
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * ��ͼƬ���н����ѹ������...
	 * 
	 * */
	private Bitmap decodeFile(File f){   
	    try {   
	         //����ͼ���С,��ͼƬ��������...��ֹͼƬ�������ڴ治��...  
	         BitmapFactory.Options o = new BitmapFactory.Options();   
	         o.inJustDecodeBounds = true;   //Ŀ���ǵõ�ͼ��Ļ�����Ϣ�����ǲ�������ȫ�Ľ�ͼƬ���н������...
	         BitmapFactory.decodeStream(new FileInputStream(f),null,o); //����������ʽ���н���...���ؽ�����Bitmap 
	    
	         //�ҵ���ȷ�Ŀ̶�ֵ����Ӧ����2���ݡ�  
	         //��ͼƬ����ѹ������...��ֹͼƬ����...
	         final int REQUIRED_SIZE=70;   
	         int width_tmp=o.outWidth, height_tmp=o.outHeight;   
	         int scale=1;   
	         while(true){   
	             if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)   
	                 break;   
	             width_tmp/=2;   
	             height_tmp/=2;   
	             scale*=2;   
	         }   
	         BitmapFactory.Options o2 = new BitmapFactory.Options();   
	         o2.inSampleSize=scale;   //ͼ������ű���...
	         return BitmapFactory.decodeStream(new FileInputStream(f), null, o2); //����һ��С��ͼƬ...ͼƬ�Ĵ�С��inSampleSize����...
	         
	     }catch (Exception e){}   
	        return null;   
    }   
	
	/*
	 *  �����߳�...
	 *  
	 * */
	class PhotosLoader implements Runnable {   //�������...
	    PhotoToLoad photoToLoad;   
	    PhotosLoader(PhotoToLoad photoToLoad){   
	        this.photoToLoad=photoToLoad;   
	    }   
	 
	    @Override  
	    public void run() {   
	        if(imageViewReused(photoToLoad))   
	            return;   
	        //�������ͼƬ�Ļ�,����ͼƬ��Դ�Ļ�ȡ...
	            
	        Bitmap bmp=getBitmap(photoToLoad.url); //����������Ǵ�sd���л�ȡͼƬ��Դ...  
	        memoryCache.put(photoToLoad.url, bmp);  //����������ͼƬ����Դ��Ϣ��url 
	        if(imageViewReused(photoToLoad))   
	            return;   
	            
	        BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);   
	        Activity a=(Activity)photoToLoad.imageView.getContext();   //��������ʹ�øÿռ��context����...
	        a.runOnUiThread(bd);   //MainActivity���ø���UI���߳�...
	    }   
	}   
	
	
    boolean imageViewReused(PhotoToLoad photoToLoad){ 
    	
	    String tag=imageviews.get(photoToLoad.imageView);   
	    if(tag==null || !tag.equals(photoToLoad.url)) 
	        return true;   
	    return false;   
	}   
	    
	//����ͼƬ������...
    class BitmapDisplayer implements Runnable{   
	    Bitmap bitmap;   
	    PhotoToLoad photoToLoad; //����url��imageView  
	    public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}   
	    public void run(){   
	        if(imageViewReused(photoToLoad))   
	            return;   
	        //����Listview��ͼƬ...
	        if(bitmap!=null)   
	            photoToLoad.imageView.setImageBitmap(bitmap);   
	        else  
	            photoToLoad.imageView.setImageResource(stub_id);   
	    }   
	}   
}
