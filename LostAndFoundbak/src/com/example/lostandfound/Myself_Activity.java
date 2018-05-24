package com.example.lostandfound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Myself_Activity extends Activity{
	private Button mybut1;
	private Button mybut2;
	String uno;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_list);
		mybut1=(Button)findViewById(R.id.myself_good);
		mybut2=(Button)findViewById(R.id.myself_mes);
		Intent intent = getIntent();
		uno=intent.getStringExtra("uno");
		mybut1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Myself_Activity.this,MygoodActivity.class);
				intent.putExtra("uno", uno);
				startActivity(intent);
			}
		});
		mybut2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Myself_Activity.this,messageActivity.class);
				intent.putExtra("uno", uno);
				startActivity(intent);
			}
		});
	}
}
