package com.example.fisrtapplication.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.activities.ItemDetailsActivity;
import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private List<Vending> listOfVendings;
    private String title;
    private String message;
    private String click_action;

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            click_action = remoteMessage.getNotification().getClickAction();

            makeNotification();
        }
    }

    @Override
    public void onDeletedMessages() {
    }

    public void makeNotification() {
        final VendingApiClient apiService = getApplicationEx().getVendingApiClient();
        final Call<List<Vending>> call = apiService.getVendings();

        call.enqueue(new Callback<List<Vending>>() {
            @Override
            public void onResponse(final Call<List<Vending>> call,
                                   final Response<List<Vending>> response) {
                listOfVendings = response.body();

                int vendingIndex = Integer.parseInt(click_action);
                Vending vending = listOfVendings.get(vendingIndex);

                sendNotification(vending);
            }

            @Override
            public void onFailure(Call<List<Vending>> call, Throwable t) {
            }
        });
    }

    private void sendNotification(Vending vending) {
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra("vending_name", vending.getName());
        intent.putExtra("vending_company", vending.getCompany());
        intent.putExtra("vending_goods", vending.getGood());
        intent.putExtra("vending_address", vending.getAddress());
        intent.putExtra("vending_img_url", vending.getPicture());
        intent.putExtra("vending_err_mes", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "l")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }
}
