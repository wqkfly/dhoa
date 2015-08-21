package com.dhcc.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
		editor = sp.edit();
	}

	// 用户的密码
	public void setPasswd(String passwd) {
		editor.putString("passwd", passwd);
		editor.commit();
	}

	public String getPasswd() {
		return sp.getString("passwd", "");
	}
	// 是否保存密码
		public void setIsSave(boolean save) {
			editor.putBoolean("isSave", save);
			editor.commit();
		}

		public boolean getIsSave() {
			return sp.getBoolean("isSave", false);
		}

	// 用户的id，即QQ号
	public void setId(String id) {
		editor.putString("id", id);
		editor.commit();
	}

	public String getId() {
		return sp.getString("id", "");
	}

	// 用户的昵称
	public String getName() {
		return sp.getString("name", "");
	}

	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	// 用户的邮箱
	public String getEmail() {
		return sp.getString("email", "");
	}

	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}

	// 用户自己的头像
	public String getImg() {
		return sp.getString("img", "");
	}

	public void setImg(String name) {
		editor.putString("img", name);
		editor.commit();
	}

	// ip
	public void setIp(String ip) {
		editor.putString("ip", ip);
		editor.commit();
	}

	public String getIp() {
		return sp.getString("ip", Constants.SERVER_IP);
	}

	// socket端口
	public void setPort(int port) {
		editor.putInt("port", port);
		editor.commit();
	}

	public int getPort() {
		return sp.getInt("port", Constants.SERVER_PORT);
	}
	// tomcat端口
		public void setTPort(int port) {
			editor.putInt("tport", port);
			editor.commit();
		}

		public int getTPort() {
			return sp.getInt("tport", Constants.SERVER_TPORT);
		}
	// 服务链接
		public void setServer(String server) {
			editor.putString("server", server);
			editor.commit();
		}

		public String getServer() {
			return sp.getString("server", Constants.SERVER_NAME);
		}
		// 服务链接
		public void setDomain(String domain) {
			editor.putString("domain", domain);
			editor.commit();
		}

		public String getDomain() {
			return sp.getString("domain",Constants.SERVER_DOMAIN);
		}
		// in ip
		public void setInIp(String ip) {
			editor.putString("inip", ip);
			editor.commit();
		}

		public String getInIp() {
			return sp.getString("inip", Constants.SERVER_INIP);
		}
		//out ip
		public void setOutIp(String ip) {
			editor.putString("outip", ip);
			editor.commit();
		}

		public String getOutIp() {
			return sp.getString("outip", Constants.SERVER_OUTIP);
		}
		//tomcat in port
		public void setTInPort(int port) {
			editor.putInt("tinport", port);
			editor.commit();
		}

		public int getTInPort() {
			return sp.getInt("tinport", Constants.SERVER_INTPORT);
		}
		//tomcat out port
		public void setTOutPort(int port) {
			editor.putInt("toutport", port);
			editor.commit();
		}

		public int getTOutPort() {
			return sp.getInt("toutport", Constants.SERVER_OUTTPORT);
		}
		//select
		
		public void setSelect(int select) {
			editor.putInt("select", select);
			editor.commit();
		}
		public int getSelect() {
			return sp.getInt("select", Constants.SERVER_SELECT);
		}
	// 是否在后台运行标记
	public void setIsStart(boolean isStart) {
		editor.putBoolean("isStart", isStart);
		editor.commit();
	}

	public boolean getIsStart() {
		return sp.getBoolean("isStart", false);
	}

	// 是否第一次运行本应用
	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getisFirst() {
		return sp.getBoolean("isFirst", true);
	}
	public void setcName(String cName) {
		editor.putString("cname", cName);
		editor.commit();
		
	}
	public String getcName() {
		return sp.getString("cname", "");
	}
	public void setNum(String key,int num){
		editor.putInt(key, num);
		editor.commit();
	}
	public int getNum(String key){
		return sp.getInt(key, 0);
	}
}
