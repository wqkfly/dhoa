package com.dhcc.adapter;
import java.util.List;



import com.dhcc.activity.ChatActivity;
import com.dhcc.activity.R;
import com.dhcc.entity.User;
import com.dhcc.entity.LoginUser;
import com.dhcc.util.DoSharePre;
import com.dhcc.util.GetBitmap;
import com.dhcc.util.ImageDownLoader;
import com.dhcc.util.ImageDownLoader.onImageLoaderListener;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;





public class ContactListViewAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private  List<User> emps;
	public int selectIndex=-1; 
	public int HeadHeight;
	public int buttonHeight;
	public boolean right_boolean=true;
	private ImageDownLoader mImageDownLoader;
	private ListView listView;
	GetBitmap getBitmap=new GetBitmap();
	public ContactListViewAdapter(Context context,
			List<User> listItem,ImageDownLoader imageDownLoader,ListView listView){
		super();
		this.context=context;
		this.emps=listItem;
		mImageDownLoader=imageDownLoader;
		this.listView=listView;
	}
	public void setEmps( List<User> p){
		
		this.emps=p;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return emps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return emps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void remove(int position) {
		emps.remove(position);
		 //System.out.println(emps.size());
		this.notifyDataSetChanged();
	}
	public void add(User p) {
		emps.add(p);
		
		 //System.out.println(emps.size());
		this.notifyDataSetChanged();
	}
	public void update(User p){
		for(int i=0;i<emps.size();i++ ){
            if(emps.get(i).getId()==p.getId()){
                emps.remove(i);
                emps.add(i,p);
            }
		}
		this.notifyDataSetChanged();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder=null;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=View.inflate(context, R.layout.listview_person, null);
			viewholder.head=convertView.findViewById(R.id.person_item_head);
			viewholder.context=convertView.findViewById(R.id.person_item_layout_context);
			viewholder.handler=convertView.findViewById(R.id.person_item_layout_handler);
			viewholder.name=(TextView) convertView.findViewById(R.id.item_name);
			viewholder.phone=(TextView) convertView.findViewById(R.id.item_phone);
			viewholder.group=(TextView) convertView.findViewById(R.id.item_group);		    
			viewholder.call=(ImageView) convertView.findViewById(R.id.person_call);
			viewholder.message=(ImageView) convertView.findViewById(R.id.person_message);
		    viewholder.person_name=(TextView) convertView.findViewById(R.id.person_name);
		    viewholder.more=(ImageView) convertView.findViewById(R.id.person_more);
		    viewholder.icon=(ImageView) convertView.findViewById(R.id.person_item_icon);
		    viewholder.right_layout= convertView.findViewById(R.id.person_item_right_layout);
			viewholder.right_image=(ImageView) convertView.findViewById(R.id.person_item_right);
		    convertView.setTag(viewholder); 
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		//设置组件高。宽
//		int w = View.MeasureSpec.makeMeasureSpec(0,
//		        View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0,
//		        View.MeasureSpec.UNSPECIFIED);
//		viewholder.call.measure(w, h); 
//		buttonHeight=viewholder.call.getMeasuredWidth()/2;
		LayoutParams Prams_h=viewholder.head.getLayoutParams();
		LayoutParams Prams_i=viewholder.icon.getLayoutParams();
		Prams_h.height=HeadHeight*175/220;
		Prams_h.width=HeadHeight*175/220;
		Prams_i.height=HeadHeight*140/220;
		Prams_i.width=HeadHeight*140/220;
		viewholder.head.setLayoutParams(Prams_h);
		viewholder.icon.setLayoutParams(Prams_i);
		
		LayoutParams Prams1=viewholder.call.getLayoutParams();
		LayoutParams Prams2=viewholder.message.getLayoutParams();
		LayoutParams Prams3=viewholder.person_name.getLayoutParams();
		LayoutParams Prams4=viewholder.more.getLayoutParams();
		Prams1.height=buttonHeight*90/220;
		Prams2.height=buttonHeight*90/220;
		Prams3.height=buttonHeight*90/220;
		Prams4.height=buttonHeight*90/220;
		Prams1.width=buttonHeight*90/220;
		Prams2.width=buttonHeight*90/220;
		Prams3.width=buttonHeight*180/220;
		Prams4.width=buttonHeight*90/220;
		viewholder.call.setLayoutParams(Prams1);
		viewholder.message.setLayoutParams(Prams2);
		viewholder.person_name.setLayoutParams(Prams3);
		viewholder.person_name.setTextSize(17);
		viewholder.more.setLayoutParams(Prams4);
		
		
		LayoutParams Prams_r=viewholder.right_layout.getLayoutParams();
		Prams_r.height=HeadHeight*175/220;
		Prams_r.width=HeadHeight*175/220;
		viewholder.right_layout.setLayoutParams(Prams_r);
		if(!right_boolean)
		{
			viewholder.right_layout.setVisibility(View.GONE);
		}else{
			viewholder.right_layout.setVisibility(View.VISIBLE);
		}
		
		final User p=emps.get(position);
		//设置动画效果
		final Animation animation = AnimationUtils.loadAnimation(context, R.anim.personview);
		if(selectIndex!=-1){
		if(selectIndex==position)
		{
			if(viewholder.context.getVisibility()!=View.GONE)
			{
			viewholder.context.setVisibility(View.GONE);
			viewholder.handler.setVisibility(View.VISIBLE);
			viewholder.right_image.setBackgroundResource(R.drawable.c_bgright_a);
			viewholder.head.setBackgroundResource(R.drawable.c_headmusk_a);
			viewholder.handler.startAnimation(animation);
			}else{
				viewholder.head.setBackgroundResource(R.drawable.c_headmusk_n);
				viewholder.context.setVisibility(View.VISIBLE);
				viewholder.handler.setVisibility(View.GONE);
				viewholder.right_image.setBackgroundResource(R.drawable.c_bgright_n);
			}
			
		}else{
			viewholder.context.setVisibility(View.VISIBLE);
			viewholder.handler.setVisibility(View.GONE);
			viewholder.head.setBackgroundResource(R.drawable.c_headmusk_n);
			viewholder.right_image.setBackgroundResource(R.drawable.c_bgright_n);
		//	viewholder.context.startAnimation(animation);
			}}else{
				viewholder.context.setVisibility(View.VISIBLE);
				viewholder.handler.setVisibility(View.GONE);
				viewholder.head.setBackgroundResource(R.drawable.c_headmusk_n);
				viewholder.right_image.setBackgroundResource(R.drawable.c_bgright_n);
			}
		//对列表设值
		viewholder.person_name.setText(p.getcName());
		viewholder.name.setText(p.getcName());
		viewholder.phone.setText(p.getPhone());
		viewholder.group.setText(p.getGroup());
		
		if(p.getIcon()==null)
		{
			p.setIcon(String.valueOf(position));
			
		}
		
		//解决contentView被重用的时候，图片预设问题
		if(((LoginUser.webUrl==null)||(LoginUser.webUrl.equals("")))){
			DoSharePre.crashCall(PreferenceManager.getDefaultSharedPreferences(context));
		}
		Bitmap no=getBitmap.GetId(context, R.drawable.ic_launcher);
		viewholder.icon.setImageBitmap(no);
		final String mImageUrl=LoginUser.webUrl+"Image/"+p.getIcon()+".jpg";		
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
		//对按钮做适配器

		viewholder.call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 生成呼叫意图
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ p.getPhone()));
				// 开始呼叫
				context.startActivity(intent);
			}
		});
		//进入聊天窗口
				viewholder.message.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intentDetail = new Intent();
						intentDetail.setClass(context,ChatActivity.class);
					
						intentDetail.putExtra("person",p);
						context.startActivity(intentDetail);
						
					}
				});
		viewholder.more.setOnClickListener(this);
		return convertView;
	}
	
	class ViewHolder{
		View context;
		View handler;
		View head;
		TextView name;
		TextView phone;
		TextView group;
		ImageView call;
		ImageView message;
		TextView person_name;
		ImageView more;
		ImageView icon;
		View right_layout;
		ImageView right_image;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.person_message:
			
			break;
		case R.id.person_more:
			
			break;

		default:
			break;
		}
	}
}

