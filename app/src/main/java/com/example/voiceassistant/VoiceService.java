package com.example.voiceassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import java.io.File;
import java.io.IOException;

public class VoiceService extends Service implements RecognitionListener {

    private SpeechRecognizer recognizer;

    @Override
    public void onCreate() {
        super.onCreate();
        setupRecognizer();
    }

    private void setupRecognizer() {
        try {
            Assets assets = new Assets(this);
            File assetDir = assets.syncAssets();

            recognizer = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetDir, "en-us-ptm"))
                    .setDictionary(new File(assetDir, "cmudict-en-us.dict"))
                    .getRecognizer();

            recognizer.addListener(this);

            // إضافة الكلمات المراد التعرف عليها أوفلاين
            recognizer.addKeyphraseSearch("kws", "open whatsapp");
            recognizer.startListening("kws");

        } catch (IOException e) {
            Log.e("VoiceService", "Error setting up recognizer: " + e.getMessage());
        }
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) return;
        String text = hypothesis.getHypstr();
        if (text.equals("open whatsapp")) {
            recognizer.stop();
            executeCommand(text);
            recognizer.startListening("kws");
        }
    }

    private void executeCommand(String command) {
        Intent intent = new Intent(this, AppAccessibilityService.class);
        intent.putExtra("COMMAND", command);
        startService(intent);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {}
    @Override
    public void onBeginningOfSpeech() {}
    @Override
    public void onEndOfSpeech() {}
    @Override
    public void onError(Exception error) {}
    @Override
    public void onTimeout() {}
    @Override
    public IBinder onBind(Intent intent) { return null; }
}

