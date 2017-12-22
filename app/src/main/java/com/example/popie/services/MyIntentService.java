package com.example.popie.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {


    public static volatile boolean b;
    float limit;
    int progrees;
    private String Tag = "MTAG";


    public MyIntentService() {
        super("My Thread");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        Log.d(Tag, "Service Started");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String l = intent.getStringExtra("limit");
        limit = Integer.parseInt(l);

        for (float i = 0; i < limit; i++) {
            if (b) {
                try {
                    Thread.sleep(1000);
                    Log.d(Tag, "Service : " + i);
                    progrees = Math.round(i / limit * 100);
                    String msg = "" + progrees;
                    EventBus.getDefault().post(new Event(msg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                stopSelf();
            }
        }
    }

    final class Event {
        String msg;

        public Event(String msg) {
            this.msg = msg;
        }
    }

}
