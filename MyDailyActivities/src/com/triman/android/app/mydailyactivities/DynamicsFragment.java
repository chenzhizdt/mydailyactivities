package com.triman.android.app.mydailyactivities;

import java.util.ArrayList;

import com.triman.android.app.mydailyactivities.model.Dynamic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DynamicsFragment extends Fragment{
	
	private ArrayList<Dynamic> dynamics;
	private DynamicsAdapter adapter;
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
		adapter = new DynamicsAdapter(getActivity(), dynamics);
		lvDynamics.setAdapter(adapter);
		return view;
	}
}
