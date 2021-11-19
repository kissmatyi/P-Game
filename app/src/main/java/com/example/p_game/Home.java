package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void toLogin(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    public void toRegister(View v){
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }
}