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

    private static final String TAG = "HelpActivity";

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
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);

            String versionName = pInfo.versionName;

            long versionCode = PackageInfoCompat.getLongVersionCode(pInfo);

            String versionDisplay = versionName + " (Code: " + versionCode + ")";
            textViewAppVersion.setText(versionDisplay);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not get package info", e);
            textViewAppVersion.setText("N/A");
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