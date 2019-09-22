package com.example.fisrtapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button userSignIn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email_sign_in);
        password = findViewById(R.id.password_sign_in);
        userSignIn = findViewById(R.id.sign_in_button);


        mAuth = FirebaseAuth.getInstance();

        userSignIn.setOnClickListener(v -> {
            mAuth.signInWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    Toast.makeText(SignInActivity.this,
                            task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });

        findViewById(R.id.sign_up_button).setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });


    }

}
