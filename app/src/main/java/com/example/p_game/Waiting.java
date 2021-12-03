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
        this.gameId = randomId();
        twGameId = findViewById(R.id.gameID);
        twGameId.setText(this.gameId.toUpperCase());
        this.db = new DatabaseConn().getConnection();
        gameIdToDb();

    }

    private void gameIdToDb(){
        Intent i = getIntent();
        DatabaseReference dr = this.db.getReference("games");
        db.getReference().child("games").child(this.gameId).child(i.getStringExtra("userName")).setValue(1);
        //dr.child("games").setValue(this.gameId);

    }

    private String randomId(){
        String randomId = "";
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            randomId += c;
        }
        Log.d(randomId, "randomId: ");
        return randomId.toUpperCase();
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}