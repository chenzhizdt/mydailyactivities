package com.triman.android.app.mydailyactivities;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.triman.android.app.mydailyactivities.model.Dynamic;

public class DynamicsAdapter extends BaseArrayAdapter<Dynamic>{
	
	public static final int LAYOUT_RESOURCE = R.layout.list_item_dynamics;
	
	public DynamicsAdapter(Context context, List<Dynamic> objects){
		this(context, LAYOUT_RESOURCE, objects);
	}

	protected DynamicsAdapter(Context context, int resource,
			List<Dynamic> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindDataToView(Dynamic data, View view) {
		TextView time = (TextView) view.findViewById(R.id.tv_dynamic_time);
		TextView day = (TextView) view.findViewById(R.id.tv_dynamic_day);
		TextView content = (TextView) view.findViewById(R.id.tv_dynamic_content);
		
		time.setText(data.getTimeStr());
		day.setText(data.getDayStr());
		content.setText(data.getContent());
	}

	@Override
	protected int getResource() {
		return LAYOUT_RESOURCE;
	}
	
}
