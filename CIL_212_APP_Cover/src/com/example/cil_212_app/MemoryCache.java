package com.example.cil_212_app;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class MemoryCache {

	//��HashMap��װ��һ���̰߳�ȫ�ļ��ϣ�����ʹ�������õķ�ʽ��ֹOOM���ڴ治�㣩...
    //������ListView�л���ش�����ͼƬ.��ôΪ����Ч�ķ�ֹOOM���³�����ֹ�����...
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
