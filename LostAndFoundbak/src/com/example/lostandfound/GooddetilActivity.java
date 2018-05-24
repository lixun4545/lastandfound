package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GooddetilActivity extends Activity {

	String tel;
	public URL httpurl;
//	private TextView de_decrible = null;
	private TextView de_type = null;
	private TextView de_adress = null;
	private TextView de_time = null;
	private TextView de_name = null;
	private ImageView imag = null;
	private Button but1 = null;
	private Button but2 = null;
	private Button buttongoback=null;
	private Button buttondetil=null;
	String uno;
	String nowuno;
	String gno;
	String name;
	String describe;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gooddetial);
		Intent intent = getIntent();
		String image = intent.getStringExtra("imag");
		describe = intent.getStringExtra("describe");
		String type = intent.getStringExtra("type");
		String time = intent.getStringExtra("time");
		name = intent.getStringExtra("name");
		uno = intent.getStringExtra("uno");// 为全局变量
		nowuno = intent.getStringExtra("nowuno");// 为全局变量
		gno = intent.getStringExtra("gno");
		System.out.println(gno+"--------------------------");
//		nowuno="95";
//		gno="6";
//		name="222";
		String adress=intent.getStringExtra("adress");
//		String uno=intent.getStringExtra("uno");
		
		//de_decrible = (TextView) findViewById(R.id.de_decrible);
		de_type = (TextView) findViewById(R.id.de_type);
		de_adress = (TextView) findViewById(R.id.de_adress);
		de_time = (TextView) findViewById(R.id.de_time);
		de_name=(TextView) findViewById(R.id.de_name);
		imag = (ImageView) findViewById(R.id.de_image);
		but1 = (Button) findViewById(R.id.but_renling);
		but2 = (Button) findViewById(R.id.but_tel);
		buttongoback=(Button) findViewById(R.id.buttongoback);
		buttondetil=(Button)findViewById(R.id.buttondetil);
//		de_decrible.setText(describe);
		de_type.setText("物品类型："+type);
		de_adress.setText("捡到地点："+adress);
		de_time.setText("捡到时间："+time);
		de_name.setText("物品名称："+name);
		imag.setImageBitmap(convertStringToIcon(image));

		buttongoback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//认领
		but1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(uno==nowuno)
					tishi("自己上传的东西不能自己认领！");
				else if (nowuno==null)
					tishi("未登录不能认领");
				else
				dialog1("确认认领？");
			}
		});
		but2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(nowuno!=null)
				new Thread(new Runnable() {
					public void run() {
						String url = getString(R.string.url)
								+ "/goodServlet?param=gettel&uno=" + uno;
						try {
							httpurl = new URL(url);
							URLConnection urlconn = httpurl.openConnection();
							InputStream is = urlconn.getInputStream();
							InputStreamReader isr = new InputStreamReader(is);
							BufferedReader br = new BufferedReader(isr);
							StringBuilder builder = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								builder.append(line);
							}
							JSONObject root = new JSONObject(builder.toString());
							tel = root.getString("tel");
							//String QQ = root.getString("QQ");
							System.out.println(tel);
							handler.sendEmptyMessage(1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}).start();
				else
					tishi("未登录不能用此功能");
			}
		});
		buttondetil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog(describe);
			}
		});
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu, menu);
	// return true;
	// }
	private void changeHeadIcon(String tel1) {
		final CharSequence[] items = { "tel:"+tel1 };
		AlertDialog dlg = new AlertDialog.Builder(GooddetilActivity.this)
				.setTitle("选择联系方式")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {

							Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));
							startActivity(intent);
						} else {

						}
					}
				}).create();
		dlg.show();
	}
	// String转bitmap
	public static Bitmap convertStringToIcon(String st) {
		// OutputStream out;
		Bitmap bitmap = null;
		try {
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			changeHeadIcon(tel);
			// System.out.println("用户名不存在");
		}

	};
	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(GooddetilActivity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				GooddetilActivity.this.finish();
			}
		});
		builder.create().show();
	}
	protected void dialog1(String lin) {
		AlertDialog.Builder builder = new Builder(GooddetilActivity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				GooddetilActivity.this.finish();
				new Thread(){
					public void run(){
						String url;
						try {
							url = getString(R.string.url)
									+ "/goodServlet?param=ugadd&uno="
									+ nowuno + "&gno=" + gno
									+ "&gname="+name;
							URL httpurl = new URL(url);
							URLConnection urlconn = httpurl
									.openConnection();
							InputStream is = urlconn.getInputStream();
							InputStreamReader isr = new InputStreamReader(
									is);
							BufferedReader br = new BufferedReader(isr);
							StringBuilder builder = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								builder.append(line);
							}
							JSONObject root = new JSONObject(builder
									.toString());
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
		
					};
				}.start();
				tishi("信息已发送，请联系对方，请耐心等待回应");
			}
		});
		
		builder.create().show();
	}
	public void tishi(String str){
		Toast.makeText(GooddetilActivity.this, str, Toast.LENGTH_SHORT).show();
	}
}
