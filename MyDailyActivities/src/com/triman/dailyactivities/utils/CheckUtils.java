package com.triman.dailyactivities.utils;

public class CheckUtils {
	public static boolean isNullOrBlank(String str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}
}
