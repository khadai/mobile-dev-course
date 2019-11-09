package com.example.fisrtapplication.utils;

import android.app.Application;

import com.example.fisrtapplication.api.VendingApiClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationEx extends Application {

    private FirebaseAuth mAuth;
    private VendingApiClient vendingApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
        vendingApiClient = createVendingApiClient();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public VendingApiClient getVendingApiClient() {
        return vendingApiClient;
    }

    private VendingApiClient createVendingApiClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-fisrt-application.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(VendingApiClient.class);
    }
}
