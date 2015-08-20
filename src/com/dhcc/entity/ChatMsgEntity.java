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
	private String date;// 消息日期
	private String message;// 消息内容
	private String img;
	private boolean isComMeg = true;// 是否为收到的消息
	private boolean isRead;
	public ChatMsgEntity() {

	}

	public ChatMsgEntity(String fromUser, String date, String text, String img,
			boolean isComMsg) {
		super();
		this.fromUser = fromUser;
		this.date = date;
		this.message = text;
		this.img = img;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	
}
