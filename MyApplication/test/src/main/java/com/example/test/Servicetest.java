package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Servicetest extends Service {
    int num;
    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        num = intent.getIntExtra("num",-1);
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.d("YYYCCC",num+"번쨰 스레드");
                while(true){
                   if(num>10)
                       break;
                }
            }
        };
        thread.run();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
