package com.example.fisrtapplication.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisrtapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private Button uploadImgButton;
    private ImageView profileImage;
    private Button newEmailButton;
    private Button newNameButton;
    private EditText newEmail;
    private EditText newName;
    private TextView profileName;
    private TextView profileEmail;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private static final int TARGET_WIDTH = 100;
    private static final int TARGET_HEIGHT = 100;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private DatabaseReference urlRef;
    private DatabaseReference nameRef;
    private DatabaseReference emailRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        setupViews(inflate);

        urlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                showProfileImage(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).
                        getString(R.string.failed_load_image), Toast.LENGTH_SHORT).show();
            }
        });

        showEmail();
        showName();

        uploadImgButton.setOnClickListener(view -> openImage());
        newNameButton.setOnClickListener(view -> updateName());
        newEmailButton.setOnClickListener(view -> updateEmail());

        return inflate;
    }

    private void showEmail() {
        String email = fuser.getEmail();
        profileEmail.setText(email);
    }

    private void showName(){
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                profileName.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmail() {
        String email = newEmail.getText().toString();
//        if (validateEmail(email)) {
            fuser.updateEmail(email)
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "User email address updated.",
                                    Toast.LENGTH_SHORT).show();
                            newEmail.getText().clear();
                            showEmail();
                        }
                    });
//        }
    }

    private void updateName() {
        String name = newName.getText().toString();
        if (validateName(name)) {
            reference.child("name").setValue(name);
            Toast.makeText(getContext(), "Name updated", Toast.LENGTH_SHORT).show();
            newName.getText().clear();
            showName();
        }
    }

    private void setupViews(View inflate) {
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        uploadImgButton = inflate.findViewById(R.id.new_image_button);
        newEmailButton = inflate.findViewById(R.id.new_email_button);
        newNameButton = inflate.findViewById(R.id.new_name_button);
        newName = inflate.findViewById(R.id.name_new);
        newEmail = inflate.findViewById(R.id.email_new);
        profileName = inflate.findViewById(R.id.username);
        profileEmail = inflate.findViewById(R.id.user_email);
        profileImage = inflate.findViewById(R.id.image_profile);
        urlRef = reference.child("imageURL");
        nameRef = reference.child("name");
        emailRef = reference.child("email");
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = null;
                    if (downloadUri != null) {
                        mUri = downloadUri.toString();
                    }
                    reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", mUri);
                    reference.updateChildren(map);
                    showProfileImage(mUri);
                    pd.dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    private void showProfileImage(String mUri) {
        Picasso.get()
                .load(mUri)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerCrop()
                .into(profileImage);
    }

    private boolean validateEmail(final String mEmail) {
        if (mEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            String emailError = getString(R.string.email_error);
            newEmail.setError(emailError);
            return false;
        } else {
            newEmail.setError(null);
            return true;
        }
    }

    private boolean validateName(final String mName) {
        String nameRegex = getString(R.string.name_regex);
        if (!mName.matches(nameRegex)) {
            String nameError = getString(R.string.name_error);
            newName.setError(nameError);
            return false;
        } else {
            newName.setError(null);
            return true;
        }
    }
}
