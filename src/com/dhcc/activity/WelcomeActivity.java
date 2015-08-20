package com.dhcc.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.ddpush.client.udp.Params;
import org.ddpush.client.udp.service.OnlineService;

import com.dhcc.util.Constants;
import com.dhcc.util.SharePreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.WindowManager;

/**
 * 欢迎界面welcome
 * 
 */
public class WelcomeActivity extends Activity {
	private static final String USER_INFO = "person";
	private SharedPreferences sp ;
	private SharedPreferences.Editor editor;
	private SharePreferenceUtil util;
	private Handler mHandler;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉状态栏
		setContentView(R.layout.activity_welcome);
		
		SharedPreferences account = this.getSharedPreferences(Params.DEFAULT_PRE_NAME,Context.MODE_PRIVATE);
		
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		if (util.getisFirst()) {
			createShut();// 创建快捷方式
			moveSound();
		}
		initView();
	}

	public void initView() {
		if (util.getIsStart()) {// 如果正在后台运行
			goMainListActivity();
		} else {// 如果是首次运行
			mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					goLoginActivity();
				}
			}, 1000);
		}
	}

	/**
	 * 进入登陆界面
	 */
	public void goLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 进入主界面
	 */
	public void goMainListActivity() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		util.setIsStart(false);
		finish();
	}

	/**
	 * 创建桌面快捷方式
	 */
	public void createShut() {
		// 创建添加快捷方式的Intent
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		String title = getResources().getString(R.string.app_name);
		// 加载快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				WelcomeActivity.this, R.drawable.cloud);
		// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
		Intent myIntent = new Intent(WelcomeActivity.this,
				WelcomeActivity.class);
		// 设置快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 设置快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 设置快捷方式对应的Intent
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// 发送广播添加快捷方式
		sendBroadcast(addIntent);
		util.setIsFirst(false);
	}

	/**
	 * 复制原生资源文件“来消息声音”到应用目录下，
	 */
	public void moveSound() {
		InputStream is = getResources().openRawResource(R.raw.msg);
		File file = new File(getFilesDir(), "msg.mp3");
		try {
			OutputStream os = new FileOutputStream(file);
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
//			System.out.println("声音复制完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}