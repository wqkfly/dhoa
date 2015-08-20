package com.dhcc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dhcc.activity.R;
import com.dhcc.entity.Notification;

public class TimelineAdapter extends BaseAdapter {

	private Context context;
	private List<Notification> notes;
	private LayoutInflater inflater;

	public TimelineAdapter(Context context, List<Notification> list) {
		super();
		this.context = context;
		this.notes = list;
	}

	@Override
	public int getCount() {

		return notes.size();
	}

	@Override
	public Object getItem(int position) {
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.listview_item, null);
			

			viewHolder.content = (TextView) convertView.findViewById(R.id.content);
			viewHolder.time = (TextView) convertView.findViewById(R.id.show_time);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Notification l=notes.get(position);
		viewHolder.content.setText(l.getSender()+":"+l.getContent());
		viewHolder.time.setText(l.getTime());

		return convertView;
	}

	static class ViewHolder {
		public TextView year;
		public TextView month;
		public TextView content;
		public TextView time;
	}
}
