package com.dhcc.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ddpush.client.udp.Params;
import org.ddpush.client.udp.Util;
import org.ddpush.client.udp.service.OnlineService;
import org.ddpush.im.v1.client.appserver.Pusher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhcc.adapter.ChatLVAdapter;
import com.dhcc.adapter.FaceGVAdapter;
import com.dhcc.adapter.FaceVPAdapter;
import com.dhcc.client.Client;
import com.dhcc.client.ClientOutputThread;
import com.dhcc.entity.ChatMsgEntity;
import com.dhcc.entity.MesList;
import com.dhcc.entity.TextMessage;
import com.dhcc.entity.TranObject;
import com.dhcc.entity.TranObjectType;
import com.dhcc.entity.User;
import com.dhcc.mobile.mobilecom;
import com.dhcc.util.Constants;
import com.dhcc.util.MessageDB;
import com.dhcc.util.MyDate;
import com.dhcc.util.SharePreferenceUtil;
import com.dhcc.view.DropdownListView;
import com.dhcc.view.MyEditText;

public class ChatActivity extends MyActivity implements OnClickListener{
	private mobilecom comm=new mobilecom();
	private DropdownListView mListView;
	private SimpleDateFormat sd;
	private ChatLVAdapter mLvAdapter;
	private Button back,send,logout;
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;
	private LinearLayout chat_face_container;
	private ImageView image_face;//表情图标
	// 7列3行
	private int columns = 6;
	private int rows = 4;
	private List<View> views = new ArrayList<View>();
	private MyEditText input;
	private User user;

	private SharePreferenceUtil util;
	private String content="";
	private List<String> staticFacesList;
	private MessageDB messageDB;
	private List<ChatMsgEntity> chatArray=new ArrayList<ChatMsgEntity>();//存放聊天内容
	private MyApplication application;
	private MesList mesList;
	private Context context;
	//private ContactActivity contactActity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		context=getApplication();
		//contactActity=new ContactActivity();
		application = (MyApplication) getApplicationContext();
		messageDB = new MessageDB(this);
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		
		user=(User) getIntent().getSerializableExtra("person");
		mesList=(MesList) getIntent().getSerializableExtra("mesList");
		
		initStaticFaces();
		initView();
		initData();//初始化数据
		/*if(mesList!=null){
			initData();
		}else{
			initData1();
		}*/
		setListener();
		
	}
	

	private void initView() {
		back=(Button) findViewById(R.id.chat_back); 
		logout=(Button) findViewById(R.id.setting);
		send=(Button) findViewById(R.id.send); 
		input=(MyEditText) findViewById(R.id.input_sms);
		//表情图标
		image_face=(ImageView) findViewById(R.id.image_face);
		//表情布局
		chat_face_container=(LinearLayout) findViewById(R.id.chat_face_container);
		logout.setOnClickListener(this);
		back.setOnClickListener(this);
		//发送
		send.setOnClickListener(this);
		//输入框
		input.setOnClickListener(this);
		
		image_face.setOnClickListener(this);
		mListView = (DropdownListView) findViewById(R.id.chat_listview);
		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		mViewPager.setOnPageChangeListener(new PageChange());
		//表情下小圆点
	    mDotsLayout=(LinearLayout) findViewById(R.id.face_dots_container);
	   // mListView.setOnRefreshListenerHead(this);
	    initViewPager();
	    mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					if(chat_face_container.getVisibility()==View.VISIBLE){
						chat_face_container.setVisibility(View.GONE);
					}
				}
				return false;
			}
	    	
	    });
	}
	

	private void initData(){
		
		/*if(mesList!=null){
			for( ChatMsgEntity entity :mesList.getContents()){
				
				ChatMsgEntity entity=new ChatMsgEntity();
				
				if(mes.getIsRead()==0){
					entity.setDate(mes.getTime());
					entity.setMessage(mes.getContent());
					entity.setMsgType(true);
					entity.setName(mesList.getFromUser());
					entity.setRead(true);
					mes.setIsRead(1);
					//未读消息查看后，存入本地数据库
					
					messageDB.saveMsg(user.getName(), entity);
				}
				
				
			}
		}*/
		
		List<ChatMsgEntity> list=messageDB.getMsg(util.getName());
		
		if(list.size()>0){
			for(ChatMsgEntity entity :list){
				
				if(entity.getFromUser().equals("")){
					entity.setFromUser(user.getName());
				}
				/*if(entity.getImg()==null){
					entity.setImg(user.getIcon());
				}*/
				chatArray.add(entity);
			}
			Collections.reverse(chatArray);
		}
		
		updataUnRead();
		mLvAdapter =new ChatLVAdapter(this, chatArray);
		mListView.setAdapter(mLvAdapter);
		mListView.setSelection(mLvAdapter.getCount() - 1);
	}
	private void updataUnRead() {
		String cls="com.dhcc.container.MessageAction";
		String method="updateMessages";
		String str=user.getName()+"^"+util.getName();
		String param="param="+str;
		//若数据在内存或硬盘直接取出，负责从服务器获取并存储到内存和硬盘；
		comm.ThreadHttp(cls, method,param, "method", context, 0, null);
		
	}
	private void setListener() {
		this.mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
					hideSoftInputView();//隐藏软键盘
			}
			
		});
	}
	public void onClick(View v){
		switch(v.getId()){
		case R.id.input_sms://输入框
			if(chat_face_container.getVisibility()==View.VISIBLE){
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.image_face://表情
			hideSoftInputView();//隐藏软键盘
			if(chat_face_container.getVisibility()==View.GONE){
				chat_face_container.setVisibility(View.VISIBLE);
			}else{
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.send://发送
			send();
			break;
		case R.id.chat_back:
			messageDB.close();
			Intent intent = new Intent();
			intent.setClass(ChatActivity.this,MainActivity.class);
			startActivity(intent);  
			break;
		case R.id.setting:
			
			Dialog dialog=new AlertDialog.Builder(this).setTitle("亲!你要走了吗?").setIcon(R.drawable.cloud)
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
					messageDB.close();
					//跳转到登录界面
					Intent logout=new Intent(ChatActivity.this,WelcomeActivity.class);
					startActivity(logout);
					finish();
					
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//Toast.makeText(ChatActivity.this, "你点击了取消",0);
					
				}
			}).create();
			dialog.show();
		default:
			break;
		}
		
	}
	


	/**
	 * 发送消息
	 */
	private void send() {
		content=input.getText().toString();
		if(!TextUtils.isEmpty(content)){
			ChatMsgEntity entity=new ChatMsgEntity();
			entity.setFromUser(util.getName());
			entity.setDate(MyDate.getDateEN());
			entity.setMessage(content);
			//entity.setImg(util.getImg());
			entity.setMsgType(false);
			
			//以接受人的名字建表，将聊天内容保存
			messageDB.saveMsg(user.getName(),entity);
			//将聊天内容保存到集合中
			chatArray.add(entity);
		
			
			mLvAdapter.notifyDataSetChanged();
			mListView.setSelection(mLvAdapter.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
			
			//获取发送线程
			//application=(MyApplication)this.getApplicationContext();
			Client client=application.getClient();
			ClientOutputThread out=client.getClientOutputThread();
			
			if(out!=null){
				TranObject<TextMessage> o=new TranObject<TextMessage>(TranObjectType.MESSAGE);
				TextMessage message=new TextMessage();
				message.setMessage(content);
				//发送到服务器的内容有：消息内容，发送者，接受者
				o.setObject(message);
				o.setFromUser(util.getName());
				o.setToUser(user.getName());
				out.setMsg(o);
			}
			List<User> emps=ContactActivity.getEmps();
			for (User user : emps) {
				if(user.getName().equals(util.getName())){//说明是当前用户
					util.setcName(user.getcName());
				}
				
			}
			push();

			for (User user : emps) {
				pushAll(user.getName());
			}	
		}
		
	}
	
	private void pushAll(String username) {

		SharedPreferences account = this.getSharedPreferences(Params.DEFAULT_PRE_NAME,Context.MODE_PRIVATE);
		String serverIp = account.getString(Params.SERVER_IP, "");
		String pushPort = account.getString(Params.PUSH_PORT, "");
		int port;
		try{
			port = Integer.parseInt(pushPort);
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "推送端口格式错误："+pushPort, Toast.LENGTH_SHORT).show();
			return;
		}
		byte[] uuid = null;
		try{
			
			uuid = Util.md5Byte(username);
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
			//targetUserName.requestFocus();
			return;
		}
		byte[] msg = null;
		try{
			String str=util.getcName()+":"+input.getText().toString();
			msg = str.getBytes("UTF-8");
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
			input.requestFocus();
			return;
		}
		Thread t = new Thread(new sendTask(this,serverIp,port,uuid,msg));
		t.start();
		//input.setText("");
	
		
	}


	private void push() {
		SharedPreferences account = this.getSharedPreferences(Params.DEFAULT_PRE_NAME,Context.MODE_PRIVATE);
		String serverIp = account.getString(Params.SERVER_IP, "");
		String pushPort = account.getString(Params.PUSH_PORT, "");
		int port;
		try{
			port = Integer.parseInt(pushPort);
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "推送端口格式错误："+pushPort, Toast.LENGTH_SHORT).show();
			return;
		}
		byte[] uuid = null;
		try{
			uuid = Util.md5Byte(user.getName());
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
			//targetUserName.requestFocus();
			return;
		}
		byte[] msg = null;
		try{
			String str=util.getcName()+":"+input.getText().toString();
			msg = str.getBytes("UTF-8");
			//msg = input.getText().toString().getBytes("UTF-8");
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
			input.requestFocus();
			return;
		}
		Thread t = new Thread(new sendTask(this,serverIp,port,uuid,msg));
		t.start();
		//input.setText("");
	}


	public void getMessage(TranObject msg){
		switch(msg.getType()){
		case MESSAGE:
			TextMessage tm=(TextMessage) msg.getObject();
			String message=tm.getMessage();
			ChatMsgEntity entity=new ChatMsgEntity(user.getName(),
					MyDate.getDateEN(), message, user.getIcon(), true);
			
			if(msg.getFromUser().equals(user.getName())){
				messageDB.saveMsg(user.getName(), entity);
				chatArray.add(entity);
				mLvAdapter.notifyDataSetChanged();
				mListView.setSelection(mLvAdapter.getCount() - 1);
				MediaPlayer.create(this, R.raw.msg).start();
				
			}else if(msg.getFromUser().equals("server")){//对方不在时，需要推送一下
				chatArray.add(entity);
				mLvAdapter.notifyDataSetChanged();
				mListView.setSelection(mLvAdapter.getCount() - 1);
				MediaPlayer.create(this, R.raw.msg).start();
				
			}else{
				messageDB.saveMsg(msg.getFromUser(), entity);// 保存到数据库
				Toast.makeText(ChatActivity.this,"您有来自"+msg.getFromUser()+ ": " + message, 0).show();// 其他好友的消息，就先提示，并保存到数据库
				MediaPlayer.create(this, R.raw.msg).start();
			}
			break;
		case LOGIN:
			User loginUser = (User) msg.getObject();
			Toast.makeText(ChatActivity.this, loginUser.getcName() + "上线了", 0)
					.show();
			MediaPlayer.create(this, R.raw.msg).start();
			break;
		case LOGOUT:
			User logoutUser = (User) msg.getObject();
			Toast.makeText(ChatActivity.this, logoutUser.getcName() + "下线了", 0).show();
			MediaPlayer.create(this, R.raw.msg).start();
			break;
		default:
			break;
		
		}
	}

	private void initStaticFaces() {
		
		try {
			staticFacesList=new ArrayList<String>();
			String[] faces=getAssets().list("face/png");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for(int i=0;i<faces.length;i++){
				staticFacesList.add(faces[i]);
			}
			//去掉删除图片
			staticFacesList.remove("emotion_del_normal.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private View dotsItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout=inflater.inflate(R.layout.dot_image, null);
		ImageView iv=(ImageView) layout.findViewById(R.id.face_dot);
		iv.setId(position);
		return iv;
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
	
	/**
	 * 表情页改变时，dots效果也要跟着改变
	 * */
	class PageChange implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			for(int i=0;i<mDotsLayout.getChildCount();i++){
				mDotsLayout.getChildAt(i).setSelected(false);
			}
			mDotsLayout.getChildAt(arg0).setSelected(true);
			
		}
	}
	class sendTask implements Runnable{
		private Context context;
		private String serverIp;
		private int port;
		private byte[] uuid;
		private byte[] msg;
		
		public sendTask(Context context, String serverIp, int port, byte[] uuid, byte[] msg){
			this.context = context;
			this.serverIp = serverIp;
			this.port = port;
			this.uuid = uuid;
			this.msg = msg;
		}
		
		public void run(){
			Pusher pusher = null;
			Intent startSrv = new Intent(context, OnlineService.class);
			startSrv.putExtra("CMD", "TOAST");
			try{
				boolean result;
				
				
				pusher = new Pusher(serverIp,port, 1000*5);
				result = pusher.push0x20Message(uuid,msg);
				if(result){
					//startSrv.putExtra("TEXT", "自定义信息发送成功");
				}else{
					startSrv.putExtra("TEXT", "发送失败！格式有误");
				}
			}catch(Exception e){
				e.printStackTrace();
				startSrv.putExtra("TEXT", "发送失败！"+e.getMessage());
			}finally{
				if(pusher != null){
					try{pusher.close();}catch(Exception e){};
				}
			}
			context.startService(startSrv);
		}
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
			return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	/**
	 * 初始化表情
	 */
	private void initViewPager() {
		//获取页数
		for(int i=0;i<getPagerCount();i++){
			views.add(viewPaperItem(i));
			LayoutParams params=new LayoutParams(16,16);
			mDotsLayout.addView(dotsItem(i),params);
		}
		FaceVPAdapter mVpAdapter=new FaceVPAdapter(views);
		mViewPager.setAdapter(mVpAdapter);
		mDotsLayout.getChildAt(0).setSelected(true);
		
	}
	
	


	private View viewPaperItem(int position) {
		LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout=inflater.inflate(R.layout.face_gridview, null);//表情布局
		GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
		/**
		 * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
		 * */
		List<String> subList=new ArrayList<String>();
		subList.addAll(staticFacesList.subList(position * (columns * rows - 1), 
				(columns * rows - 1) * (position + 1) > staticFacesList
				.size() ? staticFacesList.size() : (columns
				* rows - 1)
				* (position + 1)));
		//末尾添加删除图标
		subList.add("emotion_del_normal.png");
		FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
		gridview.setAdapter(mGvAdapter);
		gridview.setNumColumns(columns);
		//单击表情执行的操作
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
				String png=((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
				if(!png.contains("emotion_del_normal")){// 如果不是删除图标
					insert(getFace(png));
				}else{
					delete();
				}
			}
		});
		return gridview;
	}

	

	/**
	 * 向输入框添加表情
	 * @param face
	 */
	private void insert(CharSequence text) {
		int iCursorStart=Selection.getSelectionStart(input.getText());
		int iCursorEnd=Selection.getSelectionEnd(input.getText());
		if(iCursorStart!=iCursorEnd){
			((Editable)input.getText()).replace(iCursorStart, iCursorEnd, "");
		}
		int iCursor=Selection.getSelectionEnd((input.getText()));
		((Editable) input.getText()).insert(iCursor, text);
	}
	/**
	 * 删除图标执行事件
	 * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
	 * */
	private void delete() {
		if (input.getText().length() != 0) {
			int iCursorEnd = Selection.getSelectionEnd(input.getText());
			int iCursorStart = Selection.getSelectionStart(input.getText());
			if (iCursorEnd > 0) {
				if (iCursorEnd == iCursorStart) {
					if (isDeletePng(iCursorEnd)) {
						String st = "#[face/png/f_static_000.png]#";
						((Editable) input.getText()).delete(
								iCursorEnd - st.length(), iCursorEnd);
					} else {
						((Editable) input.getText()).delete(iCursorEnd - 1,
								iCursorEnd);
					}
				} else {
					((Editable) input.getText()).delete(iCursorStart,iCursorEnd);
				}
			}
		}
		
	}
	/**
	 * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
	 * **/
	private boolean isDeletePng(int cursor) {
		String st = "#[face/png/f_static_000.png]#";
		String content = input.getText().toString().substring(0, cursor);
		if (content.length() >= st.length()) {
			String checkStr = content.substring(content.length() - st.length(),
					content.length());
			String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(checkStr);
			return m.matches();
		}
		return false;
	}


	protected SpannableStringBuilder getFace(String png) {
		SpannableStringBuilder ss=new SpannableStringBuilder();
		String tempText="#[" + png + "]#";
		ss.append(tempText);
		try {
			ss.setSpan(new ImageSpan(ChatActivity.this, BitmapFactory
					.decodeStream(getAssets().open(png))),
					ss.length()-tempText.length(),ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}


	/**
	 * 根据表情数量以及GridView设置的行数和列数计算Pager数量
	 * @return
	 */
	private int getPagerCount() {
		int count=staticFacesList.size();
		return count%(columns*rows-1)==0?count/(columns * rows - 1)
				:count/(columns*rows-1)+1;
	}

}

