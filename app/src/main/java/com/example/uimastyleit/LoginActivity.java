package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
        Button btn = findViewById(R.id.loginBtn);
        btn.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


//        DAOUser dao  = new DAOUser();

//        VideoView videoview = (VideoView) findViewById(R.id.videoView);
//        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clothesstockfootage);
//        videoview.setVideoURI(uri);
//        videoview.start();

    }

    // TODO: add shared preferences?
    public void registerPage(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    private void loginUser() {
        String emailText = email.getText().toString().trim();
        String passText = password.getText().toString().trim();

        if(emailText.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Valid email is required");
            email.requestFocus();
            return;
        }
        if(passText.isEmpty()) {
            password.setError("Email is required");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                loginUser();
                break;
        }
    }

    /** Called when the user clicks the login button */
//    public void launchMain(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//
//        name = editText.getText().toString();
//        // permanently store name for future app launches and for nav header
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("name", name);
//        editor.apply();
//
//        startActivity(intent);
//    }


//    public void onResume () {
////        super.onResume();
//////        VideoView videoview = (VideoView) findViewById(R.id.videoView);
////        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clothesstockfootage);
////        videoview.setVideoURI(uri);
////        videoview.start();
//    }



}
