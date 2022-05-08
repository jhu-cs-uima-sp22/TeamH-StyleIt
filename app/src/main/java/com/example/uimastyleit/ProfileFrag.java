package com.example.uimastyleit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileFrag extends Fragment {
    private MainActivity myact;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    private View view;
    private User userprofile;
    private ImageView imageProfile;
    private FirebaseAuth mAuth;
    private DAOUser userDao;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.frag_profile, container, false);
        user = mAuth.getCurrentUser();
        imageProfile = (ImageView) view.findViewById(R.id.profileImage);
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageProfile);
            }
        });


        TextView name = view.findViewById(R.id.profileName);
        TextView email = view.findViewById(R.id.profileEmail);
        Button changePass = view.findViewById(R.id.userPassword);
        Button confirm = view.findViewById(R.id.confirmChange);
        EditText newPass = view.findViewById(R.id.newPassword);
        EditText newPassConf = view.findViewById(R.id.newPasswordConfirm);


        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPass.setVisibility(View.VISIBLE);
                newPassConf.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
            }
        });
        userDao = new DAOUser();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPass.getText().toString().equals(newPassConf.getText().toString())) {
                    user.updatePassword(newPass.getText().toString());
                    HashMap<String, Object> hashmap = new HashMap<>();
                    hashmap.put("password", newPass.getText().toString());
                    userDao.update(FirebaseAuth.getInstance().getCurrentUser().getUid(), hashmap);
                    Toast.makeText(getActivity(), "Password Changed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    newPass.setError("Passwords do not match");
                    newPass.requestFocus();
                }
            }
        });
        Button logOut = view.findViewById(R.id.logout);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton2);
        final PopupMenu dropDownMenu = new PopupMenu(this.getContext(), imageButton);
        final Menu menu = dropDownMenu.getMenu();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        menu.add(0, 0, 0, "Choose from gallery");

        dropDownMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult ( intent, 1);
                    return true;
            }
            return false;
        });
        imageButton.setOnClickListener(v -> dropDownMenu.show());

        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userprofile = snapshot.getValue(User.class);
                name.setText(userprofile.getName());
                email.setText(userprofile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "User error!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(userprofile.getName());
        if(requestCode == 1) {
            Uri uri = data.getData();
            uploadImagetoFirebase(uri);
        }
    }

    private void uploadImagetoFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("users/"+userID+"/profile.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageProfile);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
