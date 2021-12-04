package com.example.p_game;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ConnectToGame extends AppCompatActivity {

    FirebaseDatabase db;
    EditText gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_game);
        this.db = new DatabaseConn().getConnection();
        gameID = findViewById(R.id.gameID);
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void toGame(View v){
        Intent i = getIntent();
        Intent next = new Intent(this, Waiting.class);
        final String GAMEID = gameID.getText().toString();
        final String USERNAME = i.getStringExtra("userName");
        next.putExtra("userName", USERNAME);
        next.putExtra("gameId", GAMEID);

        db.getReference().child("games").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(GAMEID).exists()){
                    final int USERCOUNT = (int)(dataSnapshot.child(GAMEID).getChildrenCount());
                    db.getReference().child("games").child(GAMEID).child(USERNAME).setValue(USERCOUNT+1);
                    startActivity(next);
                }
                else{
                    Toast.makeText(ConnectToGame.this, "There is no room with this ID", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Doesn't work", "onCancelled: ");
            }
        });
    }
}