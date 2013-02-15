package com.example.habitator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {


public static final String TABLE_TASKS = "tasks";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TASK = "task";
  public static final String COLUMN_TIME = "time";
  public static final String COLUMN_INITDATE = "initdate";
  
  private static final String DATABASE_NAME = "tasks.db";
  private static final int DATABASE_VERSION = 1;
  
  private static final String DATABASE_CREATE = "create table " + TABLE_TASKS + "("
	+ COLUMN_ID + " integer primary key autoincrement, "+ COLUMN_TASK
	+ " text not null, "+ COLUMN_TIME+" integer not null, "+ COLUMN_INITDATE+" integer not null);";
  
  public MySqliteHelper(Context context) {
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
  

@Override
  public void onCreate(SQLiteDatabase database) {
	  database.execSQL(DATABASE_CREATE);
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  Log.w(MySqliteHelper.class.getName(),
			  "Upgrading database from version" + oldVersion + "to" + newVersion + 
			  ", which will destroy all old data");
	  db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS );
	  onCreate(db);
  }
  
}
