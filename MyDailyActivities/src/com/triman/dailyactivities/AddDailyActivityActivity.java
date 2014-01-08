package com.triman.dailyactivities;

import java.util.ArrayList;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.RoundListViewAdapter;

import android.os.Bundle;
import android.widget.ListView;

public class AddDailyActivityActivity extends BaseActivity {

	private ListView lvAttrList;
	private RoundListViewAdapter<String> adapter;
	private ArrayList<String> attrs = new ArrayList<String>();
	private int attrNames[] = { R.string.date, R.string.time, R.string.address,
			R.string.theme, R.string.participiants };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_daily_activity);
		init();
	}

	private void init() {
		lvAttrList = (ListView) findViewById(R.id.lv_attr_list);
		for(int i = 0; i < attrNames.length; i++){
			String attrName = getResources().getString(attrNames[i]);
			attrs.add(attrName);
		}
		
	}
}
