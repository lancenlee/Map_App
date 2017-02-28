package com.example.cil_212_app;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class MemoryCache {

	//将HashMap封装成一个线程安全的集合，并且使用软引用的方式防止OOM（内存不足）...
    //由于在ListView中会加载大量的图片.那么为了有效的防止OOM导致程序终止的情况...
	private Map<String,SoftReference<Bitmap>>cache=Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
	
	public Bitmap get(String id){
		if(!cache.containsKey(id))		
			return null;	
		SoftReference<Bitmap>ref=cache.get(id);
		return ref.get();
		
	}
	
	public void put(String id,Bitmap bitmap){
		cache.put(id, new SoftReference<Bitmap>(bitmap));
	}
	
	public void clear(){
		cache.clear();
	}
}
