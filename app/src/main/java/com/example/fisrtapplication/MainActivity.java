package com.example.fisrtapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button signOut;
    TextView welcomeText;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut = findViewById(R.id.sign_out_button);
        welcomeText = findViewById(R.id.welcome_text);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        welcomeText.setText("Welcome, " + mUser.getDisplayName() + "!");

        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        });

    }
}
