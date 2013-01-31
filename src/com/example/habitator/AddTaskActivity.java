package com.example.habitator;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddTaskActivity extends FragmentActivity {

	public String taskname;
	public int time;
	public int date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}

/* helper handler */
	
    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Button button = (Button) findViewById(R.id.timepicker);
            button.setText(msg.getData().getString("btn"));
            time = ((msg.getData().getInt("hourOfDay"))*100)+msg.getData().getInt("minute");
            date = 123455;
        }
    };
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_task, menu);
		return true;
	}


	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(h);
	    newFragment.show(getFragmentManager(), "time picker");
	}

	public void insertToDb(View view){
		Intent intent = new Intent(this, Habitator.class);
		EditText editText = (EditText) findViewById(R.id.name);
		taskname = editText.getText().toString();
		if(taskname.isEmpty()) {
			Log.w(AddTaskActivity.class.getName(), taskname);
		}
		else {
		Log.w(AddTaskActivity.class.getName(), "taskname = "+taskname+"EOF");
		intent.putExtra("taskname", taskname);
		intent.putExtra("time", time);
		startActivity(intent);
		}
	}

	
}

class TimePickerFragment extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {

//constructor for accepting handler
Handler h = new Handler();    
public TimePickerFragment(Handler h2) {
	// TODO Auto-generated constructor stub
		h = h2;
}


	
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current time as the default values for the picker
final Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);

// Create a new instance of TimePickerDialog and return it
return new TimePickerDialog(getActivity(), this, hour, minute,
		DateFormat.is24HourFormat(getActivity()));
}

public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	// Do something with the time chosen by the user
	Message msg = new Message();
	Bundle data = new Bundle();
	data.putString("btn", hourOfDay+":"+minute);
	data.putInt("hourOfDay", hourOfDay);
	data.putInt("minute", minute);
	msg.setData(data);
	h.sendMessage(msg);
	
	
}
}
