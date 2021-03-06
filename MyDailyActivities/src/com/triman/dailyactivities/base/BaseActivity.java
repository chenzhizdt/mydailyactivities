package com.triman.dailyactivities.base;

import com.triman.dailyactivities.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.RGBA_8888);
	}
	
	protected void alert(String msg, String title) {
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setNegativeButton(R.string.confirm, null);
		dialog.show();
	}
	
	protected void alert(int msg, int title){
		String strMsg = getResources().getString(msg);
		String strTitle = getResources().getString(title);
		alert(strMsg, strTitle);
	}
	
	protected void alert(String msg, int title) {
		String strTitle = getResources().getString(title);
		alert(msg, strTitle);
	}
	
	protected void alert(String msg) {
		this.alert(msg, R.string.prompt);
	}
	
	protected void alert(int msg) {
		this.alert(msg, R.string.prompt);
	}
}
