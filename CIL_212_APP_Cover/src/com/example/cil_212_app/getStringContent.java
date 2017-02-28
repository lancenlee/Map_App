package com.example.cil_212_app;

import java.io.File;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class getStringContent {

	String DataBaseName=Environment.getExternalStorageDirectory()+File.separator+"cildatabase"+File.separator+"android.db";
	String s="";
	String titlestring="";
	public String getString(){
		int i=1;
		SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(DataBaseName, null);
		String selectstring="select content from android_basic where id=1";
		Cursor result=database.rawQuery(selectstring, null);
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			System.out.println(result.getString(0));
			System.out.println(i++);
			s+=result.getString(0);
		}
		return s;
	}
	public String getTitle(){
		SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(DataBaseName, null);
		String title="select title from android_basic where id=1";
		Cursor result_title=database.rawQuery(title, null);
		for(result_title.moveToFirst();!result_title.isAfterLast();result_title.moveToNext()){
			titlestring=titlestring.concat(result_title.getColumnName(0));
		}
		return titlestring;
	}
}
