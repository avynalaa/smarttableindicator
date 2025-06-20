package com.smarttableindicator.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smarttableindicator.app.R;
import com.smarttableindicator.app.config.Constants;
import com.smarttableindicator.app.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextStaffId, editTextPassword;
    private Button buttonLogin;

    private static final String TAG = Constants.TAG_LOGIN_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextStaffId = findViewById(R.id.editTextStaffId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);


        // Check if already logged in
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(Constants.KEY_LOGGED_IN_FLAG, false);
        if (loggedIn) {
            Log.d(TAG, "User already logged in, redirecting to MainActivity");
            goToMain();
            finish();
            return;
        }
        
        Log.d(TAG, "User not logged in, showing login screen");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    /**
     * Performs login with comprehensive validation and error handling
     */
    private void performLogin() {
        Log.d(TAG, "Login attempt initiated");
        
        // Get input values
        String staffId = editTextStaffId.getText().toString();
        String password = editTextPassword.getText().toString();
        
        // Disable login button to prevent multiple clicks
        buttonLogin.setEnabled(false);
        buttonLogin.setText("Memproses...");
        
        // Validate staff ID
        ValidationUtils.ValidationResult staffIdValidation = ValidationUtils.validateStaffId(staffId);
        if (!staffIdValidation.isValid()) {
            showValidationError(staffIdValidation.getErrorMessage());
            resetLoginButton();
            editTextStaffId.requestFocus();
            return;
        }
        
        // Validate password
        ValidationUtils.ValidationResult passwordValidation = ValidationUtils.validatePassword(password);
        if (!passwordValidation.isValid()) {
            showValidationError(passwordValidation.getErrorMessage());
            resetLoginButton();
            editTextPassword.requestFocus();
            return;
        }
        
        // Sanitize inputs for security
        staffId = ValidationUtils.sanitizeInput(staffId.trim());
        password = password.trim(); // Don't sanitize password content, just trim
        
        Log.d(TAG, "Input validation passed for staff ID: " + staffId);
        
        // Perform authentication
        authenticateUser(staffId, password);
    }
    
    /**
     * Authenticates user credentials
     * @param staffId Validated and sanitized staff ID
     * @param password Validated password
     */
    private void authenticateUser(String staffId, String password) {
        Log.d(TAG, "Authenticating user: " + staffId);
        
        try {
            // DEMO Login check - TODO: Replace with proper authentication
            if (staffId.equals(Constants.DEMO_STAFF_ID) && password.equals(Constants.DEMO_PASSWORD)) {
                Log.d(TAG, "Authentication successful for staff ID: " + staffId);
                onLoginSuccess(staffId);
            } else {
                Log.w(TAG, "Authentication failed for staff ID: " + staffId);
                onLoginFailure("Username atau password Anda salah!");
            }
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error during authentication", e);
            onLoginFailure("Terjadi kesalahan sistem. Silakan coba lagi.");
        }
    }
    
    /**
     * Handles successful login
     * @param staffId The authenticated staff ID
     */
    private void onLoginSuccess(String staffId) {
        Log.d(TAG, "Processing successful login for: " + staffId);
        
        try {
            // Save login state in SharedPreferences
            SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            
            editor.putBoolean(Constants.KEY_LOGGED_IN_FLAG, true);
            editor.putString(Constants.KEY_STAFF_ID, staffId);
            editor.putString(Constants.KEY_STAFF_NAME, "Staff " + staffId);
            
            // Use commit() instead of apply() to ensure data is saved before proceeding
            boolean saved = editor.commit();
            
            if (saved) {
                Log.d(TAG, "Login state saved successfully");
                Toast.makeText(this, "Login berhasil! Selamat datang, " + staffId, Toast.LENGTH_SHORT).show();
                
                // Navigate to main activity
                goToMain();
                finish();
            } else {
                Log.e(TAG, "Failed to save login state");
                onLoginFailure("Gagal menyimpan data login. Silakan coba lagi.");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error saving login state", e);
            onLoginFailure("Terjadi kesalahan saat menyimpan data. Silakan coba lagi.");
        }
    }
    
    /**
     * Handles login failure
     * @param errorMessage The error message to display
     */
    private void onLoginFailure(String errorMessage) {
        Log.w(TAG, "Login failed: " + errorMessage);
        
        showValidationError(errorMessage);
        resetLoginButton();
        
        // Clear password field for security
        editTextPassword.setText("");
        editTextPassword.requestFocus();
    }
    
    /**
     * Shows validation error message to user
     * @param message The error message to display
     */
    private void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.w(TAG, "Validation error: " + message);
    }
    
    /**
     * Resets login button to original state
     */
    private void resetLoginButton() {
        buttonLogin.setEnabled(true);
        buttonLogin.setText(getString(R.string.login_button_text));
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}