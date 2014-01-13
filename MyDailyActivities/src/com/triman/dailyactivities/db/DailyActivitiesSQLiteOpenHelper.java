package com.triman.dailyactivities.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DailyActivitiesSQLiteOpenHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "dailyactivities.db";
	public static final int DATABASE_VERSION = 2;
	
	/**
	 * 活动表
	 */
	public static final String TABLE_DAILY_ACTIVITY = "dailyactivity";
	public static final String KEY_DA_ID = "id";
	public static final String KEY_DA_TIME = "time";
	public static final String KEY_DA_THEME = "theme";
	public static final String KEY_DA_ADDRESS = "address";
	
	/**
	 * 动态表
	 */
	public static final String TABLE_DYNAMIC = "dynamic";
	public static final String KEY_DM_ID = "id";
	public static final String KEY_DM_TIME = "time";
	public static final String KEY_DM_CONTENT = "content";
	
	/**
	 * 参与者表
	 */
	public static final String TABLE_PARTICIPANT = "participant";
	public static final String KEY_PP_ID = "id";
	public static final String KEY_PP_NAME = "name";
	public static final String KEY_PP_PHONE = "phone";
	public static final String KEY_PP_DA_ID = "dailyactivityid";
	
	private static final String CREATE_TABLE_DAILY_ACTIVITY = "create table " +
			TABLE_DAILY_ACTIVITY + " (" + KEY_DA_ID +
			" integer primary key autoincrement, " +
			KEY_DA_TIME + " long not null, " +
			KEY_DA_THEME + " text not null, " +
			KEY_DA_ADDRESS + " text not null" + ");";
	
	private static final String CREATE_TABLE_DYNAMIC = "create table " +
			TABLE_DYNAMIC + " (" + KEY_DM_ID +
			" integer primary key autoincrement, " +
			KEY_DM_TIME + " long not null, " +
			KEY_DM_CONTENT + " text not null" + ");";
	
	private static final String CREATE_TABLE_PARTICIPANT = "create table " +
			TABLE_PARTICIPANT + " (" + KEY_PP_ID +
			" integer primary key autoincrement, " +
			KEY_PP_DA_ID + " integer not null, " +
			KEY_PP_NAME + " text not null, " +
			KEY_PP_PHONE + " text not null" + ");";
	

	public DailyActivitiesSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_DAILY_ACTIVITY);
		db.execSQL(CREATE_TABLE_DYNAMIC);
		db.execSQL(CREATE_TABLE_PARTICIPANT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DailyActivitiesSQLiteOpenHelper",
				"Upgrading from version " + oldVersion + " to " + newVersion + ", which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_ACTIVITY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DYNAMIC);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANT);
		onCreate(db);
	}
	
}
