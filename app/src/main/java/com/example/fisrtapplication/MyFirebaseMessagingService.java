package com.example.fisrtapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.fisrtapplication.activities.ItemDetailsActivity;
import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;
import com.example.fisrtapplication.utils.ApplicationEx;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private List<Vending> listOfVendings;

    private static final String TAG = "FirebaseMessagingService";
    private String title;
    private String message;
    private String click_action;

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        loadVendings();
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
            title = remoteMessage.getNotification().getTitle(); //get title
            message = remoteMessage.getNotification().getBody(); //get message
            click_action = remoteMessage.getNotification().getClickAction(); //get click_action

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);

            loadVendings();
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    public void loadVendings() {
//        progressBar.setVisibility(View.VISIBLE);
        final VendingApiClient apiService = getApplicationEx().getVendingApiClient();
        final Call<List<Vending>> call = apiService.getVendings();
        Log.d(TAG, "API body " + call);


        call.enqueue(new Callback<List<Vending>>() {
            @Override
            public void onResponse(final Call<List<Vending>> call,
                                   final Response<List<Vending>> response) {
                listOfVendings = response.body();
                Log.d(TAG, "Messageee body " + listOfVendings);
                Log.d(TAG, "Messageee el " + listOfVendings.get(1).getName());

                int vendingIndex = Integer.parseInt(click_action);
                Vending vending = listOfVendings.get(vendingIndex);


                sendNotification(title, message, click_action, vending);


//                vendingsAdapter = new VendingsAdapter(content.getContext(), responseList);
//                recyclerView.setAdapter(vendingsAdapter);
//                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Vending>> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void sendNotification(String title, String messageBody, String click_action, Vending vending) {
//        loadVendings(title, message, click_action);
        Log.d(TAG, "Message body " + this.listOfVendings);
//        Log.d(TAG, "Message size " + listOfVendings.size());

//        listOfVendings = getApplicationEx().getListOfVendings();

        Intent intent = new Intent(this, ItemDetailsActivity.class);
//        int vendingIndex = Integer.parseInt(click_action);
//        Vending vending = this.listOfVendings.get(vendingIndex);
        Log.d(TAG, "Vending name " + vending.getName());


//        DataListFragment fragment = new DataListFragment();
//        fragment.loadVendings();
//        List<Vending> list = fragment.getResponseList();



        intent.putExtra("vending_name", vending.getName());
        intent.putExtra("vending_company", vending.getCompany());
        intent.putExtra("vending_goods", vending.getGood());
        intent.putExtra("vending_address", vending.getAddress());
        intent.putExtra("vending_img_url", vending.getPicture());
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
