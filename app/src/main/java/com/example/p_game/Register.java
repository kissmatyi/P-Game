package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText pw;
    EditText pwagain;
    Button regBtn;
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pw = findViewById(R.id.userPass);
        pwagain = findViewById(R.id.userPassAgain);
        userName = findViewById(R.id.userName);
        regBtn = findViewById(R.id.registerBtn);
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toMenu(View v) {
        if(!pw.getText().toString().equals("") && !pwagain.getText().toString().equals("") && !userName.getText().toString().equals("")){
            if (!(pw.getText().toString().equals(pwagain.getText().toString()))) {
                Toast.makeText(this, "The 2 passwords must be the same", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(this, Menu.class);
                // itt kell majd lementeni a belépési adatokat
                startActivity(i);
            }
        }

        else{
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        }
    }
}