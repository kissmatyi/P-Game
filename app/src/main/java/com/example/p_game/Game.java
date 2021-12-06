package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Game extends AppCompatActivity {
    private String gameId;
    private TextView twGameId;
    private FirebaseDatabase db;
    private boolean isRecording;
    private boolean isPlaying;
    private Model model;
    private String userName;
    private List<String> userNamesList;

    private TextView twPlayer1;
    private TextView twPlayer2;
    private TextView twPlayer3;
    private TextView twPlayer4;

    private ImageView imagePlayer1;
    private ImageView imagePlayer2;
    private ImageView imagePlayer3;
    private ImageView imagePlayer4;

    TextView topicName;
    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initValues();
    }

    private void initValues() {
        Intent i = getIntent();
        this.gameId = i.getStringExtra("gameId");
        this.topic = i.getStringExtra("selectedTopic");
        this.twGameId = findViewById(R.id.gameID);
        this.db = new DatabaseConn().getConnection();
        this.topicName = findViewById(R.id.topicName);
        topicName.setText(topic);
        twGameId.setText(this.gameId);
        this.twPlayer1 = findViewById(R.id.twPlayer1);
        this.twPlayer2 = findViewById(R.id.twPlayer2);
        this.twPlayer3 = findViewById(R.id.twPlayer3);
        this.twPlayer4 = findViewById(R.id.twPlayer4);
        this.imagePlayer1 = findViewById(R.id.imagePlayer1);
        this.imagePlayer2 = findViewById(R.id.imagePlayer2);
        this.imagePlayer3 = findViewById(R.id.imagePlayer3);
        this.imagePlayer4 = findViewById(R.id.imagePlayer4);
        this.userNamesList = new ArrayList();
        this.isRecording = false;
        this.isPlaying = false;
        this.model = new Model();
        this.userName = this.model.readUserNameFromFile(this.getApplicationContext());
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
                        userNamesList.add(snapshot.getKey());
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

    public void btnPlayOnClick(View v){
        ImageButton imgBtn = findViewById(v.getId());
        AudioPlayer player = new AudioPlayer("");
        if(!this.isPlaying){
            playRecording(imgBtn, this);
        }else{
           stopPlaying(imgBtn);
        }
        //TODO
        //Check if the file was uploaded or not
    }

    private void stopPlaying(ImageButton imgBtn) {
        imgBtn.setImageDrawable(getResources().getDrawable(R.drawable.play));
        this.model.stopPlaying();
        this.isPlaying = true;
    }

    private void playRecording(ImageButton imgBtn, Activity activity) {
        imgBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_bw));
        this.model.playRecord(this.gameId,getUserName(imgBtn), activity);
        this.isPlaying = false;
    }

    public void btnRecordOnClick(View v){
        ImageButton imgBtn = findViewById(v.getId());
        if(hasAccessToButton(imgBtn)){
            if(!this.isRecording){
                startRec(imgBtn);
            }else{
                stopRec(imgBtn);
            }
        }else{
            //Not his/her button
        }

    }

    private boolean hasAccessToButton(ImageButton imgBtn){
        return this.userName.equals(getUserName(imgBtn)) && getUserName(imgBtn).length() != 0;
    }

    private String getUserName(ImageButton imgBtn){
        String userName = "";
        int index = 0;
        try{
            index = Integer.parseInt(imgBtn.getTag().toString())-1;
            userName = this.userNamesList.get(index);
        }catch(Exception e){

        }

        return userName;
    }

    private void startRec(ImageButton imgBtn) {
        this.model = new Model();
        imgBtn.setImageDrawable(getResources().getDrawable(R.drawable.voice_record));
        this.model.recordStart(getApplicationContext(), this, this.gameId, getUserName(imgBtn));
        this.isRecording = true;
    }

    private void stopRec(ImageButton imgBtn) {
        imgBtn.setImageDrawable(getResources().getDrawable(R.drawable.voice_record_bw));
        this.model.recordStop();
        this.isRecording = false;
        this.model.uploadSpeech(this.gameId,getUserName(imgBtn), this);
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void toGameOver(View v){
        Intent i = new Intent(this, GameOver.class);
        startActivity(i);
    }
}