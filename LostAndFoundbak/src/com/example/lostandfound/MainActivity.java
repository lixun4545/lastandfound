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

	
	int state=0;//0��δ��¼,1���ѵ�¼
	String uno;
	String username;
	String user="���¼";//��ʾ�û���
	Button buttonSig=null;//��¼
	Button buttonLos=null;//�Ҷ���
	Button buttonFou=null;//����
	Button buttonMod=null;//�޸�
	Button buttonReg=null;//ע��
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
					// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
//					String saveuno =sharedPreferences.getString("uno", "");
//					String savecount =sharedPreferences.getString("count", "");
//					if(saveuno!=null){
////						uno=saveuno;
//						state=1;
//					}
//					if(savecount==null){
//						dialog("ʰ�������л�����Ĵ�ͳ���£��ܶ�ʱ�����Ƕ��˶�����ȴû���㹻���;��ȥѰ�ң�Ϊ���Һ��ҵ�" +
//								"�Ŷ��ǿ����˴�������������Ŀ����Ҫ�Ƿ���ͬѧ�ǿ����ҵ��Լ���ʧ����Ʒ������ȥʵ��д����ȥ�ˣ��Ժ��ٱ�ɣ�");
//					}
					
		}
		else {
			state=1;
			uno=intent.getStringExtra("uno");
			//ʵ����SharedPreferences���󣨵�һ���� 
//			SharedPreferences mySharedPreferences= getSharedPreferences("test", 
//			Activity.MODE_PRIVATE); 
//			//ʵ����SharedPreferences.Editor���󣨵ڶ����� 
//			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
//			//��putString�ķ����������� 
////			editor.putString("uno", uno); 
//			editor.putString("count", "0"); 
//			//�ύ��ǰ���� 
//			editor.commit(); 
		}
		findviewbyid();
		if(state==1){
			dialog("�Ŷӳ�Ա����ѫ����Զ�֡����ס����񲩡�ʰ�������л�����Ĵ�ͳ���£��ܶ�ʱ�����Ƕ��˶�����ȴû���㹻���;��ȥѰ�ң�Ϊ���Һ��ҵ�" +
					"�Ŷ��ǿ����˴�������������Ŀ����Ҫ�Ƿ���ͬѧ�ǿ����ҵ��Լ���ʧ����Ʒ------�˴�ʡ��һ����");
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
				MainActivity.this.finish();//��activity�رգ���¼�ɹ�������
				startActivity(intent);
				}
				else{
					tishi("�ѵ�¼");
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
				tishi("���ȵ�¼");
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
				tishi("���ȵ�¼");
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
					tishi("���ȵ�¼");
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
		builder.setTitle("��ܰ��ʾ��");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
