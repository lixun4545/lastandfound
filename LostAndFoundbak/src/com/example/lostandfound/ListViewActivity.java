package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends Activity {

	
	private Button Search = null;
	private EditText editgoodType =null;
	private EditText editgood = null;
	private List<Map<String, Object>> mData;
	private List<Map<String, Object>> filterData;
	private ListView list;
	private List<Good> data = new ArrayList<Good>();
//	List<Good> somegood = new ArrayList<Good>();
	// Good的一个集合
	PoiResultAdapter resultAdapter = null;
	PoiResultAdapter someresultAdapter = null;
	String uno;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewactivity);  
		Intent intent = getIntent();
		uno = intent.getStringExtra("uno");
		Search = (Button) findViewById(R.id.searchbutton);
		editgood = (EditText) findViewById(R.id.editgood);
		list = (ListView) findViewById(R.id.resultlist);
		editgoodType=(EditText)findViewById(R.id.editgoodType);
		resultAdapter = new PoiResultAdapter(this);
//		editgoodType.setEnabled(false);
		// 获取数据
		resultAdapter.setData(data);
		// 将这些数据放到适配器中
		list.setAdapter(resultAdapter);
		// 再讲适配器中数据放入list中
		list.setOnItemClickListener(mOnClickListener);
		// 为每一个list设置一个监听器
		Search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!(editgoodType.getText().toString().trim().equals("电子产品")||
						editgoodType.getText().toString().trim().equals("其它")||
						editgoodType.getText().toString().trim().equals("钱包")||
						editgoodType.getText().toString().trim().equals("书本")||
						editgoodType.getText().toString().trim().equals("学生证/校园卡")||
						editgoodType.getText().toString().trim().equals("衣物")||
						editgoodType.getText().toString().trim().equals("银行卡")||
						editgoodType.getText().toString().trim().equals("钥匙/U盘")||
						editgoodType.getText().toString().trim().equals(""))){
					tishi("分类项目输入不正确");
					return;

				}
				new Thread(new Runnable(){
					public void run(){
//						somegood.clear();
						List<Good> somegood = new ArrayList<Good>();
						String good = editgood.getText().toString().trim();
						String url;
//						str = HttpUtility.UrlEncode("中文测试");
						if(editgoodType.getText().toString().trim().equals("")){
							url = getString(R.string.url)
									+ "/goodServlet?param=selectsome&name=" + good;
							
						}
						else{
							if(editgood.getText().toString().trim().equals(""))
								url = getString(R.string.url)
								+ "/goodServlet?param=selectsome&type=" + 
										editgoodType.getText().toString().trim();
							else
							url = getString(R.string.url)
									+ "/goodServlet?param=selectsome&name=" + good
									+ "&type="+editgoodType.getText().toString().trim();
						}

						try {
							URL httpUrl = new URL(url);
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
//							JSONObject json=new JSONObject()
							JSONArray array = new JSONArray(builder.toString());
							if (array.length() == 0) {
								handler.sendEmptyMessage(2);
							}
//							JSONObject json1 = array.getJSONObject(0);
//							System.out.println(json1.toString());
							for (int i = 0; i < array.length(); i++) {

								JSONObject json = array.getJSONObject(i);
//								System.out.println(json.getString("adress"));
//								System.out.println(json.toString());
								Good goo = new Good(Integer.parseInt(json
										.getString("gno")), json.getString("name"),
										json.getString("time"), json
												.getString("describe"), json
												.getString("uno"), json
												.getString("type"), json
												.getString("imagname"), json
												.getBoolean("claimstate"),json
												.getString("adress"),json
												.getString("imag"));
								somegood.add(goo);
								
								someresultAdapter = new PoiResultAdapter(
										ListViewActivity.this);
								someresultAdapter.setData(somegood);
								
							}
							handler.sendEmptyMessage(1);
							data=somegood;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		editgoodType.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeHeadIcon2();
			}
		});
	}

	// 监听器的实现方法
	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			 Intent intent = new Intent();
			 Good good=data.get(position);
//			 System.out.println(good.getName());
			 intent.putExtra("imagname", good.getImagname());
			 intent.putExtra("describe", good.getDescribe());
			 intent.putExtra("name", good.getName());
			 intent.putExtra("time", good.getTime());
			 intent.putExtra("type", good.getType());
			 intent.putExtra("uno", good.getUno());
			 intent.putExtra("gno",  Integer.toString(good.getGno()));
			 intent.putExtra("nowuno", uno);
			 System.out.println(good.getGno()+"--------------------------");
			 intent.putExtra("imag", good.getImag());
			 intent.putExtra("adress", good.getAdress());
			 intent.setClass(ListViewActivity.this, GooddetilActivity.class);//点击跳转到详细
			 startActivity(intent);
			//System.out.println(position);
		}
	};

	public class PoiResultAdapter extends BaseAdapter {

		private List<Good> listper;
		private LayoutInflater inflater;
		List<Boolean> mChecked;
		HashMap<Integer, View> map = new HashMap<Integer, View>();

		public PoiResultAdapter(Context context) {
			inflater = LayoutInflater.from(context);

		}

		// 给适配器赋值数据
		public void setData(List<Good> listper) {
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
				convertView = inflater.inflate(R.layout.listview, null);

				holder = new Holder(convertView);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Good pers = listper.get(position);
			// 显示他的内容
			
			holder.name.setText("物品名称："+pers.getName());

			holder.time.setText("捡到时间："+pers.getTime());

			holder.type.setText("物品类别："+pers.getType());

			holder.adress.setText("捡到地点："+pers.getAdress());

			Bitmap bm=convertStringToIcon(pers.getImag());
			
			holder.image.setImageBitmap(bm);
			final int p = position;

			return convertView;
		}

		class Holder {
			private TextView name;
			private TextView type;
			private TextView time;
			private TextView adress;
			private ImageView image;
			
			public Holder(View view) {
				name = (TextView) view.findViewById(R.id.ML_Text_name);
				type = (TextView) view.findViewById(R.id.LM_Text_name);
				time = (TextView) view.findViewById(R.id.L_Text_time);
				adress = (TextView) view.findViewById(R.id.L_Text_adress);
				image=(ImageView) view.findViewById(R.id.L_Image_name);
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
				Toast.makeText(ListViewActivity.this, str, Toast.LENGTH_SHORT).show();
			}
			private void changeHeadIcon2() {
				final CharSequence[] items = { "电子产品","学生证/校园卡","钱包" ,"书本" ,"衣物" ,"银行卡" ,"钥匙/U盘" ,"其它"  };
				AlertDialog dlg = new AlertDialog.Builder(ListViewActivity.this)
						.setTitle("选择类别")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								if (item == 0) {
									editgoodType.setText("电子产品");
								}
								else if(item==1){
									editgoodType.setText("学生证/校园卡");
								}
								else if(item==2){
									editgoodType.setText("钱包");
								}
								else if(item==3){
									editgoodType.setText("书本");
								}
								else if(item==4){
									editgoodType.setText("衣物");
								}
								else if(item==5){
									editgoodType.setText("银行卡");
								}
								else if(item==6){
									editgoodType.setText("钥匙/U盘");
								}
								else {
									editgoodType.setText("其它");
								}
								
							}
						}).create();
				dlg.show();
			}
}
