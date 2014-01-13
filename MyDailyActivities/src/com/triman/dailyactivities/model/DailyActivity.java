package com.triman.dailyactivities.model;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.triman.dailyactivities.utils.Utils;

@SuppressLint("SimpleDateFormat")
public class DailyActivity {
	private long time = new Date().getTime();
	private String theme = "";
	private String address = "";
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
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
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		return sdf.format(Utils.longToDate(time));
	}
	public String getDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		return sdf.format(Utils.longToDate(time));
	}
}
