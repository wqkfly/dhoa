package com.dhcc.client;

import com.dhcc.entity.TranObject;



/**
 * 消息监听接口
 * 
 * @author way
 * 
 */
public interface MessageListener {
	public void Message(TranObject msg);
}
