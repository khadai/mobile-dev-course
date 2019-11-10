package com.example.fisrtapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fisrtapplication.R;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView email;
    private TextView name;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

        firebase.auth()
                .signInWithEmailAndPassword('you@domain.com', 'correcthorsebatterystaple')
                .then(function(userCredential) {
            userCredential.user.updateEmail('newyou@domain.com')
        })

        // TODo: update display name
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

    public void setNewEmail(String email) {
        ;
    }

    public void setNewName(String name) {
        ;
    }
}
