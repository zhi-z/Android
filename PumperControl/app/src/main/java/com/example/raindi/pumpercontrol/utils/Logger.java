package com.example.raindi.pumpercontrol.utils;

import android.util.Log;

import com.example.raindi.pumpercontrol.BuildConfig;

public class Logger {
	private static String TAG = "yjx";
	private static boolean Debug = BuildConfig.DEBUG;
	public static void E(String str){
		if (Debug)
			Log.e(TAG,str);
	}
	
	public static void e(Class className,String msg){
		if (Debug)
			Log.e(TAG,className.getName()+": "+ msg);
	}
	
	public static void E(String tag,String str){
		if (Debug)
			Log.e(tag,str);
	}
	
	public static void I(String str){
		if (Debug)
			Log.i(TAG,str);
	}
	
	public static void I(String tag,String str){
		if (Debug)
			Log.i(tag,str);
	}
	
	public static void i(Class className,String msg){
		if (Debug)
			Log.i(TAG,className.getName()+":"+ msg);
	}
	
	public static void D(String str){
		if (Debug)
			Log.d(TAG,str);
	}

	public static void d(Class className,String msg){
		if (Debug)
			Log.d(TAG,className.getName()+":"+ msg);
	}
	
	public static void d(String msg){
		if (Debug)
			Log.d(TAG, msg);
	}
	
	public static void D(String tag,String str){
		if (Debug)
			Log.d(tag,str);
	}
}
