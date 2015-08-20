package com.dhcc.adapter;

import java.util.List;

import com.dhcc.activity.R;




import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;





public class ContactListViewButtonAdapter extends BaseAdapter{
	private Context context;
	private List<String> listitem;
	public int selectIndex=-1;
	public ContactListViewButtonAdapter(Context context,List<String> listItem){
		super();
		this.context=context;
		this.listitem=listItem;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return listitem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listitem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder=null;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=View.inflate(context, R.layout.listview_contact_button, null);
			viewholder.name=(TextView) convertView.findViewById(R.id.listview_contact_button_name);
			convertView.setTag(viewholder); 
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		viewholder.name.setText(listitem.get(position));
		MarginLayoutParams params=(MarginLayoutParams) viewholder.name.getLayoutParams();

		if(selectIndex!=-1){
		if(selectIndex==position)
		{
			params.leftMargin=0;
			
		}else{
			params.leftMargin=20;
		}
		
		}else{
			params.leftMargin=20;
			
		}
		viewholder.name.setLayoutParams(params);
		return convertView;
	}
	
	class ViewHolder{
		TextView name;

	}



}

