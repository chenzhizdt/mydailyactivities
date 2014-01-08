package com.triman.dailyactivities;

import com.triman.dailyactivities.base.BaseActivity;
import com.triman.dailyactivities.utils.CheckUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private EditText etName;
//	private EditText etPhone;
	private Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etName = (EditText) findViewById(R.id.et_name);
//		etPhone = (EditText) findViewById(R.id.et_phone);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String name = etName.getText().toString();
		if(CheckUtils.isNullOrBlank(name)){
			alert("姓名不能为空");
			return;
		}
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
