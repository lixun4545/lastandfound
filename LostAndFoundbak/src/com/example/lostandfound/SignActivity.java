package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

//import com.chao.createcode.MainActivity;
//import com.example.*;

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
import android.widget.ImageView;
import android.widget.Toast;

public class SignActivity extends Activity {
	private ImageView iv_showCode;
	private EditText et_phoneCode;
//	private EditText et_phoneNum;
	Button buttonsubmit1 = null;
	Button buttonreturn1 = null;
	public URL httpurl;
	private EditText editpassword;
	private EditText editusername;
	private String realCode;
	String uno;
	private void findviewbyid() {
		buttonsubmit1 = (Button) findViewById(R.id.buttonsubmit1);
		buttonreturn1 = (Button) findViewById(R.id.buttonreturn1);
		editpassword = (EditText) findViewById(R.id.editTextpassword);
		editusername = (EditText) findViewById(R.id.editTextusername);
		iv_showCode=(ImageView)findViewById(R.id.iv_showCode);
		et_phoneCode=(EditText)findViewById(R.id.et_phoneCode);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findviewbyid();
//		et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes);
//		Button but_toSetCode = (Button) findViewById(R.id.but_forgetpass_toSetCodes);
//		but_toSetCode.setOnClickListener(this);
		iv_showCode = (ImageView) findViewById(R.id.iv_showCode);
		//将验证码用图片的形式显示出来
		iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
		realCode = Code.getInstance().getCode();
		iv_showCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
				realCode = Code.getInstance().getCode();
			}
		});
		buttonsubmit1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String phoneCode = et_phoneCode.getText().toString();
				if(yanzheng(phoneCode,realCode)){
					tishi("验证码正确");
				}else{
//					Toast.makeText(SignActivity.this, phoneCode+"验证码错误", Toast.LENGTH_SHORT).show();
					tishi("验证码错误");
					return;
				}
				new Thread(new Runnable() {

					public void run() {
						try {
							String name = editusername.getText().toString().trim();
							String password = editpassword.getText().toString();
							String url = getString(R.string.url)
									+ "/UserServerlet?param=sign&name=" + name
									+ "&password=" + password;
							System.out.println(url);
							httpurl = new URL(url);
							System.out.println(111);
							URLConnection urlconn = httpurl.openConnection();
							System.out.println(111);
							InputStream is = urlconn.getInputStream();
							System.out.println(111);
							InputStreamReader isr = new InputStreamReader(is);
							System.out.println(111);
							BufferedReader br = new BufferedReader(isr);
							StringBuilder builder = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								builder.append(line);
							}
							JSONObject root = new JSONObject(builder.toString());
							String state = root.getString("state");
							System.out.println(state);
							if (state != null && state.equals("0"))
								handler.sendEmptyMessage(0);
							else if (state != null && state.equals("-1"))
								handler.sendEmptyMessage(-1);
							else{
								uno=state;
								handler.sendEmptyMessage(1);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							handler.sendEmptyMessage(2);
						}
					};
				}).start();
			}
		});
		buttonreturn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				Intent intent=new Intent();
//				intent.putExtra("uno", uno);
//				intent.putExtra("name", editusername.getText().toString().trim());
				intent.setClass(SignActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}

	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(SignActivity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		if(lin.equals("登录成功"))
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				SignActivity.this.finish();
				SignActivity.this.finish();
				Intent intent=new Intent();
				intent.putExtra("uno", uno);
				intent.putExtra("name", editusername.getText().toString().trim());
				intent.setClass(SignActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		else
			builder.setPositiveButton("确认", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					SignActivity.this.finish();
					Intent intent=new Intent();
					intent.setClass(SignActivity.this,MainActivity.class);
					startActivity(intent);
				}
			});
		builder.create().show();

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1)
				dialog("登录成功");
			else if (msg.what == 0)
				tishi("密码错误");
			else if (msg.what == -1)
				tishi("用户名不存在");
			else if (msg.what == 2)
				tishi("请检查网络连接");
		}

	};
	public void tishi(String str){
		Toast.makeText(SignActivity.this, str, Toast.LENGTH_SHORT).show();
	}
	public boolean yanzheng(String str1,String str2){//严重大小写
		boolean a=true;
		for(int i=0;i<4;i++){
			char chs1[]=str1.toCharArray();
			char chs2[]=str2.toCharArray();
//			System.out.println(chs1[i]);
			int b=chs1[i];int c=chs2[i];
			System.out.println(b+"---------"+c);
			if(b==c||b-c==32||c-b==32){
				
			}else{
				a=false;
			}
		}
		return a;
	}

}
