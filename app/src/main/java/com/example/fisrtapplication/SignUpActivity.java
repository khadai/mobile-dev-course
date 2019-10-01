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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText name;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        phone = findViewById(R.id.phone_sign_up);
        name = findViewById(R.id.name_sign_up);
        Button signUp = findViewById(R.id.sign_up_button);
        Button linkSignIn = findViewById(R.id.sign_in_link);

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance();


        signUp.setOnClickListener(v -> {
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

    private void signUp(final String email, final String name, final String phone,
                        final String password) {
        if (!validateEmail(email) || !validateName(name) || !validatePhone(phone) ||
                !validatePassword(password))
            return;

        mAuth.createUserWithEmailAndPassword(email,
                password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSignUpSuccess();
            } else {
                onSignUpFailed(task);
            }
        });


    }

    private void onSignUpSuccess() {
        FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
        mBase.getReference("users").child(user.getUid()).child("phone").setValue(phone.getText().toString());
        mBase.getReference("users").child(user.getUid()).child("name").setValue(name.getText().toString());
        mBase.getReference("users").child(user.getUid()).child("email").setValue(email.getText().toString());

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
