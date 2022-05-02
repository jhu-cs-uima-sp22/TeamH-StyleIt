package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText regName, regPass, regEmail, regPassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        regName = (EditText)findViewById(R.id.registerName);
        regPass = (EditText)findViewById(R.id.registerPass);
        regPassConfirm = (EditText)findViewById(R.id.registerPassConfirm);
        regEmail = (EditText)findViewById(R.id.registerEmail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void buttonClicked(View v) {
        submitForm();
    }

    private void submitForm() {
        String name = regName.getText().toString().trim();
        String password = regPass.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String passwordConfirm = regPassConfirm.getText().toString().trim();

        if(name.isEmpty()) {
            regName.setError("Name is required");
            regName.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            regPass.setError("Password is required");
            regPass.requestFocus();
            return;
        }
        if(password.length() < 6) {
            regPass.setError("Minimum Password length is 6 characters");
            regPass.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            regEmail.setError("Email is required");
            regEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regEmail.setError("Valid email is required");
            regEmail.requestFocus();
            return;
        }
        if(passwordConfirm.compareTo(password) != 0) {
            regPass.setError("Passwords do not match");
            regPassConfirm.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(name, password, email);
                            DAOUser userDao = new DAOUser();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Profile created!", Toast.LENGTH_SHORT).show();
                                        user.setDbUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        HashMap<String, Object> hashmap = new HashMap<>();
                                        hashmap.put("dbUid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        userDao.update(FirebaseAuth.getInstance().getCurrentUser().getUid(), hashmap);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(Register.this, "Profile NOT created!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Profile NOT created!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}