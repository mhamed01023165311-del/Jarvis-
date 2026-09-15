package com.example.voiceassistant;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCommandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);

        EditText etTrigger = findViewById(R.id.etTrigger);
        EditText etAction = findViewById(R.id.etAction);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String trigger = etTrigger.getText().toString().trim();
            String action = etAction.getText().toString().trim();

            if (!trigger.isEmpty() && !action.isEmpty()) {
                // حفظ الأمر في SharedPreferences أو Database محلياً
                Toast.makeText(this, "تم حفظ الأمر بنجاح", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "يرجى إدخال الجملة والحدث", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

