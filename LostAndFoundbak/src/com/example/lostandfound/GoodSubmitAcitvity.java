package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class GoodSubmitAcitvity extends Activity{

	


	String adressStr;
	String imagestr;
	private EditText name;
	private EditText time;
	private EditText adress;
	private EditText type;
	private EditText other;
	private Button butsubmit;
	private Button butchoose;
	private ImageView goodimage;
	String uno;
	byte imbyte[];
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goodubmit2);
		Intent intent = getIntent();
		uno = intent.getStringExtra("uno");//需要主界面传过来uno
		name=(EditText)findViewById(R.id.ediGoodname);
		time=(EditText)findViewById(R.id.editGoodTime);
		adress=(EditText)findViewById(R.id.editAdress);
		type=(EditText)findViewById(R.id.buttontype);
		other=(EditText)findViewById(R.id.editOther);
		butsubmit=(Button)findViewById(R.id.goodbutsubmit);
		butchoose=(Button)findViewById(R.id.goodrree);
		goodimage=(ImageView)findViewById(R.id.goodimage);
//		type.setEnabled(false);
		time.setText(gettime());
		adress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(){
					public void run(){
						TelephonyManager mTelMan = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String operator = mTelMan.getNetworkOperator();
						String mcc = operator.substring(0, 3);
						String mnc = operator.substring(3);
						GsmCellLocation location = (GsmCellLocation) mTelMan.getCellLocation();
						int cid = location.getCid();
						int lac = location.getLac();
						String httpUr1l = "http://apis.baidu.com/lbs_repository/cell/query";
						String httpAr1g = "mcc="+mcc+"&mnc="+mnc+"&lac="+lac+"&ci="+cid+"&coord=gcj02";
						String jsonResult = request(httpUr1l, httpAr1g);
//						JSONArray array = new JSONArray(jsonResult);
						try {
							JSONObject json=new JSONObject(jsonResult);
							System.out.println(jsonResult);
							adressStr=json.getString("address");
//							adress.setText(adressStr);
							System.out.println(adressStr);
							handler.sendEmptyMessage(2);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					};
				}.start();
			}
		});
		butsubmit.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!(type.getText().toString().trim().equals("电子产品")||
						type.getText().toString().trim().equals("其它")||
						type.getText().toString().trim().equals("钱包")||
						type.getText().toString().trim().equals("书本")||
						type.getText().toString().trim().equals("学生证/校园卡")||
						type.getText().toString().trim().equals("衣物")||
						type.getText().toString().trim().equals("银行卡")||
						type.getText().toString().trim().equals("钥匙/U盘"))){
					tishi("分类项目输入不正确");
					return;
					
				}
				new Thread(new Runnable(){
					public void run(){
						String strname=name.getText().toString().trim();
						String strtime=time.getText().toString().trim();
						String stradress=adress.getText().toString().trim();
						String strtype=type.getText().toString().trim();
						String strother=other.getText().toString().trim();
						
						try {
							JSONObject json=new JSONObject();
							json.put("type",strtype);
							json.put("name",strname);
							json.put("image",imagestr);
							json.put("time",strtime);
							json.put("other",strother);
							json.put("adress",stradress);
							json.put("uno", uno);
							String url=getString(R.string.url)+ "/goodServlet";
							//构造请求对象
							HttpClient httpClient=new DefaultHttpClient();
							//构建GET方法提交,post方式提交
							HttpPost httpPost=new HttpPost(url);
							List<NameValuePair> nameValue=new ArrayList<NameValuePair>();
							nameValue.add(new BasicNameValuePair("jsonString", json.toString()));
							nameValue.add(new BasicNameValuePair("param","goodadd"));
							httpPost.setEntity(new UrlEncodedFormEntity(nameValue,HTTP.UTF_8));
							httpClient.execute(httpPost);
							handler.sendEmptyMessage(2);
						} catch (Exception e) {
							// TODO: handle exception
							handler.sendEmptyMessage(8);
							e.printStackTrace();
						}
					};
				}).start();
				
			}
		});
		goodimage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){
				public void run(){
					handler.sendEmptyMessage(1);
				};
			}).start();
			}
		});
		type.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeHeadIcon2();
			}
		});
		butchoose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			Bitmap bm = (Bitmap) bundle.get("data");
			goodimage.setImageBitmap(bm);
			imagestr=convertIconToString(bm);
//			imbyte=Bitmap2Bytes(bm);
		}
//			System.out.println(imbyte.toString());
//			System.out.println(imbyte.toString().getBytes().toString());
//		} else if (requestCode == 1001 && resultCode == RESULT_OK) {
//			Uri uri = data.getData();
//			ContentResolver resolver = getContentResolver();
//			try {
//				Bitmap bitmap = BitmapFactory.decodeStream(resolver
//						.openInputStream(uri));
//				imagestr=convertIconToString(bitmap);
////				imbyte=Bitmap2Bytes(bitmap);
//				goodimage.setImageBitmap(bitmap);
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
	}
	public static String convertIconToString(Bitmap bitmap)  
    {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream  
        bitmap.compress(CompressFormat.PNG, 100, baos);  
        byte[] appicon = baos.toByteArray();// 转为byte数组  
        return Base64.encodeToString(appicon, Base64.DEFAULT);  
    }
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1)
			changeHeadIcon();
			else if(msg.what==8)
				tishi("上传失败检查网络连接");
			else if(msg.what==2)
				adress.setText(adressStr);
			else
			dialog("上传成功点击返回");
		}

	};
	private void changeHeadIcon() {
		final CharSequence[] items = { "拍照上传"};
		AlertDialog dlg = new AlertDialog.Builder(GoodSubmitAcitvity.this)
				.setTitle("选择图片的方式")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							Intent intent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(intent, 1000); 
						} //else {
//							Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//							intent.setType("image/*");
//							intent.putExtra("crop", true);
//							intent.putExtra("return-data", true);
//							startActivityForResult(intent, 1001);
//						}
					}
				}).create();
		dlg.show();
	}
	private void changeHeadIcon2() {
		final CharSequence[] items = { "电子产品","学生证/校园卡","钱包" ,"书本" ,"衣物" ,"银行卡" ,"钥匙/U盘" ,"其它"  };
		AlertDialog dlg = new AlertDialog.Builder(GoodSubmitAcitvity.this)
				.setTitle("选择类别")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							type.setText("电子产品");
						}
						else if(item==1){
							type.setText("学生证/校园卡");
						}
						else if(item==2){
							type.setText("钱包");
						}
						else if(item==3){
							type.setText("书本");
						}
						else if(item==4){
							type.setText("衣物");
						}
						else if(item==5){
							type.setText("银行卡");
						}
						else if(item==6){
							type.setText("钥匙/U盘");
						}
						else {
							type.setText("其它");
						}
						
					}
				}).create();
		dlg.show();
	}
	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(GoodSubmitAcitvity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				GoodSubmitAcitvity.this.finish();
			}
		});
		builder.create().show();
	}
	public void tishi(String str){
		Toast.makeText(GoodSubmitAcitvity.this, str, Toast.LENGTH_SHORT).show();
	}
	public String gettime(){
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm"); 
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间 
		String str = formatter.format(curDate); 
		return str;
	}
	public static String request(String httpUrl, String httpArg) {

				BufferedReader reader = null;
			    String result = null;
			    StringBuffer sbf = new StringBuffer();
			    httpUrl = httpUrl + "?" + httpArg;

			    try {
			        URL url = new URL(httpUrl);
			        HttpURLConnection connection = (HttpURLConnection) url
			                .openConnection();
			        connection.setRequestMethod("GET");
			        // 填入apikey到HTTP header
			        connection.setRequestProperty("apikey",  "56020b78730d2e88abe2641d2339ba84");
			        connection.connect();
			        InputStream is = connection.getInputStream();
			        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			        String strRead = null;
			        while ((strRead = reader.readLine()) != null) {
			            sbf.append(strRead);
			            sbf.append("\r\n");
			        }
			        reader.close();
			        result = sbf.toString();
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			    return result;
	}

}
