package com.example.cil_212_app;

public class getVideoUrl {

	String name;
	int position;
	String url="";
	
	public getVideoUrl(String name,int position){
		this.name=name;
		this.position=position;
	}
	public String reUrl(){
		url="http://www.iitbox.com/admin/"+name+"_"+position+".mp4";
		return url;
	}
	
}
