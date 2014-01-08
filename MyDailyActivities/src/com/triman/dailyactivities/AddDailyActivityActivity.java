package com.triman.dailyactivities;

import java.util.ArrayList;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.base.ui.BaseRoundAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AddDailyActivityActivity extends BaseActivity implements DataBinder<String>{
	
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
}
