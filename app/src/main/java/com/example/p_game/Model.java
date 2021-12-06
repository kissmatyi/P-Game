package com.example.p_game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Model {

    private static final int PERMISSION_CODE = 21;
    private AudioRecorder audioRecorder;
    private String path;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private MediaRecorder mediaRecorder;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri voiceUri;

    public Model() {
        this.mediaRecorder = new MediaRecorder();
        this.audioRecorder = new AudioRecorder("voices", this.mediaRecorder);
        //this.databaseReference = FirebaseDatabase.getInstance().getReference("speeches");
        this.storageReference = new DatabaseConn().getStReference();
    }

    private String getFileName(String gameId, String userName, Activity activity){
        return  gameId + "-" + userName + ".mp3";
    }

    public void uploadSpeech(String gameId, String userName, Activity activity){
        //storageReference speechRef = storageReference.child(getFileName(gameId,userName));
        StorageReference speechDirRef = storageReference.child(getFileName(gameId,userName, activity));
        File voiceFile = new File(activity.getExternalFilesDir("/").getAbsolutePath() +"/"+getFileName(gameId, userName, activity));

        if(voiceFile.exists()){

            this.voiceUri = Uri.fromFile(voiceFile);
            speechDirRef.putFile(voiceUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("SIKER", "onSuccess: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(e.getLocalizedMessage(), "onFailure: ");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(taskSnapshot.toString(), "onProgress: ");
                    }
                });

        }
    }

    public void saveUserNameToFile(String userName, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("userName.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(userName);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readUserNameFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("userName.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    protected void recordStart(Context context, Activity activity, String gameId, String userName){
        if(checkPermissions(context, activity)){
            this.mediaRecorder = new MediaRecorder();
            //TODO
            //only 30 sec
            audioRecorder.start(activity, gameId, userName);
        }
    }

    private boolean checkPermissions(Context context, Activity activity) {
        if(ActivityCompat.checkSelfPermission(context, recordPermission) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }

    }

    protected void recordStop(){
        audioRecorder.stop();
        this.mediaRecorder = null;
    }

    protected void playRecord(String gameId, String userName, Activity activity){
        String fileName = activity.getExternalFilesDir("/").getAbsolutePath() +"/"+getFileName(gameId, userName, activity);
        try {
            this.audioRecorder.playRecording(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void stopPlaying(){
        this.audioRecorder.stop();
    }

}
