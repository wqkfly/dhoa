package com.dhcc.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.dhcc.adapter.TimelineAdapter;
import com.dhcc.entity.Notification;
import com.dhcc.mobile.mobilecom;

public class HomeActivity extends Fragment implements OnClickListener{


	private mobilecom comm = new mobilecom();
	private ListView listView;
	private JSONArray jsonArray=null;
	private List<Notification> letters=null;
	List<String> data ;
	private TimelineAdapter timelineAdapter;
	
	private static final int VIEW_DETAIL = 0;// 查看详情
	private static final int VIEW_DELETE = 1;// 删除操作
	private Context context;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View  contentView=  inflater.inflate(R.layout.activity_home,container,false);

	        
	        return contentView;
	    }
	    @Override
		public void onActivityCreated(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
		//	context=this.getActivity();
		//	initData();
	//		setListener();
			
		}


	private void initData() {
		
		listView = (ListView) this.getActivity().findViewById(R.id.listview);
		String param="&param={'id':'3'}";
		//comm.ThreadHttp("com.android.container.LetterAction", "getLetters",param, "method", context, 0, handler);
		
		letters=new ArrayList<Notification>();
		String result="[{'id':'1','acc_id':'2','sender':'lala','content':'hehehehhe','pic':null,'time':'11:00','title':'hehe'},{'id':'2','acc_id':'2','sender':'lala','content':'hehehehhe','pic':null,'time':'11:00','title':'hehe'}]";
		try {
			jsonArray=new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				
				Notification l=new Notification();
				int id=Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
				int accId=Integer.parseInt(jsonArray.getJSONObject(i).getString("acc_id"));
				String sender=jsonArray.getJSONObject(i).getString("sender");
				l.setId(id);
				l.setAcc_id(accId);
				l.setSender(sender);
				l.setContent(jsonArray.getJSONObject(i).getString("content"));
				l.setPic(jsonArray.getJSONObject(i).getString("pic"));
				l.setTime(jsonArray.getJSONObject(i).getString("time"));
				l.setTitle(jsonArray.getJSONObject(i).getString("title"));
				//System.out.println(jsonArray.getJSONObject(i).getString("content"));
				letters.add(l);
			}
			timelineAdapter = new TimelineAdapter(context,letters);
			listView.setAdapter(timelineAdapter);} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		listView.setDividerHeight(0);
		System.out.println("打印一行测试");
		//timelineAdapter = new TimelineAdapter(this, getData());
		//listView.setAdapter(timelineAdapter);
		
	}
	Handler handler = new Handler() {
		public void handleMessage(Message paramMessage) {
			if (paramMessage.what == 0) {
				if (comm.RetData.indexOf("error") != -1) {
					Toast.makeText(context, "检查网络!", 1000).show();
					return;
				}
				if (!comm.RetData.equals("")) {
					letters=new ArrayList<Notification>();
					String result=comm.RetData;
					try {
						jsonArray=new JSONArray(result);
						for(int i=0;i<jsonArray.length();i++){
							
							Notification l=new Notification();
							int id=Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
							int accId=Integer.parseInt(jsonArray.getJSONObject(i).getString("acc_id"));
							String sender=jsonArray.getJSONObject(i).getString("sender");
							l.setId(id);
							l.setAcc_id(accId);
							l.setSender(sender);
							l.setContent(jsonArray.getJSONObject(i).getString("content"));
							l.setPic(jsonArray.getJSONObject(i).getString("pic"));
							l.setTime(jsonArray.getJSONObject(i).getString("time"));
							l.setTitle(jsonArray.getJSONObject(i).getString("title"));
							//System.out.println(jsonArray.getJSONObject(i).getString("content"));
							letters.add(l);
						}
						timelineAdapter = new TimelineAdapter(context,letters);
						listView.setAdapter(timelineAdapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	};
	private void setListener() {
		this.listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intentDetail = new Intent();
				intentDetail.setClass(context,ChatActivity.class);
			
				//intentDetail.putExtra("person",emps.get(position));
				startActivity(intentDetail);
				
			}

			});

		this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> parent,View v, final int position, long id) {
			
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("请选择以下操作").setItems(new String[] { "查看详情", "删除操作"},new OnClickListener() {
											
				public void onClick(DialogInterface dialog,int which) {
												
					switch (which) {
						case VIEW_DETAIL:
							//showDetail(position);
							Intent intentDetail = new Intent();
							intentDetail.setClass(context,ChatActivity.class);
						
							//intentDetail.putExtra("person",emps.get(position));
							startActivity(intentDetail);
					
						break;
						case VIEW_DELETE:
										
							//deleteItem(position);
							//empAdapter.remove(position);
						break;
						
					}
				}

			

				private void showDetail(int position) {
												// TODO Auto-generated method
												// stub
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
						/*General general = generals.get(position);
						builder.setTitle(general.getName())
								.setMessage(general.getDetail())
								.setPositiveButton("返回", null);
					AlertDialog dialog = builder.create();
								dialog.show();*/
				}



			});
						AlertDialog dialog = builder.create();
						dialog.show();
						return true;
					}
				});

	
	}

	

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "这是第1行测试数据");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "这是第2行测试数据");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "这是第3行测试数据");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "这是第4行测试数据");
		list.add(map);
		return list;
	}

//
//	private void initData() {
//		
//		listView = (ListView) this.getActivity().findViewById(R.id.listview);
//		String param="&param={'id':'3'}";
//		//comm.ThreadHttp("com.android.container.LetterAction", "getLetters",param, "method", context, 0, handler);
//		
//		letters=new ArrayList<Letter>();
//		String result="[{'id':'1','acc_id':'2','sender':'lala','content':'hehehehhe','pic':null,'time':'11:00','title':'hehe'},{'id':'2','acc_id':'2','sender':'lala','content':'hehehehhe','pic':null,'time':'11:00','title':'hehe'}]";
//		try {
//			jsonArray=new JSONArray(result);
//			for(int i=0;i<jsonArray.length();i++){
//				
//				Letter l=new Letter();
//				int id=Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
//				int accId=Integer.parseInt(jsonArray.getJSONObject(i).getString("acc_id"));
//				String sender=jsonArray.getJSONObject(i).getString("sender");
//				l.setId(id);
//				l.setAcc_id(accId);
//				l.setSender(sender);
//				l.setContent(jsonArray.getJSONObject(i).getString("content"));
//				l.setPic(jsonArray.getJSONObject(i).getString("pic"));
//				l.setTime(jsonArray.getJSONObject(i).getString("time"));
//				l.setTitle(jsonArray.getJSONObject(i).getString("title"));
//				//System.out.println(jsonArray.getJSONObject(i).getString("content"));
//				letters.add(l);
//			}
//			timelineAdapter = new TimelineAdapter(context,letters);
//			listView.setAdapter(timelineAdapter);} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		listView.setDividerHeight(0);
//		System.out.println("打印一行测试");
//		//timelineAdapter = new TimelineAdapter(this, getData());
//		//listView.setAdapter(timelineAdapter);
//		
//	}
//	Handler handler = new Handler() {
//		public void handleMessage(Message paramMessage) {
//			if (paramMessage.what == 0) {
//				if (comm.RetData.indexOf("error") != -1) {
//					Toast.makeText(context, "检查网络!", 1000).show();
//					return;
//				}
//				if (!comm.RetData.equals("")) {
//					letters=new ArrayList<Letter>();
//					String result=comm.RetData;
//					try {
//						jsonArray=new JSONArray(result);
//						for(int i=0;i<jsonArray.length();i++){
//							
//							Letter l=new Letter();
//							int id=Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
//							int accId=Integer.parseInt(jsonArray.getJSONObject(i).getString("acc_id"));
//							String sender=jsonArray.getJSONObject(i).getString("sender");
//							l.setId(id);
//							l.setAcc_id(accId);
//							l.setSender(sender);
//							l.setContent(jsonArray.getJSONObject(i).getString("content"));
//							l.setPic(jsonArray.getJSONObject(i).getString("pic"));
//							l.setTime(jsonArray.getJSONObject(i).getString("time"));
//							l.setTitle(jsonArray.getJSONObject(i).getString("title"));
//							//System.out.println(jsonArray.getJSONObject(i).getString("content"));
//							letters.add(l);
//						}
//						timelineAdapter = new TimelineAdapter(context,letters);
//						listView.setAdapter(timelineAdapter);
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//			}
//		}
//	};
//	private void setListener() {
//		this.listView.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				
//				Intent intentDetail = new Intent();
//				intentDetail.setClass(context,ChatActivity.class);
//			
//				//intentDetail.putExtra("person",emps.get(position));
//				startActivity(intentDetail);
//				
//			}
//
//			});
//
//		this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//		public boolean onItemLongClick(AdapterView<?> parent,View v, final int position, long id) {
//			
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setTitle("请选择以下操作").setItems(new String[] { "查看详情", "删除操作"},new OnClickListener() {
//											
//				public void onClick(DialogInterface dialog,int which) {
//												
//					switch (which) {
//						case VIEW_DETAIL:
//							//showDetail(position);
//							Intent intentDetail = new Intent();
//							intentDetail.setClass(context,ChatActivity.class);
//						
//							//intentDetail.putExtra("person",emps.get(position));
//							startActivity(intentDetail);
//					
//						break;
//						case VIEW_DELETE:
//										
//							//deleteItem(position);
//							//empAdapter.remove(position);
//						break;
//						
//					}
//				}
//
//			
//
//				private void showDetail(int position) {
//												// TODO Auto-generated method
//												// stub
//					AlertDialog.Builder builder = new AlertDialog.Builder(context);
//						/*General general = generals.get(position);
//						builder.setTitle(general.getName())
//								.setMessage(general.getDetail())
//								.setPositiveButton("返回", null);
//					AlertDialog dialog = builder.create();
//								dialog.show();*/
//				}
//
//
//
//			});
//						AlertDialog dialog = builder.create();
//						dialog.show();
//						return true;
//					}
//				});
//
//	
//	}
//
//	
//
//	private List<Map<String, Object>> getData() {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("title", "这是第1行测试数据");
//		list.add(map);
//
//		map = new HashMap<String, Object>();
//		map.put("title", "这是第2行测试数据");
//		list.add(map);
//
//		map = new HashMap<String, Object>();
//		map.put("title", "这是第3行测试数据");
//		list.add(map);
//
//		map = new HashMap<String, Object>();
//		map.put("title", "这是第4行测试数据");
//		list.add(map);
//		return list;
//	}



	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	  @Override
		public void onDestroyView() {
		  super.onDestroyView();
		}
		@Override  
	    public void onDestroy() {  
	        super.onDestroy();   
	    } 

}
