package com.dhcc.activity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.dhcc.adapter.ContactListViewAdapter;
import com.dhcc.adapter.ContactListViewButtonAdapter;
import com.dhcc.client.Client;
import com.dhcc.client.ClientOutputThread;

import com.dhcc.entity.TranObject;
import com.dhcc.entity.TranObjectType;
import com.dhcc.entity.User;
import com.dhcc.mobile.mobilecom;
import com.dhcc.util.Constants;
import com.dhcc.util.DialogFactory;
import com.dhcc.util.FileUtils;
import com.dhcc.util.GetBitmap;
import com.dhcc.util.ImageDownLoader;
import com.dhcc.util.SharePreferenceUtil;
import com.dhcc.util.TextDownLoader;




import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends Fragment implements OnClickListener {

	
	private mobilecom comm=new mobilecom();
	private String  subUrl=null;
	private TextDownLoader textdownLoader;
	private ImageDownLoader mImageDownLoader;
	private FileUtils fileUtils;
	//private View this_view;
	private ListView lvPerson = null;
	private JSONArray jsonArray=null;
	private static List<User> emps=null;
	private String result=null;
	private static ContactListViewAdapter empAdapter;
	private static ContactListViewButtonAdapter groupBtAdapter;
	private ListView gButton=null;
	private List<String> gButtons=null;
	

	private static TextView c_button_group;
	private static TextView c_button_area;
	
	private View c_left_layout;
	private View c_button_layout_handler;
	private static View c_ContactAndGroup_layout;
	private static View c_Area_layout;
	private  static boolean c_Area_boolean=false;
	public static boolean c_Area_b=false;
//	private View c_title_layout;
	private ImageButton c_title_theme;
//	private TextView c_title_text;
//	float scaleWidth;
//	float scaleHeight;
	int height;
	int a_width;
	int a_height;
	int item_height;
	int v_height=1960;
	int v_width=1080;
	private View c_Area_map_layout;
	private ImageView c_Area_map_b_bj;
	private ImageView c_Area_map_b_tj;
	private ImageView c_Area_map_b_sx;
	private ImageView c_Area_map_b_ta;
	private ImageView c_Area_map_b_cd;
	private ImageView c_Area_map_b_cs;
	private ImageView c_Area_map_b_yx;
	private ImageView c_Area_map_b_gz;
	private static ImageView c_title_back;
	private TextView  c_group_all;
	private ImageView c_map_state;
	
	private Context context;
	private View  contentView;
	private View c_title_menu;
	private View c_title_refresh;
	private View c_title_quit;
	private View c_title_groupSend;
	private Dialog mDialog = null;
	private SharePreferenceUtil util;
	private MyApplication application;
	private View this_view;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=  inflater.inflate(R.layout.activity_contact,container,false);


        return contentView;
    }	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this.getActivity().getApplicationContext();
		//初始化
		//获取屏幕大小
		getScreen();
		application = (MyApplication) context.getApplicationContext();
		util = new SharePreferenceUtil(context, Constants.SAVE_USER);
		fileUtils = new FileUtils(context);
		textdownLoader=new TextDownLoader(context);
		mImageDownLoader=new ImageDownLoader(context);
		//获取元素
		getMemter();
		 //设置本届面跳转按钮layout的高
		LayoutParams Prams_h=c_button_layout_handler.getLayoutParams();
		Prams_h.height=100*height/220;
		c_button_layout_handler.setLayoutParams(Prams_h);
		
		//设置地图界面
		CreateAreaLayout();
		//列表初始化
		initView();
		//列表监听
		setListener();
		//对按钮设置响应
		setButtonListener();

	}
	private void setButtonListener() {
		// TODO Auto-generated method stub
		c_button_group.setOnClickListener(this);
		c_button_area.setOnClickListener(this);
		c_title_back.setOnClickListener(this);
		c_Area_map_b_bj.setOnClickListener(this);
		c_Area_map_b_tj.setOnClickListener(this);
		c_Area_map_b_sx.setOnClickListener(this);
		c_Area_map_b_ta.setOnClickListener(this);
		c_Area_map_b_cd.setOnClickListener(this);
		c_Area_map_b_cs.setOnClickListener(this);
		c_Area_map_b_yx.setOnClickListener(this);
		c_Area_map_b_gz.setOnClickListener(this);
		c_group_all.setOnClickListener(this);
		c_title_refresh.setOnClickListener(this);
		c_title_theme.setOnClickListener(this);
		c_title_quit.setOnClickListener(this);
		c_title_groupSend.setOnClickListener(this);
		this_view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
			
				c_title_menu.setVisibility(View.GONE);
				return true;
				
			}
		});
		
	}
	private void getMemter() {
		// TODO Auto-generated method stub
		this_view=getView().findViewById(R.id.contact_activity);
		c_button_layout_handler=getView().findViewById(R.id.c_button_layout_handler);//内容切换layout
		c_title_back=(ImageView) getView().findViewById(R.id.c_title_back);//返回按钮
		c_Area_map_layout=getView().findViewById(R.id.c_Area_map_layout);//地图layout
		c_Area_map_b_bj=(ImageView) getView().findViewById(R.id.c_Area_map_b_bj);//地图北京按钮
		c_Area_map_b_tj=(ImageView) getView().findViewById(R.id.c_Area_map_b_tj);
		c_Area_map_b_sx=(ImageView) getView().findViewById(R.id.c_Area_map_b_sx);
		c_Area_map_b_ta=(ImageView) getView().findViewById(R.id.c_Area_map_b_ta);
		c_Area_map_b_cd=(ImageView) getView().findViewById(R.id.c_Area_map_b_cd);
		c_Area_map_b_cs=(ImageView) getView().findViewById(R.id.c_Area_map_b_cs);
		c_Area_map_b_yx=(ImageView) getView().findViewById(R.id.c_Area_map_b_yx);
		c_Area_map_b_gz=(ImageView) getView().findViewById(R.id.c_Area_map_b_gz);
		c_button_group=(TextView) getView().findViewById(R.id.c_button_group);//组别按键
		c_button_area=(TextView) getView().findViewById(R.id.c_button_area);//地图按键
		c_left_layout=getView().findViewById(R.id.contact_left_layout);//组别左侧选择按钮layout
		c_ContactAndGroup_layout=getView().findViewById(R.id.c_ContactAndGroup_layout);//组别左侧按键和联系人列表的组合layout
		c_Area_layout=getView().findViewById(R.id.c_Area_layout);//地域layout
		gButton=(ListView) getView().findViewById(R.id.c_g_b_listview);
		c_group_all=(TextView) getView().findViewById(R.id.c_group_all);
		c_map_state=(ImageView) getView().findViewById(R.id.c_map_state);
		c_title_menu=getView().findViewById(R.id.c_title_menu_view);
		c_title_refresh=getView().findViewById(R.id.c_title_menu_refresh_view);
		c_title_theme=(ImageButton) getView().findViewById(R.id.c_title_theme);
		c_title_quit=getView().findViewById(R.id.c_title_menu_quit_view);
		c_title_groupSend=getView().findViewById(R.id.c_title_menu_groupsend_view);
	}
	private void getScreen() {
		// TODO Auto-generated method stub
		//获取屏幕真是高宽
		 DisplayMetrics metric = new DisplayMetrics();
	     this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
	     a_width = metric.widthPixels;
	     a_height=metric.heightPixels;
		//设置菜单栏大小
		 height=a_width/5;
	}
	//根据实际屏幕高宽以及虚拟屏幕高建立地图界面的实际高宽
	private void CreateAreaLayout() {
		// TODO Auto-generated method stub
		

		MarginLayoutParams Prams=(MarginLayoutParams) c_Area_map_layout.getLayoutParams();
		Prams.leftMargin=36*a_width/v_width;
		Prams.topMargin=68*a_height/v_height;
		Prams.rightMargin=90*a_width/v_width;
		c_Area_map_layout.setLayoutParams(Prams);

		GetBitmap getBitmap=new GetBitmap();
		//将图片引用改成drawable，解决bitmap缓存溢出的问题
//		Bitmap btp =getBitmap.GetId(context, R.drawable.c_map);
//		Drawable drawable = new BitmapDrawable(btp); 

//		c_Area_map_layout.setBackgroundDrawable(drawable);
//		Bitmap btp1 =getBitmap.GetId(context, R.drawable.c_text);
//		Drawable drawable1 = new BitmapDrawable(btp1); 
//		c_map_state.setBackgroundDrawable(drawable1);
		c_Area_map_layout.setBackgroundResource(R.drawable.c_map);
		c_map_state.setBackgroundResource(R.drawable.c_text);
		//高，宽，左边距，上边距，基础x=36,y=348
		SetAreaMapButtonParam(c_Area_map_b_bj,78,186,504,192);
		SetAreaMapButtonParam(c_Area_map_b_tj,78,216,748,222);
		SetAreaMapButtonParam(c_Area_map_b_sx,78,116,476,300);
		SetAreaMapButtonParam(c_Area_map_b_ta,78,216,748,408);
		SetAreaMapButtonParam(c_Area_map_b_cd,78,210,172,408);
		SetAreaMapButtonParam(c_Area_map_b_cs,78,216,728,538);
		SetAreaMapButtonParam(c_Area_map_b_yx,78,132,240,634);
		SetAreaMapButtonParam(c_Area_map_b_gz,78,120,684,692);
		
	}
	//地图界面各个按钮的高、宽、上边距、左边距
	private void SetAreaMapButtonParam(ImageView view, int i,
			int j, int k, int l) {
		// TODO Auto-generated method stub
		MarginLayoutParams prams=(MarginLayoutParams) view.getLayoutParams();
		prams.height=i*a_height/v_height;
		prams.width=j*a_width/v_width;
		prams.leftMargin=k*a_width/v_width;
		prams.topMargin=l*a_height/v_height;
		view.setLayoutParams(prams);
	}
	
	//列表的数据获取以及建立
	private void initView() {
		lvPerson = (ListView) getView().findViewById(R.id.c_listview);
	//	String param = "";

		String cls="com.dhcc.container.UserAction";
		String method="getAllContact";
		String param="";
		//若数据在内存或硬盘直接取出，负责从服务器获取并存储到内存和硬盘；
		result=textdownLoader.downloadText(cls,method,param);
		subUrl = (cls+method+param).replaceAll("[^\\w]", "");
		if(result!=null)
		{
			initListView(result);
		}else{
			//Toast.makeText(this.getActivity(), "未获取数据", 0).show();			
			comm.ThreadHttp(cls, method,param, "method", context, 0, handler);
		}
	}
	
	

	private void initListView(String s) {
		// TODO Auto-generated method stub
		emps = new ArrayList<User>();
		gButtons=new ArrayList<String>();
		try {
			
			jsonArray = new JSONArray(s);
			

			for (int i = 0; i < jsonArray.length(); i++) {

				User p=new User();
				int id=Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
				p.setId(id);
				p.setcName(jsonArray.getJSONObject(i).getString("e_name"));
				p.setName(jsonArray.getJSONObject(i).getString("name"));
				p.setPhone(jsonArray.getJSONObject(i).getString("phone1"));
				p.setIcon(jsonArray.getJSONObject(i).getString("icon"));
				p.setGroup(jsonArray.getJSONObject(i).getString("group"));
				p.setArea(jsonArray.getJSONObject(i).getString("area"));
				if(!p.getGroup().equals("null")&&!p.getGroup().equals(""))
				{
					gButtons.add(p.getGroup());
				}
				emps.add(p);
			}
			for  ( int  i=0;i<gButtons.size()-1;i++ )   { 
			    for  ( int j=gButtons.size()-1;j>i;j-- )   { 
			      if  (gButtons.get(i).equals(gButtons.get(j)))   { 
			        gButtons.remove(j); 
			      } 
			    } 
			  }
			gButtons.add("其他组");
			
			empAdapter=new ContactListViewAdapter(this.getActivity(),emps,mImageDownLoader,lvPerson);
			groupBtAdapter=new ContactListViewButtonAdapter(this.getActivity(),gButtons);
			groupBtAdapter.selectIndex=-1;
			gButton.setAdapter(groupBtAdapter);
			empAdapter.buttonHeight=height;
			empAdapter.HeadHeight=height;
			lvPerson.setAdapter(empAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	 Handler handler = new Handler() {
		public void handleMessage(Message paramMessage) {

			if (paramMessage.what == 0) {
				if (comm.RetData.indexOf("error") != -1) {
					Toast.makeText(context, "检查网络!", 1000).show();
					return;
				}
				if (!comm.RetData.equals("")) {
					String result=comm.RetData;
					try {
						fileUtils.writeTextFile(subUrl, result);
					} catch (IOException e) {
						// TODO Auto-generated catch block
					//	Toast.makeText(context, "写入异常", 0).show();
						e.printStackTrace();
					}
					textdownLoader.addTextToMemoryCache(subUrl, result);
					initListView(result);
					if (mDialog!=null&&mDialog.isShowing())
						{
						mDialog.dismiss();
						Toast.makeText(context, "刷新完成", 0).show();

						}
				}
			}
		}};

	//列表事件
	private void setListener() {
		this.lvPerson.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				empAdapter.selectIndex=position;
				empAdapter.notifyDataSetChanged();
			}
		});
		this.gButton.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				groupBtAdapter.selectIndex=position;
				groupBtAdapter.notifyDataSetChanged();
				MarginLayoutParams params=(MarginLayoutParams) c_group_all.getLayoutParams();
				params.leftMargin=20;
				c_group_all.setLayoutParams(params);
				getGroupNemps(gButtons.get(position));

			}
			
		});
		this.lvPerson.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				c_title_menu.setVisibility(View.GONE);
				return false;
			}
		});
		this.gButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				c_title_menu.setVisibility(View.GONE);
				return false;
			}
		});
	}
	/*
	//手机按钮监听截取
	public static boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
		boolean b=false;
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	b=c_Area_boolean;
           if(c_Area_boolean){
        	OnclickTitleBack();        	
           }  
        }  
        return b;  
          
    }  */

	//按钮的点击事件
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		c_title_menu.setVisibility(View.GONE);
		switch (v.getId()) {
		case R.id.c_button_group:
			if(emps!=null)
			{
			c_ContactAndGroup_layout.setVisibility(View.VISIBLE);
			c_Area_layout.setVisibility(View.GONE);
			c_button_group.setBackgroundResource(R.drawable.c_group_a);
			c_button_area.setBackgroundResource(R.drawable.c_group_n);
			
			empAdapter.setEmps(emps);
			empAdapter.selectIndex=-1;
			empAdapter.right_boolean=false;
			empAdapter.buttonHeight=height*9/10;
			empAdapter.notifyDataSetChanged();
			
			MarginLayoutParams params=(MarginLayoutParams) c_group_all.getLayoutParams();
			params.leftMargin=0;
			c_group_all.setLayoutParams(params);
			groupBtAdapter.selectIndex=-1;
			groupBtAdapter.notifyDataSetChanged();
			c_left_layout.setVisibility(View.VISIBLE);
			c_Area_boolean=false;
			c_title_back.setVisibility(View.GONE);
			}
			break;
		case R.id.c_button_area:
			if(emps!=null)
			{
			c_ContactAndGroup_layout.setVisibility(View.GONE);
			c_Area_layout.setVisibility(View.VISIBLE);
			c_button_group.setBackgroundResource(R.drawable.c_group_n);
			c_button_area.setBackgroundResource(R.drawable.c_group_a);
			c_Area_boolean=false;
			c_title_back.setVisibility(View.GONE);
			}
			break;
		case R.id.c_title_back:
			OnclickTitleBack();
			break;
		case R.id.c_Area_map_b_bj:
			Toast.makeText(this.getActivity(), "北京小伙伴欢迎您，带你去天安门", 0).show();
			getAreaNemps("北京");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_tj:
			Toast.makeText(this.getActivity(), "天津小伙伴欢迎您，吃着狗不理", 0).show();
			getAreaNemps("天津");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_sx:
			Toast.makeText(this.getActivity(), "山西小伙伴欢迎您，拿着肉夹馍", 0).show();
			getAreaNemps("山西");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_ta:
			Toast.makeText(this.getActivity(), "泰安小伙伴欢迎您，爬着泰山", 0).show();
			getAreaNemps("泰安");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_cd:
			Toast.makeText(this.getActivity(), "成都小伙伴欢迎您，浏览都江堰", 0).show();
			getAreaNemps("成都");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_cs:
			Toast.makeText(this.getActivity(), "长沙小伙伴欢迎您，品着小龙虾", 0).show();
			getAreaNemps("长沙");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_yx:
			Toast.makeText(this.getActivity(), "玉溪小伙伴欢迎您，抽着玉溪", 0).show();
			getAreaNemps("玉溪");
			OnclikAreaMapButton();
			break;
		case R.id.c_Area_map_b_gz:
			Toast.makeText(this.getActivity(), "广州小伙伴欢迎您，我们只有钱", 0).show();
			getAreaNemps("广州");
			OnclikAreaMapButton();
			break;
		case R.id.c_group_all:
			groupBtAdapter.selectIndex=-1;
			groupBtAdapter.notifyDataSetChanged();
			MarginLayoutParams params=(MarginLayoutParams) c_group_all.getLayoutParams();
			params.leftMargin=0;
			c_group_all.setLayoutParams(params);
			empAdapter.setEmps(emps);
			empAdapter.notifyDataSetChanged();
			break;
		case R.id.c_title_theme:
			c_title_menu.setVisibility(View.VISIBLE);
			break;
		case R.id.c_title_menu_refresh_view:
			showRequestDialog();
			c_title_menu.setVisibility(View.GONE);
			String cls="com.dhcc.container.UserAction";
			String method="getAllContact";
			String param="";
			comm.ThreadHttp(cls, method,param, "method", context, 0, handler);
			break;
		case R.id.c_title_menu_groupsend_view:
			c_title_menu.setVisibility(View.GONE);
			Toast.makeText(context, "发送群消息", 0).show();
//			Intent choice=new Intent(context,ChoiceActivity.class);
//			startActivity(choice);
//			getActivity().finish();
			break;
		case R.id.c_title_menu_quit_view:
			c_title_menu.setVisibility(View.GONE);
			Dialog dialog=new AlertDialog.Builder(context).setTitle("亲!你要走了吗?").setIcon(R.drawable.cloud)
			.setPositiveButton("退出", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int i) {
					//Toast.makeText(ChatActivity.this, "你点击了确定",0);
					if (application.isClientStart()) {
						Client client=application.getClient();
						ClientOutputThread out=client.getClientOutputThread();
						TranObject<User> o = new TranObject<User>(TranObjectType.LOGOUT);
						User u=new User();
						//u.setId(Integer.parseInt(accounts));
						u.setName(util.getName());
						//u.setPassword(Encode.getEncode("MD5", password));
						o.setObject(u);
						out.setMsg(o);
					}
					//跳转到登录界面
					Intent logout=new Intent(context,WelcomeActivity.class);
					startActivity(logout);
					getActivity().finish();
					
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//Toast.makeText(ChatActivity.this, "你点击了取消",0);
					
				}
			}).create();
			dialog.show();
			break;
		default:
			break;
		}
	}

	private void getGroupNemps(String groupName) {
		// TODO Auto-generated method stub
		List<User> NewEmps=new ArrayList<User>();
		List<User> OldEmps=emps;
		if(!groupName.equals("其他组"))
		{
		for(int i=0;i<OldEmps.size();i++)
		{
			User p=new User();
			p=OldEmps.get(i);
			if(p.getGroup().equals(groupName))
			{
				NewEmps.add(p);
			}
		}}else{
			for(int i=0;i<OldEmps.size();i++)
			{
				User p=new User();
				p=OldEmps.get(i);
				if(p.getGroup().equals("其他组")||p.getGroup().equals("null")||p.getGroup().equals(""))
				{
					NewEmps.add(p);
				}
			}
		}
		empAdapter.selectIndex=-1;
		empAdapter.setEmps(NewEmps);
		empAdapter.notifyDataSetChanged();		
	}
	private void getAreaNemps(String AreaName) {
		// TODO Auto-generated method stub
		List<User> NewEmps=new ArrayList<User>();
		List<User> OldEmps=emps;
		for(int i=0;i<OldEmps.size();i++)
		{
			User p=new User();
			p=OldEmps.get(i);
			if(p.getArea().equals(AreaName))
			{
				NewEmps.add(p);
			}
		}
		empAdapter.setEmps(NewEmps);
		empAdapter.right_boolean=true;
		empAdapter.notifyDataSetChanged();
		
	}
	//地图按钮的点击
	private void OnclikAreaMapButton() {
		// TODO Auto-generated method stub
		c_ContactAndGroup_layout.setVisibility(View.VISIBLE);
		c_left_layout.setVisibility(View.GONE);
		c_Area_layout.setVisibility(View.GONE);
		empAdapter.selectIndex=-1;
		empAdapter.right_boolean=true;
		empAdapter.buttonHeight=height;
		empAdapter.notifyDataSetChanged();
		c_Area_boolean=true;
		c_title_back.setVisibility(View.VISIBLE);
	}
	//点击返回相应
	private static void OnclickTitleBack() {
		// TODO Auto-generated method stub
			c_ContactAndGroup_layout.setVisibility(View.GONE);
			c_Area_layout.setVisibility(View.VISIBLE);
			c_button_group.setBackgroundResource(R.drawable.c_group_n);
			c_button_area.setBackgroundResource(R.drawable.c_group_a);
			c_Area_boolean=false;
			c_title_back.setVisibility(View.GONE);
			empAdapter.setEmps(emps);
			empAdapter.notifyDataSetChanged();
	}


	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this.getActivity(), "刷新通讯录...");
		mDialog.show();
	}
	@Override
	public void onDestroyView() {
		mImageDownLoader.cancelTask();
		super.onDestroyView();
	}
	@Override  
    public void onDestroy() {  
        super.onDestroy();   
    } 
	public static List<User> getEmps() {
		return emps;
	} 
}

