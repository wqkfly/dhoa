package com.dhcc.entity;

import java.io.Serializable;
import java.util.List;

//{'fromUser':'wangqiankun','cfromUser':'王乾坤','toUser':'lulin','content':'啦啦啦','time':'上午12:20','counts':'5','isRead':'1'}

public class MesList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromUser;
	private String cfromUser;
	private String toUser;
	private String lastCotent;
	private String time;
	private int counts;
	private List<ChatMsgEntity> contents;//所有未读信息
	private int isRead;
	
	
	public MesList(String fromUser2, int counts2, String date, int isRead2,
			String lastContent) {
		this.fromUser=fromUser2;
		this.counts=counts2;
		this.time=date;
		this.isRead=isRead2;
		this.lastCotent=lastContent;
	}
	public String getFromUser() {
		
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getCfromUser() {
		return cfromUser;
	}
	public void setCfromUser(String cfromUser) {
		this.cfromUser = cfromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getLastCotent() {
		return lastCotent;
	}
	public void setLastCotent(String lastCotent) {
		this.lastCotent = lastCotent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public List<ChatMsgEntity> getContents() {
		return contents;
	}
	public void setContents(List<ChatMsgEntity> contents) {
		this.contents = contents;
	}
	
	
	
	
	
}
