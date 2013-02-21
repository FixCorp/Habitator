package com.example.habitator;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteActivity extends ListActivity {
	private TasksDataSource datasource; //see TaskDataSource.java; it defines basic functions for creating and retrieving data objects, in this case 'tasks'
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle("Trash");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		/* After setting the layout, the data from the database needs to be retrieved and displayed in the list view */
		/* For that the database needs to be connected and queried, for now TaskDataSource class defines the methods for it. so, datasource object is initiated with that class*/
		datasource = new TasksDataSource(this); //here
		datasource.open(); //method to connect to sqlite	
		List<Task> values = datasource.getAllTasks(); // A List Variable of Type 'Task' is initiated to getAllTasks in a variable 'values'; Task is the object we defined in Task.java
		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, R.layout.simple_list_item_multiple_choice, values); //An ArrayAdapter in initialised with the specified layout to populate the ListView with 'values'
		setListAdapter(adapter); //I don't exactly know how ArrayAdapter is used with List variable :(
		int count = 0;
		ListView lv = getListView();
		initialize(lv);
		
		lv.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

			    //Toast.makeText(getBaseContext(), newcount + "", Toast.LENGTH_LONG).show();
			    //((CheckedTextView) v).toggle();
				//  CheckedTextView textView = (CheckedTextView)v;
				//  textView.toggle();
				ListView lv2 = getListView(); 
				lv2.setItemChecked(position, true);
			}
			
		});

		
		MultiChoiceModeListener multiChoiceModeListener = new MultiChoiceModeListener(){
			 @Override
			    public void onItemCheckedStateChanged(ActionMode mode, int position,
			                                          long id, boolean checked) {
			        // Here you can do something when items are selected/de-selected,
			        // such as update the title in the CAB
				 	if(checked) {
				 		increaseCount();
				 	}
				 	else {
				 		decreaseCount();
				 	}
				 	updateActionModeTitle(mode);
				 
			    }
			 
			 	
			    @Override
			    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			        // Respond to clicks on the actions in the CAB
			        switch (item.getItemId()) {
			            case R.id.menu_delete_button:
			            	getSelectedItemsAndDeleteThem();
			                scount=0;
			            	//deleteSelectedItems();
			                mode.finish(); // Action picked, so close the CAB
			                return true;
			                
			            case R.id.menu_cancel_button:
			            	mode.finish();
			            	scount=0;
			            	return false;
			            	
			            default:
			            	getSelectedItemsAndDeleteThem();
			                scount=0;
			            	return true;
			        }
			    }

			    @Override
			    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			        // Inflate the menu for the CAB
			    	updateActionModeTitle(mode);
			        MenuInflater inflater = mode.getMenuInflater();
			        inflater.inflate(R.menu.context_delete, menu);
			        return true;
			    }

			    @Override
			    public void onDestroyActionMode(ActionMode mode) {
			        // Here you can make any necessary updates to the activity when
			        // the CAB is removed. By default, selected items are deselected/unchecked.
			    	getSelectedItemsAndDeleteThem();
			    	
			    }

			    @Override
			    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			        // Here you can perform updates to the CAB due to
			        // an invalidate() request
			        return false;
			    }
		};
		
		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(multiChoiceModeListener);
	
	
	}
	
	public void deleteSeveralHabits() {
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter();
		Toast.makeText(getBaseContext(), adapter1.getCount() + "", Toast.LENGTH_LONG).show();
	}
	
	public void initialize(ListView lv) {
		for( int i=0; i<lv.getCount(); i++) {
			lv.setItemChecked(i, false);
		}
	}
	int scount=0;
	public void increaseCount() {
		scount++;
	}
	
	public void decreaseCount() {
		scount--;
	}
	
	public void updateActionModeTitle(ActionMode mode) {
		if(scount==1){
			mode.setTitle("delete");
			mode.setSubtitle("only this");
		}
		else {
			mode.setTitle("Delete");
			mode.setSubtitle(scount+ " items");
		}
	}
	
	public void getSelectedItemsAndDeleteThem() {
		Log.w("getSelectedItemsAndDeleteThem","inside func");
		ArrayAdapter<Task> adapter1 = (ArrayAdapter<Task>) getListAdapter();
		ListView lv = getListView();
		//lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		Task task;
		SparseBooleanArray positions = lv.getCheckedItemPositions();
		int len = lv.getCount();
		ArrayList<Long> taskIds = new ArrayList<Long>();
		for(int i=0; i<len; i++) {
			if(positions.get(i)) {
			Log.w("afjndklfnkladjsflkadfj", positions.valueAt(i)+"");
			task = adapter1.getItem(i);
			long id = task.getId();
			taskIds.add(id);
			}
		}
		
		if(!taskIds.isEmpty()){
			long taskIds1[] = convertLong(taskIds);
			datasource.deleteMultipleTasks(taskIds1);
		}

		Intent intent = new Intent(this, Habitator.class);
        startActivity(intent); 
        
	}
	
	public static long[] convertLong(ArrayList<Long> longs)
	{
	    long[] ret = new long[longs.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = longs.get(i).intValue();
	    }
	    return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_delete, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.backActionButton:
	            // app icon in action bar clicked; go home
	        	Intent intent = new Intent(getBaseContext(),Habitator.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}

	@Override
	public void startActivity(Intent intent) {
	    super.startActivity(intent);
	    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}

	@Override
	public void finish() {
	    super.finish();
	    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}


}
