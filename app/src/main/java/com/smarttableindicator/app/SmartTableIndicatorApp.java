package com.smarttableindicator.app;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.installations.FirebaseInstallations;
import android.util.Log;
import com.smarttableindicator.app.activities.SettingsActivity;

public class SmartTableIndicatorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Load the saved theme preference
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.THEME_PREF_NAME, MODE_PRIVATE);
        int currentNightMode = prefs.getInt(SettingsActivity.KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        // Apply the theme preference
        AppCompatDelegate.setDefaultNightMode(currentNightMode);

        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String fid = task.getResult();
                Log.i("MyFirebaseFID", "Firebase Installation ID: " + fid);
            } else {
                Log.e("MyFirebaseFID", "Failed to get Firebase Installation ID", task.getException());
            }
        });
    }
}