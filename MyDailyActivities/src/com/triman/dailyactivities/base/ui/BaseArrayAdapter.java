package com.triman.dailyactivities.base.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BaseArrayAdapter<T> extends ArrayAdapter<T>{
	
	private LayoutInflater inflater;
	private int resource;
	private DataBinder<T> binder;
	
	public BaseArrayAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		T data = getItem(position);
		
		if(convertView == null){
			convertView = inflater.inflate(resource, null);
		}
		
		if(binder != null){
			binder.bindDataToView(data, convertView);
		}
		
		return convertView;
	}
	
	protected DataBinder<T> getBinder(){
		return binder;
	}

	public void setBinder(DataBinder<T> binder) {
		this.binder = binder;
	}

	public interface DataBinder<T>{
		public void bindDataToView(T data, View view);
	}
}
