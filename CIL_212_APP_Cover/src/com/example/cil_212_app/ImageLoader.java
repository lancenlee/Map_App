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
	ExecutorService executorService;  //使用线程池...
	
	/*
	 *  使用线程加载图片数据...
	 *  
	 * */
	public ImageLoader(Context context){
		fileCache=new FileCache(context);
		executorService=Executors.newFixedThreadPool(5);  //设置运行线程的最大数量...
	}
	
	/*
	 * 将图片展示在ListView上...
	 * 
	 * */
	public void DisplayImage(String url,ImageView imageView){
		imageviews.put(imageView, url);
		Bitmap bitmap=memoryCache.get(url);
		if(bitmap!=null){
			imageView.setImageBitmap(bitmap);
		}else{
			queuePhoto(url,imageView);   //防止滑动过快的时候部分数据没有被更新...因此放入一个任务队列中..
			imageView.setImageResource(stub_id);  //设置图片暂时不进行显示...
		}
	}
	
	/*
	 * 定义一个图片加载队列...
	 * 
	 * */
	public void queuePhoto(String url, ImageView imageView){
		// TODO Auto-generated method stub
		PhotoToLoad p=new PhotoToLoad(url,imageView);  
		executorService.submit(new  PhotosLoader(p));  //开启线程并提交传递数据对象...
	}
	
	/*
	 * 获取图片数据...
	 * 
	 * */
	private Bitmap getBitmap(String url){  //仅仅获取到url是不够的，我们要实现图片的下载...
		
		File f=fileCache.getFile(url);  //从文件缓存中获取url SD卡
		
		
		//从sd卡获取...
		Bitmap b=decodeFile(f);
		if(b!=null)
			return b;
		
		//如果sd卡没有图片资源的缓存,那么从网络上进行下载...
		try {
			 Bitmap bitmap=null;
			 URL imageurl=new URL(url);
			
			 HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();   
			 
	         conn.setConnectTimeout(30000);   
	         conn.setReadTimeout(30000);   
	         conn.setInstanceFollowRedirects(true);   
	         
	         InputStream is=conn.getInputStream();  //获取下载的输入流... 
	         OutputStream os = new FileOutputStream(f);  //打开文件的输出流... 
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
	 * 对图片进行解码和压缩操作...
	 * 
	 * */
	private Bitmap decodeFile(File f){   
	    try {   
	         //解码图像大小,对图片进行缩放...防止图片过大导致内存不足...  
	         BitmapFactory.Options o = new BitmapFactory.Options();   
	         o.inJustDecodeBounds = true;   //目的是得到图像的基本信息，但是并不是完全的将图片进行解码操作...
	         BitmapFactory.decodeStream(new FileInputStream(f),null,o); //以码流的形式进行解码...返回解码后的Bitmap 
	    
	         //找到正确的刻度值，它应该是2的幂。  
	         //对图片进行压缩操作...防止图片过大...
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
	         o2.inSampleSize=scale;   //图像的缩放比例...
	         return BitmapFactory.decodeStream(new FileInputStream(f), null, o2); //返回一个小的图片...图片的大小由inSampleSize决定...
	         
	     }catch (Exception e){}   
	        return null;   
    }   
	
	/*
	 *  任务线程...
	 *  
	 * */
	class PhotosLoader implements Runnable {   //任务队列...
	    PhotoToLoad photoToLoad;   
	    PhotosLoader(PhotoToLoad photoToLoad){   
	        this.photoToLoad=photoToLoad;   
	    }   
	 
	    @Override  
	    public void run() {   
	        if(imageViewReused(photoToLoad))   
	            return;   
	        //如果是新图片的话,进行图片资源的获取...
	            
	        Bitmap bmp=getBitmap(photoToLoad.url); //从网络或者是从sd卡中获取图片资源...  
	        memoryCache.put(photoToLoad.url, bmp);  //缓冲区放入图片的资源信息和url 
	        if(imageViewReused(photoToLoad))   
	            return;   
	            
	        BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);   
	        Activity a=(Activity)photoToLoad.imageView.getContext();   //返回正在使用该空间的context对象...
	        a.runOnUiThread(bd);   //MainActivity调用更新UI的线程...
	    }   
	}   
	
	
    boolean imageViewReused(PhotoToLoad photoToLoad){ 
    	
	    String tag=imageviews.get(photoToLoad.imageView);   
	    if(tag==null || !tag.equals(photoToLoad.url)) 
	        return true;   
	    return false;   
	}   
	    
	//设置图片的数据...
    class BitmapDisplayer implements Runnable{   
	    Bitmap bitmap;   
	    PhotoToLoad photoToLoad; //保存url和imageView  
	    public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}   
	    public void run(){   
	        if(imageViewReused(photoToLoad))   
	            return;   
	        //设置Listview的图片...
	        if(bitmap!=null)   
	            photoToLoad.imageView.setImageBitmap(bitmap);   
	        else  
	            photoToLoad.imageView.setImageResource(stub_id);   
	    }   
	}   
}
