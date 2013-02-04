package com.example.habitator;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/* Launcher Activity class and it extends listactivity to facilitate list view */
public class Habitator extends ListActivity {
	private TasksDataSource datasource; //see TaskDataSource.java; it defines basic functions for creating and retrieving data objects, in this case 'tasks'
	
	public String taskname; // just a global variable for passing data
	public int time; //just a global vrible for passing data, we no longer need this variable. we just need a task name.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		/* this block is to change the font of EditText Element, but it crashes. So its commented out.
		EditText txt = (EditText) findViewById(R.id.name);  
		Typeface font = Typeface.createFromAsset(getAssets(), "Molot.otf");  
		txt.setTypeface(font);   */
		
		/* This is the intent reciever, the intent is sent from AddTaskActivity.java. It sends data about the task-name and time along with the intent */
		/* Anyway we dont need this now, since addTaskActivity is no longer needed. Just kept for reference */
		/*
		Intent intent = getIntent();
		taskname = intent.getStringExtra("taskname"); //taskname is retrieved from intent and stored in the lobal taskname varible defined above
		time = intent.getIntExtra("time",1000); //time is also retrieved from intent
		*/
		/* This activity is inflated with activity_habitator.xml layout file */
		setContentView(R.layout.activity_habitator);
		
		/* After setting the layout, the data from the database needs to be retrieved and displayed in the list view */
		/* For that the database needs to be connected and queried, for now TaskDataSource class defines the methods for it. so, datasource object is initiated with that class*/
		datasource = new TasksDataSource(this); //here
		datasource.open(); //method to connect to sqlite
		
		List<Task> values = datasource.getAllTasks(); // A List Variable of Type 'Task' is initiated to getAllTasks in a variable 'values'; Task is the object we defined in Task.java
		
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter); //I don't exactly know how ArrayAdapter is used with List variable :(
		
		/*
		if(taskname.isEmpty()) { //check if taskname has some value
		//Log.v("adfdf", "taskname :"+taskname);
		//Log.v("flow :"," calling insertIntoDb with parameter taskname");
		
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter(); // setListAdapter() 
	    Task task = null; // initialize a Task Object. This wil be used in 'task = datasource.createTask(taskname)'
	    task = datasource.createTask(taskname); //method 'createTask()' returns the newly created 'Task' Object. Assign it to 'task' 
	    adapter.add(task); //add task to adapter
	    adapter.notifyDataSetChanged(); //update Listview 
		} */
	
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_habitator, menu); // layout for action bar menu items. see /res/menu/activity_habitator.xml
	    MenuItem menuItem = menu.findItem(R.id.addTaskMenuButton); // Get the 'addTaskMenuBarButton' in 'menuItem'
	    menuItem.setOnActionExpandListener(new OnActionExpandListener() { //set an onClick event to 'menuItem'. This is what happens, when clicking the plus button in Action Bar
	        @Override
	        public boolean onMenuItemActionCollapse(MenuItem item) { 
	            // Do something when collapsed
	            return true;  // Return true to collapse action view
	        }

	        @Override
	        public boolean onMenuItemActionExpand(MenuItem item) {
	        	
	        	View vw = item.getActionView(); // get the changed view after click event in 'vw'
	        	//Log.w(Habitator.class.getName(), "Variable: "+vw);
	        	EditText et = (EditText) vw.findViewById(R.id.actionBarEdit); // from vw get the EditText element
	        	//Log.w(Habitator.class.getName(), "Variable et :" +et);
	        	et.requestFocus(); //set the new keyboard focus to EditText element. We have to do this, because it doesn't get focussed by default
	        	
	        	
	        	/* We also have to get the Tic Button and assign it an onClick Event to store the task entered in editText, to database. Need to do that later  */	        	
	        	return true;  // Return true to expand action view
	        }
	    });
	    return true; // Return true to Optionsmenuoncreate
	}

	/* this is no more needed
	public void addTaskActivityStarter(View view){
		Intent intent = new Intent(this, AddTaskActivity.class);
	    startActivity(intent);
	}
	*/
	
	public void insertToDb(View v) {
		Log.w(Habitator.class.getName(), "into event callback");
    	EditText et = (EditText) findViewById(R.id.actionBarEdit); 
    	taskname = et.getText().toString();
		if(!taskname.isEmpty()) { //check if taskname has some value
		//Log.v("adfdf", "taskname :"+taskname);
		//Log.v("flow :"," calling insertIntoDb with parameter taskname");
		
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter(); // setListAdapter() 
	    Task task = null; // initialize a Task Object. This wil be used in 'task = datasource.createTask(taskname)'
	    task = datasource.createTask(taskname); //method 'createTask()' returns the newly created 'Task' Object. Assign it to 'task' 
	    adapter1.add(task); //add task to adapter
	    adapter1.notifyDataSetChanged(); //update Listview 
		} 
	}
		public void insertToDbFoot(View v) {
			Log.w(Habitator.class.getName(), "into event callback");
	    	EditText et = (EditText) findViewById(R.id.name); 
	    	taskname = et.getText().toString();
			if(!taskname.isEmpty()) { //check if taskname has some value
			//Log.v("adfdf", "taskname :"+taskname);
			//Log.v("flow :"," calling insertIntoDb with parameter taskname");
			
			ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter(); // setListAdapter() 
		    Task task = null; // initialize a Task Object. This wil be used in 'task = datasource.createTask(taskname)'
		    task = datasource.createTask(taskname); //method 'createTask()' returns the newly created 'Task' Object. Assign it to 'task' 
		    adapter1.add(task); //add task to adapter
		    adapter1.notifyDataSetChanged(); //update Listview 
			} 
		
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
