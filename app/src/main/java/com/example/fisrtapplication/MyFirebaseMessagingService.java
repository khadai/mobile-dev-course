package com.example.fisrtapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.fisrtapplication.activities.ItemDetailsActivity;
import com.example.fisrtapplication.entities.Vending;
import com.example.fisrtapplication.fragments.DataListFragment;
import com.example.fisrtapplication.utils.ApplicationEx;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingService";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageReceived: \n" +
                        "Extra Information: " + jsonMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);

            sendNotification(title, message, click_action);
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title, String messageBody, String click_action) {


        Intent intent = new Intent(this, ItemDetailsActivity.class);
        int vendingIndex = Integer.parseInt(click_action);

        DataListFragment fragment = new DataListFragment();
        List<Vending> list = fragment.getResponseList();

        intent.putExtra("vending_name", list.get(vendingIndex).getName());
        intent.putExtra("vending_company", list.get(vendingIndex).getCompany());
        intent.putExtra("vending_goods", list.get(vendingIndex).getGood());
        intent.putExtra("vending_address", list.get(vendingIndex).getAddress());
        intent.putExtra("vending_img_url", list.get(vendingIndex).getPicture());


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }

//    public void openItemDetails(int position) {
//        Log.d("FROM", vendings.toString());
//
//        Intent intent = new Intent(mContext, ItemDetailsActivity.class);
//        intent.putExtra("vending_name", vendings.get(position).getName());
//        intent.putExtra("vending_company", vendings.get(position).getCompany());
//        intent.putExtra("vending_goods", vendings.get(position).getGood());
//        intent.putExtra("vending_address", vendings.get(position).getAddress());
//        intent.putExtra("vending_img_url", vendings.get(position).getPicture());
//
//        mContext.startActivity(intent);
//    }

}
