package com.example.fisrtapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    private Button userSignIn;
    private Button linkSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email_sign_in);
        password = findViewById(R.id.password_sign_in);
        userSignIn = findViewById(R.id.sign_in_button);
        linkSignUp = findViewById(R.id.sign_up_link);

        mAuth = FirebaseAuth.getInstance();

        userSignIn.setOnClickListener(v -> {
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();

            signIn(emailString, passwordString);
        });

        linkSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    private void signIn(final String email, final String password) {
        if (!validateEmail(email) || !validatePassword(password))
            return;

        mAuth.signInWithEmailAndPassword(email,
                password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSignInSuccess();
            } else {
                onSignInFailed(task);
            }
        });
    }

    private void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void onSignInFailed(Task<AuthResult> task) {
        Toast.makeText(SignInActivity.this,
                Objects.requireNonNull(task.getException()).getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private boolean validateEmail(final String mEmail) {
        if (mEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            String emailError = getString(R.string.email_error);
            email.setError(emailError);
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword(final String mPassword) {
        if (mPassword.isEmpty() || mPassword.length() < 8) {
            String passwordError = getString(R.string.password_error);
            password.setError(passwordError);
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

}
