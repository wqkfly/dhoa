package com.dhcc.util;

public class Constants {
	public static final String SERVER_IP = "10.1.20.70";// 服务器ip
	public static final int SERVER_PORT = 5469;// socket服务器端口
	public static final int SERVER_TPORT = 8080;// tomcat服务器端口
	public static final String SERVER_NAME="dhoa_server";
	public static final String SERVER_DOMAIN="com.dhcc.com.cn/net";
	public static final String SERVER_INIP="10.1.20.70";
	public static final int SERVER_INTPORT=8080;
	public static final String SERVER_OUTIP="222.222.222.222";
	public static final int SERVER_OUTTPORT=8082;
	public static final int SERVER_SELECT = 1;
	
	public static final int REGISTER_FAIL = 0;//注册失败
	public static final String ACTION = "com.dhcc.message";//消息广播action
	public static final String MSGKEY = "message";//消息的key
	public static final String IP_PORT = "ipPort";//保存ip、port的xml文件名
	public static final String SAVE_USER = "saveUser";//保存用户信息的xml文件名
	public static final String BACKKEY_ACTION="com.dhcc.backKey";//返回键发送广播的action
	public static final int NOTIFY_ID = 0x911;//通知ID
	public static final String DBNAME = "qq.db";//数据库名称
}
