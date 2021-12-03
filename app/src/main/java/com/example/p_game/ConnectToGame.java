package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConnectToGame extends AppCompatActivity {

    FirebaseDatabase db = new DatabaseConn().getConnection();
    EditText gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_game);
        gameID = findViewById(R.id.gameID);
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void toGame(View v){
        Intent i = new Intent(this, Game.class);
        db.getReference().child("games").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(gameID.getText().toString()).exists()){
                    i.putExtra("gameID", gameID.getText().toString());
                    startActivity(i);
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