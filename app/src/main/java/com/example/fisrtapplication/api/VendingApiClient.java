package com.example.fisrtapplication.api;


import com.example.fisrtapplication.entities.Vending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VendingApiClient {
    @GET("vendings")
    Call<List<Vending>> getVendings();
}
