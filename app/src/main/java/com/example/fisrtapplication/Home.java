package com.example.fisrtapplication;

import android.app.Application;
import android.content.Intent;

import com.example.fisrtapplication.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            startActivity(new Intent(Home.this, MainActivity.class));
        }
    }
}
