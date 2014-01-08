package com.triman.android.app.mydailyactivities;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T>{
	
	protected BaseArrayAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view;
		T data = getItem(position);
		
		if(convertView == null){
			view = new LinearLayout(getContext());
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = (LinearLayout) li.inflate(getResource(), view, true);
		} else {
			view = (LinearLayout) convertView;
		}
		
		bindDataToView(data, view);
		
		return view;
	}
	
	abstract protected void bindDataToView(T data, View view);
	abstract protected int getResource();
}
