package com.dhcc.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.cookie.Cookie;

import com.dhcc.entity.LoginUser;
import com.dhcc.util.DoSharePre;


//import com.example.helloworld.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class HttpGetPostCls {
	private static URL url = null;
	private InputStream inputStream = null;

	/**
	 *  网络请求响应码
	 *   <br>
	 */
	private int responseCode = 1;
	/**
	 *   408为网络超时
	 */
	public static final int REQUEST_TIMEOUT_CODE = 408;

	/**
	 * 请求字符编码
	 */
	private static final String CHARSET = "UTF-8";
	/**
	 * 请求服务器超时时间
	 */
	private static final int REQUEST_TIME_OUT = 12000; 
	/**
	 * 读取响应的数据时间
	 */
	private static final int READ_TIME_OUT = 25000;
	
	public static String sCookie;
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		return false;
	}

public static String LinkData(String cls,String mth,String Param,String Typ,Context frm)throws Exception 
{
try
   {/*
   	String enUft = URLEncoder.encode("广东省福利彩票开奖信息网", "UTF-8");

	System.out.println(enUft); 

	java.net.URLDecoder urlDecoder = new java.net.URLDecoder(); 

	String s = urlDecoder.decode(enUft,"GB2312"); 

   */
	
	//试图解决demo内存变量或者整个护士站被demo管理kill掉的问题
//	if(((LoginUser.name==null)||(LoginUser.name.equals("")))
//			&&(frm.getClass().getName().equals("com.dhc.demo.MainActivity"))){
//		DoSharePre.crashCall(PreferenceManager.getDefaultSharedPreferences(frm));
//	}
   
   String[] arr=Param.split("\\&");
   String enparam="";
   for (int i=0;i<arr.length;i++)
   {
	   if (arr[i].equals("")) continue;
	   String[] itm=arr[i].split("\\=");
	   if (itm.length>1)
	   {
	   arr[i]=itm[0]+"="+URLEncoder.encode(itm[1],"UTF-8");
	   }else{
		   arr[i]=itm[0]+"=";  
	   }
	   enparam="&"+arr[i]+enparam;
   }
 //  String WebUrl="http://10.1.41.11:8080/android_test/";
  // LoginUser.webUrl="http://10.1.20.61:8080/dhoa_server/";
   String linkcsp="StringService";
   if (LoginUser.webUrl.equals("")) LoginUser.webUrl="http://10.1.20.63:8080/dhoa_server/";
   // String enparam=URLEncoder.encode(Param,"UTF-8");
	
	String url=LoginUser.webUrl+LoginUser.linkscp+"?className="+cls+"&methodName="+mth+"&type="+Typ+enparam;
    String ret=GetData(url,frm);
	return ret;
    }catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(frm.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		return "error3";
	}
}
public static String GetData(String urlStr,Context frm)throws Exception {
	try
	{
		if (mobilecom.isConnect(frm) == false) {
			 Toast.makeText(frm, "连接错误", 1000).show();
			 return "error";
			}

		StringBuffer sb = new StringBuffer();
		String line = null;
		//BufferedReader有一个readLine（）方法，可以每次读取一行数据
		BufferedReader buffer = null;
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        
		int num=0;
		while(true)
		{
			 
		try{
			//创建一个URL对象
			//创建一个Http连接
		    
		    
			num=num+1;
			//设置连接超时时间
			urlConn.setConnectTimeout(REQUEST_TIME_OUT);
			urlConn.setReadTimeout(READ_TIME_OUT);  
	
			//urlConn.setDoOutput(true); 
			//urlConn.setUseCaches(false); 
            
	         if(sCookie!=null && sCookie.length()>0){   
	        	 urlConn.setRequestProperty("Cookie", sCookie);            
	        	 }
			urlConn.setRequestProperty("Charset", CHARSET); 
			urlConn.connect();
			//urlConn.setRequestMethod("POST");
			//urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//urlConn.setRequestProperty("Charset", "utf-8");
	
			//String aa=urlConn.getRequestProperties("Charset");
			//String reqheads=urlConn.getRequestProperties().toString();
			int code=urlConn.getResponseCode();
			if (code!= 404)
			{
				//使用IO流读取数据，InputStreamReader将读进来的字节流转化成字符流
				//但是字符流还不是很方便，所以再在外面套一层BufferedReader，
				//用BufferedReader的readLine（）方法，一行一行读取数据
				InputStream is = urlConn.getInputStream(); 
				InputStreamReader reads=new InputStreamReader(is);
				buffer = new BufferedReader(reads);
				while((line = buffer.readLine()) != null){
					sb.append(line);
				}
		      //is.close();
			  String cookie = urlConn.getHeaderField("set-cookie");   
			  if(cookie!=null && cookie.length()>0){   
				  sCookie = cookie;  
			  }
			    urlConn.disconnect(); 
			}
		}catch (Exception e) {
			urlConn.disconnect();
			sCookie=null;
			e.printStackTrace();
			continue;
		}finally{
			try{
				buffer.close();
				//sCookie=null;
				urlConn.disconnect();
			}catch (Exception e) {
				if (num>1) return "error1";
					e.printStackTrace();
					sCookie=null;
					urlConn.disconnect();
					//Toast.makeText(frm.getApplicationContext(), "连接错误", Toast.LENGTH_LONG).show();
				continue;
			}
		}
		return sb.toString();
		}
	}catch(Exception e)
	{
		e.printStackTrace();
        return "error2";
	}
	}
public InputStream getInputStreamFromURL(String urlStr){
	HttpURLConnection urlConn = null;
	try {
		url = new URL(urlStr);
		urlConn = (HttpURLConnection) url.openConnection();
		inputStream = urlConn.getInputStream();
    } catch (MalformedURLException e) {  
    	e.printStackTrace();  
    } catch (IOException e) {  
    	e.printStackTrace();  
    }
	
	return inputStream;
}
}
