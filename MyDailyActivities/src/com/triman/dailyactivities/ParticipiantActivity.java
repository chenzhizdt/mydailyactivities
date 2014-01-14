package com.triman.dailyactivities;

import java.util.ArrayList;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter;
import com.triman.dailyactivities.base.ui.BaseArrayAdapter.DataBinder;
import com.triman.dailyactivities.base.ui.SlideListView;
import com.triman.dailyactivities.base.ui.SlideListView.RemoveDirection;
import com.triman.dailyactivities.base.ui.SlideListView.RemoveListener;
import com.triman.dailyactivities.base.ui.BaseRoundAdapter;
import com.triman.dailyactivities.model.Participant;
import com.triman.dailyactivities.utils.CheckUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ParticipiantActivity extends BaseActivity implements DataBinder<Participant>, RemoveListener{
	
	public static final String EXTRA_PARTICIPANTS = "participants";
	
	private BaseArrayAdapter<Participant> adapter;
	private ArrayList<Participant> participants;
	private SlideListView lvParticipant;
	private Button btnConfirm;
	private Button btnAdd;
	private EditText etName;
	private EditText etPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant);
		lvParticipant = (SlideListView) findViewById(R.id.lv_participant_list);
		init();
	}
	
	private void init() {
		participants = getIntent().getParcelableArrayListExtra(EXTRA_PARTICIPANTS);
		adapter = new BaseRoundAdapter<Participant>(this, R.layout.list_item_participant, participants);
		adapter.setBinder(this);
		lvParticipant.setAdapter(adapter);
		lvParticipant.setRemoveListener(this);
		btnConfirm = (Button) findViewById(R.id.btn_confirm_add_participant);
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putParcelableArrayListExtra(EXTRA_PARTICIPANTS, participants);
				setResult(RESULT_OK, data);
				finish();
			}
		});
		
		etName = (EditText) findViewById(R.id.et_participant_name);
		etPhone = (EditText) findViewById(R.id.et_participant_phone);
		btnAdd = (Button) findViewById(R.id.btn_add_participant);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = etName.getText().toString();
				String phone = etPhone.getText().toString();
				if(CheckUtils.isNullOrBlank(name)){
					alert("姓名不能为空");
					return;
				}
				if(CheckUtils.isNullOrBlank(phone)){
					alert("手机不能为空");
					return;
				}
				if(isExist(name, phone)){
					alert("列表中已存在该参与人员");
					return;
				}
				Participant p = new Participant();
				p.setName(name);
				p.setPhone(phone);
				participants.add(p);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private boolean isExist(String name, String phone){
		if(participants.size() > 0){
			for(int i = 0; i < participants.size(); i++){
				Participant p = participants.get(i);
				if(name.equals(p.getName()) && phone.equals(p.getPhone())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void bindDataToView(Participant data, View view) {
		TextView textView1 = (TextView) view.findViewById(R.id.tv_participant_name);
		textView1.setText(data.getName());
		TextView textView2 = (TextView) view.findViewById(R.id.tv_participant_phone);
		textView2.setText(data.getPhone());
	}

	@Override
	public void onRemove(RemoveDirection direction, int position) {
		adapter.remove(participants.get(position));
	}
}
