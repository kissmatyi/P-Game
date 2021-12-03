package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent i = getIntent();
        name = i.getStringExtra("userName");
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toProfile(View v){
        Intent i = new Intent(this, MyProfile.class);
        i.putExtra("userName", name);
        startActivity(i);
    }

    public void toConnect(View v){
        Intent i = new Intent(this, ConnectToGame.class);
        startActivity(i);
    }

    public void toNewGame(View v){
        Intent i = new Intent(this, Waiting.class);
        i.putExtra("userName",this.name);
        startActivity(i);
    }
}