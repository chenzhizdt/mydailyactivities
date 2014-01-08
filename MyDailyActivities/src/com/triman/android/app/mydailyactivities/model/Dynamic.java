package com.triman.android.app.mydailyactivities.model;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Dynamic {
	
	private String content = "你在2014年01月01日参加了活动";
	private Date time = new Date();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:ss");
		return sdf.format(time);
	}
	
	public String getDayStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(time);
	}
}
