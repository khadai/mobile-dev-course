package com.example.fisrtapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fisrtapplication.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText name;

    private Button linkSignIn;
    private Button userSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupViews();

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance();

        userSignUp.setOnClickListener(v -> {
                    String emailString = email.getText().toString();
                    String passwordString = password.getText().toString();
                    String nameString = name.getText().toString();
                    String phoneString = phone.getText().toString();

                    signUp(emailString, nameString, phoneString, passwordString);
                }
        );

        linkSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

    }

    public void setupViews() {
        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        phone = findViewById(R.id.phone_sign_up);
        name = findViewById(R.id.name_sign_up);
        userSignUp = findViewById(R.id.sign_up_button);
        linkSignIn = findViewById(R.id.sign_in_link);
        progressBar = findViewById(R.id.progress_bar_sign_up);
    }

    private void signUp(final String email, final String name, final String phone,
                        final String password) {
        if (!validateEmail(email) | !validateName(name) | !validatePhone(phone) |
                !validatePassword(password))
            return;
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,
                password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                onSignUpSuccess();
            } else {
                onSignUpFailed(task);
            }
        });
    }

    private void onSignUpSuccess() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mBase.getReference("users").child(user.getUid()).child("phone").setValue(phone.getText().toString());
            mBase.getReference("users").child(user.getUid()).child("name").setValue(name.getText().toString());
            mBase.getReference("users").child(user.getUid()).child("imageURL").setValue("default");
        }
        String regSuccess = getString(R.string.reg_success);
        Toast.makeText(SignUpActivity.this, regSuccess,
                Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, MainActivity.class));

        email.getText().clear();
        password.getText().clear();
        phone.getText().clear();
        name.getText().clear();
    }

    private void onSignUpFailed(Task<AuthResult> task) {
        Toast.makeText(SignUpActivity.this,
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

    private boolean validateName(final String mName) {
        String nameRegex = getString(R.string.name_regex);
        if (!mName.matches(nameRegex)) {
            String nameError = getString(R.string.name_error);
            name.setError(nameError);
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    private boolean validatePhone(final String mPhone) {
        if (mPhone.isEmpty() || !android.util.Patterns.PHONE.matcher(mPhone).matches()) {
            String phoneError = getString(R.string.phone_error);
            phone.setError(phoneError);
            return false;
        } else {
            phone.setError(null);
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
