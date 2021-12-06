package com.example.p_game;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DatabaseConn {

    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private StorageReference stReference;

    DatabaseConn(){
        this.db = FirebaseDatabase.getInstance("https://p-game-a75c2-default-rtdb.europe-west1.firebasedatabase.app/");
        this.storage = FirebaseStorage.getInstance();
        this.stReference = storage.getReferenceFromUrl("gs://p-game-a75c2.appspot.com/speeches");

    }

    public FirebaseDatabase getConnection(){
        return this.db;
    }

    public FirebaseStorage getStorage() { return storage; }

    public StorageReference getStReference() {
        return stReference;
    }
}
