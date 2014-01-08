package com.triman.dailyactivities;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.fragment.ActivitiesFragment;
import com.triman.dailyactivities.fragment.DynamicsFragment;
import com.triman.dailyactivities.fragment.RegistrationFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	
	private FragmentTabHost mTabHost;
	private LayoutInflater mLayoutInflater;
	private Class<?> mFragments[] = {ActivitiesFragment.class, DynamicsFragment.class, RegistrationFragment.class};
	private int mTitles[] = {R.string.activities, R.string.dynamics, R.string.registration};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}
	
	private void initView(){
		mLayoutInflater = LayoutInflater.from(this);
		
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        
        for(int i = 0; i < mFragments.length; i++){        
            TabSpec tabSpec = mTabHost.newTabSpec(getResources().getString(mTitles[i])).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragments[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.menu_background);
        }
	}
	
	private View getTabItemView(int i){
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTitles[i]);
		return view;
	}
}
