package com.example;


import java.util.Date;
import java.util.UUID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
                           

public class MyAlarmService extends Service 

{
     private NotificationManager mManager;
     BluetoothAdapter mBluetoothAdapter;
     private Handler mHandler;
     private Handler mHandlerStartScan;
     private boolean mScanning;
		private static final long SCAN_PERIOD = 10000;
		//private static final long WAITING_PERIOD = 1000*60*30;	
		private static final long WAITING_PERIOD = 0;	
		
		
	public boolean waiting;	
	public Date waitingStartDate;
		
     @Override
     public IBinder onBind(Intent arg0)
     {
       // TODO Auto-generated method stub
        return null;
     }

    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }

   @SuppressWarnings("static-access")
   @Override
   public void onStart(Intent intent, int startId)
   {
       super.onStart(intent, startId);
       //intent.
       
       if(waiting){
    	   Date now = new Date();
    	   if(now.getTime()-waitingStartDate.getTime()>(WAITING_PERIOD)){
    		   waiting = false;
    	   }else
    		   return;
       }
     
       BluetoothManager bluetoothManager =(BluetoothManager)this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
   	   mBluetoothAdapter = bluetoothManager.getAdapter();
       
   	   //if(mBluetoothAdapter.isEnabled()==false)
    	 //  return;
   	   
  
   	   
       scanLeDevice(true);
    }

   public void makeNotify(){
	   mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
	   Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);
	
	   Notification notification = new Notification(R.drawable.ic_launcher,"Ibadethaneye hosgeldiniz", System.currentTimeMillis());
	
	   intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

	   PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;
       notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "Ibadethaneye hosgeldiniz!", pendingNotificationIntent);

       mManager.notify(0, notification);
   }
   
   public void runVibration(){
	   // Get instance of Vibrator from current Context
	   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    
	   // Vibrate for 300 milliseconds
	   v.vibrate(2000);
	   AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
	   
	   if(mAudioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE)
		   return;
	
	   mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
       mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

   }
   
   public void waitForLeaving(){
	   waiting = true;
	   waitingStartDate = new Date();
   }
   
    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void scanLeDevice(final boolean enable) {
    	BluetoothManager bluetoothManager =(BluetoothManager)this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
    	mBluetoothAdapter = bluetoothManager.getAdapter();
    	
//    	mBluetoothAdapter.disable();
    	mBluetoothAdapter.enable();
    	
    	mHandler = new Handler();
    	if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;

                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    //distance=0;
                    mBluetoothAdapter.disable();
                }
            }, SCAN_PERIOD);

            
            UUID[] uuidList = new UUID[2];
            UUID uid = UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"); 
            UUID uid2 = UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"); 
            uuidList[0] = uid;  
            uuidList[1] = uid2; 
            //mBluetoothAdapter.startLeScan(uuidList,mLeScanCallback);
          
            mHandlerStartScan = new Handler();
            mHandlerStartScan.postDelayed(new Runnable() {
                @Override
                public void run() {
                	mScanning = true;
                	mBluetoothAdapter.startLeScan(mLeScanCallback);
                }
            }, 5000);
            
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
       
    }
    
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
         		BLEConnectionPreference bleConnectionPreference = new BLEConnectionPreference(device, rssi, scanRecord);
        		if("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0".equals(bleConnectionPreference.getUuid())){
        			makeNotify();
        			runVibration();
        			waitForLeaving();
        			mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
        		}
        		
//            	if (bleConnectionPreference.getMajor()==0){
//            		//back = bleConnectionPreference;
//            		//double estimateDistance  =kalmanFilterUtil.calculateFromBack(bleConnectionPreference.getDistance());
//            		//bleConnectionPreference.setEstimateDistance(estimateDistance);
//            	}else{
//            		//front  =  bleConnectionPreference;
//             		//double estimateDistance  =kalmanFilterUtil.calculateFromFront(bleConnectionPreference.getDistance());
//            		//bleConnectionPreference.setEstimateDistance(estimateDistance);
//            		//distance = bleConnectionPreference.getDistance();
//            		Log.i("AlarmDemo", "Scan Bulundu");
//            	}
            	
            	
            	//addDeviceFOrUI(bleConnectionPreference);
        		
      }
    };
}