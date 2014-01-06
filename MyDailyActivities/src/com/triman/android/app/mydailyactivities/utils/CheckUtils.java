package com.triman.android.app.mydailyactivities.utils;

public class CheckUtils {
	public static boolean isNullOrBlank(String str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}
}
