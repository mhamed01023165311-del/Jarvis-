package com.example.voiceassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private View btnEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // البحث عن التكست والزر ديناميكياً باستخدام getIdentifier لمنع خطأ R.id
        int statusId = getResources().getIdentifier("tvStatus", "id", getPackageName());
        if (statusId != 0) {
            tvStatus = findViewById(statusId);
        }

        btnEnable = findViewByPossibleIds("btnEnable", "btn_enable_accessibility", "buttonEnable", "btn_enable");

        if (btnEnable != null) {
            btnEnable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            });
        }
    }

    private View findViewByPossibleIds(String... ids) {
        for (String idName : ids) {
            int resId = getResources().getIdentifier(idName, "id", getPackageName());
            if (resId != 0) {
                View view = findViewById(resId);
                if (view != null) return view;
            }
        }
        return null;
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
