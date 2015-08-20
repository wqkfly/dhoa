package com.dhcc.entity;

import java.io.Serializable;

/**
 * 一个聊天消息的JavaBean
 * 
 * @author way
 * 
 */
public class ChatMsgEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromUser;// 消息来自
	private String toUser;
	private String date;// 消息日期
	private String message;// 消息内容
	//private String img;
	private boolean isComMeg = true;// 是否为收到的消息
	private boolean isRead;
	private int type;//1,普通文本消息，2，图片3，语音，4，视频
	public ChatMsgEntity() {

	}

	public ChatMsgEntity(String fromUser,String toUser, String date, String text, 
			boolean isComMsg) {
		super();
		this.fromUser = fromUser;
		this.date = date;
		this.message = text;
		this.toUser=toUser;
		this.isComMeg = isComMsg;
	}


	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	
}
