package com.triman.android.app.mydailyactivities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends BaseActivity {
	
	private ViewPager vpFragmentContainer;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
}
