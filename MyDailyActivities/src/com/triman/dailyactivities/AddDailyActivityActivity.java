package com.triman.dailyactivities;

import java.util.ArrayList;
import java.util.Calendar;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.base.ui.BaseRoundAdapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AddDailyActivityActivity extends BaseActivity implements DataBinder<String>, OnItemClickListener{
	
	private static final String TAG = "AddDailyActivityActivity";

	private ListView lvAttrList;
	private BaseRoundAdapter<String> adapter;
	private ArrayList<String> attrs = new ArrayList<String>();
	private int attrNames[] = { R.string.date, R.string.time, R.string.address,
			R.string.theme, R.string.participiants };
	private Button btnConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_daily_activity);
		lvAttrList = (ListView) findViewById(R.id.lv_attr_list);
		btnConfirm = (Button) findViewById(R.id.btn_confirm_add_activity);
		init();
	}

	private void init() {
		for(int i = 0; i < attrNames.length; i++){
			String attrName = getResources().getString(attrNames[i]);
			attrs.add(attrName);
		}
		adapter = new BaseRoundAdapter<String>(this, R.layout.list_item_add_activity, attrs);
		adapter.setBinder(this);
		lvAttrList.setAdapter(adapter);
		lvAttrList.setOnItemClickListener(this);
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v(TAG, "确定");
			}
		});
	}

	@Override
	public void bindDataToView(String data, View view) {
		TextView textView = (TextView) view.findViewById(R.id.tv_add_activity_label);
		textView.setText(data);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Item Click");
		switch(position){
		case 0:
			Calendar calendar = Calendar.getInstance();
			DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day) {
					// TODO Auto-generated method stub
					Log.v(TAG, "Date Set" + ": " + year + "/" + month + "/" + day);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.show();
			break;
		case 1:
			Calendar date = Calendar.getInstance();
			TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					Log.v(TAG, "Date Set" + ": " + hourOfDay + ":" + minute);
				}
			}, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true);
			timePickerDialog.show();
			break;
		case 2:
			buildEnterDialog(new Callback() {

				@Override
				public void onConfirm(String text) {
					// TODO Auto-generated method stub
					Log.v(TAG, text);
				}
			}).show();
			break;
		case 3:
			buildEnterDialog(new Callback() {

				@Override
				public void onConfirm(String text) {
					// TODO Auto-generated method stub
					Log.v(TAG, text);
				}
			}).show();
			break;
		case 4:
			// TODO 添加业务逻辑Participiant返回业务逻辑
			Intent i = new Intent(this, ParticipiantActivity.class);
			startActivity(i);
			break;
		default:break;
		}
	}
	
	private Builder buildEnterDialog(final Callback callback){
		Builder dialog = new AlertDialog.Builder(this);
		final EditText editText = new EditText(this);
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
