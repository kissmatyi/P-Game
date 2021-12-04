package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Menu extends AppCompatActivity {

    private String name;
    private FirebaseDatabase db;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent i = getIntent();
        this.name = i.getStringExtra("userName");
        this.db = new DatabaseConn().getConnection();
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toTopic(View v){
        Intent i = new Intent(this, Topic.class);
        startActivity(i);
    }

    public void toProfile(View v){
        Intent i = new Intent(this, MyProfile.class);
        i.putExtra("userName", this.name);
        startActivity(i);
    }

    public void toConnect(View v){
        Intent i = new Intent(this, ConnectToGame.class);
        i.putExtra("userName",this.name);
        startActivity(i);
    }

    public void toNewGame(View v){
        Intent i = new Intent(this, Waiting.class);
        i.putExtra("userName",this.name);
        gameIdToDb();
        i.putExtra("gameId", this.gameId);
        startActivity(i);
    }

    private void generateGameId(){
        String randomId = "";
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            randomId += c;
        }
        this.gameId = randomId.toUpperCase();
    }

    private void gameIdToDb(){
        generateGameId();
        db.getReference().child("games").child(this.gameId).child(this.name).setValue(1);
    }



}