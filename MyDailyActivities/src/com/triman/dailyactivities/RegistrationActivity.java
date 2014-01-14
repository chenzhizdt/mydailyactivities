package com.triman.dailyactivities;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.model.DailyActivity;
import com.triman.dailyactivities.model.Participant;

public class RegistrationActivity extends BaseActivity implements DataBinder<Participant>{
	
	private static final String TAG = "RegistrationActivity";
	public static final String EXTRA_DAILYACTIVITY_ID = "dailyactivityid";
	
	private GridView gvParticipants;
	private TextView tvAddress;
	private TextView tvTheme;
	private BaseArrayAdapter<Participant> adapter;
	private ArrayList<Participant> participants;
	private DailyActivity da;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		gvParticipants = (GridView) findViewById(R.id.gv_participants);
		tvAddress = (TextView) findViewById(R.id.tv_address);
		tvTheme = (TextView) findViewById(R.id.tv_theme);
		init();
	}
	
	private void init(){
		int dailyActivityId;
		dailyActivityId = getIntent().getIntExtra(EXTRA_DAILYACTIVITY_ID, -1);
		if(dailyActivityId == -1){
			return;
		}
		da = getDailyActivity(dailyActivityId);
		tvAddress.setText(da.getAddress());
		tvTheme.setText(da.getTheme());
		participants = getParticipants(da);
		adapter = new BaseArrayAdapter<Participant>(this, R.layout.grid_item_registration, participants);
		gvParticipants.setAdapter(adapter);
	}
	
	private ArrayList<Participant> getParticipants(DailyActivity da){
		ArrayList<Participant> participants = new ArrayList<Participant>();
		return participants;
	}
	
	private DailyActivity getDailyActivity(int dailyActivityId){
		DailyActivity da = new DailyActivity();
		return da;
	}

	@Override
	public void bindDataToView(Participant data, View view) {
		TextView tvName = (TextView) view.findViewById(R.id.tv_name);
		TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
		tvName.setText(data.getName());
		tvPhone.setText(data.getPhone());
		if(data.isExist()){
			tvName.setTextColor(getResources().getColor(R.color.green));
		} else {
			tvName.setTextColor(getResources().getColor(R.color.red));
		}
	}
}
