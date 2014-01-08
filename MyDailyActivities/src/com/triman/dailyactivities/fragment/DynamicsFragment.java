package com.triman.dailyactivities.fragment;

import java.util.ArrayList;

import com.triman.dailyactivities.R;
import com.triman.dailyactivities.R.id;
import com.triman.dailyactivities.R.layout;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.model.Dynamic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DynamicsFragment extends Fragment implements DataBinder<Dynamic>{
	
	private ArrayList<Dynamic> dynamics;
	private BaseArrayAdapter<Dynamic> adapter;
	private ListView lvDynamics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dynamics, container, false);
		lvDynamics = (ListView) view.findViewById(R.id.lv_dynamics);
		dynamics = new ArrayList<Dynamic>();
		for(int i = 0; i < 20; i++){
			dynamics.add(new Dynamic());
		}
		adapter = new BaseArrayAdapter<Dynamic>(getActivity(), R.layout.list_item_dynamics, dynamics);
		adapter.setBinder(this);
		lvDynamics.setAdapter(adapter);
		return view;
	}

	@Override
	public void bindDataToView(Dynamic data, View view) {
		
		TextView time = (TextView) view.findViewById(R.id.tv_dynamic_time);
		TextView day = (TextView) view.findViewById(R.id.tv_dynamic_day);
		TextView content = (TextView) view.findViewById(R.id.tv_dynamic_content);
		
		time.setText(data.getTimeStr());
		day.setText(data.getDayStr());
		content.setText(data.getContent());
	}
}
