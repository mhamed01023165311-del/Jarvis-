package com.example.voiceassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button btnEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnEnable = findViewById(R.id.btnEnable);

        btnEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    private void updateServiceStatus() {
        boolean isEnabled = isAccessibilityServiceEnabled(this, AppAccessibilityService.class);

        if (tvStatus != null) {
            tvStatus.setText(isEnabled ? "Voice Assistant: Enabled" : "Voice Assistant: Disabled");
        }

        if (btnEnable != null) {
            btnEnable.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isAccessibilityServiceEnabled(Context context, Class<?> serviceClass) {
        String expectedService = context.getPackageName() + "/" + serviceClass.getName();
        String enabledServices = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (enabledServices == null) return false;

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
        splitter.setString(enabledServices);

        while (splitter.hasNext()) {
            String service = splitter.next();
            if (service.equalsIgnoreCase(expectedService)) {
                return true;
            }
        }
        return false;
    }
}
