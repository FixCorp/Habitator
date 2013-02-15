package com.example.habitator;

import java.util.ArrayList;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;


public class TasksDataSource {
  private SQLiteDatabase database;
  private MySqliteHelper dbhelper;
  private String[] allColumns = { MySqliteHelper.COLUMN_ID,
		  MySqliteHelper.COLUMN_TASK, MySqliteHelper.COLUMN_TIME,
		  MySqliteHelper.COLUMN_INITDATE 
  		  };
  
  public TasksDataSource(Context context){
	  dbhelper = new MySqliteHelper(context);
  }
  
  public void open() throws SQLException {
	  database = dbhelper.getWritableDatabase();
  }
  
  public void close(){
	  dbhelper.close();
  }
  
 
  
  public Task createTask(String task) {
	  ContentValues values = new ContentValues();
	  values.put(MySqliteHelper.COLUMN_TASK, task);
	  values.put(MySqliteHelper.COLUMN_TIME, 900);
	  values.put(MySqliteHelper.COLUMN_INITDATE,120913);
	  long rowid = database.insert(MySqliteHelper.TABLE_TASKS, null, values);
	  Cursor cursor = database.query(MySqliteHelper.TABLE_TASKS,
			  allColumns, MySqliteHelper.COLUMN_ID + " = " + rowid, null, null, null, null);
	  cursor.moveToFirst();
	  Task newTask = cursorToTask(cursor);
	  cursor.close();
	  return newTask;
  }
  
  public void deleteTask(long id) {
	  System.out.println("Task deleted with id: "+id);
	  database.delete(MySqliteHelper.TABLE_TASKS, MySqliteHelper.COLUMN_ID + " = " + id, null);
	 
  }
  
  public void deleteMultipleTasks(long[] taskIds) {
	  for (int id=0; id<taskIds.length; id++) {
	  database.delete(MySqliteHelper.TABLE_TASKS, MySqliteHelper.COLUMN_ID + " = " + taskIds[id], null);
	  }
  }
  public List<Task> getAllTasks() {
	  List<Task> tasks = new ArrayList<Task>();
	  Cursor cursor = database.query(MySqliteHelper.TABLE_TASKS, allColumns, null, null, null, null, null);
	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
		  Task task = cursorToTask(cursor);
		  tasks.add(task);
		  cursor.moveToNext();
	  }
	  
	  cursor.close();
	  return tasks;
  }
  
  private Task cursorToTask(Cursor cursor) {
	  Task task1 = new Task();
	  task1.setId(cursor.getLong(0));
	  task1.setTask(cursor.getString(1));
	  task1.setTime(cursor.getInt(2));
	  task1.setDate(cursor.getInt(3));
	  return task1;
	 
  	}

}
