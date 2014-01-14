package com.triman.dailyactivities.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DailyActivitiesContentProvider extends ContentProvider{
	
	public static final String AUTHORITY = "com.triman.dailyactivities";
	public static final String CONTETN_BASE_PATH = "database";
	public static final String BASE_CONTENT_URI = "content://" + AUTHORITY + "/" + CONTETN_BASE_PATH;
	public static final String VALUES_DAILY_ACTIVITY = "values_daily_activity";
	public static final String VALUES_DYNAMIC = "values_dynamic";
	public static final String VALUES_PARTICIPANT = "values_participant";
	
	private static final UriMatcher uri_matcher;
	private static final int DAILY_ACTIVITY = 1;
	private static final int DAILY_ACTIVITY_QUERY_BY_ROWID = 2;
	private static final int DYNAMIC = 3;
	private static final int DYNAMIC_QUERY_BY_ROWID = 4;
	private static final int PARTICIPANT = 5;
	private static final int PARTICIPANT_QUERY_BY_ROWID = 6;
	
	static{
		uri_matcher = new UriMatcher(UriMatcher.NO_MATCH);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY, DAILY_ACTIVITY);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC, DYNAMIC);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT, PARTICIPANT);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY + "/#", DAILY_ACTIVITY_QUERY_BY_ROWID);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC + "/#", DYNAMIC_QUERY_BY_ROWID);
		uri_matcher.addURI(AUTHORITY, CONTETN_BASE_PATH + "/" + DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT + "/#", PARTICIPANT_QUERY_BY_ROWID);
	}
	
	private DailyActivitiesSQLiteOpenHelper mSQLiteOpenHelper;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		String table = null;
		String rowId = null;
		switch (uri_matcher.match(uri)) {
		case DAILY_ACTIVITY:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			break;
		case DAILY_ACTIVITY_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			rowId = uri.getPathSegments().get(2);
			selection = "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		case DYNAMIC:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			break;
		case DYNAMIC_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			rowId = uri.getPathSegments().get(2);
			selection = "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		case PARTICIPANT:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			break;
		case PARTICIPANT_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			rowId = uri.getPathSegments().get(2);
			selection = "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int deleteCount = db.delete(table, selection, selectionArgs);
		notifyChange(uri);
		return deleteCount;
	}
	
	/**
	 * 暂时不实现
	 */
	@Override
	public String getType(Uri uri) {
		switch (uri_matcher.match(uri)) {
		case DAILY_ACTIVITY:
			break;
		case DAILY_ACTIVITY_QUERY_BY_ROWID:
			break;
		case DYNAMIC:
			break;
		case DYNAMIC_QUERY_BY_ROWID:
			break;
		case PARTICIPANT:
			break;
		case PARTICIPANT_QUERY_BY_ROWID:
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		String table = null;
		String _id = null;
		switch (uri_matcher.match(uri)) {
		case DAILY_ACTIVITY:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			_id = DailyActivitiesSQLiteOpenHelper.KEY_DA_ID;
			break;
		case DYNAMIC:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			_id = DailyActivitiesSQLiteOpenHelper.KEY_DM_ID;
			break;
		case PARTICIPANT:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			_id = DailyActivitiesSQLiteOpenHelper.KEY_PP_ID;
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		long rowid = db.insert(table, null, values);
		
		if(rowid > -1){
			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
			queryBuilder.appendWhere("rowid = " + rowid);
			queryBuilder.setTables(table);
			Cursor cursor = queryBuilder.query(db, new String[]{_id}, null, null, null, null, null);
			int keyIdIndex = cursor.getColumnIndex(_id);
			cursor.moveToNext();
			int id = cursor.getInt(keyIdIndex);
			Uri insertId = ContentUris.withAppendedId(uri, id);
			notifyChange(insertId);
			return insertId;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		mSQLiteOpenHelper = new DailyActivitiesSQLiteOpenHelper(getContext(),
				DailyActivitiesSQLiteOpenHelper.DATABASE_NAME, null,
				DailyActivitiesSQLiteOpenHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		
		String groupBy = null;
		String having = null;
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		String table = null;
		String rowId = null;
		switch (uri_matcher.match(uri)) {
		case DAILY_ACTIVITY:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			break;
		case DAILY_ACTIVITY_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			rowId = uri.getPathSegments().get(2);
			queryBuilder.appendWhere("id" + "=" + rowId);
			break;
		case DYNAMIC:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			break;
		case DYNAMIC_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			rowId = uri.getPathSegments().get(2);
			queryBuilder.appendWhere("id" + "=" + rowId);
			break;
		case PARTICIPANT:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			break;
		case PARTICIPANT_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			rowId = uri.getPathSegments().get(2);
			queryBuilder.appendWhere("id" + "=" + rowId);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		queryBuilder.setTables(table);
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		String rowId = null;
		String table = null;
		switch(uri_matcher.match(uri)){
		case DAILY_ACTIVITY_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY;
			rowId = uri.getPathSegments().get(2);
			selection =  "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		case DYNAMIC_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_DYNAMIC;
			rowId = uri.getPathSegments().get(2);
			selection =  "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		case PARTICIPANT_QUERY_BY_ROWID:
			table = DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT;
			rowId = uri.getPathSegments().get(2);
			selection =  "id=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int updateCount  = db.update(table, values, selection, selectionArgs);
		notifyChange(uri);
		return updateCount;
	}
	
	// 通知所有观察者，数据集已改变
	private void notifyChange(Uri uri) {
		getContext().getContentResolver().notifyChange(uri, null);
	}
}
