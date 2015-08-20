package com.dhcc.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.dhcc.adapter.NewsListViewAdapter;
import com.dhcc.entity.ChatMsgEntity;
import com.dhcc.entity.MesList;
import com.dhcc.entity.User;
import com.dhcc.util.Constants;
import com.dhcc.util.ImageDownLoader;
import com.dhcc.util.MessageDB;
import com.dhcc.util.MyDate;
import com.dhcc.util.SharePreferenceUtil;

/**
 * Created by Doraemon on 2015/7/21.
 */
public class NewsActivity extends Fragment {
	private ListView  listview;
	private JSONArray jsonArray;
	private NewsListViewAdapter newsAdapter;
	private ImageDownLoader mImageDownLoader;
	private List<MesList> mesList;
	private SharePreferenceUtil util;
	private int height;
	private int a_width;
	private int a_height;
	private Context context;
	private MessageDB messageDB;
	
	@Override
	public void onAttach(Activity activity){
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mesList=LoginActivity.getUnReadMessages();
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  contentView=  inflater.inflate(R.layout.activity_news,container,false);
        
        
        return contentView;
    }
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context=this.getActivity();
		messageDB=new MessageDB(context);
		mImageDownLoader=new ImageDownLoader(context);
		listview=(ListView) getView().findViewById(R.id.news_listview);
		util = new SharePreferenceUtil(this.getActivity(), Constants.SAVE_USER);
		getScreen();
		initview();
		setListener();
	}
	private void setListener(){
		//点击消息列表进入聊天窗口
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent startChat=new Intent();
				startChat.setClass(context, ChatActivity.class);
				User user=new User();
				user.setName(mesList.get(position).getFromUser());
				startChat.putExtra("person",user);
			    startChat.putExtra("mesList",mesList.get(position));
			    // mesList.get(position).setCounts(0);
			    messageDB.upDateMesList(util.getName(),mesList.get(position).getFromUser());
				context.startActivity(startChat);
				
				mesList=null;
				Toast.makeText(context, "点击事件", 0).show();
				
			}
		});	
	}
	private void initview() {
		// TODO Auto-generated method stub
		//emps=new ArrayList<Mes>();
		
		if(mesList==null){
			getMesListFromSqlite();
			
		}else{
			messageDB.saveNesList(util.getName() ,mesList);
			
			for(int i=0;i<mesList.size();i++){
				List<ChatMsgEntity> contents=mesList.get(i).getContents();
				//ChatMsgEntity entity=new ChatMsgEntity();
				for (ChatMsgEntity entity : contents) {
					if(!entity.isRead()){
					//未读消息查看后，存入本地数据库
						messageDB.saveMsg(util.getName(), entity);
					}
				}
				
			}
	
			mesList=null;
			getMesListFromSqlite();
		}
			
		
			
			//List<User> emps=ContactActivity.getEmps();
		/*	for (String username: fromUsers) {
				ChatMsgEntity entity=messageDB.getLastMsg(username);
				if(entity!=null){
					MesList mes=new MesList();
					mes.setCfromUser(entity.getName());
					mes.setLastCotent(entity.getMessage());
					mes.setTime(entity.getDate());
					mes.setFromUser(entity.getName());
					mes.setCounts(0);
					mesList.add(mes);
				}
			}
			*/
			/*MesList meslis=new MesList();
		
			if(!mesList.contains(meslis.getFromUser())){
				List<Mes> mess=new ArrayList<Mes>();
				Mes mes=new Mes();
				meslis.setFromUser("dhcc");
				meslis.setCfromUser("东华办公平台");
				meslis.setLastCotent("东华办公平台欢迎你。很高兴你开启了手机办公，期待能为你和小伙伴们带来全新的办公体验。");
				mes.setContent("东华办公平台欢迎你。很高兴你开启了手机办公，期待能为你和小伙伴们带来全新的办公体验。");
				mess.add(mes);
				meslis.setContents(mess);
				//meslis.setFromUser("cloud");
				meslis.setTime(MyDate.getDate());
				meslis.setCounts(1);
				mesList.add(meslis);
			}*/
			
			newsAdapter=new NewsListViewAdapter(this.getActivity(),mesList,mImageDownLoader,listview);
			newsAdapter.HeadHeight=height;
			listview.setAdapter(newsAdapter);
			
		
	}
	private void getMesListFromSqlite() {
		mesList=new ArrayList<MesList>();
		mesList =messageDB.getMesList(util.getName());
		//如果有历史聊天记录，则取出
		/*List<String> fromUsers=messageDB.getTableNames();
		
		for(String username: fromUsers){
			System.out.println(username);
			if(username.endsWith("_list")){
				
			}
		
		}*/
		
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
}
