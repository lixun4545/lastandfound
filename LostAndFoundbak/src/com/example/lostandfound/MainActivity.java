package com.example.lostandfound;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.SharedPreferences;
//import android.view.Menu;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	int state=0;//0是未登录,1是已登录
	String uno;
	String username;
	String user="请登录";//显示用户名
	Button buttonSig=null;//登录
	Button buttonLos=null;//找东西
	Button buttonFou=null;//捡东西
	Button buttonMod=null;//修改
	Button buttonReg=null;//注册
	Button buttonmyself=null;
	Button fabugonggao=null;
	private void findviewbyid()
	{
		buttonSig=(Button)findViewById(R.id.buttonSig);
		buttonLos=(Button)findViewById(R.id.buttonLos);
		buttonFou=(Button)findViewById(R.id.buttonFou);
		buttonMod=(Button)findViewById(R.id.buttonMod);
		buttonReg=(Button)findViewById(R.id.buttonReg);
	    buttonmyself=(Button)findViewById(R.id.button_myself);
	    fabugonggao=(Button)findViewById(R.id.buttonFou2);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shouye);
		Intent intent = getIntent();
		username=intent.getStringExtra("name");
		if(intent.getStringExtra("uno")==null)
		{
			state=0;
//			SharedPreferences sharedPreferences= getSharedPreferences("test", 
//					Activity.MODE_PRIVATE); 
					// 使用getString方法获得value，注意第2个参数是value的默认值 
//					String saveuno =sharedPreferences.getString("uno", "");
//					String savecount =sharedPreferences.getString("count", "");
//					if(saveuno!=null){
////						uno=saveuno;
//						state=1;
//					}
//					if(savecount==null){
//						dialog("拾金不昧是中华民族的传统美德，很多时候我们丢了东西，却没有足够多的途径去寻找，为此我和我的" +
//								"团队们开发了此软件，本软件的目的主要是方便同学们快速找到自己丢失的物品，（我去实在写不下去了，以后再编吧）");
//					}
					
		}
		else {
			state=1;
			uno=intent.getStringExtra("uno");
			//实例化SharedPreferences对象（第一步） 
//			SharedPreferences mySharedPreferences= getSharedPreferences("test", 
//			Activity.MODE_PRIVATE); 
//			//实例化SharedPreferences.Editor对象（第二步） 
//			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
//			//用putString的方法保存数据 
////			editor.putString("uno", uno); 
//			editor.putString("count", "0"); 
//			//提交当前数据 
//			editor.commit(); 
		}
		findviewbyid();
		if(state==1){
			dialog("团队成员：李勋、杨远林、刘炎、鲍玉博。拾金不昧是中华民族的传统美德，很多时候我们丢了东西，却没有足够多的途径去寻找，为此我和我的" +
					"团队们开发了此软件，本软件的目的主要是方便同学们快速找到自己丢失的物品------此处省略一万字");
		}
//		dialog("");
		buttonReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		fabugonggao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,fabugonggaoacti.class);
				startActivity(intent);
			}
		});
		buttonSig.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				if(state==0){
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,SignActivity.class);
				MainActivity.this.finish();//主activity关闭，登录成功后启动
				startActivity(intent);
				}
				else{
					tishi("已登录");
				}
			}
		});
		buttonLos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,ListViewActivity.class);
				intent.putExtra("uno", uno);
				startActivity(intent);
			}
		});
		buttonFou.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(state==0)
				tishi("请先登录");
				else
				{
					String uid=uno;
					Intent intent=new Intent();
					intent.putExtra("uno", uid);
					intent.setClass(MainActivity.this,GoodSubmitAcitvity.class);
					startActivity(intent);
				}
			}
		});
		buttonMod.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(state==0)
				tishi("请先登录");
				else
				{
					Intent intent=new Intent();
					intent.putExtra("uno", uno);
					intent.putExtra("name", username);
					intent.setClass(MainActivity.this,ModActivity.class);
					startActivity(intent);
				}
			}
		});
		buttonmyself.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(state==1){
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,Myself_Activity.class);
				intent.putExtra("uno", uno);
				startActivity(intent);
				}
				else
				{
					tishi("请先登录");
				}
			}
		});
		
	}
	
	public void tishi(String str){
		Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
	}
	protected void dialog(String lin) {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage(lin);
		builder.setTitle("温馨提示！");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});

		builder.create().show();
	}
//	protected void onDestroy() {
//		  // TODO Auto-generated method stub
//		  if (clearshared) {
//		   editor.clear();
//		   editor.commit();
//		  }
//		  super.onDestroy();
//		 }
}
