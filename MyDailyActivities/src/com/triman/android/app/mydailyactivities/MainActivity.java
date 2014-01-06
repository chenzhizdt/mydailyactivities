package com.triman.android.app.mydailyactivities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {
	
	private ViewPager vpFragmentContainer;
	private Button btnActivities;
	private Button btnDynamics;
	private Button btnRegistration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		vpFragmentContainer = (ViewPager) findViewById(R.id.vp_fragment_container);
		setFragmentContainerAdapter(vpFragmentContainer);
		
		btnActivities = (Button) findViewById(R.id.btn_activities);
		btnActivities.setOnClickListener(new MenuClickListener(vpFragmentContainer, 0));
		
		btnDynamics = (Button) findViewById(R.id.btn_dynamics);
		btnDynamics.setOnClickListener(new MenuClickListener(vpFragmentContainer, 1));
		
		btnRegistration = (Button) findViewById(R.id.btn_registration);
		btnRegistration.setOnClickListener(new MenuClickListener(vpFragmentContainer, 2));
	}
	
	private void setFragmentContainerAdapter(ViewPager vpFragmentContainer){
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ActivitiesFragment fgActivities = new ActivitiesFragment();
		DynamicsFragment fgDynamics = new DynamicsFragment();
		RegistrationFragment fgRegistration = new RegistrationFragment();
		fragments.add(fgActivities);
		fragments.add(fgDynamics);
		fragments.add(fgRegistration);
		FragmentContainerAdapter fca = new FragmentContainerAdapter(getSupportFragmentManager(), fragments);
		vpFragmentContainer.setAdapter(fca);
		vpFragmentContainer.setCurrentItem(0);
	}
	
	private class MenuClickListener implements OnClickListener{
		
		private ViewPager vp;
		private int index;
		
		public MenuClickListener(ViewPager vp, int index){
			this.vp = vp;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			vp.setCurrentItem(index);
		}
	}
}
