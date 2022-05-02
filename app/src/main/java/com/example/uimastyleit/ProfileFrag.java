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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        view = inflater.inflate(R.layout.frag_profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
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
        DAOUser userDao = new DAOUser();
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

        //imageProfile = (ImageView) view.findViewById(R.id.profileImage);

        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton2);
        final PopupMenu dropDownMenu = new PopupMenu(this.getContext(), imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add(0, 0, 0, "Take picture");
        menu.add(0, 1, 0, "Choose from gallery");

        dropDownMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 0);
                    return true;
                case 1:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult ( intent, 1 );
                    return true;
            }
            return false;
        });
        imageButton.setOnClickListener(v -> dropDownMenu.show());

        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("ONDATACHANGE");
                userprofile = snapshot.getValue(User.class);
                name.setText(userprofile.getName());
                email.setText(userprofile.getEmail());
                System.out.println(userprofile.getImage());
//                imageProfile.setImageBitmap(userprofile.getImage());
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
        if (requestCode == 0) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageProfile.setImageBitmap(image);
            userprofile.setImage(image);
            System.out.println(userprofile.getImage());
            System.out.println("saved image");


        } else if(requestCode == 1) {

            System.out.println("Printing for gallery");
            try {
                Uri uri = data.getData();
                InputStream inputStream;
                inputStream = this.getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                userprofile.setImage(bitmap);
                imageProfile.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }
}
