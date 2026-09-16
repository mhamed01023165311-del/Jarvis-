package com.example.voiceassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;

public class VoiceService extends Service implements RecognitionListener {

    private SpeechService speechService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("VoiceService", "VoiceService Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPartialResult(String hypothesis) {
        Log.d("VoiceService", "Partial: " + hypothesis);
    }

    @Override
    public void onResult(String hypothesis) {
        Log.d("VoiceService", "Result: " + hypothesis);
    }

    @Override
    public void onFinalResult(String hypothesis) {
        Log.d("VoiceService", "Final: " + hypothesis);
    }

    @Override
    public void onError(Exception exception) {
        Log.e("VoiceService", "Error: " + exception.getMessage());
    }

    @Override
    public void onTimeout() {
        Log.d("VoiceService", "Timeout");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (speechService != null) {
            speechService.stop();
            speechService.shutdown();
        }
    }
}
