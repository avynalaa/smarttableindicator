package com.smarttableindicator.app.utils;

import android.text.TextUtils;
import android.util.Log;
import java.util.regex.Pattern;

/**
 * Utility class for input validation across the application.
 * Centralizes validation logic to ensure consistency and reusability.
 */
public class ValidationUtils {
    
    private static final String TAG = "ValidationUtils";
    
    // Validation constants
    private static final int MIN_STAFF_ID_LENGTH = 3;
    private static final int MAX_STAFF_ID_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 50;
    
    // Regex patterns for validation
    private static final Pattern STAFF_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final Pattern SAFE_TEXT_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s._-]+$");
    
    // Private constructor to prevent instantiation
    private ValidationUtils() {}
    
    /**
     * Validation result class to provide detailed feedback
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;
        
        private ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }
        
        public boolean isValid() { return isValid; }
        public String getErrorMessage() { return errorMessage; }
        
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }
        
        public static ValidationResult error(String message) {
            return new ValidationResult(false, message);
        }
    }
    
    /**
     * Validates staff ID input
     * @param staffId The staff ID to validate
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validateStaffId(String staffId) {
        Log.d(TAG, "Validating staff ID: " + (staffId != null ? staffId.length() + " characters" : "null"));
        
        // Check for null or empty
        if (TextUtils.isEmpty(staffId)) {
            return ValidationResult.error("Staff ID tidak boleh kosong");
        }
        
        // Trim whitespace
        staffId = staffId.trim();
        
        // Check length
        if (staffId.length() < MIN_STAFF_ID_LENGTH) {
            return ValidationResult.error("Staff ID minimal " + MIN_STAFF_ID_LENGTH + " karakter");
        }
        
        if (staffId.length() > MAX_STAFF_ID_LENGTH) {
            return ValidationResult.error("Staff ID maksimal " + MAX_STAFF_ID_LENGTH + " karakter");
        }
        
        // Check format (alphanumeric, underscore, hyphen only)
        if (!STAFF_ID_PATTERN.matcher(staffId).matches()) {
            return ValidationResult.error("Staff ID hanya boleh berisi huruf, angka, underscore, dan strip");
        }
        
        Log.d(TAG, "Staff ID validation successful");
        return ValidationResult.success();
    }
    
    /**
     * Validates password input
     * @param password The password to validate
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validatePassword(String password) {
        Log.d(TAG, "Validating password: " + (password != null ? password.length() + " characters" : "null"));
        
        // Check for null or empty
        if (TextUtils.isEmpty(password)) {
            return ValidationResult.error("Password tidak boleh kosong");
        }
        
        // Check length
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return ValidationResult.error("Password minimal " + MIN_PASSWORD_LENGTH + " karakter");
        }
        
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return ValidationResult.error("Password maksimal " + MAX_PASSWORD_LENGTH + " karakter");
        }
        
        // Check for whitespace at start/end
        if (!password.equals(password.trim())) {
            return ValidationResult.error("Password tidak boleh dimulai atau diakhiri dengan spasi");
        }
        
        Log.d(TAG, "Password validation successful");
        return ValidationResult.success();
    }
    
    /**
     * Validates general text input for safety
     * @param text The text to validate
     * @param fieldName The name of the field being validated (for error messages)
     * @param maxLength Maximum allowed length
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validateSafeText(String text, String fieldName, int maxLength) {
        Log.d(TAG, "Validating " + fieldName + ": " + (text != null ? text.length() + " characters" : "null"));
        
        // Allow empty for optional fields
        if (TextUtils.isEmpty(text)) {
            return ValidationResult.success();
        }
        
        // Check length
        if (text.length() > maxLength) {
            return ValidationResult.error(fieldName + " maksimal " + maxLength + " karakter");
        }
        
        // Check for potentially dangerous characters
        if (!SAFE_TEXT_PATTERN.matcher(text).matches()) {
            return ValidationResult.error(fieldName + " mengandung karakter yang tidak diizinkan");
        }
        
        Log.d(TAG, fieldName + " validation successful");
        return ValidationResult.success();
    }
    
    /**
     * Validates table number input
     * @param tableNumberStr The table number as string
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validateTableNumber(String tableNumberStr) {
        Log.d(TAG, "Validating table number: " + tableNumberStr);
        
        if (TextUtils.isEmpty(tableNumberStr)) {
            return ValidationResult.error("Nomor meja tidak boleh kosong");
        }
        
        try {
            int tableNumber = Integer.parseInt(tableNumberStr.trim());
            
            if (tableNumber <= 0) {
                return ValidationResult.error("Nomor meja harus lebih dari 0");
            }
            
            if (tableNumber > 999) {
                return ValidationResult.error("Nomor meja maksimal 999");
            }
            
            Log.d(TAG, "Table number validation successful: " + tableNumber);
            return ValidationResult.success();
            
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid table number format: " + tableNumberStr);
            return ValidationResult.error("Nomor meja harus berupa angka");
        }
    }
    
    /**
     * Sanitizes input by removing potentially dangerous characters
     * @param input The input to sanitize
     * @return Sanitized input
     */
    public static String sanitizeInput(String input) {
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        
        // Remove potential script injection attempts
        return input.trim()
                   .replaceAll("[<>\"'&]", "") // Remove HTML/XML special chars
                   .replaceAll("\\s+", " ");   // Normalize whitespace
    }
} 