package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class messageActivity extends Activity{
	private Button Search = null;
	String uno;
	public URL httpurll;
//	private EditText editgoodType =null;
//	private EditText editgood = null;
	private List<Map<String, Object>> mData;
	private List<Map<String, Object>> filterData;
	String sendgno;
	private ListView list;
	private List<Good> data = new ArrayList<Good>();
//	private ListView list2;
	private List<GUood> data2 = new ArrayList<GUood>();
//	List<Good> somegood = new ArrayList<Good>();
	// Good的一个集合
	PoiResultAdapter resultAdapter = null;
	PoiResultAdapter someresultAdapter = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.renling); 
		Intent intent = getIntent();
		uno=intent.getStringExtra("uno");
//		uno="108";
		Search = (Button) findViewById(R.id.renling_return23);
//		editgood = (EditText) findViewById(R.id.editgood);
		list = (ListView) findViewById(R.id.renling_resultlist);
//		editgoodType=(EditText)findViewById(R.id.editgoodType);
		resultAdapter = new PoiResultAdapter(this);
		
		// 获取数据
		resultAdapter.setData(data2);
		// 将这些数据放到适配器中
		list.setAdapter(resultAdapter);
		// 再讲适配器中数据放入list中
		list.setOnItemClickListener(mOnClickListener);
		// 为每一个list设置一个监听器
		show1();
		Search.setOnClickListener(new View.OnClickListener() {

			@Override

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	// 监听器的实现方法
	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
//			 Intent intent = new Intent();
//			 Good good=data.get(position);
////			 System.out.println(good.getName());
//			 intent.putExtra("imagname", good.getImagname());
//			 intent.putExtra("describe", good.getDescribe());
//			 intent.putExtra("name", good.getName());
//			 intent.putExtra("time", good.getTime());
//			 intent.putExtra("type", good.getType());
//			 intent.putExtra("uno", good.getUno());
//			 intent.putExtra("gno", good.getGno());
//			 intent.putExtra("imag", good.getImag());
//			 intent.putExtra("adress", good.getAdress());
//			 intent.setClass(messageActivity.this, GooddetilActivity.class);//点击跳转到详细
//			 startActivity(intent);
			//System.out.println(position);
		}
	};

	public class PoiResultAdapter extends BaseAdapter {

		private List<GUood> listper;
		private LayoutInflater inflater;
		List<Boolean> mChecked;
		HashMap<Integer, View> map = new HashMap<Integer, View>();

		public PoiResultAdapter(Context context) {
			inflater = LayoutInflater.from(context);

		}

		// 给适配器赋值数据
		public void setData(List<GUood> listper) {
			this.listper = listper;
		}

		// list有几条数据
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listper.size();
		}

		// 获得点list的条
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listper.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = null;
			if (convertView == null) {
				// 赋值语句
				convertView = inflater.inflate(R.layout.listview_result, null);

				holder = new Holder(convertView);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			GUood pers = listper.get(position);
			// 显示他的内容
			sendgno=pers.getGno();
			
			holder.name.setText("申请人："+pers.getName());

			holder.time.setText("申请时间："+pers.getTime());

//			holder.type.setText("物品类别："+pers.getType());

			holder.adress.setText("联系方式："+pers.getTel());

			Bitmap bm=convertStringToIcon(pers.getImage());
			
			holder.image.setImageBitmap(bm);
			holder.butagree.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Thread(){
						public void run(){
							String url3 = getString(R.string.url)
									+ "/goodServlet?param=updategood&gno=" + sendgno;
							System.out.println(url3);
							try {

								URL httpUrl1 = new URL(url3);
//------------------------------------------
								URLConnection conn = httpUrl1.openConnection();
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(conn.getInputStream(),
												"utf-8"));
								StringBuffer builder = new StringBuffer();
								String str;
								while ((str = reader.readLine()) != null) {
									builder.append(str);
								}
								//-------------------------------
							handler.sendEmptyMessage(4);
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();
				}
			});
			holder.butdisagree.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			final int p = position;

			return convertView;
		}

		class Holder {
			private String sendgno;
			private TextView name;
//			private TextView type;
			private TextView time;
			private TextView adress;
			private ImageView image;
			private Button butagree;
			private Button butdisagree;
			
			public Holder(View view) {
				name = (TextView) view.findViewById(R.id.LM_Text_name);
//				type = (TextView) view.findViewById(R.id.LM_Text_name);
				time = (TextView) view.findViewById(R.id.LM_Text_time);
				adress = (TextView) view.findViewById(R.id.LM_Text_tel);
				image=(ImageView) view.findViewById(R.id.LM_Image_name);
				butagree=(Button)view.findViewById(R.id.L_agree);
				butdisagree=(Button)view.findViewById(R.id.L_disagree);
			}
		}

	}
	 public boolean onCreateOptionsMenu(Menu menu) {
	 // Inflate the menu; this adds items to the action bar if it is present.
	 getMenuInflater().inflate(R.menu.main, menu);
	 return true;
	 }
		private Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1)
				list.setAdapter(someresultAdapter);
				else if(msg.what==4)
					tishi("成功");
				else
				tishi("暂无您要找的物品信息");
			}
		};
		//String 转Bitmap
		 public static Bitmap convertStringToIcon(String st)  
		    {  
		        Bitmap bitmap = null;  
		        try  
		        {  
		            byte[] bitmapArray;  
		            bitmapArray = Base64.decode(st, Base64.DEFAULT);  
		            bitmap =  
		                    BitmapFactory.decodeByteArray(bitmapArray, 0,  
		                            bitmapArray.length);  
		            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);  
		            return bitmap;  
		        }  
		        catch (Exception e)  
		        {  
		            return null;  
		        }  
		    }  
			public void tishi(String str){
				Toast.makeText(messageActivity.this, str, Toast.LENGTH_SHORT).show();
			}

			public void show1()
			{
				new Thread(new Runnable(){
					public void run(){
//						somegood.clear();
//						String uno="94";
						List<GUood> somegood = new ArrayList<GUood>();
//						String good = editgood.getText().toString().trim();
						String url;
						String url2;
							url = getString(R.string.url)
									+ "/goodServlet?param=myselect&uno=" + uno;
							url2 = getString(R.string.url)
									+ "/goodServlet?param=selectun";


						try {
							URL httpUrl = new URL(url);
//							System.out.println("1234"+editgoodType.getText().toString().trim());
							System.out.println(url);
							URLConnection conn = httpUrl.openConnection();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(conn.getInputStream(),
											"utf-8"));
							StringBuffer builder = new StringBuffer();
							String str;
							while ((str = reader.readLine()) != null) {
								builder.append(str);
							}
							JSONArray array = new JSONArray(builder.toString());
							if (array.length() == 0) {
								handler.sendEmptyMessage(2);
							}
							URL httpUrl2 = new URL(url2);
//							System.out.println("1234"+editgoodType.getText().toString().trim());
							System.out.println(url2);
							URLConnection conn2 = httpUrl2.openConnection();
							BufferedReader reader2 = new BufferedReader(
									new InputStreamReader(conn2.getInputStream(),
											"utf-8"));
							StringBuffer builder2 = new StringBuffer();
							String str2;
							while ((str2 = reader2.readLine()) != null) {
								builder2.append(str2);
							}
							JSONArray array2 = new JSONArray(builder2.toString());
							if (array2.length() == 0) {
								handler.sendEmptyMessage(2);
							}
//							JSONObject ujson2 = array2.getJSONObject(0);
//							System.out.println(ujson2.toString());
//							JSONObject ujson3 = array.getJSONObject(0);
//							System.out.println(ujson3.toString());
							for(int j=0;j<array2.length();j++){
								JSONObject json2=array2.getJSONObject(j);
								for (int i = 0; i < array.length(); i++){
									JSONObject json = array.getJSONObject(i);
									System.out.println(json.getString("gno")+"------------"+json2.getString("gno"));
									if(json.getString("gno").equals(json2.getString("gno")))
									{
										GUood guood=new GUood(json.getString("imag"),json2.getString("uno"),
												json2.getString("gno"),json2.getString("uname"),json2.getString("unno"),
												json2.getString("time"),json2.getString("time"));
										somegood.add(guood);
										sendgno=json2.getString("gno");
									}
								}
							}
//							for (int i = 0; i < array.length(); i++) {
//
//								JSONObject json = array.getJSONObject(i);
////								System.out.println(json.getString("adress"));
////								System.out.println(json.toString());
//								Good goo = new Good(Integer.parseInt(json
//										.getString("gno")), json.getString("name"),
//										json.getString("time"), json
//												.getString("describe"), json
//												.getString("uno"), json
//												.getString("type"), json
//												.getString("imagname"), json
//												.getBoolean("claimstate"),json
//												.getString("adress"),json
//												.getString("imag"));
//								somegood.add(goo);
//							}
							someresultAdapter = new PoiResultAdapter(
									messageActivity.this);
//							somegood.remove(1);
//							somegood.remove(2);
							someresultAdapter.setData(somegood);
							handler.sendEmptyMessage(1);
							data2=somegood;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}

}
