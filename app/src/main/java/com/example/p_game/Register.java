package com.example.p_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText pw;
    EditText pwagain;
    Button regBtn;
    EditText userName;
    FirebaseDatabase db = new DatabaseConn().getConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pw = findViewById(R.id.userPass);
        pwagain = findViewById(R.id.userPassAgain);
        userName = findViewById(R.id.userName);
        regBtn = findViewById(R.id.registerBtn);
    }

    public void toHome(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void toMenu(View v) {
        if(!pw.getText().toString().equals("") && !pwagain.getText().toString().equals("") && !userName.getText().toString().equals("")){
            if (!(pw.getText().toString().equals(pwagain.getText().toString()))) {
                Toast.makeText(this, "The 2 passwords must be the same", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(this, Menu.class);
                DatabaseReference ref = db.getReference("player");
                db.getReference().child("player").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(userName.getText().toString()).exists()){
                            Toast.makeText(Register.this, "Player with this name already exists", Toast.LENGTH_LONG).show();
                        }

                        else{
                            ref.child(userName.getText().toString()).child("password").setValue(pw.getText().toString());
                            i.putExtra("userName", userName.getText().toString());
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("Doesn't work", "onCancelled: ");
                    }
                });
            }
        }

        else{
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        }
    }
}