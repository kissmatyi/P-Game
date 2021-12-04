package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Waiting extends AppCompatActivity {
    TextView twGameId;
    String gameId;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        initValues();
    }

    private void initValues(){
        Intent i = getIntent();
        this.gameId = i.getStringExtra("gameId");
        this.twGameId = findViewById(R.id.gameID);
        this.twGameId.setText(this.gameId.toUpperCase());
        this.db = new DatabaseConn().getConnection();
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }


}