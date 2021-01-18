package com.example.myapplication.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.databuff.Repository;
import com.example.myapplication.databuff.WaitlistBuffer;
import com.example.myapplication.model.Waitlist;

import okhttp3.FormBody;
import okhttp3.Request;

public class WaitlistService extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private final String id = Waitlist.getInstance().getCategory() + Waitlist.getInstance().getId();
    private final String CHANNEL_ID = "CHANNEL_WAIT_LIST";

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("NewWaitlistQueue",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;
        serviceHandler.sendMessage(message);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("message", R.string.channel_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Your wait number is: " + id)
                .setContentText("Tracking the waiting status..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Notification notification = builder.build();
        startForeground(99, notification);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            try {
                while(Waitlist.getInstance().getRank() > 0) {
                    if(Waitlist.getInstance().getId() < 0) break;
                    int rank = Waitlist.getInstance().getRank();
                    Thread.sleep(10000 + rank * rank * 1000);
                    checkWaitState();
                }
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if(Waitlist.getInstance().getRank() == 0) {
                NotificationCompat.Builder builder = new NotificationCompat
                        .Builder(getApplicationContext(), CHANNEL_ID);
                Intent nfIntent = new Intent(getApplication(), MainActivity.class);
                nfIntent.putExtra("message", R.string.channel_id);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), 0,
                                nfIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent)
                        .setContentTitle(id + ", it's your turn now!")
                        .setContentText("Congratulations, We are ready to serve you!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND |
                                     Notification.DEFAULT_VIBRATE |
                                     Notification.DEFAULT_LIGHTS)
                        .setWhen(System.currentTimeMillis());
                Notification notification = builder.build();
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(100, notification);
            }
            stopSelf(message.arg1);
        }

        private void checkWaitState() {
            int waitId = Waitlist.getInstance().getId();
            String waitCategory = Waitlist.getInstance().getCategory();
            FormBody body = new FormBody.Builder()
                    .add("waitId", String.valueOf(waitId))
                    .add("waitCategory", waitCategory)
                    .build();
            final Request request = new Request.Builder()
                    .url(NetworkUtils.SERVER_URL + "/mobile_waitstate/")
                    .post(body)
                    .build();
            NetworkUtils.getResponse(request, WaitlistBuffer.class, Repository.getInstance(null));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
