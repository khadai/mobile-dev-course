package com.example.fisrtapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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

        signUp.setOnClickListener(v ->
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString()).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){

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
                            }else {
                                Toast.makeText(SignUpActivity.this,
                                        task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        })
        );


        findViewById(R.id.sign_in_link).setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }
}
