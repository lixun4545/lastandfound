package com.example.lostandfound;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModActivity extends Activity {
	private Button mod_submit = null;
	private EditText mod_password = null;
	private EditText mod_newpassword1 = null;
	private EditText mod_newpassword2 = null;
	private EditText mod_tel = null;
	String uno;
	String username;
	Pattern p = null;
	Matcher m = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modmessage);
		Intent intent=getIntent();
		uno = intent.getStringExtra("uno");
		username = intent.getStringExtra("name");
		mod_submit = (Button) findViewById(R.id.mod_submit);
		mod_password = (EditText) findViewById(R.id.mod_password);
		mod_newpassword1 = (EditText) findViewById(R.id.mod_newpassword1);
		mod_newpassword2 = (EditText) findViewById(R.id.mod_newpassword2);
		mod_tel = (EditText) findViewById(R.id.mod_tel);
		mod_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mod_tel.getText().toString().trim().length()!=11){
					tishi("手机号格式不正确");
				}
				p = Pattern.compile("[a-zA-Z0-9]{1,16}");
		        m = p.matcher(mod_newpassword1.getText().toString().trim());
		        if(!m.matches()){
		        	tishi("密码由字母和数字构成");
		        	return;
		        }
				try {

					if (mod_newpassword1.getText().toString()
							.equals(mod_newpassword2.getText().toString()))
						new Thread() {
							public void run() {
								JSONObject json=new JSONObject();
								try {
									json.put("password",mod_password.getText().toString());
									json.put("uno",uno);
									json.put("name",username);
									json.put("newpassword", mod_newpassword1.getText().toString());
									json.put("tel", mod_tel.getText().toString().trim());
									String url=getString(R.string.url)+ "/UserServerlet";
									//构造请求对象
									HttpClient httpclient=new DefaultHttpClient();
									//创建post方式提交HttpPost
									HttpPost httpPost=new HttpPost(url);
									HttpResponse httpResponse=null;
									//键值对
									List<NameValuePair> namevalue=new ArrayList<NameValuePair>();
									namevalue.add(new BasicNameValuePair("jsonString", json.toString()));
									namevalue.add(new BasicNameValuePair("param","update"));
									httpPost.setEntity(new UrlEncodedFormEntity(namevalue,HTTP.UTF_8));
//									httpclient.execute(httpPost);
									httpResponse=httpclient.execute(httpPost);
									String response = EntityUtils.toString(httpResponse.getEntity());
									System.out.println("response"+response);
									if(response.equals("0")){
										handler.sendEmptyMessage(0);
//										tishi("密码错误");
									}
									else if(response.equals("-1")){
										handler.sendEmptyMessage(-1);
//										tishi("未知错误");
									}
									else{
										handler.sendEmptyMessage(1);
//										tishi("修改成功");
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									handler.sendEmptyMessage(8);
									e.printStackTrace();
								}
								
							};
						}.start();
					else
						handler.sendEmptyMessage(2);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}

	public void tishi(String str) {
		Toast.makeText(ModActivity.this, str, Toast.LENGTH_SHORT).show();
	}
	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(ModActivity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		builder.create().show();
	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==-1)
				tishi("未知错误");
			else if(msg.what==0)
				tishi("密码错误");
			else if (msg.what==2)
				tishi("两次密码输入不同");
			else if(msg.what==8)
				tishi("检查网络连接");
			else
				dialog("修改成功");		
		}
	};
}
