package com.example.lostandfound;


//import com.example.lostandfound.*

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowActivity extends Activity{
	private ListView lv;
	private ArrayAdapter<GUood> data;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showactivity);
		lv=(ListView)findViewById(R.id.listView1);
//		data=new ArrayAdapter<good>(this,android.R.layout.simple_list_item_1);
	}
}
