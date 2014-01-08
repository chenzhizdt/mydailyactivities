package com.triman.android.app.mydailyactivities;

import java.util.ArrayList;

import com.triman.android.app.mydailyactivities.model.DailyActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ActivitiesFragment extends Fragment{
	
	private ArrayList<DailyActivity> activities;
	private ActivitiesAdapter adapter;
	private ListView lvActivities;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activities, container, false);
		lvActivities = (ListView) view.findViewById(R.id.lv_activities);
		activities = new ArrayList<DailyActivity>();
		for(int i = 0; i < 20; i++){
			activities.add(new DailyActivity());
		}
		adapter = new ActivitiesAdapter(getActivity(), activities);
		lvActivities.setAdapter(adapter);
		return view;
	}
}
