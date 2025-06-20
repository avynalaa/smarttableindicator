package com.smarttableindicator.app.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

/**
 * Centralized Firebase error handling with user-friendly messages and retry mechanisms.
 * Provides consistent error handling across the entire application.
 */
public class FirebaseErrorHandler {
    
    private static final String TAG = "FirebaseErrorHandler";
    
    public enum ErrorCategory {
        NETWORK_ERROR,
        PERMISSION_ERROR,
        DATA_ERROR,
        AUTHENTICATION_ERROR,
        UNKNOWN_ERROR
    }
    
    public static class ErrorResult {
        private final ErrorCategory category;
        private final String userMessage;
        private final String technicalMessage;
        private final boolean isRetryable;
        private final int retryDelayMs;
        
        private ErrorResult(ErrorCategory category, String userMessage, String technicalMessage, 
                           boolean isRetryable, int retryDelayMs) {
            this.category = category;
            this.userMessage = userMessage;
            this.technicalMessage = technicalMessage;
            this.isRetryable = isRetryable;
            this.retryDelayMs = retryDelayMs;
        }
        
        public ErrorCategory getCategory() { return category; }
        public String getUserMessage() { return userMessage; }
        public String getTechnicalMessage() { return technicalMessage; }
        public boolean isRetryable() { return isRetryable; }
        public int getRetryDelayMs() { return retryDelayMs; }
    }
    
    /**
     * Handles Firebase DatabaseError and returns appropriate error information
     * @param error The Firebase DatabaseError
     * @return ErrorResult with categorized error information
     */
    public static ErrorResult handleDatabaseError(DatabaseError error) {
        if (error == null) {
            Log.w(TAG, "Received null DatabaseError");
            return new ErrorResult(
                ErrorCategory.UNKNOWN_ERROR,
                "Terjadi kesalahan yang tidak diketahui",
                "Null DatabaseError received",
                true,
                2000
            );
        }
        
        Log.e(TAG, "Firebase DatabaseError: " + error.getCode() + " - " + error.getMessage());
        
        switch (error.getCode()) {
            case DatabaseError.NETWORK_ERROR:
                return new ErrorResult(
                    ErrorCategory.NETWORK_ERROR,
                    "Tidak ada koneksi internet. Periksa koneksi Anda dan coba lagi.",
                    "Network error: " + error.getMessage(),
                    true,
                    3000
                );
                
            case DatabaseError.PERMISSION_DENIED:
                return new ErrorResult(
                    ErrorCategory.PERMISSION_ERROR,
                    "Akses ditolak. Anda mungkin tidak memiliki izin untuk mengakses data ini.",
                    "Permission denied: " + error.getMessage(),
                    false,
                    0
                );
                
            case DatabaseError.UNAVAILABLE:
                return new ErrorResult(
                    ErrorCategory.NETWORK_ERROR,
                    "Server sedang tidak tersedia. Silakan coba lagi dalam beberapa saat.",
                    "Service unavailable: " + error.getMessage(),
                    true,
                    5000
                );
                
            case DatabaseError.OPERATION_FAILED:
                return new ErrorResult(
                    ErrorCategory.DATA_ERROR,
                    "Operasi gagal. Silakan coba lagi.",
                    "Operation failed: " + error.getMessage(),
                    true,
                    2000
                );
                
            case DatabaseError.EXPIRED_TOKEN:
                return new ErrorResult(
                    ErrorCategory.AUTHENTICATION_ERROR,
                    "Sesi Anda telah berakhir. Silakan login kembali.",
                    "Token expired: " + error.getMessage(),
                    false,
                    0
                );
                
            case DatabaseError.INVALID_TOKEN:
                return new ErrorResult(
                    ErrorCategory.AUTHENTICATION_ERROR,
                    "Autentikasi tidak valid. Silakan login kembali.",
                    "Invalid token: " + error.getMessage(),
                    false,
                    0
                );
                
            case DatabaseError.MAX_RETRIES:
                return new ErrorResult(
                    ErrorCategory.NETWORK_ERROR,
                    "Koneksi tidak stabil. Periksa koneksi internet Anda.",
                    "Max retries exceeded: " + error.getMessage(),
                    true,
                    5000
                );
                
            case DatabaseError.OVERRIDDEN_BY_SET:
                return new ErrorResult(
                    ErrorCategory.DATA_ERROR,
                    "Data telah diubah oleh pengguna lain. Silakan refresh dan coba lagi.",
                    "Data overridden: " + error.getMessage(),
                    true,
                    1000
                );
                
            case DatabaseError.USER_CODE_EXCEPTION:
                return new ErrorResult(
                    ErrorCategory.DATA_ERROR,
                    "Terjadi kesalahan dalam pemrosesan data.",
                    "User code exception: " + error.getMessage(),
                    true,
                    2000
                );
                
            default:
                return new ErrorResult(
                    ErrorCategory.UNKNOWN_ERROR,
                    "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi.",
                    "Unknown error (" + error.getCode() + "): " + error.getMessage(),
                    true,
                    3000
                );
        }
    }
    
    /**
     * Handles general exceptions that might occur during Firebase operations
     * @param exception The exception that occurred
     * @return ErrorResult with categorized error information
     */
    public static ErrorResult handleException(Exception exception) {
        if (exception == null) {
            Log.w(TAG, "Received null Exception");
            return new ErrorResult(
                ErrorCategory.UNKNOWN_ERROR,
                "Terjadi kesalahan yang tidak diketahui",
                "Null exception received",
                true,
                2000
            );
        }
        
        Log.e(TAG, "Firebase Exception: " + exception.getClass().getSimpleName() + " - " + exception.getMessage(), exception);
        
        String exceptionType = exception.getClass().getSimpleName();
        
        if (exceptionType.contains("Network") || exceptionType.contains("Timeout") || 
            exceptionType.contains("UnknownHost") || exceptionType.contains("ConnectException")) {
            return new ErrorResult(
                ErrorCategory.NETWORK_ERROR,
                "Masalah koneksi jaringan. Periksa koneksi internet Anda.",
                "Network exception: " + exception.getMessage(),
                true,
                3000
            );
        }
        
        if (exceptionType.contains("Security") || exceptionType.contains("Permission")) {
            return new ErrorResult(
                ErrorCategory.PERMISSION_ERROR,
                "Akses ditolak. Periksa pengaturan keamanan aplikasi.",
                "Security exception: " + exception.getMessage(),
                false,
                0
            );
        }
        
        if (exceptionType.contains("IllegalArgument") || exceptionType.contains("IllegalState")) {
            return new ErrorResult(
                ErrorCategory.DATA_ERROR,
                "Data tidak valid. Silakan periksa input Anda.",
                "Invalid data exception: " + exception.getMessage(),
                false,
                0
            );
        }
        
        return new ErrorResult(
            ErrorCategory.UNKNOWN_ERROR,
            "Terjadi kesalahan sistem. Silakan coba lagi atau restart aplikasi.",
            "Unknown exception: " + exception.getMessage(),
            true,
            3000
        );
    }
    
    /**
     * Shows user-friendly error message and logs technical details
     * @param context Android context for showing toast
     * @param errorResult The error result from handleDatabaseError or handleException
     */
    public static void showErrorToUser(Context context, ErrorResult errorResult) {
        if (context == null || errorResult == null) {
            Log.w(TAG, "Cannot show error to user: context or errorResult is null");
            return;
        }
        
        Toast.makeText(context, errorResult.getUserMessage(), Toast.LENGTH_LONG).show();
        
        Log.e(TAG, "Error shown to user - Category: " + errorResult.getCategory() + 
                  ", Technical: " + errorResult.getTechnicalMessage() + 
                  ", Retryable: " + errorResult.isRetryable());
    }
    
    /**
     * Creates a user-friendly error message for network connectivity issues
     * @param isNetworkAvailable Current network status
     * @return User-friendly error message
     */
    public static String getNetworkErrorMessage(boolean isNetworkAvailable) {
        if (!isNetworkAvailable) {
            return "Tidak ada koneksi internet. Periksa WiFi atau data seluler Anda.";
        } else {
            return "Koneksi tidak stabil. Periksa kualitas sinyal internet Anda.";
        }
    }
    
    /**
     * Determines if an operation should be retried based on error type and attempt count
     * @param errorResult The error result
     * @param attemptCount Current number of attempts
     * @param maxAttempts Maximum allowed attempts
     * @return true if operation should be retried
     */
    public static boolean shouldRetry(ErrorResult errorResult, int attemptCount, int maxAttempts) {
        if (errorResult == null || attemptCount >= maxAttempts) {
            return false;
        }
        
        if (errorResult.getCategory() == ErrorCategory.PERMISSION_ERROR || 
            errorResult.getCategory() == ErrorCategory.AUTHENTICATION_ERROR) {
            return false;
        }
        
        return errorResult.isRetryable();
    }
    
    /**
     * Calculates exponential backoff delay for retries
     * @param attemptCount Current attempt number (0-based)
     * @param baseDelayMs Base delay in milliseconds
     * @return Calculated delay in milliseconds
     */
    public static long calculateRetryDelay(int attemptCount, long baseDelayMs) {
        long delay = baseDelayMs * (1L << Math.min(attemptCount, 4));
        return Math.min(delay, 30000);
    }
} 