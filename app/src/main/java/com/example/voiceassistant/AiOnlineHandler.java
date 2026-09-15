package com.example.voiceassistant;

import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AiOnlineHandler {

    // إرسال الأوامر المعقدة للـ API عند توفر إنترنت
    public static void sendPromptToAi(String userPrompt, AiCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL("https://api.your-ai-provider.com/v1/process");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonInputString = "{\"prompt\": \"" + userPrompt + "\"}";

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    callback.onSuccess("Action Parsed Successfully");
                } else {
                    callback.onError("Server Error: " + responseCode);
                }

            } catch (Exception e) {
                Log.e("AiOnlineHandler", "Error processing AI request", e);
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public interface AiCallback {
        void onSuccess(String parsedAction);
        void onError(String error);
    }
}

