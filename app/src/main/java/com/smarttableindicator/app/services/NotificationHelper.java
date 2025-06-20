package com.smarttableindicator.app.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.smarttableindicator.app.R;
import com.smarttableindicator.app.config.Constants;
import com.smarttableindicator.app.activities.MainActivity;

public class NotificationHelper {

    private static final String TAG = Constants.TAG_NOTIFICATION_HELPER;
    private static final String CHANNEL_NAME = "Table Status Updates";
    private static final String CHANNEL_DESCRIPTION = "Notifications for important table status changes";


    public static void sendTableNotification(Context context, String title, String body, String tableId, int notificationId) {
        Log.d(TAG, "Preparing to send notification for tableId: " + tableId + " with title: " + title);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (tableId != null) {
            intent.putExtra("highlightTableId", tableId);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, Constants.TABLE_STATUS_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM);


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            Log.e(TAG, "NotificationManager is null. Cannot send notification.");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.TABLE_STATUS_CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH); // High importance for sound & heads-up
            channel.setDescription(CHANNEL_DESCRIPTION);
            // Optional: Configure light, vibration (channel.enableLights(true); channel.setLightColor(Color.RED); etc.)
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Notification channel created/ensured: " + Constants.TABLE_STATUS_CHANNEL_ID);
        }

        notificationManager.notify(notificationId, notificationBuilder.build());
        Log.i(TAG, "Notification sent for tableId: " + tableId + " (Notification ID: " + notificationId + ")");
    }
} 