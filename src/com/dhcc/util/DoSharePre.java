package com.dhcc.util;

import com.dhcc.entity.LoginUser;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DoSharePre {
	public static void loginCall(SharedPreferences sp)
	{
		
		Editor edit=sp.edit();
		edit.putString("webUrl", LoginUser.webUrl);
		edit.putString("name", LoginUser.name);
		edit.putString("ps", LoginUser.ps);
		edit.putString("id", LoginUser.id);
		edit.putString("power", LoginUser.power);
		edit.putString("isOnline", LoginUser.isOnline);
		edit.putString("DefaultRec", LoginUser.DefaultRec);
		edit.commit();
		
	}
	//被内存管理kill掉后调用
		public static void crashCall(SharedPreferences sp)
		{
			LoginUser.webUrl=sp.getString("webUrl", "");
			LoginUser.name=sp.getString("name", "");
			LoginUser.ps=sp.getString("ps", "");
			LoginUser.id=sp.getString("id", "");
			LoginUser.DefaultRec=sp.getString("DefaultRec", "");
			
		}
}
