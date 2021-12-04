package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Waiting extends AppCompatActivity {
    private String gameId;
    private FirebaseDatabase db;

    private TextView twGameId;
    private TextView twPlayer1;
    private TextView twPlayer2;
    private TextView twPlayer3;
    private TextView twPlayer4;

    private ImageView imagePlayer1;
    private ImageView imagePlayer2;
    private ImageView imagePlayer3;
    private ImageView imagePlayer4;

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
        this.twPlayer1 = findViewById(R.id.twPlayer1);
        this.twPlayer2 = findViewById(R.id.twPlayer2);
        this.twPlayer3 = findViewById(R.id.twPlayer3);
        this.twPlayer4 = findViewById(R.id.twPlayer4);
        this.imagePlayer1 = findViewById(R.id.imagePlayer1);
        this.imagePlayer2 = findViewById(R.id.imagePlayer2);
        this.imagePlayer3 = findViewById(R.id.imagePlayer3);
        this.imagePlayer4 = findViewById(R.id.imagePlayer4);
        loadPlayers();
    }

    private void loadPlayers() {

    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }


}