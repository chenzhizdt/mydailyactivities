package com.triman.dailyactivities.base.ui;

import java.util.List;

import com.triman.dailyactivities.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class RoundListViewAdapter<T> extends ArrayAdapter<T>{

	private LayoutInflater inflater;
	private int resource;
	
	public RoundListViewAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(resource, null);
		}
		int count = getCount();
		if (count == 1) {
			setBackgroundDrawable(convertView,
					R.drawable.list_round_selector);
		} else if (getCount() == 2) {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == count - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			}
		} else {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == count - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			} else {
				setBackgroundDrawable(convertView,
						R.drawable.list_rect_selector);
			}
		}
		return convertView;
	}

	private void setBackgroundDrawable(View view, int resID) {
		view.setBackgroundDrawable(getContext().getResources().getDrawable(resID));
	}
}
