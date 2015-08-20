package com.dhcc.util;


import java.io.IOException;
import android.content.Context;
import android.support.v4.util.LruCache;

public class TextDownLoader {
	/**
	 * 缓存text的类，当存储text的大小大于LruCache设定的值，系统自动释放内存
	 */
	private LruCache<String, String> mMemoryCache;
	/**
	 * 操作文件相关类对象的引用
	 */
	private FileUtils fileUtils;
	/**
	 * 下载text的线程池
	 */

	
	public TextDownLoader(Context context){
		//获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
        int mCacheSize = maxMemory / 8;
        //给LruCache分配1/8 4M
		mMemoryCache = new LruCache<String, String>(mCacheSize){

			//必须重写此方法，来测量text的大小
			@Override
			protected int sizeOf(String key, String value) {
				return value.length();
			}
			
		};
		fileUtils = new FileUtils(context);
	}
	
	
	
	
	/**
	 * 添加text到内存缓存
	 * @param key
	 * @param bitmap
	 */
	public void addTextToMemoryCache(String key, String value) {  
	    if (getTextFromMemCache(key) == null && value != null) {  
	        mMemoryCache.put(key, value);  
	    }  
	}  
	 
	/**
	 * 从内存缓存中获取一个String
	 * @param key
	 * @return
	 */
	public String getTextFromMemCache(String key) {  
	    return mMemoryCache.get(key);  
	} 
	
	/**
	 * 先从内存缓存中获取text,如果没有就从SD卡或者手机缓存中获取，SD卡或者手机缓存
	 * 没有就去下载
	 * @param param 
	 * @param method 
	 * @param url
	 * @param listener
	 * @return
	 */
	public String downloadText(final String cls,final String method,final String param){
		//替换Url中非字母和非数字的字符，这里比较重要，因为我们用Url作为文件名，比如我们的Url
		//是Http://xiaanming/abc.jpg;用这个作为图片名称，系统会认为xiaanming为一个目录，
		//我们没有创建此目录保存文件就会保存
		String name=cls+method+param;
		String subUrl = name.replaceAll("[^\\w]", "");
		String text= showCacheText(subUrl);
		if(text != null){
			return text;
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public String showCacheText(String url){
		if(getTextFromMemCache(url) != null){
			return getTextFromMemCache(url);
		}else if(fileUtils.isConcatFileExists(url) && fileUtils.getConcatFileSize(url) != 0){
			//从SD卡获取手机里面获取Bitmap
			String bitmap=null;
			try {
				bitmap = fileUtils.readTextFile(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//将Bitmap 加入内存缓存
			addTextToMemoryCache(url, bitmap);
			return bitmap;
		}
		
		return null;
	}
	

}


//fileUtils.writeTextFile(subUrl, bitmap);
//addTextToMemoryCache(subUrl, bitmap);
