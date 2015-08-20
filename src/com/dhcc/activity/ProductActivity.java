package com.dhcc.activity;

import com.dhcc.entity.LoginUser;
import com.dhcc.util.ImageDownLoader;
import com.dhcc.util.ImageDownLoader.onImageLoaderListener;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Doraemon on 2015/7/21.
 */
public class ProductActivity extends Fragment {
	private ImageView image;
	private TextView text;
	private String imageSrc="Image/wangqiankun.png";
	SharedPreferences perferences;
	private ImageDownLoader mImageDownLoader;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  contentView=  inflater.inflate(R.layout.activity_product,container,false);


        return contentView;
    }	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		image=(ImageView) getView().findViewById(R.id.p_image);
		text=(TextView) getView().findViewById(R.id.p_edit);
	//	String url=LoginUser.webUrl+imageSrc;
	//	text.setText(url);
		String url = "http://www.nowamagic.net/librarys/images/random/rand_11.jpg";
	//	Bitmap bitmap = getHttpBitmap(url);
        //显示
		mImageDownLoader= new ImageDownLoader(this.getActivity());//关键
		showImage(url, image);
        
		}
		
	
	private void showImage(String mImageUrl, final ImageView view){
		Bitmap bitmap = null;
		mImageDownLoader.downloadImage(mImageUrl,view, new onImageLoaderListener() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if(view!= null && bitmap != null){
					view.setImageBitmap(bitmap);
				}
				
			}
		});
		
	
	}
	@Override
	public void onDestroy() {
		mImageDownLoader.cancelTask();
		super.onDestroyView();
	}
	  @Override
		public void onDestroyView() {
			super.onDestroy();
		}

	  
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			fileUtils.deleteFile();
			Toast.makeText(getApplication(), "清空缓存成功", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}*/

}
