package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Topic extends AppCompatActivity {

    private FirebaseDatabase db;
    TextView topics;
    EditText newTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        topics = findViewById(R.id.topics);
        newTopic = findViewById(R.id.newTopic);
        this.db = new DatabaseConn().getConnection();
        initValues();
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void initValues(){
        this.db.getReference().child("topics")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        topics.setText("");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String data = snapshot.getKey().toString().toLowerCase();
                            topics.append(data + "\n");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void addTopic(View v){
        String newTopicString = newTopic.getText().toString().toLowerCase();
        this.db.getReference().child("topics").child(newTopicString).setValue("1");
    }
}