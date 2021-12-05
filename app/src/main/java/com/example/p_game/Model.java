package com.example.p_game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
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
    private DatabaseConn db;

    public Model() {
        this.mediaRecorder = new MediaRecorder();
        this.audioRecorder = new AudioRecorder("voices", this.mediaRecorder);

    }

    public void uploadSpeech(String gameId, String userName){

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

}
