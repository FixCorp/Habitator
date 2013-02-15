package com.example.habitator;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/* Launcher Activity class and it extends listactivity to facilitate list view */
@SuppressLint("NewApi")
public class Habitator extends ListActivity {
	private TasksDataSource datasource; //see TaskDataSource.java; it defines basic functions for creating and retrieving data objects, in this case 'tasks'
	ViewGroup viewGroup;
	public String taskname; // just a global variable for passing data
	ImageView delete;
	ImageView edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		/* this block is to change the font of EditText Element, but it crashes. So its commented out. */
		TextView tv = (TextView) findViewById(R.id.task);  
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSans.ttf");  
		//tv.setTypeface(tf);   
		
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

		
	    ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, R.layout.task, R.id.task, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter); //I don't exactly know how ArrayAdapter is used with List variable :(
		int oldcount=adapter.getCount();
		Log.w("old count :", ""+oldcount);

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
				ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter();
			    Task task = (Task) parent.getAdapter().getItem(position);
			    Toast.makeText(getBaseContext(), task.getId() + "", Toast.LENGTH_LONG).show();
			    long itemid = task.getId();
			    datasource.deleteTask(itemid);
				//I don't exactly know how ArrayAdapter is used with List variable :(
			    adapter1.remove(task);
			    adapter1.notifyDataSetChanged();
			}
			
		});
		  

		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
		    public boolean onItemLongClick(AdapterView<?> p, View view, int pos, long id) {

		        //The id of the row in the database
		        long variableThatIWantToPassToCallback = id; 
		        return true;
		    }
		});

	
	
		//ImageView delete = /* However you were getting the current row's delete button */;
	
		
		/*	delete.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {

		        // You can now get your tag value here
		        Object ID = v.getTag();
		        datasource.deleteTask((Task) ID);
		    }
		});*/
		
		
	/*	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		Task holder = null;
	    View row = convertView;
	    row.setTag(holder);
	    
	//    final TextView label = (TextView)row.findViewById(R.id.task);
	    final ImageView delete = (ImageView) row.findViewById(R.id.delete);

	  //  ViewWorkEntryBean mrb = Task.elementAt(position);

	    // set tag here
	    delete.setTag(Task);
	    return row;
	}
*/
		/*
		if(taskname.isEmpty()) { //check if taskname has some value
		//Log.v("adfdf", "taskname :"+taskname);
		//Log.v("flow :"," calling insertIntoDb with parameter taskname");
		

		
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
	
	
	Menu menuGlobal;
	MenuItem itemGlobal;

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        //inflater.inflate(R.menu.context_menu, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
//	            case R.id.menu_share:
//	                shareCurrentItem();
//	                mode.finish(); // Action picked, so close the CAB
//	                return true;
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	// mActionMode = null;
	    }
	};
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_habitator, menu); // layout for action bar menu items. see /res/menu/activity_habitator.xml
	    menuGlobal = menu;
		MenuItem menuItem = menu.findItem(R.id.addTaskMenuButton); // Get the 'addTaskMenuBarButton' in 'menuItem'
	   itemGlobal = menuItem;
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
		
		MenuItem deleteMenuItem = menu.findItem(R.id.deleteTaskMenuButton);

	    return true; // Return true to Optionsmenuoncreate
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.deleteTaskMenuButton:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, DeleteActivity.class);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
	    et.setText("");
		} 
	    itemGlobal.collapseActionView();
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
