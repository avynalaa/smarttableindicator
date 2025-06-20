package com.smarttableindicator.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;

import com.smarttableindicator.app.R;

public class HelpActivity extends AppCompatActivity {

    private static final String TAG = "HelpActivity"; // For logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Help & About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textViewAppVersion = findViewById(R.id.textViewAppVersion);
        try {
            // Get the PackageInfo object for your app
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);

            // Get the version name (e.g., "1.0", "1.0.1-beta")
            String versionName = pInfo.versionName;

            // Get the version code (as a long, handles deprecation)
            long versionCode = PackageInfoCompat.getLongVersionCode(pInfo);

            // Display them
            String versionDisplay = versionName + " (Code: " + versionCode + ")";
            textViewAppVersion.setText(versionDisplay); // Example: "1.0.0 (Prototype) (Code: 1)"

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not get package info", e);
            textViewAppVersion.setText("N/A"); // Fallback if version info can't be retrieved
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}