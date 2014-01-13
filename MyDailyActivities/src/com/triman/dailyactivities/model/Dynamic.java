package com.triman.dailyactivities.model;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.triman.dailyactivities.utils.Utils;

@SuppressLint("SimpleDateFormat")
public class Dynamic {
	
	private String content = "你在2014年01月01日参加了活动";
	private long time = new Date().getTime();
	private int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
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
