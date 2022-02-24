package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    String path;
    String fileName;
    private FirebaseDatabase db;
    private int point;
    private int points = 0;
    private int games = 0;

    TextView userName;
    ImageView userAvatar;
    TextView userPoints;
    TextView userGamesPlayed;
    TextView userAchievements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initValues();
    }

    private void initValues(){
        this.db = new DatabaseConn().getConnection();
        userName = findViewById(R.id.userName);
        userAvatar = findViewById(R.id.userAvatar);
        userPoints = findViewById(R.id.userPoints);
        userGamesPlayed = findViewById(R.id.userGamesPlayed);
        userAchievements = findViewById(R.id.userAchievements);
        Intent i = getIntent();
        String name = i.getStringExtra("userName");
        userName.setText(name);
        initPoints();
        initgames();
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void initPoints(){
            this.db.getReference().child("games").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot ss : snapshot.getChildren()) {
                            if (ss.getKey().equals(userName.getText().toString())) {
                                point = Integer.valueOf(ss.getValue().toString());
                                points += point;
                            }
                            Log.d(String.valueOf(points), "points: ");
                            userPoints.setText(String.valueOf(points));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Doesn't work", "onCancelled: ");
                }
            });
    }

    public void initgames(){
        this.db.getReference().child("games").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot ss : snapshot.getChildren()) {
                        if (ss.getKey().equals(userName.getText().toString())) {
                            games++;
                        }
                        Log.d(String.valueOf(points), "points: ");
                        userGamesPlayed.setText(String.valueOf(games));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Doesn't work", "onCancelled: ");
            }
        });
    }

    public void UploadPic(View v){
        /*Intent i = getIntent();
        String name = i.getStringExtra("userName");
        fileName = name + "_avatar";
        this.path = this.getExternalFilesDir("/").getAbsolutePath();
        .setOutputFile(path + "/" + fileName);
        Log.d(path, "UploadPic: ");*/
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivity(intent);
    }
}