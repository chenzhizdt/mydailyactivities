package com.triman.dailyactivities;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.model.Participant;

public class RegistrationActivity extends BaseActivity{
	
	private static final String TAG = "RegistrationActivity";
	private static final String NAME = "name";
	
	private GridView gvParticipants;
	private SimpleAdapter adapter;
	private ArrayList<HashMap<String, Object>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		gvParticipants = (GridView) findViewById(R.id.gv_participants);
		data = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < 20; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			Participant p = new Participant();
			map.put(NAME, p.getName() + " " + i);
			data.add(map);
		}
		adapter = new SimpleAdapter(this, data, R.layout.grid_item_registration, new String[]{NAME}, new int[]{R.id.name});
		gvParticipants.setAdapter(adapter);
	}
}
