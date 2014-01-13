package com.triman.dailyactivities.utils;

import java.util.Date;

import android.content.Context;

public class Utils {
	/**
	 * 根据dp值转换成px值
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据px值转换成dp值
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 将时间转换为Date
	 * @param time
	 * @return
	 */
	public static Date longToDate(long time){
		Date d = new Date();
		d.setTime(time);
		return d;
	}
}
