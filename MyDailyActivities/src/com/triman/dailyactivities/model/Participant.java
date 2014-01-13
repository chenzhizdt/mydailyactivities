package com.triman.dailyactivities.model;

public class Participant {
	
	private String name = "姓名";
	private String phone = "130XXXXXXXX";
	private int dailyActivityId;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDailyActivityId() {
		return dailyActivityId;
	}
	public void setDailyActivityId(int dailyActivityId) {
		this.dailyActivityId = dailyActivityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
