package com.dhcc.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhcc.activity.R;

import com.dhcc.entity.ChatMsgEntity;
import com.dhcc.util.AnimatedGifDrawable;
import com.dhcc.util.AnimatedImageSpan;

@SuppressLint("NewApi")
public class ChatLVAdapter extends BaseAdapter{
	private Context mContext;
	private List<ChatMsgEntity> list;
	private LayoutInflater inflater;
	
	public ChatLVAdapter(Context context, List<ChatMsgEntity> infos) {
		super();
		this.mContext = context;
		this.list = infos;
		inflater = LayoutInflater.from(mContext);
	}
	public void setList(List<ChatMsgEntity> list) {
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);  
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = inflater.from(mContext).inflate(R.layout.listview_chat, null);
			hodler.fromContainer = (ViewGroup) convertView.findViewById(R.id.chart_from_container);
			hodler.toContainer = (ViewGroup) convertView.findViewById(R.id.chart_to_container);
			
			hodler.fromContent = (TextView) convertView.findViewById(R.id.chatfrom_content);
			hodler.toContent = (TextView) convertView.findViewById(R.id.chatto_content);
			
			hodler.fromIcon=(ImageView) convertView.findViewById(R.id.chatfrom_icon);
			hodler.toIcon=(ImageView) convertView.findViewById(R.id.chatto_icon);
			hodler.time = (TextView) convertView.findViewById(R.id.chat_time);
			convertView.setTag(hodler);
		}else {
			hodler = (ViewHodler) convertView.getTag();
		}
		ChatMsgEntity entity = list.get(position);
		
		if (entity.getMsgType()) {
			// 收到消息  from显示
			hodler.toContainer.setVisibility(View.GONE);
			hodler.fromContainer.setVisibility(View.VISIBLE);

			// 对内容做处理
			SpannableStringBuilder ss = handler(hodler.fromContent,entity.getMessage());
			hodler.fromContent.setText(ss);
			hodler.time.setText(entity.getDate());
			try {
				String name=entity.getFromUser();
				Bitmap mBitmap = BitmapFactory.decodeStream(mContext.getAssets().open("head/" + name+".jpg"));
				hodler.fromIcon.setImageBitmap(mBitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// 发送消息 to显示
			hodler.toContainer.setVisibility(View.VISIBLE);
			hodler.fromContainer.setVisibility(View.GONE);

			// 对内容做处理
			SpannableStringBuilder ss = handler(hodler.toContent,entity.getMessage());
			hodler.toContent.setText(ss);
			hodler.time.setText(entity.getDate());
			
			try {
				String name=entity.getFromUser();
				Bitmap mBitmap = BitmapFactory.decodeStream(mContext.getAssets().open("head/" + name+".jpg"));
				hodler.toIcon.setImageBitmap(mBitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return convertView;
	}
	private SpannableStringBuilder handler(final TextView gifTextView,String content){
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring("#[face/png/f_static_".length(), tempText.length()- ".png]#".length());
				String gif = "face/gif/f" + num + ".gif";
				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif
				 * 否则说明gif找不到，则显示png
				 * */
				InputStream is = mContext.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("#[".length(),tempText.length() - "]#".length());
				try {
					sb.setSpan(new ImageSpan(mContext, BitmapFactory.decodeStream(mContext.getAssets().open(png))), m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	};
	class ViewHodler {
		ImageView fromIcon, toIcon;
		TextView fromContent, toContent, time;
		ViewGroup fromContainer, toContainer;
	}
	
}
