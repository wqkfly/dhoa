package com.dhcc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dhcc.activity.LoginActivity;
import com.dhcc.entity.ChatMsgEntity;
import com.dhcc.entity.MesList;

public class MessageDB {
	private SQLiteDatabase db;
	private List<MesList> mesList=LoginActivity.getUnReadMessages();
	private SharePreferenceUtil util_count;
	private SharePreferenceUtil util;
	private Context context;
	public MessageDB(Context context) {
		util_count=new SharePreferenceUtil(context, Constants.COUNTS);
		util= new SharePreferenceUtil(context, Constants.SAVE_USER);
		db = context.openOrCreateDatabase(Constants.DBNAME,Context.MODE_PRIVATE, null);
	}
	
	public void saveUnReadMsg(String fromUser,String toUser,  ChatMsgEntity entity) {//数据表以 from_to样式命名
		db.execSQL("CREATE table IF NOT EXISTS "
				+ fromUser+"_"+toUser
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT,toUser TEXT, date TEXT,isCome TEXT,message TEXT)");
		int isCome = 0;
		if (entity.getMsgType()) {//如果是收到的消息，保存在数据库的值为1
			isCome = 1;
		}
		//未读消息存一次
		
		db.execSQL("insert into " + fromUser+"_"+toUser+ " (fromUser,toUser,date,isCome,message) values(?,?,?,?,?)",
						new Object[] { fromUser,toUser,entity.getDate(), isCome, entity.getMessage()});
		
		
	}
	public void saveMsg(String toUser,String fromUser,  ChatMsgEntity entity) {//数据表以 from_to样式命名
		db.execSQL("CREATE table IF NOT EXISTS "
				+ toUser+"_"+fromUser
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT,toUser TEXT, date TEXT,isCome TEXT,message TEXT)");
		int isCome = 0;
		if (entity.getMsgType()) {//如果是收到的消息，保存在数据库的值为1
			isCome = 1;
		}
		db.execSQL("insert into " + toUser+"_"+fromUser+ " (fromUser,toUser,date,isCome,message) values(?,?,?,?,?)",
						new Object[] { fromUser,toUser,entity.getDate(), isCome, entity.getMessage()});	
		
	}
	/*public void saveNesList(String name, List<MesList> meslist) {
		db.execSQL("CREATE table IF NOT EXISTS "
				+ name+"_list"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT, counts INTEGER,date TEXT,isRead INTEGER,lastContent TEXT)");
		int isCome = 0;
		if (entity.getMsgType()) {//如果是收到的消息，保存在数据库的值为1
			isCome = 1;
		}
		for(MesList mes :meslist){
	
			db.execSQL(
					"insert into " + name+"_list"+ "(fromUser,counts,date,isRead,lastContent) values(?,?,?,?,?)",
					new Object[] { mes.getFromUser(),mes.getCounts(),mes.getTime(), 0, mes.getLastCotent() });
		}
		
	}*/
	public List<MesList> getMesList(String name){
		/*List<MesList> list=new ArrayList<MesList>();
		db.execSQL("CREATE table IF NOT EXISTS "
				+ name+"_list"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT,counts INTEGER, date TEXT,isRead INTEGER,lastContent TEXT)");
		Cursor c = db.rawQuery("SELECT * from " + name+"_list"+ " ORDER BY _id DESC LIMIT 10", null);
		while (c.moveToNext()) {
			String fromUser = c.getString(c.getColumnIndex("fromUser"));
			int counts = c.getInt(c.getColumnIndex("counts"));
			String date = c.getString(c.getColumnIndex("date"));
			int isRead = c.getInt(c.getColumnIndex("isRead"));
			String lastContent = c.getString(c.getColumnIndex("lastContent"));
			MesList mesList=new MesList(fromUser,counts,date,isRead,lastContent);
			
			List<ChatMsgEntity> chats= getMsg(name);
			
			mesList.setContents(chats);
			//ChatMsgEntity entity = new ChatMsgEntity(name, date, message, img,isComMsg);
			list.add(mesList);
		}
		c.close();*/
		List<MesList> list=new ArrayList<MesList>();
		 List<String> tables=getTableNames();
		 for (String table : tables) {
			if(table.endsWith("_"+name)){
				
				List<ChatMsgEntity>chats=getMsg(table);
				
				String fromUser="";
				if(chats.size()>1){
					fromUser=chats.get(chats.size()-1).getFromUser();
					if(fromUser.equals(util.getName())){
						fromUser=chats.get(chats.size()-1).getToUser();
					}
					
				}
				
				String lastContent=chats.get(0).getMessage();
				String date=chats.get(chats.size()-1).getDate();
				
				MesList mesList=new MesList(fromUser,date,0,lastContent);
				Map<String,Integer> unReadNums=new HashMap<String,Integer>();
				//好友_自己
				unReadNums.put(table, util_count.getNum(table));
				mesList.setUnReadNums(unReadNums);
				mesList.setContents(chats);
				list.add(mesList);
			}
		}
		return list;
		
	};
	public List<ChatMsgEntity> getMsg(String username) {
		List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
		db.execSQL("CREATE table IF NOT EXISTS "
				+ username
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT,toUser TEXT, date TEXT,isCome TEXT,message TEXT)");
		//fromUser,toUser,date,isCome,message
		Cursor c = db.rawQuery("SELECT * from " + username + " ORDER BY date desc LIMIT 10", null);
		while (c.moveToNext()) {
			String fromUser = c.getString(c.getColumnIndex("fromUser"));
			String toUser = c.getString(c.getColumnIndex("toUser"));
			String date = c.getString(c.getColumnIndex("date"));
			int isCome = c.getInt(c.getColumnIndex("isCome"));
			String message = c.getString(c.getColumnIndex("message"));
			
			boolean isComMsg = false;
			if (isCome == 1) {  
				isComMsg = true;
			}
			
			ChatMsgEntity entity = new ChatMsgEntity(fromUser, toUser, date, message,isComMsg);
		
			list.add(entity);
		}
		c.close();
		
		return list;
	}
	public ChatMsgEntity getLastMsg(String username) {
		//List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
		/*db.execSQL("CREATE table IF NOT EXISTS "
				+ username
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT)");*/
		Cursor c = db.rawQuery("SELECT * from " + username + " ORDER BY _id DESC LIMIT 1", null);
		if(c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("name"));
			String img = c.getString(c.getColumnIndex("img"));
			String date = c.getString(c.getColumnIndex("date"));
			int isCome = c.getInt(c.getColumnIndex("isCome"));
			String message = c.getString(c.getColumnIndex("message"));
			boolean isComMsg = false;
			if (isCome == 1) {
				isComMsg = true;
			}
			ChatMsgEntity entity = new ChatMsgEntity(name, date, message, img,isComMsg);
			//list.add(entity);
			c.close();
			
			return entity;
		}
		
		return null;
	}
	public void close() {
		if (db != null)
			db.close();
	}

	public void upDateMesList(String name,String fromUser){
		db.execSQL("update "+name+"_list set counts = 0 where fromUser = ?", new String[]{fromUser});
	};

	public List<String> getTableNames() {
		List<String> tableNames=new ArrayList<String>();
		Cursor c = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
		//select name from sqlite_master where type='table' order by name;
		while(c.moveToNext()) {
			//String name = c.getString(c.getColumnIndex("name"));
			String name = c.getString(0);  
			tableNames.add(name);
		
			//list.add(entity);
			
		}
		c.close();
		
		return tableNames;
	}

	public void updateMsg(String fromUser, String toUser) {
		db.execSQL("update "+fromUser+"_"+toUser+" set num = 0 ", null);
		
	}

}
