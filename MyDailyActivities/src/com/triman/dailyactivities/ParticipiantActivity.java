package com.triman.dailyactivities;

import java.util.ArrayList;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.base.ui.BaseRoundAdapter;
import com.triman.dailyactivities.model.Participant;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ParticipiantActivity extends BaseActivity implements DataBinder<Participant>{
	
	private BaseArrayAdapter<Participant> adapter;
	private ArrayList<Participant> participants;
	private ListView lvParticipant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant);
		lvParticipant = (ListView) findViewById(R.id.lv_participant_list);
		init();
	}
	
	private void init() {
		participants = new ArrayList<Participant>();
		for(int i = 0; i < 20; i++){
			Participant p = new Participant();
			participants.add(p);
		}
		adapter = new BaseRoundAdapter<Participant>(this, R.layout.list_item_participant, participants);
		adapter.setBinder(this);
		lvParticipant.setAdapter(adapter);
	}

	@Override
	public void bindDataToView(Participant data, View view) {
		TextView textView1 = (TextView) view.findViewById(R.id.tv_participant_name);
		textView1.setText(data.getName());
		TextView textView2 = (TextView) view.findViewById(R.id.tv_participant_phone);
		textView2.setText(data.getPhone());
	}
}
