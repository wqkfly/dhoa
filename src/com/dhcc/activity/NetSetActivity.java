package com.dhcc.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dhcc.util.Constants;
import com.dhcc.util.SharePreferenceUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NetSetActivity extends Activity implements OnClickListener {
	
	private LinearLayout content;
	private ImageView logo;
	private RelativeLayout layout_in;
	private TextView text_in;
	private EditText input_in;
	private RelativeLayout layout_in_port;
	private TextView text_in_port;
	private EditText input_in_port;
	
	private RelativeLayout layout_out;
	private TextView text_out;
	private EditText input_out;
	private RelativeLayout layout_out_port;
	private TextView text_out_port;
	private EditText input_out_port;
	
	private RelativeLayout layout_service;
	private TextView text_service;
	private EditText input_service;
	private RelativeLayout layout_domain;
	private TextView text_domain;
	private EditText input_domain;
	
	private RelativeLayout layout_select;
	private RadioGroup radios;
	private RadioButton radio_out;
	private RadioButton radio_in;
	
	
	private RelativeLayout layout_button;
	private Button save;
	private Button cancel;
	private ImageView back;
	
	private int a_width;
	private int a_height;
	private int v_height=1960;
	private int v_width=1080;
	private  LinearLayout layout_this;
	private SharePreferenceUtil util;
	private SharePreferenceUtil util_user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_set);
		util=new SharePreferenceUtil(NetSetActivity.this, Constants.IP_PORT);
		util_user=new SharePreferenceUtil(NetSetActivity.this,Constants.SAVE_USER);
		//获取屏幕大小
		getScreen();
		getMemter();
		setParams();
		setHint();
		
		setListener();
		 logo.setFocusable(true); 
		 logo.setFocusableInTouchMode(true);    
		 logo.requestFocus();            
		 logo.requestFocusFromTouch();
	}

	private void setHint() {
		// TODO Auto-generated method stub
		if(util.getSelect()==1)
		{
			radio_in.setChecked(true);
		}else{
			radio_out.setChecked(true);
		}
		input_in.setText(util.getInIp());
		input_in_port.setText(String.valueOf(util.getTInPort()));
		input_out.setText(util.getOutIp());
		input_out_port.setText(String.valueOf(util.getTOutPort()));
		input_service.setText(util.getServer());
		input_domain.setText(util.getDomain());
	}

	private void setListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		content.setOnClickListener(this);
	}

	private void setParams() {
		// TODO Auto-generated method stub
		//设置整体内容参数
		MarginLayoutParams params=(MarginLayoutParams) content.getLayoutParams();
		params.leftMargin=150*a_width/v_width;
		params.rightMargin=150*a_width/v_width;
		content.setLayoutParams(params);
		//设置图片参数
		MarginLayoutParams params_logo=(MarginLayoutParams) logo.getLayoutParams();
		params_logo.topMargin=86*a_height/v_height;
		params_logo.bottomMargin=62*a_height/v_height;
		params_logo.height=350*a_height/v_height;
		params_logo.width=426*a_height/v_height;
		logo.setLayoutParams(params_logo);
		
		setNetParam(layout_in,150);
		setNetParam(layout_in_port,150);
		setNetParam(layout_out,150);
		setNetParam(layout_out_port,150);
		setNetParam(layout_service,150);
		setNetParam(layout_domain,150);
		setNetParam(layout_select,150);
		MarginLayoutParams params_save=(MarginLayoutParams) save.getLayoutParams();
		params_save.leftMargin=96*a_width/v_width;
		params_save.width=260*a_width/v_width;
		save.setLayoutParams(params_save);
//		save.setTextSize(40*a_height/v_height);
		MarginLayoutParams params_cancel=(MarginLayoutParams) cancel.getLayoutParams();
		params_cancel.width=260*a_width/v_width;
		params_cancel.rightMargin=96*a_width/v_width;
		cancel.setLayoutParams(params_cancel);
//		cancel.setTextSize(40*a_height/v_height);
	}
	private void setNetParam(RelativeLayout l,int h) {
		// TODO Auto-generated method stub
		MarginLayoutParams params=(MarginLayoutParams) l.getLayoutParams();
		params.height=h*a_height/v_height;
		l.setLayoutParams(params);
	}

	private void getScreen() {
		// TODO Auto-generated method stub
		//获取屏幕真是高宽
		 DisplayMetrics metric = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metric);
	     a_width = metric.widthPixels;
	     a_height=metric.heightPixels;
	}
	private void getMemter() {
		// TODO Auto-generated method stub
		back=(ImageView) findViewById(R.id.n_title_back);
		layout_button=(RelativeLayout) findViewById(R.id.n_button);
		save=(Button) findViewById(R.id.n_button_save);
		cancel=(Button) findViewById(R.id.n_button_cancel);
		content=(LinearLayout) findViewById(R.id.n_content);
		logo=(ImageView) findViewById(R.id.n_logo);
		layout_in=(RelativeLayout) findViewById(R.id.n_in_src);
		text_in=(TextView) findViewById(R.id.n_in_src_name_text);
		input_in=(EditText) findViewById(R.id.n_in_src_name_input);
		layout_in_port=(RelativeLayout) findViewById(R.id.n_in_port);
		text_in_port=(TextView) findViewById(R.id.n_in_port_name_text);
		input_in_port=(EditText) findViewById(R.id.n_in_port_name_input);
		
		layout_out=(RelativeLayout) findViewById(R.id.n_out_src);
		text_out=(TextView) findViewById(R.id.n_out_name_text);
		input_out=(EditText) findViewById(R.id.n_out_name_input);
		layout_out_port=(RelativeLayout) findViewById(R.id.n_out_port);
		text_out_port=(TextView) findViewById(R.id.n_out_port_name_text);
		input_out_port=(EditText) findViewById(R.id.n_out_port_name_input);
		
		
		layout_service=(RelativeLayout) findViewById(R.id.n_service_src);
		text_service=(TextView) findViewById(R.id.n_service_src_name_text);
		input_service=(EditText) findViewById(R.id.n_service_src_name_input);
		layout_domain=(RelativeLayout) findViewById(R.id.n_domain_src);
		text_domain=(TextView) findViewById(R.id.n_domain_src_name_text);
		input_domain=(EditText) findViewById(R.id.n_domain_src_name_input);
		layout_this=(LinearLayout) findViewById(R.id.activity_net_set_layout);
		layout_select=(RelativeLayout) findViewById(R.id.n_r_select_layout);
		radios=(RadioGroup) findViewById(R.id.n_r_select);
		radio_in=(RadioButton) findViewById(R.id.n_r_in);
		radio_out=(RadioButton) findViewById(R.id.n_r_out);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.net_set, menu);
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.n_button_save:
			String s1=input_in.getText().toString();
			String s2=input_out.getText().toString();
			if(!isIP(s1)||!isIP(s2))
			{
				Toast.makeText(this, "地址输入不符合规范", 0).show();
				
			}else{saveNetParams();
			Intent n=new Intent();
			n.setClass(this, LoginActivity.class);
			startActivity(n);
			finish();}
			break;
		case R.id.n_button_cancel:
			Intent n1=new Intent();
			n1.setClass(this, LoginActivity.class);
			startActivity(n1);	
			finish();
			break;
		case R.id.n_title_back:
				Intent n2=new Intent();
				n2.setClass(this, LoginActivity.class);
				startActivity(n2);
				finish();
			break;
		case R.id.n_content:
			hideSoftInputView();
			
			break;
		default:
			break;
		}
	}

	private void saveNetParams() {
		// TODO Auto-generated method stub
		String insrc=input_in.getText().toString();
		String inport=input_in_port.getText().toString();
		String outsrc=input_out.getText().toString();
		String outport=input_out_port.getText().toString();
		String service=input_service.getText().toString();
		String domain=input_domain.getText().toString();
		boolean b=radio_in.isChecked();
		if(b)
		{
			util.setSelect(1);
			util.setIp(insrc);
			util.setTPort(Integer.parseInt(inport));
			util_user.setIp(insrc);
		}else{
			util.setSelect(2);
			util.setIp(outsrc);
			util.setTPort(Integer.parseInt(outport));
			util_user.setIp(insrc);
		}
		util.setInIp(insrc);
		util.setTInPort(Integer.parseInt(inport));
		util.setOutIp(outsrc);
		util.setTOutPort(Integer.parseInt(outport));
		util.setServer(service);
		util.setDomain(domain);
		
	}
	 public boolean isIP(String addr) {
	        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
	            return false;
	        }
	        String rexp = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	 
	        // return addr.matches(rexp);
	 
	        Pattern pat = Pattern.compile(rexp);
	 
	        Matcher mat = pat.matcher(addr);
	 
	        boolean ipAddress = mat.matches();
	        return ipAddress;
	 
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
	//禁掉返回键
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
			return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	
}
