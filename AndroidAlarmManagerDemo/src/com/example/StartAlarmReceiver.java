package com.example;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartAlarmReceiver extends BroadcastReceiver
{
	int interval = 60*1000*5;
	@Override
	 public void onReceive(Context context, Intent intent)
	{
	   
	   Intent myIntent = new Intent(context, MyReceiver.class);
	   PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
	    
	   AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
	   //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
	   alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
	   
	 }
	
}
