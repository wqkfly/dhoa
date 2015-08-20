package com.dhcc.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.dhcc.activity.R;



import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;


public class GetBitmap {
	  static Bitmap pic = null;
	public Bitmap getRes(Context context,String name)
	{
		ApplicationInfo appInfo = context.getApplicationInfo();
		int resID = context.getResources().getIdentifier(name, "drawable", appInfo.packageName);
		return BitmapFactory.decodeResource(context.getResources(), resID);
	}
	public Bitmap GetId(Context context,int id){
		InputStream is = context.getResources().openRawResource(id);
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		//options.inSampleSize = 5; //width，hight设为原来的十分一
		Bitmap btp =BitmapFactory.decodeStream(is,null,options);
		return btp;
	}

}
