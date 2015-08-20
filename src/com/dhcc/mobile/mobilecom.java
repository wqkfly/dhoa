package com.dhcc.mobile;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class mobilecom {

	public String RetData="";
	public String Code="";
	public static String ACTION_CONTENT_NOTIFY = "android.intent.action.CONTENT_NOTIFY";
	public static  String ACTION_CONTENT_NOTIFY_EMII = "com.ge.action.barscan";
	public static  String ACTION_CONTENT_NOTIFY_MOTO = "ACTION_CONTENT_NOTIFY_MOTO";
	public static  String ACTION_CONTENT_NOTIFY_SCAN = "com.android.server.scannerservice.broadcast";
	public static Map<String, String> actionMap = new HashMap<String, String>();
    public Map<String, String> RetDataPag = null;
 	public mobilecom()
	{
		super();
		actionMap.put(ACTION_CONTENT_NOTIFY, "CONTENT");
		actionMap.put(ACTION_CONTENT_NOTIFY_EMII, "value");
		actionMap.put(ACTION_CONTENT_NOTIFY_MOTO, "com.motorolasolutions.emdk.datawedge.data_string");
		actionMap.put(ACTION_CONTENT_NOTIFY_SCAN,"scannerdata");
	}
 public void searchcontrol(ViewGroup vv,Map OBJBTEMP)
	{
		for (int i = 0; i < vv.getChildCount(); i++) {

			if ( vv.getChildAt(i) instanceof TextView){
				TextView btn = (TextView)vv.getChildAt(i);
				String title=btn.getText().toString();
				if (title.indexOf("Title")!=-1)
				{
				if (OBJBTEMP.get(title)==null)
					{
					 if (title.equals("Title0")) 
					 {
						 continue;
					 }else{

					  btn.setText("");
					  btn.setLayoutParams(getlayparam(Integer.valueOf(2),btn.getLayoutParams().height));

					  continue;
					 }
					}
				String[] itm=(String[])OBJBTEMP.get(title);
				btn.setText(itm[0]);
				//LayoutParams lp=(LayoutParams)btn.getLayoutParams();
				
				//btn.setWidth(Integer.valueOf(itm[1]));
				btn.setLayoutParams(getlayparam(Integer.valueOf(itm[1]),btn.getLayoutParams().height));
				//getScreenWidth() - DimensionUtility.dip2px(this, 20))
				//btn.setWidth((getScreenWidth() - DimensionUtility.dip2px(this, 20))/3);
				}
				//btn.setWidth(180);
				//btn.setHeight(120);
				//String tag=(String)btn.getTag();
			}
			if (vv.getChildAt(i) instanceof LinearLayout) {
				searchcontrol((ViewGroup)vv.getChildAt(i),OBJBTEMP);
			}
			if (vv.getChildAt(i) instanceof RelativeLayout) {
				searchcontrol((ViewGroup)vv.getChildAt(i),OBJBTEMP);
			}
			if (vv.getChildAt(i) instanceof AbsoluteLayout) {
				searchcontrol((ViewGroup)vv.getChildAt(i),OBJBTEMP);
			}
		}

	}
	private LinearLayout.LayoutParams getlayparam(int w,int h)
	{
		LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(w,
				h);
		return lp0;

	}

   /*public static void ShowDialog(String Title,Context context)
    {
        // TODO Auto-generated method stub
        LayoutInflater factory = LayoutInflater.from(context);                
        final View view = factory.inflate(R.layout.dialog, null);// 获得自定义对话框
        
        AlertDialog.Builder dialog07 = new AlertDialog.Builder(context);
          dialog07.setInverseBackgroundForced(true);
        //dialog07.setIcon(R.drawable.qq);
        dialog07.setTitle(Title);
        dialog07.setView(view);
        dialog07.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

    
            }
        });
        /*      dialog07.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                //Toast.makeText(dialog_demo.this, "你选择了取消", Toast.LENGTH_LONG).show();
                showDialog("你选择了取消");
            }
        });
  
        dialog07.create().show();
        return;
    }*/
   public static String ChangeDate(String itmdate)
   {
	   
	   String[] itm=itmdate.split("\\/");
	   if (itm.length<2) return itmdate;
	   return itm[2]+"-"+itm[1]+"-"+itm[0];
   }
    public static String GetDate(String typ)
    {
   		int mHour;

   		int mMinute;

   		int mYear;

   		int mMonth;

   		int mDay;
   		final Calendar c = Calendar.getInstance();

   		mYear = c.get(Calendar.YEAR);
   		// 获取当前年份
   		mMonth = c.get(Calendar.MONTH)+1;// 获取当前月份
   		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
   		mHour = c.get(Calendar.HOUR_OF_DAY);// 获取当前的小时数
   		mMinute = c.get(Calendar.MINUTE);// 获取当前的分钟数
   		String Currdate = mDay + "/" + mMonth + "/" + mYear;
   		String CurrTime = mHour + ":" + mMinute;
   		if (typ.equals("d"))
   			return Currdate;
   		if (typ.equals("t"))
   			return CurrTime;
   		return Currdate + "," + CurrTime;

   	}public static String GetDiffDate(String typ,int days)
 {
		int mHour;

		int mMinute;

		int mYear;

		int mMonth;

		int mDay;
		
	    GregorianCalendar c = new GregorianCalendar();
        c.add(GregorianCalendar.DATE, days);

	//	final Calendar c = Calendar.getInstance();
  		mYear = c.get(Calendar.YEAR);
		// 获取当前年份
		mMonth = c.get(Calendar.MONTH)+1;// 获取当前月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
		mHour = c.get(Calendar.HOUR_OF_DAY);// 获取当前的小时数
		mMinute = c.get(Calendar.MINUTE);// 获取当前的分钟数
		String Currdate = mDay + "/" + mMonth + "/" + mYear;
		String CurrTime = mHour + ":" + mMinute;
		if (typ.equals("d"))
			return Currdate;
		if (typ.equals("t"))
			return CurrTime;
		return Currdate + "," + CurrTime;

	}
//map类型参数
	public void ThreadServHttp(final String serviceUrl,
			final String MethodName, final Map Param, final int whatmsg,
			final Handler handler, final Activity context) {
		if (isConnect(context) == false) {
			 Toast.makeText(context, "检查网路！", 1000).show();
			 return;
			}

//

		Thread thread = new Thread() {

			public void run() {
				try {
					this.sleep(10);
					RetData = "";
					RetData = WsApiUtil.loadSoapObject(serviceUrl, MethodName,
							Param);
					if (Param.containsKey("Code")) {
						Code = Param.get("Code").toString();
						RetDataPag.put(Code, RetData);

					}
					Message msg = new Message();
					msg.what = whatmsg;
					handler.sendMessage(msg);
				} catch (Exception e) {
					RetData="error";
				}

			}
		};
		thread.start();

	}
//线程获取数据库数据
public void ThreadHttp(final String Cls, final String mth,
			final String Param, final String Typ, final Context context,
			final int whatmsg, final Handler handler) {
		if (mobilecom.isConnect(context) == false) {
			 Toast.makeText(context, "检查网路！", 1000).show();
			 return ;
			}

		Thread thread = new Thread() {

			public void run() {
				try {
					this.sleep(10);
					RetData = "";
					RetData = HttpGetPostCls.LinkData(Cls, mth, Param, Typ,
							context);
					Message msg = new Message();
					msg.what = whatmsg;
					handler.sendMessage(msg);
				} catch (Exception e) {

				}

			}
		};
		thread.start();

	}
//带有缓存数据的线程
	public void ThreadHttp(final String Cls, final String mth,
			final String Param, final String Typ, final Context context,
			final int whatmsg, final Handler handler,final String Code) {
		if (mobilecom.isConnect(context) == false) {
			 Toast.makeText(context, "检查网路！", 1000).show();
			 return ;
			}

		Thread thread = new Thread() {

			public void run() {
				try {
					//this.sleep(10);
					RetData = "";
					RetData = HttpGetPostCls.LinkData(Cls, mth, Param, Typ,
							context);
					if (!Code.equals("")) {
					
						RetDataPag.put(Code, RetData);

					}
					Message msg = new Message();
					msg.what = whatmsg;
					handler.sendMessage(msg);
				} catch (Exception e) {

				}

			}
		};
		thread.start();

	}
	
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
	/*public static void ToastShow(String Title,Activity context)
	{
		LayoutInflater inflater = LayoutInflater.from(context); //context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom,
				(ViewGroup)context.findViewById(R.id.llToast));
		ImageView image = (ImageView) layout
				.findViewById(R.id.tvImageToast);
		image.setImageResource(R.drawable.h001);
		TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
		title.setText("提示");
		TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
		text.setText(Title);
		Toast toast = new Toast(context.getApplicationContext());
		toast.setGravity(Gravity.CENTER, 12, 40);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}*/
} 
