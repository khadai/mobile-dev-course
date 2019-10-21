package com.example.fisrtapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.fisrtapplication.ApplicationEx;
import com.example.fisrtapplication.R;
import com.example.fisrtapplication.adapters.VendingsAdapter;
import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;

import java.util.List;

public class DataListActivity extends AppCompatActivity {

    private RecyclerView vendingsListView;
    private VendingsAdapter vendingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vendingsAdapter = new VendingsAdapter();
        vendingsListView.setAdapter(vendingsAdapter);

    }
    
    private void setupViews(){
        vendingsListView = findViewById(R.id.recycler_view);
    }

    private void loadMovies(){
        final VendingApiClient apiClient = getApplicationEx().getVendingApiClient();
        apiClient.getVendings().enqueue(new Callback<List<Vending>>() {
            @Override
            public void onResponse(Call<List<Vending>> call, Response<List<Vending>> response) {
                vendingsAdapter.updateVendings(response.body());
            }

            @Override
            public void onFailure(Call<List<Vending>> call, Throwable t) {

            }
        });
    }

    private ApplicationEx getApplicationEx(){
        return ((ApplicationEx) getApplication());
    }
}
