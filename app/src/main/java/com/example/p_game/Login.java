package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.userName);
        pw = findViewById(R.id.userPass);
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        if(!pw.getText().toString().equals("") && !userName.getText().toString().equals("")){
            // itt kell majd ellenőrizni hogy a beírt adatok benne vannak-e az adatbázisban
            startActivity(i);
        }

        else{
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        }
    }
}