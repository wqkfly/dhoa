package com.dhcc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

public class FileUtils {
	/**
	 * sd卡的根目录
	 */
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
	/**
	 * 手机的缓存根目录
	 */
	private static String mDataRootPath = null;
	/**
	 * 保存Image的目录名
	 */
	private final static String FOLDER_NAME = "/head";
	/**
	 * 保存CONTACT_NAME的目录名
	 */
	private final static String CONTACT_NAME="/data";
	/**
	 * 保存项目的目录名
	 */
	private final static String DHCC_NAME="/dhcc";
	private Context context;
	public FileUtils(Context context){
		mDataRootPath = context.getCacheDir().getPath();
		this.context=context;
	}
	

	/**
	 * 获取储存目录
	 * @return
	 */
	private String getDirectory(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
				mSdRootPath +DHCC_NAME : mDataRootPath + DHCC_NAME;
	}
	
	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * @param fileName 
	 * @param bitmap   
	 * @throws IOException
	 */
	public void savaBitmap(String fileName, Bitmap bitmap) throws IOException{
		if(bitmap == null){
			return;
		}
		String path = getDirectory()+FOLDER_NAME;
		File folderFile = new File(path);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		if(!fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase().contains("PNG")){
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
		}else{
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
		}
		fos.flush();
		fos.close();
	}
	
	/**
	 * 从手机或者sd卡获取Bitmap
	 * @param fileName
	 * @return
	 */
	public Bitmap getBitmap(String fileName){
		return BitmapFactory.decodeFile(getDirectory()+FOLDER_NAME + File.separator + fileName);
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isFileExists(String fileName){
		return new File(getDirectory()+FOLDER_NAME + File.separator + fileName).exists();
	}
	
	/**
	 * 获取文件的大小
	 * @param fileName
	 * @return
	 */
	public long getFileSize(String fileName) {
		return new File(getDirectory()+FOLDER_NAME + File.separator + fileName).length();
	}
	
	
	/**
	 * 删除SD卡或者手机的缓存图片data和目录
	 */
	public void deleteImageFile() {
		File dirFile = new File(getDirectory()+FOLDER_NAME);
		if(! dirFile.exists()){
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		
		dirFile.delete();
	}
	
	// 文本文件的处理
	 /** 
    * 将文本内容写入文件 
    *  
    * @param file 
    * @param str 
    * @throws IOException 
    */  
   public  void writeTextFile(String filename,String str) throws IOException {  
	   	if(str == null){
			return;
		}
   		DataOutputStream out = null; 
   		String path = getDirectory()+CONTACT_NAME;
		File folderFile = new File(path);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + filename);
	
       try {  
       	
           out = new DataOutputStream(new FileOutputStream(file));  
           out.write(str.getBytes());  
       } finally {  
           if (out != null) {  
               out.close();  
           }  
       }  
   }
   /** 
    * 读取文件 
    *  
    * @param file 
    * @return 
    * @throws IOException 
    */  
   public  String readTextFile(String filename) throws IOException {  
       String text = null;  
       InputStream is = null;  
       try {  
           is = new FileInputStream(getDirectory()+CONTACT_NAME + File.separator + filename);  
           text = readTextInputStream(is);  
       } finally {  
           if (is != null) {  
               is.close();  
           }  
       }  
       return text;  
   } 

   /** 
    * 从流中读取文件 
    *  
    * @param is 
    * @return 
    * @throws IOException 
    */  
   public  String readTextInputStream(InputStream is) throws IOException {  
       StringBuffer strbuffer = new StringBuffer();  
       String line;  
       BufferedReader reader = null;  
       try {  
           reader = new BufferedReader(new InputStreamReader(is));  
           while ((line = reader.readLine()) != null) {  
               strbuffer.append(line).append("\r\n");  
           }  
       } finally {  
           if (reader != null) {  
               reader.close();  
           }  
       }  
       return strbuffer.toString();  
   }  
   
   /**
	 * 判断文本文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isConcatFileExists(String fileName){
		return new File(getDirectory()+CONTACT_NAME + File.separator + fileName).exists();
	}
	
	/**
	 * 获取文本文件的大小
	 * @param fileName
	 * @return
	 */
	public long getConcatFileSize(String fileName) {
		return new File(getDirectory()+CONTACT_NAME + File.separator + fileName).length();
	}
	
	/**
	 * 删除SD卡或者手机的缓存data和目录
	 */
	public void deleteDataFile() {
		File dirFile = new File(getDirectory()+CONTACT_NAME);
		if(! dirFile.exists()){
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		
		dirFile.delete();
	}
}
