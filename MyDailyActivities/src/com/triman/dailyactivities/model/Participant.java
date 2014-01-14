package com.triman.dailyactivities.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Participant implements Parcelable{
	
	private String name = "姓名";
	private String phone = "130XXXXXXXX";
	private int dailyActivityId = -1;
	private int id = -1;
	private boolean isExist = false;
	
	public static final Parcelable.Creator<Participant> CREATOR = new Creator<Participant>(){

		@Override
		public Participant createFromParcel(Parcel source) {
			Participant p = new Participant();
			p.name = source.readString();
			p.phone = source.readString();
			p.dailyActivityId = source.readInt();
			p.id = source.readInt();
			return p;
		}

		@Override
		public Participant[] newArray(int size) {
			return new Participant[size];
		}
		
	};
	
	
	public boolean isExist() {
		return isExist;
	}
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
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
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);  
        dest.writeString(phone);  
        dest.writeInt(dailyActivityId);
        dest.writeInt(id);
	}
}
