package com.example.voiceassistant;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AppAccessibilityService extends AccessibilityService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("COMMAND")) {
            String command = intent.getStringExtra("COMMAND");
            if ("open whatsapp".equals(command)) {
                openWhatsAppAndSend();
            }
        }
        return START_STICKY;
    }

    private void openWhatsAppAndSend() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchIntent);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // يمكن هنا البحث عن عناصر داخل الشاشة والتفاعل معها
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo == null) return;

        // مثال للبحث عن زر أو مكان كتابة
        // List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
    }

    @Override
    public void onInterrupt() {}
}

