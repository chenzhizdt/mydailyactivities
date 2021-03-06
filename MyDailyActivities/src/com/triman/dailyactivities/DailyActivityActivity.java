package com.triman.dailyactivities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.base.ui.BaseRoundAdapter;
import com.triman.dailyactivities.db.DailyActivitiesContentProvider;
import com.triman.dailyactivities.db.DailyActivitiesSQLiteOpenHelper;
import com.triman.dailyactivities.model.DailyActivity;
import com.triman.dailyactivities.model.Participant;
import com.triman.dailyactivities.utils.Utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class DailyActivityActivity extends BaseActivity implements DataBinder<HashMap<String, Object>>, OnItemClickListener{
	
	/**
	 * 一个DialyActivity对象id
	 */
	public static final String EXTRA_DAILYACTIVITY_ID = "dialyactivityId";
	
	private static final String TAG = "AddDailyActivityActivity";
	private static final String LABEL = "label";
	private static final String CONTENT = "content";
	
	public static final int PATICIPANT_REQUEST = 1;

	private ListView lvAttrList;
	private BaseRoundAdapter<HashMap<String, Object>> adapter;
	private ArrayList<HashMap<String, Object>> attrs;
	private int attrNames[] = { R.string.date, R.string.time, R.string.address,
			R.string.theme, R.string.participiants };
	private Button btnConfirm;
	private TextView tvAddActivityTitle;
	private DailyActivity da;
	private ArrayList<Participant> participants;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_daily_activity);
		lvAttrList = (ListView) findViewById(R.id.lv_attr_list);
		btnConfirm = (Button) findViewById(R.id.btn_confirm_add_activity);
		tvAddActivityTitle = (TextView) findViewById(R.id.tv_add_activity_title);
		init();
	}

	private void init() {
		Intent intent = getIntent();
		int id = intent.getIntExtra(EXTRA_DAILYACTIVITY_ID, -1);
		da = getDailyActivity(id);
		if(id != -1){
			tvAddActivityTitle.setText(R.string.edit_activity);
		}
		participants = getParticipants(da);
		attrs = buildAttrs(da);
		adapter = new BaseRoundAdapter<HashMap<String, Object>>(this, R.layout.list_item_add_activity, attrs);
		adapter.setBinder(this);
		lvAttrList.setAdapter(adapter);
		lvAttrList.setOnItemClickListener(this);
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG, "确定");
				if(da.getAddress() == ""){
					alert("地址不可为空！");
					return;
				}
				if(da.getTheme() == ""){
					alert("主题不可为空！");
				}
				if(da.getId() == -1){
					int id = saveDailyActivity(da, participants);
					updateParticipants(id, participants);
					finish();
				} else {
					updateDailyActivity(da, participants);
					updateParticipants(da.getId(), participants);
					finish();
				}
			}
		});
	}
	
	private int saveDailyActivity(DailyActivity da, ArrayList<Participant> participants){
		ContentResolver cr = getContentResolver();
		Uri uri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
				+ "/"
				+ DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY);
		
		ContentValues values = new ContentValues();
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_TIME, da.getTime());
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_ADDRESS, da.getAddress());
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_THEME, da.getTheme());
		Uri insertId = cr.insert(uri, values);
		int id = Integer.parseInt(insertId.getPathSegments().get(2));
		return id;
	}
	
	private void updateDailyActivity(DailyActivity da, ArrayList<Participant> participants){
		ContentResolver cr = getContentResolver();
		Uri uri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
				+ "/"
				+ DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY
				+ "/" + da.getId());
		
		ContentValues values = new ContentValues();
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_TIME, da.getTime());
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_ADDRESS, da.getAddress());
		values.put(DailyActivitiesSQLiteOpenHelper.KEY_DA_THEME, da.getTheme());
		cr.update(uri, values, null, null);
	}
	
	private void updateParticipants(int dailyActivityId, ArrayList<Participant> participants){
		ContentResolver cr = getContentResolver();
		Uri deleteUri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
				+ "/"
				+ DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT);
		cr.delete(deleteUri, DailyActivitiesSQLiteOpenHelper.KEY_PP_DA_ID + "=" + dailyActivityId, null);
		Uri insertUri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
				+ "/"
				+ DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT);
		if(participants.size() > 0){
			for(int i = 0; i < participants.size(); i++){
				Participant p = participants.get(i);
				ContentValues values = new ContentValues();
				values.put(DailyActivitiesSQLiteOpenHelper.KEY_PP_DA_ID, dailyActivityId);
				values.put(DailyActivitiesSQLiteOpenHelper.KEY_PP_NAME, p.getName());
				values.put(DailyActivitiesSQLiteOpenHelper.KEY_PP_PHONE, p.getPhone());
				cr.insert(insertUri, values);
			}
		}
	}
	
	private ArrayList<Participant> getParticipants(DailyActivity da){
		ArrayList<Participant> participants = new ArrayList<Participant>();
		if(da.getId() != -1){
			ContentResolver cr = getContentResolver();
			Uri uri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
					+ "/"
					+ DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT);
			
			Cursor cursor = cr.query(uri, null, DailyActivitiesSQLiteOpenHelper.KEY_PP_DA_ID + "=" + da.getId(), null, null);
			
			int keyIdIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_PP_ID);
			int keyDaIdIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_PP_DA_ID);
			int keyNameIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_PP_NAME);
			int keyPhoneIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_PP_PHONE);
			
			while(cursor.moveToNext()){
				Participant p = new Participant();
				p.setId(cursor.getInt(keyIdIndex));
				p.setDailyActivityId(cursor.getInt(keyDaIdIndex));
				p.setName(cursor.getString(keyNameIndex));
				p.setPhone(cursor.getString(keyPhoneIndex));
				participants.add(p);
			}
		}
		return participants;
	}
	
	private DailyActivity getDailyActivity(int id){
		DailyActivity da = new DailyActivity();
		if(id == -1){
			da.setId(id);
			return da;
		} else {
			ContentResolver cr = getContentResolver();
			Uri uri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
					+ "/"
					+ DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY
					+ "/" + id);
			Cursor cursor = cr.query(uri, null, null, null, null);
			
			int keyIdIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_ID);
			int keyThemeIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_THEME);
			int keyTimeIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_TIME);
			int keyAddressIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_ADDRESS);
			
			while(cursor.moveToNext()){
				da.setId(cursor.getInt(keyIdIndex));
				da.setAddress(cursor.getString(keyAddressIndex));
				da.setTheme(cursor.getString(keyThemeIndex));
				da.setTime(cursor.getLong(keyTimeIndex));
			}
		}
		return da;
	}
	
	private ArrayList<HashMap<String, Object>> buildAttrs(DailyActivity da){
		attrs = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> attr0 = new HashMap<String, Object>();
		attr0.put(LABEL, getResources().getString(attrNames[0]));
		attr0.put(CONTENT, da.getDayStr());
		
		HashMap<String, Object> attr1 = new HashMap<String, Object>();
		attr1.put(LABEL, getResources().getString(attrNames[1]));
		attr1.put(CONTENT, da.getTimeStr());
		
		HashMap<String, Object> attr2 = new HashMap<String, Object>();
		attr2.put(LABEL, getResources().getString(attrNames[2]));
		attr2.put(CONTENT, da.getAddress());
		
		HashMap<String, Object> attr3 = new HashMap<String, Object>();
		attr3.put(LABEL, getResources().getString(attrNames[3]));
		attr3.put(CONTENT, da.getTheme());
		
		HashMap<String, Object> attr4 = new HashMap<String, Object>();
		attr4.put(LABEL, getResources().getString(attrNames[4]));
		attr4.put(CONTENT, participantsToString(participants));
		
		attrs.add(attr0);
		attrs.add(attr1);
		attrs.add(attr2);
		attrs.add(attr3);
		attrs.add(attr4);
		return attrs;
	}
	
	private String participantsToString(ArrayList<Participant> participants){
		String content = "";
		if(participants.size() > 0){
			for(int i = 0; i < participants.size(); i++){
				content += participants.get(i).getName() + ", ";
			}
			// TODO: 待测试
			content = content.substring(0, content.length() - 2);
		}
		return content;
	}

	@Override
	public void bindDataToView(HashMap<String, Object> data, View view) {
		TextView label = (TextView) view.findViewById(R.id.tv_add_activity_label);
		TextView content = (TextView) view.findViewById(R.id.tv_add_activity_content);
		label.setText((String)data.get(LABEL));
		content.setText((String)data.get(CONTENT));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		Log.v(TAG, "Item Click");
		switch(position){
		case 0:
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setTime(Utils.longToDate(da.getTime()));
			DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day) {
					Log.v(TAG, "Date Set" + ": " + year + "/" + month + "/" + day);
					HashMap<String, Object> attr = attrs.get(position);
					Calendar calendar1 = Calendar.getInstance(Locale.CHINA);
					calendar1.setTime(Utils.longToDate(da.getTime()));
					calendar1.set(year, month, day);
					da.setTime(calendar1.getTimeInMillis());
					attr.put(CONTENT, da.getDayStr());
					adapter.notifyDataSetChanged();
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.show();
			break;
		case 1:
			Calendar date = Calendar.getInstance(Locale.CHINA);
			date.setTime(Utils.longToDate(da.getTime()));
			TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
					Log.v(TAG, "Date Set" + ": " + hourOfDay + ":" + minute);
					Calendar date1 = Calendar.getInstance(Locale.CHINA);
					date1.setTime(Utils.longToDate(da.getTime()));
					date1.set(Calendar.HOUR_OF_DAY, hourOfDay);
					date1.set(Calendar.MINUTE, minute);
					da.setTime(date1.getTimeInMillis());
					HashMap<String, Object> attr = attrs.get(position);
					attr.put(CONTENT, da.getTimeStr());
					adapter.notifyDataSetChanged();
				}
			}, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true);
			timePickerDialog.show();
			break;
		case 2:
			EditText editText = new EditText(this);
			String content = (String) attrs.get(position).get(CONTENT);
			editText.setText(content);
			buildEnterDialog(new Callback() {

				@Override
				public void onConfirm(String text) {
					Log.v(TAG, text);
					HashMap<String, Object> attr = attrs.get(position);
					da.setAddress(text);
					attr.put(CONTENT, da.getAddress());
					adapter.notifyDataSetChanged();
				}
			}, editText).show();
			break;
		case 3:
			EditText editText1 = new EditText(this);
			String content1 = (String) attrs.get(position).get(CONTENT);
			editText1.setText(content1);
			buildEnterDialog(new Callback() {

				@Override
				public void onConfirm(String text) {
					Log.v(TAG, text);
					HashMap<String, Object> attr = attrs.get(position);
					da.setTheme(text);
					attr.put(CONTENT, da.getTheme());
					adapter.notifyDataSetChanged();
				}
			}, editText1).show();
			break;
		case 4:
			Intent i = new Intent(this, ParticipiantActivity.class);
			i.putParcelableArrayListExtra(ParticipiantActivity.EXTRA_PARTICIPANTS, participants);
			startActivityForResult(i, PATICIPANT_REQUEST);
			break;
		default:break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case RESULT_OK:
			participants = data.getParcelableArrayListExtra(ParticipiantActivity.EXTRA_PARTICIPANTS);
			attrs.get(4).put(CONTENT, participantsToString(participants));
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private Builder buildEnterDialog(final Callback callback, final EditText editText){
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info)
				.setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String text = editText.getText().toString();
						callback.onConfirm(text);
					}
				})
				.setNegativeButton("取消", null);
		return dialog;
	}
	
	private interface Callback{
		public void onConfirm(String text);
	}
	
}
