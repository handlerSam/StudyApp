package com.example.studyapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class FrontService extends Service {
    public static boolean flag1 = false;
    public static int MAXWWORD = 200;
    public FrontService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new ThreadService1().start();
        String id = "Mychannel2";
        SharedPreferences pref = getSharedPreferences("recite",MODE_PRIVATE);
        SharedPreferences pref1 = getSharedPreferences("wordReciteOrder",MODE_PRIVATE);

        int reciteId = pref.getInt("reciteId",0);//保证能获取到，def没有意义
        int[] orderList = new int[MAXWWORD];
        for(int i = 0; i < MAXWWORD; i++){
            orderList[i] = pref1.getInt(String.valueOf(i),i);
        }


        for(int i = (reciteId == MAXWWORD-1)? 0:reciteId+1; i <= MAXWWORD-1; i++){
            //orderList是那个随机序列，大小200
            if(!pref.getString("content"+orderList[i],"").equals("")){//如果该条背诵内容非空
                pref.edit().putInt("reciteId",i).apply();//更新储存文件中的reciteId
                reciteId = i;
                break;
            }
            if(i == MAXWWORD-1){
                i = -1;//返回到第一个
            }
        }
        RemoteViews remoteViews;
        SharedPreferences settingShared = getSharedPreferences("setting",MODE_PRIVATE);
        int now = settingShared.getInt("setting",0);
        switch(now){
            case 0:
                remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout_small);
                break;
            case 1:
                remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout_middle);
                break;
            default:
                remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout_big);
        }
        remoteViews.setTextViewText(R.id.notificationContent,"");
        remoteViews.setTextViewText(R.id.notifcationHint,"hint:"+ pref.getString("hint"+orderList[reciteId],""));

        Intent intent = new Intent(this,FrontService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0, intent,0);
        remoteViews.setOnClickPendingIntent(R.id.button3,pendingIntent);

        Intent intent2 = new Intent(this,Front2Service.class);
        PendingIntent pending2Intent = PendingIntent.getService(this,0, intent2,0);
        remoteViews.setOnClickPendingIntent(R.id.button4,pending2Intent);
        Intent intent3 = new Intent(this,Front3Service.class);
        PendingIntent pending3Intent = PendingIntent.getService(this,0, intent3,0);
        remoteViews.setOnClickPendingIntent(R.id.notificationX,pending3Intent);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(2);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(id,"Mychannel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(Math.random()+"")
                .setContentText("尝试")
                .setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher2)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .setChannelId(id)
                .build();
        manager.notify(1,notification);
        FrontService.flag1 = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.v("Sam","1finished");
        super.onDestroy();
    }

    class ThreadService1 extends Thread{
        public volatile boolean exit = false;
        public void run() {
            Log.v("Sam","thread1on");
            while (!exit){

                if(FrontService.flag1){
                    Log.v("Sam","thread1finish");
                    stopSelf();
                    exit = true;
                }
            }
        }
    }

}
