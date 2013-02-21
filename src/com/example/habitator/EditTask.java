package com.example.habitator;

import java.util.Calendar;
import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class EditTask extends ListActivity   {
	private TasksDataSource datasource;
	public String taskname;	
	private boolean isErase = true;
	TextView taskName;
	TextView myTV;
	ViewSwitcher switcher;
    EditText editText, editText1;
    LinearLayout layout, layout1;
    int flag=0,stat=1;
    ActionMode mActionMode;
    Activity activity=this;
    long iid;
    View v;
View vl;    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		datasource = new TasksDataSource(this); //here
		datasource.open(); //method to connect to sqlite
		//switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
	    //switcher.showNext(); //or switcher.showPrevious();
	    //myTV = (TextView) switcher.findViewById(R.id.task);
		List<Task> values = datasource.getAllTasks(); // A List Variable of Type 'Task' is initiated to getAllTasks in a variable 'values'; Task is the object we defined in Task.java
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, R.layout.task,R.id.task, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter);
		layout = (LinearLayout) findViewById(R.id.layout);
	/*public void onClick(View v)
	{
		taskName=(TextView) findViewById(R.id.task);
		layout = (LinearLayout) findViewById(R.id.layout);
		layout.setVisibility(View.INVISIBLE);
		editText = (EditText) findViewById(R.id.task1);
		editText.setVisibility(View.VISIBLE);
        editText.setText(taskName.getText().toString());
  	}
	*/
	
		final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		    // Called when the action mode is created; startActionMode() was called
		    @Override
		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		        // Inflate a menu resource providing context menu items
		    	mode.setTitle("Edit");
		    	mode.setSubtitle("Touch to edit");
		        MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.editmenu, menu);
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
		        
		        case R.id.cancel:
		        	resetListView();
		        	mode.finish();
		        default:
		                return false;
		        }
		    }
		    

		    // Called when the user exits the action mode
		    @Override
		    public void onDestroyActionMode(ActionMode mode) {

		    	mActionMode = null;

		    }

		/*	@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				return false;
			}*/
		};
		
	final ListView lv= getListView();
	lv.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			if(flag==0)
    		    {
    			 flag=1;
    			 stat++;	
    	         ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter();
    	         Task task = (Task) parent.getAdapter().getItem(position);
    	         Toast.makeText(getBaseContext(), task.getId() + "", Toast.LENGTH_LONG).show();
    	         long itemid = task.getId();
    	         iid=itemid;
    	         Log.w("EventListner", ""+lv.getFirstVisiblePosition());
    	         //View v = lv.getChildAt(position - lv.getFirstVisiblePosition());
    	         Log.w("ClickEventListener", ""+view);
                 taskName=(TextView) view.findViewById(R.id.task);
	             layout = (LinearLayout) view.findViewById(R.id.layout);
	    		 layout.setVisibility(View.INVISIBLE);
	    		 editText = (EditText) view.findViewById(R.id.task1);
	    		 editText.setVisibility(View.VISIBLE);
	             editText.setText(taskName.getText().toString());
	             //datasource.updateTask(itemid);
	             //insertToDbfromEdit(v);
	             adapter1.notifyDataSetChanged(); 
		         //Start the CAB using the ActionMode.Callback defined above
	             v = view;
		         mActionMode= lv.startActionMode(mActionModeCallback);
		         
		         //view.setSelected(true);
    		    }
	    		else
	    		{
	    	         Toast.makeText(getBaseContext(), "Click 'Done", Toast.LENGTH_LONG).show();
	    		}  
    			//flag=0;
	        }
		});
	// editText.setVisibility(View.INVISIBLE);
	// layout.setVisibility(View.VISIBLE);

	}
	/*
	final ListView lv1= getListView();
	lv1.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
        	if(flag>=1)
        	{
           	ArrayAdapter<Task> adapter2 = (ArrayAdapter<Task>) getListAdapter();
        	Task task1 = (Task) parent.getAdapter().getItem(position);
            Toast.makeText(getBaseContext(), task1.getId() + "", Toast.LENGTH_LONG).show();
            long itemid1 = task1.getId();          
        	//Task currTask = (Task)parent.getItemAtPosition(position);
            //long curr_id = currTask.getId();          
            //taskName=(TextView) findViewById(R.id.task);
            //Toast.makeText(getBaseContext(), "" +taskName, Toast.LENGTH_SHORT).show();
            View v1 = lv1.getChildAt(position - lv1.getFirstVisiblePosition());
            taskName=(TextView) v1.findViewById(R.id.task);
            layout1 = (LinearLayout) v1.findViewById(R.id.layout);
    		layout1.setVisibility(View.INVISIBLE);
    		editText.setVisibility(View.VISIBLE);
    		editText1 = (EditText) v1.findViewById(R.id.task1);
    		editText1.setVisibility(View.VISIBLE);
            editText1.setText(taskName.getText().toString());
            adapter2.notifyDataSetChanged();
        	}
        }
	});	*/	
/*	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      //TextView text = (TextView) v.findViewById(R.id.btitle);
     // CharSequence itemTitle = text.getText();
      //menu.setHeaderTitle(itemTitle);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.editmenu, menu);
      Button cancel= (Button)findViewById(R.id.cancel);
      Button done= (Button)findViewById(R.id.done);
    }*/
	/*		OnItemClickListener edit2 = new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
	            Task currTask = (Task)parent.getItemAtPosition(position);
	            long curr_id = currTask.getId();	            
	            
	            taskName=(TextView) findViewById(R.id.task);
	            //Toast.makeText(getBaseContext(), "" +taskName, Toast.LENGTH_SHORT).show();
	    		layout = (LinearLayout) findViewById(R.id.layout);
	    		layout.setVisibility(View.INVISIBLE);
	    		editText = (EditText) findViewById(R.id.task1);
	    		editText.setVisibility(View.VISIBLE);
	            editText.setText(taskName.getText().toString());
	            Toast.makeText(getBaseContext(),""  +curr_id, Toast.LENGTH_LONG).show();
	           // Toast.makeText(getBaseContext()," " currTask, Toast.LENGTH_LONG).show();
	             //String value = datasource.getTaskByID(curr_id);// = // Query your 'last value'.
	            //editText.setText(value);
	            //Toast.makeText(this, "list clicked", Toast.LENGTH_SHORT).show();
	         //   Intent editNote = new Intent(EditTask.this, Edit2.class);
	         //   editNote.putExtra("CURR_NOTE_ID", curr_id);
	        //    startActivity(editNote);
	        }
	    }; 
		getListView().setOnItemClickListener(edit2);
        Toast.makeText(this, "list clicked", Toast.LENGTH_SHORT).show();
	}*/
	/*
        int noteID = data.getExtras().getInt("NOTE_ID");
        noteTitle = data.getExtras().getString("NOTE_TITLE");
        noteDetails = data.getExtras().getString("NOTE_DETAILS");
        nNewNote = new Note(noteID, noteTitle, noteDetails);
        Toast.makeText(NotepadActivity.this, noteTitle + "\n" + noteDetails, Toast.LENGTH_SHORT).show();
        manager.updateNote(nNewNote);           
      */  
	/*
	public void TextViewClicked() {
        ViewSwitcher ViewSwitcherswitcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext(); //or switcher.showPrevious();
        TextView myTV = (TextView) switcher.findViewById(R.id.clickable_text_view);
        myTV.setText("value");
    }
	*/
	
	Menu menuGlobal;
	MenuItem itemGlobal;
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editdone, menu); // layout for action bar menu items. see /res/menu/activity_habitator.xml
	    menuGlobal = menu;
		MenuItem menuItem = menu.findItem(R.id.name); // Get the 'addTaskMenuBarButton' in 'menuItem'
	   itemGlobal = menuItem;
	   //menu.add(0, 2, 0, "menuItem1").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	/*   menuItem.setOnActionExpandListener(new OnActionExpandListener() { //set an onClick event to 'menuItem'. This is what happens, when clicking the plus button in Action Bar
	       @Override
	        public boolean onMenuItemActionCollapse(MenuItem item) { 
	            // Do something when collapsed
	            return true;  // Return true to collapse action view
	        }

	        @Override
	        public boolean onMenuItemActionExpand(MenuItem item) {
	        	
	        	View vw = item.getActionView(); // get the changed view after click event in 'vw'
	        	//Log.w(Habitator.class.getName(), "Variable: "+vw);
	        	Button donebt = (Button) vw.findViewById(R.id.editDone); // from vw get the EditText element
	        	//Log.w(Habitator.class.getName(), "Variable et :" +et);
	        	donebt.requestFocus(); //set the new keyboard focus to EditText element. We have to do this, because it doesn't get focussed by default
	        
	        	// We also have to get the Tic Button and assign it an onClick Event to store the task entered in editText, to database. Need to do that later  	        	
	        	return true;  // Return true to expand action view
	        }
	    });

	    return true; // Return true to Optionsmenuoncreate
	}
*/
	  
      @Override

      public boolean onCreateOptionsMenu(Menu menu) {
    	  MenuInflater inflater = getMenuInflater();
    	    inflater.inflate(R.menu.editdone, menu);
    	
    	//  getMenuInflater().inflate(R.menu.editdone, menu); // layout for action bar menu items. see /res/menu/activity_habitator.xml
  	  //  menuGlobal = menu;
  		MenuItem menuItem = menu.findItem(R.id.name); // Get the 'addTaskMenuBarButton' in 'menuItem'
  	   itemGlobal = menuItem;
      menu.clear();
      Button cancel= (Button)findViewById(R.id.cancel);
      Button done= (Button)findViewById(R.id.done);
/*      if((stat%2)==0) {
    	  //cancel.getVisibility();
    	  done.setVisibility(Menu.FLAG_PERFORM_NO_CLOSE);
    	  cancel.setVisibility(Menu.FLAG_PERFORM_NO_CLOSE);
      } 
      else {
    	  done.setVisibility(Menu.FLAG_ALWAYS_PERFORM_CLOSE);
    	  cancel.setVisibility(Menu.FLAG_ALWAYS_PERFORM_CLOSE);
     } */
      return super.onPrepareOptionsMenu(menu);
      }
      
    public void resetListView() {
	    List<Task> values = datasource.getAllTasks(); // A List Variable of Type 'Task' is initiated to getAllTasks in a variable 'values'; Task is the object we defined in Task.java
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, R.layout.task,R.id.task, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
    }
  
	public void getSelectedViewAndInsertToDb() {
		Log.w(Habitator.class.getName(), "into event callback");
    	EditText et = (EditText) v.findViewById(R.id.task1); 
    	Log.w("gdhgdgfhgdhdg", ""+et);
    	taskname = et.getText().toString();
    	Log.w("lkjgfgdgfsgsdgf",""+et.getTextSize());
    	
		if(!taskname.isEmpty()) { //check if taskname has some value
		//Log.v("adfdf", "taskname :"+taskname);
		//Log.v("flow :"," calling insertIntoDb with parameter taskname");
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter(); // setListAdapter() 
        Toast.makeText(this,""  +iid, Toast.LENGTH_LONG).show();
        Log.w("",""+iid);
	    //Task task = null; // initialize a Task Object. This wil be used in 'task = datasource.createTask(taskname)'
	    datasource.updateTask(iid,taskname); //method 'createTask()' returns the newly created 'Task' Object. Assign it to 'task' 
	    //adapter1.add(task); //add task to adapter
	    List<Task> values = datasource.getAllTasks(); // A List Variable of Type 'Task' is initiated to getAllTasks in a variable 'values'; Task is the object we defined in Task.java
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, R.layout.task,R.id.task, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter);
	    adapter1.notifyDataSetChanged(); //update Listview 
	   
	    //et.setText("");
		} 
		else {
			Log.w("emptytext", "emptyedittext");
		}
	}
	
	/* public void insertToDb(View v) {
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
	}	*/

	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void onClick1(View arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
