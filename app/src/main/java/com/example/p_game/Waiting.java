package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Waiting extends AppCompatActivity {
    private String gameId = "";
    private FirebaseDatabase db;
    String userName;

    private TextView twGameId;
    private TextView twPlayer1;
    private TextView twPlayer2;
    private TextView twPlayer3;
    private TextView twPlayer4;

    private ImageView imagePlayer1;
    private ImageView imagePlayer2;
    private ImageView imagePlayer3;
    private ImageView imagePlayer4;

    ArrayList<String> topicList = new ArrayList();
    Spinner topicSpinner;
    String selectedItem;
    TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        setAdapter();
        initValues();
    }

    private void initValues(){
        Intent i = getIntent();
        this.gameId = i.getStringExtra("gameId");
        this.userName = i.getStringExtra("userName");
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
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.getReference().child("games").child(this.gameId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                TextView[] textViews = new TextView[]{twPlayer1, twPlayer2, twPlayer3, twPlayer4};
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(index <= 4){
                        textViews[index].setText(snapshot.getKey());
                    }
                    //SET PICTURE
                    index++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void toGame(View v){
        Intent i = new Intent(this, Game.class);
        i.putExtra("gameId", this.gameId);
        i.putExtra("selectedTopic", selectedItem);
        i.putExtra("userName", this.userName);
        startActivity(i);

    }

    private void setAdapter(){
        this.db = new DatabaseConn().getConnection();
        this.db.getReference().child("topics")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String data = snapshot.getKey().toString().toLowerCase();
                            topicList.add(data);
                            topicSpinner = findViewById(R.id.topicOptions);
                            ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, topicList);
                            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                            topicSpinner.setAdapter(adapter);
                            topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    selected = findViewById(R.id.selected);
                                    selectedItem = topicSpinner.getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

}