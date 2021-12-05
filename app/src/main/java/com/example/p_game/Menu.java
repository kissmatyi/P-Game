package com.example.p_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Menu extends AppCompatActivity {

    private String name;
    private FirebaseDatabase db;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent i = getIntent();
        this.name = i.getStringExtra("userName");
        this.db = new DatabaseConn().getConnection();
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toTopic(View v){
        Intent i = new Intent(this, Topic.class);
        startActivity(i);
    }

    public void toProfile(View v){
        Intent i = new Intent(this, MyProfile.class);
        i.putExtra("userName", this.name);
        startActivity(i);
    }

    public void toConnect(View v){
        Intent i = new Intent(this, ConnectToGame.class);
        i.putExtra("userName",this.name);
        startActivity(i);
    }


    public void toNewGame(View v){
        Model model = new Model();
        this.name = model.readUserNameFromFile(this.getApplicationContext());
        Intent next = new Intent(this, Waiting.class);

        next.putExtra("userName",this.name);
        gameIdToDb();
        next.putExtra("gameId", this.gameId);
        startActivity(next);
    }

    private void generateGameId(){
        String randomId = "";
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            randomId += c;
        }
        this.gameId = randomId.toUpperCase();
    }

    private void gameIdToDb(){
        generateGameId();
        db.getReference().child("games").child(this.gameId).child(this.name).setValue(1);
    }



}