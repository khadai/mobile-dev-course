package com.example.fisrtapplication.activities;

import android.os.Bundle;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.fragments.DataListFragment;

import androidx.appcompat.app.AppCompatActivity;

public class DataListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DataListFragment())
                .commit();
    }
}


