package com.dhcc.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.dhcc.client.Client;
import com.dhcc.client.ClientOutputThread;
import com.dhcc.entity.TranObject;
import com.dhcc.entity.TranObjectType;
import com.dhcc.entity.User;
import com.dhcc.util.DialogFactory;
import com.dhcc.util.Encode;




public class RegisterActivity extends MyActivity implements OnClickListener {
	private LinearLayout layout_all;
	private RelativeLayout layout_title;
	private LinearLayout layout_content;
	private RelativeLayout layout_name;
	private RelativeLayout layout_ps;
	private RelativeLayout layout_cname;
	private RelativeLayout layout_gender;
	private RelativeLayout layout_area;
	private RelativeLayout layout_group;
	private RelativeLayout layout_phone;
	private RelativeLayout layout_tel;
	private RelativeLayout layout_problem;
	private RelativeLayout layout_answer;
	private RelativeLayout layout_b;
	private ToggleButton   b_gender;
	private Button register;
	private LinearLayout layout_gender_select;
	
	private EditText name,password,cname,area;
	private EditText group,phone1,phone2;
	private EditText ask,answer;
	private int a_width;
	private int a_height;
	private int v_height=1960;
	private int v_width=1080;
	
	private MyApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		application=(MyApplication) this.getApplicationContext();
		initView();
		getScreen();
		getMemter();
		setParams();
		
		setListener();
	}
	private void initView() {
		name=(EditText) findViewById(R.id.r_name_input);
		password=(EditText) findViewById(R.id.r_password_input);
		cname=(EditText) findViewById(R.id.r_cname_input);
		b_gender=(ToggleButton) findViewById(R.id.r_gender_logo);
		area=(EditText) findViewById(R.id.r_area_input);
		group=(EditText) findViewById(R.id.r_group_input);
		phone1=(EditText) findViewById(R.id.r_phone1_input);
		phone2=(EditText) findViewById(R.id.r_tel_input);
		ask=(EditText) findViewById(R.id.r_problem_input);
		answer=(EditText) findViewById(R.id.r_answer_input);
		register=(Button) findViewById(R.id.regist_b);
		//b_gender.isChecked();
		
	}
	private void setListener() {
		// TODO Auto-generated method stub
		b_gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{	
					buttonView.setBackgroundResource(R.drawable.r_sex_male);
				}else{
					buttonView.setBackgroundResource(R.drawable.r_sex_female);
				}
				
			}
		});
		layout_gender_select.setOnClickListener(this);
		layout_content.setOnClickListener(this);
		register.setOnClickListener(this);
	}
	private void setParams() {
		// TODO Auto-generated method stub
		//设置整体内容参数
		MarginLayoutParams params=(MarginLayoutParams) layout_content.getLayoutParams();
		params.leftMargin=100*a_width/v_width;
		params.rightMargin=100*a_width/v_width;
		layout_content.setLayoutParams(params);
		MarginLayoutParams params1=(MarginLayoutParams) b_gender.getLayoutParams();
		params1.height=58*a_width/v_width;
		params1.width=120*a_width/v_width;
		b_gender.setLayoutParams(params1);
		
		setRegistParam(layout_name,180);
		setRegistParam(layout_ps,180);
		setRegistParam(layout_cname,180);
		setRegistParam(layout_gender,180);
		setRegistParam(layout_area,180);
		setRegistParam(layout_group,180);
		setRegistParam(layout_phone,180);
		setRegistParam(layout_tel,180);
		setRegistParam(layout_problem,180);
		setRegistParam(layout_answer,180);
		setRegistParam(layout_b,180);
	}
	private void setRegistParam(RelativeLayout l,int h) {
		// TODO Auto-generated method stub
		MarginLayoutParams params=(MarginLayoutParams) l.getLayoutParams();
		params.height=h*a_height/v_height;
		params.leftMargin=70*a_width/v_width;
		params.rightMargin=70*a_width/v_width;
		l.setLayoutParams(params);
	}
	private void getMemter() {
		// TODO Auto-generated method stub
		layout_all=(LinearLayout) findViewById(R.id.activity_register_layout);
		layout_title=(RelativeLayout) findViewById(R.id.r_title_layout);
		layout_content=(LinearLayout) findViewById(R.id.r_content);
		layout_name=(RelativeLayout) findViewById(R.id.r_name_layout);
		layout_ps=(RelativeLayout) findViewById(R.id.r_password_layout);
		layout_cname=(RelativeLayout) findViewById(R.id.r_cname_layout);
		layout_gender=(RelativeLayout) findViewById(R.id.r_gender_layout);
		layout_area=(RelativeLayout) findViewById(R.id.r_area_layout);

		layout_group=(RelativeLayout) findViewById(R.id.r_group_layout);
		layout_phone=(RelativeLayout) findViewById(R.id.r_phone1_layout);
		layout_tel=(RelativeLayout) findViewById(R.id.r_tel_layout);
		layout_problem=(RelativeLayout) findViewById(R.id.r_problem_layout);
		layout_answer=(RelativeLayout) findViewById(R.id.r_answer_layout);
		layout_b=(RelativeLayout) findViewById(R.id.r_b_layout);
		layout_gender_select=(LinearLayout) findViewById(R.id.r_gender_select_layout);
		
	}
	private void getScreen() {
		// TODO Auto-generated method stub
		//获取屏幕真是高宽
		 DisplayMetrics metric = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metric);
	     a_width = metric.widthPixels;
	     a_height=metric.heightPixels;
	}
	/**
	 * 隐藏软件盘
	 */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return false;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.r_gender_select_layout:
			if(!b_gender.isChecked())
				{
				b_gender.setBackgroundResource(R.drawable.r_sex_male);
				b_gender.setChecked(true);
			}else{
				b_gender.setBackgroundResource(R.drawable.r_sex_female);
				b_gender.setChecked(false);
			}
		break;
		case R.id.r_content:
			hideSoftInputView();
			
			break;
		case R.id.regist_b://注册
			regist();
			break;
		default:
			break;
		}
	}
	private Dialog mDialog = null;

	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在注册中...");
		mDialog.show();
	}
	private void regist() {
		//name,password,cname,area; group,phone1,phone2; ask,answer;
		String rname=name.getText().toString();
		String rpassword=password.getText().toString();
		String rcname=cname.getText().toString();
		String rarea=area.getText().toString();
		String rgender=b_gender.isChecked()?"男":"女";
		String rgroup=group.getText().toString();
		String rphone1=phone1.getText().toString();
		String rphone2=phone2.getText().toString();
		String rask=ask.getText().toString();
		String ranswer=answer.getText().toString();
		
		if(TextUtils.isEmpty(rname)/*||TextUtils.isEmpty(rpassword)||TextUtils.isEmpty(rcname)||
		   TextUtils.isEmpty(rarea)||TextUtils.isEmpty(rgroup)||TextUtils.isEmpty(rphone1)||
		   TextUtils.isEmpty(rphone2)||TextUtils.isEmpty(rask)||TextUtils.isEmpty(ranswer)
		   ||TextUtils.isEmpty(rgender)*/){
			DialogFactory.ToastDialog(RegisterActivity.this, "注册","亲！所有项都不能为空的哦");
			return ;
		}else{
			showRequestDialog();
			// 提交注册信息
			if (application.isClientStart()) {// 如果已连接上服务器
				Client client = application.getClient();
//				Client client = GetMsgService.client;
				ClientOutputThread out = client.getClientOutputThread();
				TranObject<User> o = new TranObject<User>(TranObjectType.REGISTER);
				User u = new User();
				//name,password,cname,area; group,phone1,phone2; ask,answer;
				u.setName(rname);
				u.setPassword(Encode.getEncode("MD5", rpassword));
				u.setcName(rcname);
				u.setArea(rarea);
				u.setGroup(rgroup);
				u.setGender(rgender);
				u.setPhone(rphone1);
				u.setTel(rphone2);
				u.setAsk(rask);
				u.setAnswer(ranswer);
				o.setObject(u);
				out.setMsg(o);
			} else {
				if (mDialog.isShowing())
					mDialog.dismiss();
				DialogFactory.ToastDialog(this, "注册", "亲！服务器暂未开放哦");
			}
		}
		
		
	}
	@Override
	public void getMessage(TranObject msg) {
		// TODO Auto-generated method stub
		switch (msg.getType()) {
		case REGISTER:
			User u = (User) msg.getObject();
			String name=u.getName();
			if (name!=null) {
				if (mDialog != null) {
					mDialog.dismiss();
					mDialog = null;
				}
				DialogFactory.ToastDialog(RegisterActivity.this, "注册","亲！请牢记您的登录账户哦：" +name);
				Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
				//i.putExtra(Constants.MSGKEY, msg);
				startActivity(i);
				//if (mDialog.isShowing())mDialog.dismiss();
				finish();
			} else {
				if (mDialog != null) {
					mDialog.dismiss();
					mDialog = null;
				}
				DialogFactory.ToastDialog(RegisterActivity.this, "注册","亲！很抱歉！注册失败，请用其他用户名注册");
			}
			break;

		default:
			break;
		}
	}

}
