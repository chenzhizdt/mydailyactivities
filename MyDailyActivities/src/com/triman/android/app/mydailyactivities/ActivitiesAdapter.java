package com.triman.android.app.mydailyactivities;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.triman.android.app.mydailyactivities.model.DailyActivity;

public class ActivitiesAdapter extends BaseArrayAdapter<DailyActivity>{
	
	public static final int LAYOUT_RESOURCE = R.layout.list_item_activities;
	
	public ActivitiesAdapter(Context context, List<DailyActivity> objects){
		this(context, LAYOUT_RESOURCE, objects);
	}

	protected ActivitiesAdapter(Context context, int resource,
			List<DailyActivity> objects) {
		super(context, resource, objects);
	}

	@Override
	protected void bindDataToView(DailyActivity data, View view) {
		TextView time = (TextView) view.findViewById(R.id.tv_time);
		TextView day = (TextView) view.findViewById(R.id.tv_day);
		TextView address = (TextView) view.findViewById(R.id.tv_address);
		TextView theme = (TextView) view.findViewById(R.id.tv_theme);
		time.setText(data.getTimeStr());
		day.setText(data.getDayStr());
		address.setText(data.getAddress());
		theme.setText(data.getTheme());
	}

	@Override
	protected int getResource() {
		return LAYOUT_RESOURCE;
	}
}
