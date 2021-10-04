package com.example.app_lock_jimmy;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;



import java.util.List;

public class MyService extends Service
{
    private Context ctx;
    UsageStatsManager sUsageStatsManager;
    private static final String TAG = "MyService";

//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */


//    public MyService() {
//        super("MyService");
//    }
    //service一開始執行的地方

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            sUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
//        }
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            sUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        }


        createNotificationChannel();

        Intent intent1 = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,0);

        Notification notification = new NotificationCompat.Builder(this,"ChannelId1")
                .setContentTitle("My App tutorial")
                .setContentText("Our application is running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        startForeground(1,notification);



        return START_STICKY;

//        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1","Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        // we have to check if os is oreo or above
//        Log.d("MainActivity","hello");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                   while(true){
                       long endTime = System.currentTimeMillis();
                       long beginTime = endTime -  10000;

                       String result = "";
                       UsageEvents.Event event = new UsageEvents.Event();

                       UsageEvents usageEvents = sUsageStatsManager.queryEvents(beginTime, endTime);
                       while (usageEvents.hasNextEvent()) {

                           Log.d(TAG, String.valueOf(getApplicationContext()));

                           usageEvents.getNextEvent(event);
                           if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                               result = event.getPackageName();
                           }
                       }
                       if (!android.text.TextUtils.isEmpty(result)) {
                            if(result.equals("com.instagram.android")){

                                Log.d("MainActivity","hellooo!!!!!!!!");

//
                                try{
                                    Intent i = new Intent(getApplicationContext(),app_lock_pattern.class);


//                                i.putExtra("lock_package_name", "com.instagram.android");
//                                i.putExtra("lock_from", "lock_from_finish");
//                                Intent i = new Intent(MyService.this, app_lock_pattern.class);

                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
//                                Intent i = new Intent(getApplicationContext(),app_lock_pattern.class);
//
//
////                                i.putExtra("lock_package_name", "com.instagram.android");
////                                i.putExtra("lock_from", "lock_from_finish");
////                                Intent i = new Intent(MyService.this, app_lock_pattern.class);
//
//                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(i);



//                                Intent i = new Intent();
//                                i.setAction(Intent.ACTION_MAIN);
//                                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//                                i.setComponent(new ComponentName(getApplicationContext().getPackageName(), app_lock_pattern.class.getName()));
//                                startActivity(i);





                            }
                            if(result.equals("com.android.chrome"))
                            {
//                                finish();
                                Log.d("MainActivity","hellooo!!!!!!!!");
                                Intent i = new Intent(MyService.this, app_lock_pattern.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);

                            }

                           Log.d("MainActivity","123:"+result);
                       }

                       try {
                           Thread.sleep(1000);
                       } catch (Exception ignore) {
                       }

                   }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}
