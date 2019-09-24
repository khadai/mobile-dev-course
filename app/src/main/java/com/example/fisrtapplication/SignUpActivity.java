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
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText phone;
    EditText name;
    Button signUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        signUp = findViewById(R.id.sign_up_button);
        phone = findViewById(R.id.phone_sign_up);
        name = findViewById(R.id.name_sign_up);

        mAuth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(v -> {
                    String emailString = email.getText().toString();
                    String passwordString = password.getText().toString();
                    String nameString = name.getText().toString();
                    String phoneString = phone.getText().toString();

                    signUp(emailString, nameString, phoneString, passwordString);
                }
        );


        findViewById(R.id.sign_in_link).setOnClickListener(v -> {
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
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString()).build();
        if (user != null) {
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                        }
                    });
        }
        Toast.makeText(SignUpActivity.this, "Reg success",
                Toast.LENGTH_LONG).show();
        email.setText("");
        password.setText("");
        phone.setText("");
        name.setText("");
    }

    private void onSignUpFailed(Task<AuthResult> task) {
        Toast.makeText(SignUpActivity.this,
                Objects.requireNonNull(task.getException()).getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private boolean validateEmail(final String mEmail) {
        if (mEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            email.setError("Enter a valid email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateName(final String mName) {
        if (!mName.matches("^[A-Za-z]+$")) {
            name.setError("Enter a valid name");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    private boolean validatePhone(final String mPhone) {
        if (mPhone.isEmpty() || !android.util.Patterns.PHONE.matcher(mPhone).matches()) {
            phone.setError("Enter a valid phone");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    private boolean validatePassword(final String mPassword) {
        if (mPassword.isEmpty() || mPassword.length() < 8) {
            password.setError("Enter password larger than 8 characters");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

}
