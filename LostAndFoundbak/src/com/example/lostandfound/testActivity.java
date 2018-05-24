package com.example.lostandfound;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class testActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test123);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent intent = new Intent(testActivity.this,
						MainActivity.class);
				startActivity(intent);
				testActivity.this.finish();
			}
		};
		timer.schedule(task, 2000);
	}

}
