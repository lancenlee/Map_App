package com.example.cil_212_app;

import java.io.File;

import android.content.Context;

public class FileCache {

	private File cacheDir;
	
	public FileCache(Context context){
		
		//���ļ��д��ڵ�ʱ��ִ�д˹���...
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"android_basic");
		}else{
			cacheDir=context.getCacheDir();
		}
		if(!cacheDir.exists()){
			cacheDir.mkdirs();
		}
	}
	
	public File getFile(String url){
		String filename=String.valueOf(url.hashCode());
		File f=new File(cacheDir,filename);
		return f;
	}
	
	//�����е��ļ������������...
	public void clear(){
		File[] files=cacheDir.listFiles();
		if(files==null){
			return;
		}else{
			for(File f:files){
				f.delete();
			}
		}
	}
}
