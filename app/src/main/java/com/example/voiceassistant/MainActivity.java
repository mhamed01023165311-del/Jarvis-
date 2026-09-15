package com.example.voiceassistant;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEnableAccessibility = findViewById(R.id.btnEnableAccessibility);
        FloatingActionButton fabAddCommand = findViewById(R.id.fabAddCommand);

        // فتح إعدادات إمكانية الوصول لتمكين الخدمة
        btnEnableAccessibility.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });

        // زر إضافة أوامر صوتية مخصصة
        fabAddCommand.setOnClickListener(v -> {
            Toast.makeText(this, "Add Voice Trigger Screen", Toast.LENGTH_SHORT).show();
            // يمكن هنا فتح Activity جديدة لربط الجمل بالأفعال
        });

        // تشغيل خدمة الاستماع في الخلفية
        Intent serviceIntent = new Intent(this, VoiceService.class);
        startService(serviceIntent);
    }
}

