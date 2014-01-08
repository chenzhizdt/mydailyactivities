package com.triman.android.app.mydailyactivities.model;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DailyActivity {
	private Date time = new Date();
	private String theme = "主题内容是XXXXXX";
	private String address = "上海市静安区武宁南路488号智慧广场22楼";
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:ss");
		return sdf.format(time);
	}
	public String getDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(time);
	}
}
