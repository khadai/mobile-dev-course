package com.example.fisrtapplication.fragments;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.activities.SignInActivity;
import com.example.fisrtapplication.adapters.VendingsAdapter;
import com.example.fisrtapplication.api.VendingApiClient;
import com.example.fisrtapplication.entities.Vending;
import com.example.fisrtapplication.utils.ApplicationEx;
import com.example.fisrtapplication.utils.ConnectionChangeReceiver;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataListFragment extends Fragment {
    private RecyclerView recyclerView;
    private VendingsAdapter vendingsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private View content;
    private View rootView;

    public DataListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);

        setupViews();
        checkConnection();
        loadMovies();
        setSwipeToRefresh();
        return rootView;
    }

    private void checkConnection() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        ConnectionChangeReceiver receiver = new ConnectionChangeReceiver(content);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        return true;
    }

    private void setSwipeToRefresh() {
        refreshLayout.setOnRefreshListener(() -> {
            loadMovies();
            new Handler().postDelayed(() -> refreshLayout.setRefreshing(false), 0);
        });
    }

    private void setupViews() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        refreshLayout = rootView.findViewById(R.id.swipe_refresher);
        progressBar = rootView.findViewById(R.id.progress_bar_main);
        content = rootView.findViewById(R.id.content_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAuth = getApplicationEx().getAuth();
    }

    private void loadMovies() {
        progressBar.setVisibility(View.VISIBLE);
        final VendingApiClient apiService = getApplicationEx().getVendingApiClient();
        final Call<List<Vending>> call = apiService.getVendings();

        call.enqueue(new Callback<List<Vending>>() {
            @Override
            public void onResponse(final Call<List<Vending>> call,
                                   final Response<List<Vending>> response) {
                vendingsAdapter = new VendingsAdapter(content.getContext(), response.body());
                recyclerView.setAdapter(vendingsAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Vending>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) Objects.requireNonNull(getActivity()).getApplication());
    }

}
