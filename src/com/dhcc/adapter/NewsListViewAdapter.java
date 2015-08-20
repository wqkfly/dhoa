package com.dhcc.adapter;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dhcc.activity.R;
import com.dhcc.entity.LoginUser;

import com.dhcc.entity.MesList;
import com.dhcc.util.DoSharePre;
import com.dhcc.util.GetBitmap;
import com.dhcc.util.ImageDownLoader;
import com.dhcc.util.ImageDownLoader.onImageLoaderListener;





public class NewsListViewAdapter extends BaseAdapter  {
	private Context context;
	private  List<MesList> Mess;

	public int HeadHeight;
	public boolean right_boolean=true;
	private ImageDownLoader mImageDownLoader;
	private ListView listView;
	GetBitmap getBitmap=new GetBitmap();
	public NewsListViewAdapter(Context context,
			List<MesList> listItem,ImageDownLoader imageDownLoader,ListView listView){
		super();
		this.context=context;
		this.Mess=listItem;
		mImageDownLoader=imageDownLoader;
		this.listView=listView;
	}
	public void setEmps( List<MesList> p){
		
		this.Mess=p;
	}
	@Override
	public int getCount() {
		return Mess.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Mess.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void remove(int position) {
		Mess.remove(position);
		 //System.out.println(emps.size());
		this.notifyDataSetChanged();
	}
	public void add(MesList p) {
		Mess.add(p);
		
		 //System.out.println(emps.size());
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder=null;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=View.inflate(context, R.layout.listview_news, null);
			viewholder.context=convertView.findViewById(R.id.news_item_layout_context);
			viewholder.head=convertView.findViewById(R.id.news_item_head);
			viewholder.icon=(ImageView) convertView.findViewById(R.id.news_item_icon);
			viewholder.num=(TextView) convertView.findViewById(R.id.news_item_num);
			viewholder.name=(TextView) convertView.findViewById(R.id.news_item_name);
			viewholder.time=(TextView) convertView.findViewById(R.id.news_item_time);
			viewholder.mes=(TextView) convertView.findViewById(R.id.news_item_mes);
			convertView.setTag(viewholder); 
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}

		LayoutParams Prams_h=viewholder.head.getLayoutParams();
		LayoutParams Prams_i=viewholder.icon.getLayoutParams();
		LayoutParams Prams_c=viewholder.context.getLayoutParams();
		MarginLayoutParams Prams_n=(MarginLayoutParams) viewholder.num.getLayoutParams();
		Prams_h.height=HeadHeight*200/220;
		Prams_h.width=HeadHeight*200/220;
		Prams_i.height=HeadHeight*160/220;
		Prams_i.width=HeadHeight*160/220;
		Prams_c.height=HeadHeight*200/220;
		Prams_n.width=HeadHeight*50/220;
		Prams_n.height=HeadHeight*50/220;
		Prams_n.topMargin=HeadHeight*30/220;
		Prams_n.leftMargin=HeadHeight*150/220;
		viewholder.head.setLayoutParams(Prams_h);
		viewholder.icon.setLayoutParams(Prams_i);
		viewholder.context.setLayoutParams(Prams_c);
		viewholder.num.setLayoutParams(Prams_n);
	
		
		
		
		final MesList mesList=Mess.get(position);
		viewholder.name.setText(mesList.getCfromUser());
		
		if(mesList.getCounts()==0){
			viewholder.num.setVisibility(View.GONE);
		}else{
			viewholder.num.setText(String.valueOf(mesList.getCounts()));
		}
		
		
		viewholder.time.setText(mesList.getTime());
		viewholder.mes.setText(mesList.getLastCotent());
		
		//解决contentView被重用的时候，图片预设问题
		//试图解决demo内存变量或者整个app被demo管理kill掉的问题
		if(((LoginUser.webUrl==null)||(LoginUser.webUrl.equals("")))){
			DoSharePre.crashCall(PreferenceManager.getDefaultSharedPreferences(context));
		}
		Bitmap no=getBitmap.GetId(context, R.drawable.ic_launcher);
		viewholder.icon.setImageBitmap(no);
		final String mImageUrl=LoginUser.webUrl+"Image/"+mesList.getFromUser() +".jpg";		
		viewholder.icon.setTag(mImageUrl);//设置tag用于在网络获取图片时获取图片位置加载图片
		mImageDownLoader.downloadImage(mImageUrl,viewholder.icon,new onImageLoaderListener() {
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if(bitmap != null){
					ImageView imageViewByTag = (ImageView) listView.findViewWithTag(url);
                    if (imageViewByTag != null) {
                    	imageViewByTag.setImageBitmap(bitmap);
                    	imageViewByTag.setTag("");
                    }
                
				}
				
			}
		});
	
		
		return convertView;
	}
	
	class ViewHolder{
		View context;
		View head;
		ImageView icon;
		TextView num;
		TextView name;
		TextView time;
		TextView mes;
		
	}

	
}

