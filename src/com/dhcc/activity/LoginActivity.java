package com.dhcc.activity;






import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ddpush.client.udp.Params;
import org.ddpush.client.udp.service.OnlineService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dhcc.client.Client;
import com.dhcc.client.ClientOutputThread;
import com.dhcc.entity.ChatMsgEntity;
import com.dhcc.entity.LoginUser;
import com.dhcc.entity.MesList;
import com.dhcc.entity.TranObject;
import com.dhcc.entity.TranObjectType;
import com.dhcc.entity.User;
import com.dhcc.mobile.mobilecom;
import com.dhcc.util.Constants;
import com.dhcc.util.DialogFactory;
import com.dhcc.util.DoSharePre;
import com.dhcc.util.Encode;
import com.dhcc.util.MessageDB;
import com.dhcc.util.SharePreferenceUtil;
import com.dhcc.util.UserDB;


public class LoginActivity extends MyActivity implements OnClickListener {
	SharedPreferences perferences;
	private Button  login;
	private ToggleButton save;
	private EditText l_name;
	private EditText l_password;
	private View l_setNet_b;
	private TextView regist;
	private TextView lostPs;
	private int v_height=800;
	private int v_width=480;
	private int a_width;
	private int a_height;
	private float density;
	mobilecom com=new mobilecom();
	private MyApplication application;
	Context context=this;
	private SharePreferenceUtil util;
	private SharePreferenceUtil util_port;
	private SharePreferenceUtil util_count;
	private SharedPreferences.Editor editor;
	private static ArrayList<MesList>	unReadMessages;
	private MessageDB messageDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		application = (MyApplication) this.getApplicationContext();
		perferences=PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		//获取设备宽高
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        a_width = metric.widthPixels;  // 屏幕宽度（像素）
        a_height = metric.heightPixels;  // 屏幕高度（像素）
        density = metric.density;
        util= new SharePreferenceUtil(LoginActivity.this, Constants.SAVE_USER);
        util_count=new SharePreferenceUtil(LoginActivity.this, Constants.COUNTS);
        util_port= new SharePreferenceUtil(LoginActivity.this, Constants.IP_PORT);
        messageDB=new MessageDB(context);
		LoginUser.webUrl="http://"+util_port.getIp()+":"+util_port.getTPort()+"/"+util_port.getServer()+"/";//第一个参数为要获取的值的Key，第二个参数为没有Key对应的值返回的默认值。
		
		Toast.makeText(this, LoginUser.webUrl, 0).show();
		//获取元素
		getMemter();
		//设置布局
		setLayout();
		//获取perferences
		if(util.getIsSave())
		{
			l_name.setText(util.getName());
			l_password.setText(util.getPasswd());		
			save.setChecked(true);
			save.setBackgroundResource(R.drawable.save2);
		}
		reset();
		//创建监听
		setListener();
		
	}
	private void reset() {
		// TODO Auto-generated method stub
		
		SharedPreferences account = this.getSharedPreferences(Params.DEFAULT_PRE_NAME,Context.MODE_PRIVATE);
		editor = account.edit();
		editor.putString(Params.SERVER_IP, util_port.getIp());
		editor.putString(Params.SERVER_PORT, "9966");
		editor.putString(Params.PUSH_PORT, "9999");
		editor.putString(Params.USER_NAME, util.getName());
		editor.putString(Params.SENT_PKGS, "0");
		editor.putString(Params.RECEIVE_PKGS, "0");
		editor.commit();
		Intent startSrv = new Intent(this,OnlineService.class);
		//Intent startSrv = new Intent(this,OnlineService.class);
		startSrv.putExtra("CMD", "RESET");
		this.startService(startSrv);
		
		/*Intent startMsg=new Intent(this,GetMsgService.class);
		startMsg.putExtra("CMD","RESET");
		this.startService(startMsg);*/
	}

	@Override
	protected void onResume() {// 在onResume方法里面先判断网络是否可用，再启动服务,这样在打开网络连接之后返回当前Activity时，会重新启动服务联网，
		super.onResume();
		if (isNetworkAvailable()) {//连接服务
			Intent service = new Intent(this, GetMsgService.class);
			startService(service);
		} else {
			toast(this);
		}
	}
	private void setLayout() {
		// TODO Auto-generated method stub
		MarginLayoutParams prams=(MarginLayoutParams)l_setNet_b.getLayoutParams();
		prams.height=(int) (50*density);
		prams.width=(int) (50*density);
		prams.rightMargin=38*a_width/v_width;
		prams.topMargin=412*a_height/v_height;
		l_setNet_b.setLayoutParams(prams);
	}


	private void getMemter() {
		// TODO Auto-generated method stub
		l_name=(EditText) findViewById(R.id.login_edit_name);
		l_password=(EditText) findViewById(R.id.login_edit_ps);
		login=(Button) findViewById(R.id.login_but);
		save=(ToggleButton) findViewById(R.id.login_save_logo);
		l_setNet_b= findViewById(R.id.l_setnet);
		regist=(TextView) findViewById(R.id.login_text_register);
		lostPs=(TextView) findViewById(R.id.login_text_f_ps);
	}
	/**
	 * 点击登录按钮后，弹出验证对话框
	 */
	private Dialog mDialog = null;

	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在验证账号...");
		mDialog.show();
	}
	private void setListener() {
		// TODO Auto-generated method stub
	
		
		save.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{	
					buttonView.setBackgroundResource(R.drawable.save2);
					util.setName(l_name.getText().toString());
					util.setPasswd(l_password.getText().toString());
					util.setIsSave(true);
				}else{
					buttonView.setBackgroundResource(R.drawable.save);
				}
				
			}
		});
		login.setOnClickListener(this);
		l_setNet_b.setOnClickListener(this);
		regist.setOnClickListener(this);
		lostPs.setOnClickListener(this);
	
	}
	// 依据自己需求处理父类广播接收者收取到的消息
	public void getMessage(TranObject msg){
		if(msg!=null){
			switch(msg.getType()){
			case MESSAGELIST:
				ArrayList<MesList>	messages=(ArrayList<MesList>) msg.getObject();
				
				//messageDB.saveNesList(util.getName() ,messages);
				
				for(int i=0;i<messages.size();i++){
					List<ChatMsgEntity> contents=messages.get(i).getContents();
					
					Iterator iter = messages.get(i).getUnReadNums().entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String key = (String) entry.getKey();
						Integer num = (Integer) entry.getValue();
						util_count.setNum(key, num);
					}
					for (ChatMsgEntity entity : contents) {
						if(!entity.isRead()){
						//未读消息查看后，存入本地数据库
							//entity.setNum(counts);
							messageDB.saveUnReadMsg(entity.getFromUser(),util.getName(), entity);
						}
					}
					
				}
				unReadMessages=messages;
				/*for (MesList mes : messages) {
					ChatMsgEntity entity=new ChatMsgEntity();
					entity.setName(mes.getFromUser());
					entity.setDate(mes.getTime());
					entity.setImg("haha");
					entity.setMessage(mes.getContent());
					entity.setMsgType(true);
					
					//以接受人的名字建表，将聊天内容保存
					messageDB.saveMsg(util.getName(),entity);
						
				}*/
				goToLogin(msg);
			break;
			case LOGIN:
				//List<User> list = (List<User>)msg.getObject();
				//Boolean loginResult=(Boolean)msg.getObject();
				User user=(User) msg.getObject();
				if(user!=null){
					//保存用户信息
					//util.setId(l_name.getText().toString());
					unReadMessages=null;
					util.setPasswd(l_password.getText().toString());
					util.setEmail(user.getEmail());
					util.setName(user.getName());
					UserDB db = new UserDB(LoginActivity.this);
					//db.addUser(list);
					goToLogin(msg);
				}else {
					DialogFactory.ToastDialog(LoginActivity.this, "登录","亲！您的帐号或密码错误哦");
					if (mDialog.isShowing())
						mDialog.dismiss();
				}
				break;
			default:
				break;
			}
		}
	}
	private void goToLogin(TranObject msg) {
		util.setPasswd(l_password.getText().toString());
		//util.setEmail(user.getEmail());
		util.setName(l_name.getText().toString());
		//UserDB db = new UserDB(LoginActivity.this);
		//db.addUser(list);
		Intent i=new Intent(LoginActivity.this,MainActivity.class);
		i.putExtra(Constants.MSGKEY, msg);
		startActivity(i);
		if (mDialog.isShowing())mDialog.dismiss();
		finish();
		DoSharePre.loginCall(perferences); 
		Toast.makeText(getApplicationContext(), "登录成功", 0).show();
		
	}
	Handler handler = new Handler() 
	{
		public void handleMessage(Message paramMessage) 
		{
			if(paramMessage.what == 0)
			{
				if (com.RetData.indexOf("error")!=-1)
				{
					Toast.makeText(getApplicationContext(), "连接错误！", 1000).show();
					return;
				}
               if (!com.RetData.equals(""))
               {
            	   String[] s=com.RetData.split("\\^");
            	   String s1=LoginUser.ps;
            	   if(!LoginUser.ps.equals(s[2]))
            	   {
            		   Toast.makeText(LoginActivity.this, "用户名或密码错误", 0).show();
            		   return;
            	   }else{
            		LoginUser.id=s[0];
            		LoginUser.power=s[3];
            		LoginUser.isOnline=s[4];
	   				Intent i=new Intent();
	   				i.setClass(LoginActivity.this, MainActivity.class);
	   				startActivity(i);
	   		        LoginActivity.this.finish();
	   		        DoSharePre.loginCall(perferences);
            	   //Toast.makeText(MainActivity.this, com.RetData, 0).show();
            	   }
            	   }
			}
		}
	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return false;
	}
	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	private void toast(Context context) {
		new AlertDialog.Builder(context)
				.setTitle("温馨提示")
				.setMessage("亲！您的网络连接未打开哦")
				.setPositiveButton("前往打开",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						}).setNegativeButton("取消", null).create().show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_but:
			String accounts = l_name.getText().toString();
			String password = l_password.getText().toString();
			if(save.isChecked()){
				util.setName(l_name.getText().toString());
				util.setPasswd(l_password.getText().toString());
				util.setIsSave(true);
			}
			if (accounts.length() == 0 || password.length() == 0) {
				DialogFactory.ToastDialog(context, "登录", "亲！帐号或密码不能为空哦");
			} else{
				//显示请求验证
				showRequestDialog();
				if (application.isClientStart()) {
					Client client=application.getClient();
					ClientOutputThread out=client.getClientOutputThread();
					TranObject<User> o = new TranObject<User>(TranObjectType.LOGIN);
					User u=new User();
					//u.setId(Integer.parseInt(accounts));
					u.setName(accounts);
					u.setPassword(Encode.getEncode("MD5", password));
					o.setObject(u);
					out.setMsg(o);
				}else{
					if (mDialog.isShowing())
						mDialog.dismiss();
					DialogFactory.ToastDialog(LoginActivity.this, "登录","亲！服务器暂未开放哦");
				}
				
			}
			break;
		case R.id.l_setnet:
			Intent intent = new Intent();
	        intent.setClass(LoginActivity.this, NetSetActivity.class);
	        startActivity(intent);
			break;
		case R.id.login_text_register:
			Intent intent1 = new Intent();
	        intent1.setClass(LoginActivity.this, RegisterActivity.class);
	        startActivity(intent1);
			break;
		case R.id.login_text_f_ps:
			Toast.makeText(this, "忘记密码", 0).show();
			break;
		default:
			break;
		}
	}
	public static ArrayList<MesList> getUnReadMessages() {
		return unReadMessages;
	}
}
