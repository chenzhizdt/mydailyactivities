package com.triman.android.app.mydailyactivities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DynamicsFragment extends Fragment{
	
	private ArrayList<String> dynamics;
	private ArrayAdapter<String> adapter;
	private ListView lvDynamics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dynamics, container, false);
		lvDynamics = (ListView) view.findViewById(R.id.lv_dynamics);
		dynamics = new ArrayList<String>();
		for(int i = 0; i < 20; i++){
			dynamics.add("Dynamic " + (i + 1));
		}
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dynamics);
		lvDynamics.setAdapter(adapter);
		return view;
	}
}
