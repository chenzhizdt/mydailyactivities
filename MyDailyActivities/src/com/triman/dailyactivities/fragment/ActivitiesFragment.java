package com.triman.dailyactivities.fragment;

import java.util.ArrayList;

import com.triman.dailyactivities.DailyActivityActivity;
import com.triman.dailyactivities.R;
import com.triman.dailyactivities.RegistrationActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.db.DailyActivitiesContentProvider;
import com.triman.dailyactivities.db.DailyActivitiesSQLiteOpenHelper;
import com.triman.dailyactivities.model.DailyActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitiesFragment extends Fragment implements DataBinder<DailyActivity>, OnItemClickListener, LoaderCallbacks<Cursor>{
	
	private static final int ACTIVITY_REGISTRATION = 0;
	private static final int ACTIVITY_INFO = 1;
	private static final int DELETE = 2;
	private static final String TAG = "ActivitiesFragment";
	
	private ArrayList<DailyActivity> activities;
	private BaseArrayAdapter<DailyActivity> adapter;
	private ListView lvActivities;
	private Button btnAddActivity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activities, container, false);
		lvActivities = (ListView) view.findViewById(R.id.lv_activities);
		activities = new ArrayList<DailyActivity>();
		adapter = new BaseArrayAdapter<DailyActivity>(getActivity(),R.layout.list_item_activities, activities);
		adapter.setBinder(this);
		
		lvActivities.setAdapter(adapter);
		lvActivities.setOnItemClickListener(this);
		
		btnAddActivity = (Button) view.findViewById(R.id.btn_add_activity);
		btnAddActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), DailyActivityActivity.class);
				startActivity(intent);
			}
		});
		
		FragmentActivity fa = (FragmentActivity) getActivity();
		fa.getSupportLoaderManager().initLoader(0, null, this);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		FragmentActivity fa = (FragmentActivity) getActivity();
		fa.getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void bindDataToView(DailyActivity data, View view) {
		TextView time = (TextView) view.findViewById(R.id.tv_activity_time);
		TextView day = (TextView) view.findViewById(R.id.tv_activity_day);
		TextView address = (TextView) view.findViewById(R.id.tv_address);
		TextView theme = (TextView) view.findViewById(R.id.tv_theme);
		time.setText(data.getTimeStr());
		day.setText(data.getDayStr());
		address.setText(data.getAddress());
		theme.setText(data.getTheme());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		// TODO Auto-generated method stub
		Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle(R.string.operation).setItems(
				R.array.activity_context_menu, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent;
						switch(which){
						case ACTIVITY_REGISTRATION:
							Log.v(TAG, "签到");
							intent = new Intent(getActivity(), RegistrationActivity.class);
							startActivity(intent);
							break;
						case ACTIVITY_INFO:
							Log.v(TAG, "详细");
							intent = new Intent(getActivity(), DailyActivityActivity.class);
							intent.putExtra(DailyActivityActivity.EXTRA_DAILYACTIVITY_ID, adapter.getItem(position).getId());
							startActivity(intent);
							break;
						case DELETE:
							Log.v(TAG, "删除");
							long id = activities.get(position).getId();
							FragmentActivity fa = (FragmentActivity) getActivity();
							ContentResolver cr = fa.getContentResolver();
							Uri uri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
											+ "/"
											+ DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY
											+ "/" + id);
							cr.delete(uri, null, null);
							Uri participantUri = Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI
									+ "/"
									+ DailyActivitiesSQLiteOpenHelper.TABLE_PARTICIPANT);
							cr.delete(participantUri, DailyActivitiesSQLiteOpenHelper.KEY_PP_DA_ID + "=" + id, null);
							fa.getSupportLoaderManager().restartLoader(0, null, ActivitiesFragment.this);
							break;
						default:break;
						}
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader loader = new CursorLoader(
				getActivity(),
				Uri.parse(DailyActivitiesContentProvider.BASE_CONTENT_URI + "/"
						+ DailyActivitiesSQLiteOpenHelper.TABLE_DAILY_ACTIVITY),
				null, null, null, "time desc");
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int keyIdIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_ID);
		int keyThemeIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_THEME);
		int keyTimeIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_TIME);
		int keyAddressIndex = cursor.getColumnIndex(DailyActivitiesSQLiteOpenHelper.KEY_DA_ADDRESS);
		
		activities.clear();
		
		while(cursor.moveToNext()){
			DailyActivity newItem = new DailyActivity();
			newItem.setId(cursor.getInt(keyIdIndex));
			newItem.setAddress(cursor.getString(keyAddressIndex));
			newItem.setTheme(cursor.getString(keyThemeIndex));
			newItem.setTime(cursor.getLong(keyTimeIndex));
			activities.add(newItem);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
