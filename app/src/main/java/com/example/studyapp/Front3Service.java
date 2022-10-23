package com.example.studyapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Front3Service extends Service {
    public static boolean flag3 = false;
    public Front3Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new ThreadService3().start();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
        flag3 = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ThreadService3 extends Thread{
        public volatile boolean exit = false;
        public void run() {
            Log.v("Sam","thread1on");
            while (!exit){
                if(Front3Service.flag3){
                    Log.v("Sam","thread1finish");
                    stopSelf();
                    exit = true;
                }
            }
        }
    }
}
