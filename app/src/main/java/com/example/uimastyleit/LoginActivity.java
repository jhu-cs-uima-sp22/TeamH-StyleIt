package com.example.uimastyleit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText name = findViewById(R.id.loginName);
        EditText password = findViewById(R.id.loginPassword);
        Button btn = findViewById(R.id.loginBtn);
        DAOUser dao  = new DAOUser();

        btn.setOnClickListener(v->
        {
            User user = new User(name.getText().toString(), password.getText().toString());
//            dao.add(user).addOnSuccessListener(suc->
//            {
//                Toast.makeText(this, "record is inserted", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er->
//            {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            });
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put("name", name.getText().toString());
            hashmap.put("password", password.getText().toString());
            dao.update("-N-6DVAE83tOZco_urX3", hashmap).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "record is inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            });
        });
    }

    // TODO: add shared preferences?
    public void registerPage(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void launchMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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


}
