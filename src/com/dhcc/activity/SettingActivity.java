package com.dhcc.activity;

import com.dhcc.entity.LoginUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Doraemon on 2015/7/21.
 */
public class SettingActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_setting, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView t=(TextView) this.getView().findViewById(R.id.s_loginuser_t);
		t.setText(LoginUser.id+LoginUser.name+LoginUser.ps+LoginUser.power+LoginUser.isOnline);
	}
	  @Override
		public void onDestroyView() {
		  super.onDestroyView();
		}
		@Override  
	    public void onDestroy() {  
	        super.onDestroy();   
	    } 
}

