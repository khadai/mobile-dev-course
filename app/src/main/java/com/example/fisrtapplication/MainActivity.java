package com.example.fisrtapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signOut = findViewById(R.id.sign_out_button);
        TextView welcomeText = findViewById(R.id.welcome_text);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        String welcomeTextFormat = getString(R.string.welcome_name);
        assert mUser != null;
        welcomeText.setText(String.format(welcomeTextFormat, mUser.getDisplayName()));

        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        });

    }
}
