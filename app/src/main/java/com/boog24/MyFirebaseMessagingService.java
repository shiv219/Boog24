package com.boog24;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.boog24.activity.MainActivity;
import com.boog24.activity.NotificationActivity;
import com.boog24.custom.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private NotificationChannel mChannel;
    private NotificationManager notifManager;
    long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};
    Intent intent;
    NotificationUtils notificationUtils;
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);



        // TODO(developer): Handle FCM messages here.
        Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        try {
            handleDataMessage(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        Map<String, String> data = remoteMessage.getData();
        if (null != data && 0 < data.size()) {
            if (data.containsKey("title")) {
                handleDataMessage(data.get("title"),data.get("message"));
            }
        }

        Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


    }

    private void handleDataMessage(String  title,String mesage) {

        try {

            String imageUrl = "";
            String timestamp = String.valueOf(System.currentTimeMillis() / 100);

//            if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            intent.putExtra("notification","yes");
            // if (TextUtils.isEmpty(imageUrl)) {
            showNotificationMessage(getApplicationContext(), title, mesage, timestamp, intent);
              /*  } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), title, mesage, timestamp, intent, imageUrl);
                }*/

//            }
        } catch (Exception e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        Log.e("TAG", "showNotificationMessage: "+title );
        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent );
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {

        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    private void scheduleJob() {
        // [START dispatch_job]
        // [END dispatch_job]
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(String body, String title) {
        Toast.makeText(getApplicationContext(),title,Toast.LENGTH_SHORT).show();

        Log.d("@@@@@@@@@@", body +"/"+title);

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder;
            intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (mChannel == null) {
                mChannel = new NotificationChannel("0", title, importance);
                mChannel.setDescription(body);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, "0");
            intent.putExtra("notification","yes");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.logo) // required
                    .setContentText(body)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource
                            (getResources(), R.drawable.logo))
                    .setContentIntent(pendingIntent)
                    .setChannelId("0")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(vibrate);
            Notification notification = builder.build();
            notifManager.notify(123, notification);
        } else {

            intent = new Intent(this, MainActivity.class);
            intent.putExtra("notification", "yes");
            PendingIntent pendingIntent = null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                    .setSound(defaultSoundUri)
                    .setVibrate(vibrate)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(body));

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(123, notificationBuilder.build());
        }
    }
}
