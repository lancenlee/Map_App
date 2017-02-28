package com.example.cil_212_app;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

	//将图片转换成流的形式进行写入操作...
	public static void CopyStream(InputStream is, OutputStream os){
		 final int buffer_size=1024;  
		 try {
			 byte[] bytes=new byte[buffer_size];  
			 for(;;){
				 int count=is.read(bytes, 0, buffer_size);
				 if(count==-1)  
					 break;
				 os.write(bytes, 0, count);   
			 }
			 is.close();  
	         os.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}	
}
