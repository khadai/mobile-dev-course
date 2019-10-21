package com.example.fisrtapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.fisrtapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button signOut = findViewById(R.id.sign_out_button);
//        TextView welcomeText = findViewById(R.id.welcome_text);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String pathNameFormat = getString(R.string.name_path);
        assert mUser != null;
        String pathName = String.format(pathNameFormat, mUser.getUid());
        mBase = FirebaseDatabase.getInstance().getReference(pathName);

        mBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String userName = dataSnapshot.getValue(String.class);

                String welcomeTextFormat = getString(R.string.welcome_name);

//                welcomeText.setText(String.format(welcomeTextFormat, userName));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        signOut.setOnClickListener(v -> {
//            mAuth.signOut();
//            Intent intent = new Intent(this, SignInActivity.class);
//            startActivity(intent);
//        });

    }
}
