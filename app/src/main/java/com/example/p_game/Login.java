package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText pw;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://p-game-a75c2-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.userName);
        pw = findViewById(R.id.userPass);
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }


    public void toMenu(View v){
        Intent i = new Intent(this, Menu.class);
        Model model = new Model();
        if(!pw.getText().toString().equals("") && !userName.getText().toString().equals("")){
            db.getReference().child("player").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(userName.getText().toString()).exists() &&
                            dataSnapshot.child(userName.getText().toString()).child("password").getValue().equals(pw.getText().toString())){
                        Model model = new Model();
                        model.saveUserNameToFile(userName.getText().toString(), getApplicationContext());
                        i.putExtra("userName", userName.getText().toString());
                        startActivity(i);
                    }

                    else{
                        Toast.makeText(Login.this, "Please registrate first", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Doesn't work", "onCancelled: ");
                }
            });
        }

        else{
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_LONG).show();
        }
    }
}