package com.example.fisrtapplication.utils;

import android.app.Application;

import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationEx extends Application {

    private FirebaseAuth mAuth;
    private VendingApiClient vendingApiClient;
    private List<Vending> listOfVendings;

    @Override
    public void onCreate() {
        super.onCreate();

//        loadVendings();
        mAuth = FirebaseAuth.getInstance();
        vendingApiClient = createVendingApiClient();
    }

    public List<Vending> getListOfVendings() {
        return listOfVendings;
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

//
//    public void loadVendings() {
////        progressBar.setVisibility(View.VISIBLE);
////        final VendingApiClient apiService = getApplicationEx().getVendingApiClient();
//        final Call<List<Vending>> call = vendingApiClient.getVendings();
//
//        call.enqueue(new Callback<List<Vending>>() {
//            @Override
//            public void onResponse(final Call<List<Vending>> call,
//                                   final Response<List<Vending>> response) {
//                listOfVendings = response.body();
////                vendingsAdapter = new VendingsAdapter(content.getContext(), responseList);
////                recyclerView.setAdapter(vendingsAdapter);
////                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<List<Vending>> call, Throwable t) {
////                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }
}
