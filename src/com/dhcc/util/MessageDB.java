package com.dhcc.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dhcc.entity.ChatMsgEntity;

import com.dhcc.entity.MesList;

public class MessageDB {
	private SQLiteDatabase db;

	public MessageDB(Context context) {
		db = context.openOrCreateDatabase(Constants.DBNAME,Context.MODE_PRIVATE, null);
	}

	public void saveMsg(String name, ChatMsgEntity entity) {
		db.execSQL("CREATE table IF NOT EXISTS "
				+ name
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT)");
		int isCome = 0;
		if (entity.getMsgType()) {//如果是收到的消息，保存在数据库的值为1
			isCome = 1;
		}
		db.execSQL(
				"insert into " + name
						+ " (name,img,date,isCome,message) values(?,?,?,?,?)",
						new Object[] { entity.getFromUser(), entity.getImg(),
						entity.getDate(), isCome, entity.getMessage() });
	}
	public void saveNesList(String name, List<MesList> meslist) {
		db.execSQL("CREATE table IF NOT EXISTS "
				+ name+"_list"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT, counts INTEGER,date TEXT,isRead INTEGER,lastContent TEXT)");
	/*	int isCome = 0;
		if (entity.getMsgType()) {//如果是收到的消息，保存在数据库的值为1
			isCome = 1;
		}*/
		for(MesList mes :meslist){
	
			db.execSQL(
					"insert into " + name+"_list"+ "(fromUser,counts,date,isRead,lastContent) values(?,?,?,?,?)",
					new Object[] { mes.getFromUser(),mes.getCounts(),mes.getTime(), 0, mes.getLastCotent() });
		}
		
	}
	public List<MesList> getMesList(String name){
		List<MesList> list=new ArrayList<MesList>();
		db.execSQL("CREATE table IF NOT EXISTS "
				+ name+"_list"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,fromUser TEXT,counts INTEGER, date TEXT,isRead INTEGER,lastContent TEXT)");
		Cursor c = db.rawQuery("SELECT * from " + name+"_list"+ " ORDER BY _id DESC LIMIT 5", null);
		while (c.moveToNext()) {
			String fromUser = c.getString(c.getColumnIndex("fromUser"));
			int counts = c.getInt(c.getColumnIndex("counts"));
			String date = c.getString(c.getColumnIndex("date"));
			int isRead = c.getInt(c.getColumnIndex("isRead"));
			String lastContent = c.getString(c.getColumnIndex("lastContent"));
			MesList entity=new MesList(fromUser,counts,date,isRead,lastContent);
			
			List<ChatMsgEntity> chats= getMsg(name);
			
			entity.setContents(chats);
			//ChatMsgEntity entity = new ChatMsgEntity(name, date, message, img,isComMsg);
			list.add(entity);
		}
		c.close();
		return list;
		
	};
	public List<ChatMsgEntity> getMsg(String username) {
		List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
		db.execSQL("CREATE table IF NOT EXISTS "
				+ username
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT)");
		Cursor c = db.rawQuery("SELECT * from " + username + " ORDER BY date DESC LIMIT 5", null);
		while (c.moveToNext()) {
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
}
