package com.example;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity 
{

    private PendingIntent pendingIntent;
    //int interval = 60*1000*5;
    int interval = 60*1000;
    
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    

    Calendar calendar = Calendar.getInstance();
    
//    calendar.set(Calendar.MONTH, 6);
//    calendar.set(Calendar.YEAR, 2013);
//    calendar.set(Calendar.DAY_OF_MONTH, 13);
//
//    calendar.set(Calendar.HOUR_OF_DAY, 20);
//    calendar.set(Calendar.MINUTE, 48);
//    calendar.set(Calendar.SECOND, 0);
//    calendar.set(Calendar.AM_PM,Calendar.PM);
    
    calendar.roll(Calendar.SECOND, 30);
    
    
   
    
	//BluetoothManager bluetoothManager =(BluetoothManager)this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
	} //end onCreate
	
public void startAlarm(View v){
	Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
    
    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
}

public void stopAlarm(View v){
	Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
    
    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    alarmManager.cancel(pendingIntent);
}
public void stopAlarm2(View v){
	Intent myIntent = new Intent(getApplicationContext(), MyReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
    
    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    alarmManager.cancel(pendingIntent);
}
	
	
}