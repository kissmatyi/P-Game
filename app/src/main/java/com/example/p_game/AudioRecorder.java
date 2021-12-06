package com.example.p_game;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

public class AudioRecorder {

    private MediaRecorder recorder;
    private String path;
    private String recordFileName;

    public AudioRecorder(String path, MediaRecorder mediaRecorder) {
        //this.recorder = new MediaRecorder();
        this.recorder = mediaRecorder;
    }

    private String sanitizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".3gp";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + path;
    }

    public void start(Activity activity, String gameId, String userName){
        this.recordFileName = gameId+"-"+userName+".mp3";
        this.path = activity.getExternalFilesDir("/").getAbsolutePath();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path + "/" + recordFileName);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();

    }

    public void stop(){
        recorder.stop();
        recorder.release();
        //this.recorder = null;
    }

    public void playRecording(String path) throws IOException {
        MediaPlayer mp = new MediaPlayer();
        mp.setDataSource(path);
        mp.prepare();
        mp.start();
        mp.setVolume(10, 10);
    }
}