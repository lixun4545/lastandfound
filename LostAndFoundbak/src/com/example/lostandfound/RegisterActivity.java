package com.example.lostandfound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Button buttonsubmit = null;
	Button buttonsendmessage = null;
	int a;
	private EditText editname;
	private EditText editpassword1;
	private EditText editpassword2;
	private EditText edittel;
	private EditText editTextyanzheng;
	boolean bool = false;
	Pattern p = null;
	Matcher m = null;
	private void findviewbyid() {
		buttonsubmit = (Button) findViewById(R.id.buttonsubmit);
		buttonsendmessage = (Button) findViewById(R.id.buttonsendmessage);
		editpassword1 = (EditText) findViewById(R.id.editTextpassword1);
		editpassword2 = (EditText) findViewById(R.id.editTextpassword2);
		edittel = (EditText) findViewById(R.id.editTexttel);
		editname = (EditText) findViewById(R.id.editTextname);
		editTextyanzheng = (EditText) findViewById(R.id.editTextyanzheng);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);
		findviewbyid();
		buttonsubmit.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				
				// JSONObject json= new JSONObject();
				if(editname.getText().toString().trim().length()<6)
				{
					tishi("用户名不能少于6位，不能大于16位");
					return ;
				}
				if(editpassword1.getText().toString().trim().length()<6){
					tishi("密码不能少于6位");
					return ;
				}
				if(edittel.getText().toString().trim().length()!=11){
					tishi("手机号不合法，请输入正确的手机号");
					return ;
				}
				p = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}");
				m = p.matcher(editname.getText().toString().trim());
				if(!m.matches()){
					tishi("用户名由字母数字下划线组成且开头必须是字母");
					return;
				}
		        p = Pattern.compile("[a-zA-Z0-9]{1,16}");
		        m = p.matcher(editpassword1.getText().toString().trim());
		        if(!m.matches()){
		        	tishi("密码由字母和数字构成");
		        	return;
		        }
				new Thread(new Runnable() {
					public void run() {
						System.out.println("确定");
						if(editTextyanzheng.getText().toString().equals(""))
						{
							handler.sendEmptyMessage(5);
							return;
						}
						if (Integer.parseInt(editTextyanzheng.getText()
								.toString()) == a) {
							bool = true;
						}
						if (!bool) {
							handler.sendEmptyMessage(4);
							return;
						}

						String name = editname.getText().toString().trim();
						String password1 = editpassword1.getText().toString();
						String password2 = editpassword2.getText().toString();
						String tel = edittel.getText().toString().trim();
						String url;
						if (password1.equals(password2)) {
							try {
								url = getString(R.string.url)
										+ "/UserServerlet?param=add&name="
										+ name + "&password=" + password1
										+ "&tel=" + tel;
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
								String state = root.getString("state");
								if (state != null && state.equals("1"))
									handler.sendEmptyMessage(1);// 注册成功
								else
									handler.sendEmptyMessage(2);// 用户名存在
								// ----------------------------------
							} catch (Exception e) {
								// TODO Auto-generated catch block
								handler.sendEmptyMessage(8);
								e.printStackTrace();
							}
						} else {
							handler.sendEmptyMessage(3);// 两次密码输入不一致
						}
					};
				}).start();

			}
		});
		buttonsendmessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					public void run() {
						
						a=sendmessage(edittel.getText().toString());
//							handler.sendEmptyMessage(6);
							
							for(int i=60;i>0;i--){
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									handler.sendEmptyMessage(8);
									e.printStackTrace();
								}
//								System.out.println(i);
								handler1.sendEmptyMessage(i);
							}
							handler.sendEmptyMessage(7);
					};
				}).start();
			}
		});
	}

	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(RegisterActivity.this);
		builder.setMessage(lin);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				RegisterActivity.this.finish();
			}
		});
		builder.create().show();
	}

	public int sendmessage(String tel) {
		// "http://106.ihuyi.cn/webservice/sms.php?method=Submit"
		String url1 = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url1);

		client.getParams().setContentCharset(url1);
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=GBK");
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);

		System.out.println(mobile_code);
		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

		// post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		NameValuePair[] data = {// 提交短信
				new NameValuePair("account", "cf_a6161578"),
				new NameValuePair("password",
						"0d996b24baf93004a623097a0b03911f"), // 查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
				// new NameValuePair("password",
				// util.StringUtil.MD5Encode("密码")),
				new NameValuePair("mobile", tel),
				new NameValuePair("content", content), };
		// httpPost.setEntity(new UrlEncodedFormEntity(nameValue,HTTP.UTF_8));
		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			// System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
//			handler.sendEmptyMessage(Integer.parseInt(code)+10);
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			
			System.out.println(content);

			if ("2".equals(code)) {
				handler.sendEmptyMessage(6);
				System.out.println("短信提交成功");
			}
			else{
				handler.sendEmptyMessage(9);
			}

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			handler.sendEmptyMessage(8);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mobile_code;
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1)
				dialog("注册成功,点击返回");
			else if (msg.what == 2)
				tishi("用户名存在");
			else if (msg.what == 3)
				tishi("两次密码输入不一致");
			else if (msg.what == 4)
				tishi("请输入正确的验证码");
			else if (msg.what == 5)
				tishi("验证码不能为空");
			else if (msg.what == 6)
			{
				tishi("验证码发送成功");
				buttonsendmessage.setEnabled(false);
			}else if (msg.what == 7)
			{
//				tishi("验证码发送成功");
				buttonsendmessage.setEnabled(true);
				buttonsendmessage.setText("发送验证码");
			}else if(msg.what==8){
				tishi("检查网络连接");
			}else if(msg.what==9){
				tishi("发送失败，原因未知");
			}
			
		}
	};
	private Handler handler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			buttonsendmessage.setText(msg.what+"秒");
		}

	};
	public void tishi(String str) {
		Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
	}

}
