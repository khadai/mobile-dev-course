package com.example.fisrtapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.adapters.VendingsAdapter;
import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;
import com.example.fisrtapplication.utils.ApplicationEx;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VendingsAdapter vendingsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        loadMovies();
        swipeToRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mAuth.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        return true;
    }

    private void swipeToRefresh() {
        refreshLayout.setOnRefreshListener(() -> {
            loadMovies();
            new Handler().postDelayed(() -> refreshLayout.setRefreshing(false), 4000);
        });
    }

    private void setupViews() {
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.swipe_refresher);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = getApplicationEx().getAuth();
    }

    private void loadMovies() {
        final VendingApiClient apiService = getApplicationEx().getVendingApiClient();
        final Call<List<Vending>> call = apiService.getVendings();

        call.enqueue(new Callback<List<Vending>>() {
            @Override
            public void onResponse(final Call<List<Vending>> call,
                                   final Response<List<Vending>> response) {
                vendingsAdapter = new VendingsAdapter(response.body());
                recyclerView.setAdapter(vendingsAdapter);
            }

            @Override
            public void onFailure(Call<List<Vending>> call, Throwable t) {
            }
        });

    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }
}
