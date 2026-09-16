package com.example.voiceassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;

public class VoiceService extends Service implements RecognitionListener {

    private SpeechService speechService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPartialResult(String hypothesis) {}

    @Override
    public void onResult(String hypothesis) {}

    @Override
    public void onFinalResult(String hypothesis) {}

    @Override
    public void onError(Exception exception) {}

    @Override
    public void onTimeout() {}
}
