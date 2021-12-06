package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {

    String path;
    String fileName;

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
        userName = findViewById(R.id.userName);
        userAvatar = findViewById(R.id.userAvatar);
        userPoints = findViewById(R.id.userPoints);
        userGamesPlayed = findViewById(R.id.userGamesPlayed);
        userAchievements = findViewById(R.id.userAchievements);
        Intent i = getIntent();
        String name = i.getStringExtra("userName");
        userName.setText(name);
    }

    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
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