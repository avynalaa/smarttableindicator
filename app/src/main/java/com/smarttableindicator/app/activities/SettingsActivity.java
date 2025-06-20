package com.smarttableindicator.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem; // For Up button
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.smarttableindicator.app.R;
import com.smarttableindicator.app.config.Constants;

public class SettingsActivity extends AppCompatActivity {

    private TextView textViewFirebaseProjectId;
    private Button buttonTestFirebase;
    private TextView textViewFirebaseTestStatus;

    private RadioGroup radioGroupTheme;
    private RadioButton radioButtonLight;
    private RadioButton radioButtonDark;
    private RadioButton radioButtonSystem;

    private static final String TAG = Constants.TAG_SETTINGS_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back arrow
        }

        textViewFirebaseProjectId = findViewById(R.id.textViewFirebaseProjectId);
        buttonTestFirebase = findViewById(R.id.buttonTestFirebase);
        textViewFirebaseTestStatus = findViewById(R.id.textViewFirebaseTestStatus);

        // Display Firebase Project ID
        try {
            String projectId = FirebaseApp.getInstance().getOptions().getProjectId();
            if (projectId != null && !projectId.isEmpty()) {
                textViewFirebaseProjectId.setText(projectId);
            } else {
                textViewFirebaseProjectId.setText("Not configured or unavailable");
            }
        } catch (IllegalStateException e) {
            textViewFirebaseProjectId.setText("Firebase not initialized properly.");
            Log.e(TAG, "FirebaseApp not initialized: " + e.getMessage());
        }

        buttonTestFirebase.setOnClickListener(v -> testFirebaseConnection());

        radioGroupTheme = findViewById(R.id.radioGroupTheme);
        radioButtonLight = findViewById(R.id.radioButtonLight);
        radioButtonDark = findViewById(R.id.radioButtonDark);
        radioButtonSystem = findViewById(R.id.radioButtonSystem);

        loadAndSetCurrentThemeRadioSelection();

        radioGroupTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int newThemeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM; // Default

            if (checkedId == R.id.radioButtonLight) {
                newThemeMode = AppCompatDelegate.MODE_NIGHT_NO; // Light Mode
                Log.d(TAG, "Theme changed to: Light Mode");
            } else if (checkedId == R.id.radioButtonDark) {
                newThemeMode = AppCompatDelegate.MODE_NIGHT_YES; // Dark Mode
                Log.d(TAG, "Theme changed to: Dark Mode");
            } else if (checkedId == R.id.radioButtonSystem) {
                newThemeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM; // Follow System
                Log.d(TAG, "Theme changed to: Follow System");
            }

            SharedPreferences prefs = getSharedPreferences(Constants.THEME_PREF_NAME, MODE_PRIVATE);
            prefs.edit().putInt(Constants.KEY_THEME, newThemeMode).apply();

            AppCompatDelegate.setDefaultNightMode(newThemeMode);
        });
    }

    private void testFirebaseConnection() {
        textViewFirebaseTestStatus.setText("Testing connection...");
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference testRef = database.getReference(Constants.FIREBASE_CONNECTION_TEST_PATH);
            long timestamp = System.currentTimeMillis();

            testRef.setValue(timestamp)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Successfully wrote to Firebase: " + timestamp);
                        testRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Long value = dataSnapshot.getValue(Long.class);
                                if (value != null && value == timestamp) {
                                    String successMsg = "Test successful! Wrote & Read: " + value;
                                    textViewFirebaseTestStatus.setText(successMsg);
                                    Toast.makeText(SettingsActivity.this, "Firebase test successful!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, successMsg);
                                } else {
                                    String errorMsg = "Test failed: Value mismatch or null. Read: " + value;
                                    textViewFirebaseTestStatus.setText(errorMsg);
                                    Toast.makeText(SettingsActivity.this, "Firebase test: Value mismatch.", Toast.LENGTH_LONG).show();
                                    Log.e(TAG, errorMsg);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                String errorMsg = "Test failed: Read cancelled. " + databaseError.getMessage();
                                textViewFirebaseTestStatus.setText(errorMsg);
                                Toast.makeText(SettingsActivity.this, "Firebase test: Read cancelled.", Toast.LENGTH_LONG).show();
                                Log.e(TAG, "Firebase read cancelled: " + databaseError.getMessage());
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        String errorMsg = "Test failed: Write error. " + e.getMessage();
                        textViewFirebaseTestStatus.setText(errorMsg);
                        Toast.makeText(SettingsActivity.this, "Firebase test: Write error.", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Firebase write error: " + e.getMessage());
                    });
        } catch (Exception e) { // Catch broader exceptions like Firebase not initialized
            String errorMsg = "Test failed: Exception during Firebase init/operation. " + e.getMessage();
            textViewFirebaseTestStatus.setText(errorMsg);
            Toast.makeText(SettingsActivity.this, "Firebase operation error.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Exception during Firebase test: " + e.getMessage(), e);
        }
    }

    private void loadAndSetCurrentThemeRadioSelection() {
        SharedPreferences prefs = getSharedPreferences(Constants.THEME_PREF_NAME, MODE_PRIVATE);
        // Get saved mode, default to MODE_NIGHT_FOLLOW_SYSTEM if nothing is saved
        int savedNightMode = prefs.getInt(Constants.KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        if (savedNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
            radioButtonLight.setChecked(true);
        } else if (savedNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            radioButtonDark.setChecked(true);
        } else { // MODE_NIGHT_FOLLOW_SYSTEM or MODE_NIGHT_UNSPECIFIED
            radioButtonSystem.setChecked(true);
        }
        Log.d(TAG, "Loaded theme preference and set radio button: " + savedNightMode);
    }

    // Handle the Up button (back arrow) in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this); // This is one way
            finish(); // Or simply finish the activity to go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}