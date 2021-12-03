package com.example.p_game;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConn {

    private FirebaseDatabase db;

    DatabaseConn(){
        this.db = FirebaseDatabase.getInstance("https://p-game-a75c2-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public FirebaseDatabase getConnection(){
        return this.db;
    }



}
