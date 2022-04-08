package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText regName, regPass, regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        regName = (EditText)findViewById(R.id.registerName);
        regPass = (EditText)findViewById(R.id.registerPass);
        regEmail = (EditText)findViewById(R.id.registerEmail);
    }

    public void buttonClicked(View v) {
        submitForm();
    }

    private void submitForm() {
        String name = regName.getText().toString().trim();
        String password = regPass.getText().toString().trim();
        String email = regEmail.getText().toString().trim();

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

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(name, password, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isComplete()) {
                                        Toast.makeText(Register.this, "Profile created!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "Profile NOT created!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Profile NOT created!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}