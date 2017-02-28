package com.example.cil_212_app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class updatedatabase implements Runnable{

	private String DATABASE_URL="";
	private String path="";
	public updatedatabase(String url,String path) {
		// TODO Auto-generated constructor stub
		this.DATABASE_URL=url;
		this.path=path;
	}
	
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		try {		
			URL url=new URL(DATABASE_URL);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.connect();
			BufferedInputStream bin=new BufferedInputStream(connection.getInputStream());
			
			
			File file=new File(path);
			OutputStream out=new FileOutputStream(file);
			int size=0;
			byte buf[]=new byte[1024*7];
			while((size=bin.read(buf))!=-1){
				out.write(buf, 0, size);
			}
			out.flush();
			out.close();
			bin.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
