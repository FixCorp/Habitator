package com.example.habitator;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class Habitator extends ListActivity {
	private TasksDataSource datasource;
	public String taskname;
	public int time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//findViewById(R.id.container).requestFocus();
		
		EditText txt = (EditText) findViewById(R.id.name);  
		Typeface font = Typeface.createFromAsset(getAssets(), "Molot.otf");  
		//txt.setTypeface(font);  
		
		Intent intent = getIntent();
		taskname = intent.getStringExtra("taskname");
		time = intent.getIntExtra("time",1000);
		//
		//if(taskname!=null) {
		//	insertToDb(taskname);
		//}

		setContentView(R.layout.activity_habitator);
		//ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(color.black));
		datasource = new TasksDataSource(this);
		datasource.open();
		
		List<Task> values = datasource.getAllTasks();
		
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
		if(taskname!=null) {
		Log.v("adfdf", "adfadf"+taskname);
		Log.v("flow :"," calling insertIntoDb with parameter taskname");
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter();
	    Task task = null;
	    String data="";
	    task = datasource.createTask(taskname);
	    adapter.add(task);
	    adapter.notifyDataSetChanged();
		}
	
	}
	
	

;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_habitator, menu);
	    MenuItem menuItem = menu.findItem(R.id.addTaskMenuButton);
	    menuItem.setOnActionExpandListener(new OnActionExpandListener() {
	        @Override
	        public boolean onMenuItemActionCollapse(MenuItem item) {
	            // Do something when collapsed
	            return true;  // Return true to collapse action view
	        }

	        @Override
	        public boolean onMenuItemActionExpand(MenuItem item) {
	        	View vw = item.getActionView();
	        	Log.w(Habitator.class.getName(), "Variable: "+vw);
	        	EditText et = (EditText) vw.findViewById(R.id.actionBarEdit);
	        	Log.w(Habitator.class.getName(), "Variable et :" +et);
	        	et.requestFocus();
	        	//View vie = menuItem.getActionView();
	            // Do something when expanded
	            return true;  // Return true to expand action view
	        }
	    });
	    return true;
	}

	public void addTaskActivityStarter(View view){
		Intent intent = new Intent(this, AddTaskActivity.class);
	    startActivity(intent);
	}
	
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	
}
