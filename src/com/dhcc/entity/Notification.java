package com.dhcc.entity;

import java.io.Serializable;

public class Notification implements Serializable{

	private int id;
	private String sender;
	private int acc_id;
	private String content;
	private String time;
	private String title;
	private String pic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int accId) {
		acc_id = accId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	

}
