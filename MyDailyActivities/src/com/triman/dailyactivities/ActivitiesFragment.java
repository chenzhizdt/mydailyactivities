package com.triman.dailyactivities;

import java.util.ArrayList;

import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.model.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitiesFragment extends Fragment implements DataBinder<DailyActivity>{
	
	private ArrayList<DailyActivity> activities;
	private BaseArrayAdapter<DailyActivity> adapter;
	private ListView lvActivities;
	private Button btnAddActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activities, container, false);
		lvActivities = (ListView) view.findViewById(R.id.lv_activities);
		activities = new ArrayList<DailyActivity>();
		for(int i = 0; i < 20; i++){
			activities.add(new DailyActivity());
		}
		adapter = new BaseArrayAdapter<DailyActivity>(getActivity(),R.layout.list_item_activities, activities);
		adapter.setBinder(this);
		
		lvActivities.setAdapter(adapter);
		
		btnAddActivity = (Button) view.findViewById(R.id.btn_add_activity);
		btnAddActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), AddDailyActivityActivity.class));
			}
		});
		return view;
	}

	@Override
	public void bindDataToView(DailyActivity data, View view) {
		TextView time = (TextView) view.findViewById(R.id.tv_activity_time);
		TextView day = (TextView) view.findViewById(R.id.tv_activity_day);
		TextView address = (TextView) view.findViewById(R.id.tv_address);
		TextView theme = (TextView) view.findViewById(R.id.tv_theme);
		time.setText(data.getTimeStr());
		day.setText(data.getDayStr());
		address.setText(data.getAddress());
		theme.setText(data.getTheme());
	}
}
