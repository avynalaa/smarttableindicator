package com.smarttableindicator.app.config;

/**
 * Application-wide constants to avoid hardcoded values scattered throughout the codebase.
 * This makes the app easier to maintain and configure.
 */
public final class Constants {
    
    // Private constructor to prevent instantiation
    private Constants() {}
    
    // SharedPreferences Keys
    public static final String PREFS_NAME = "MyAppPrefs";
    public static final String KEY_STAFF_ID = "staff_id";
    public static final String KEY_STAFF_NAME = "staff_name";
    public static final String KEY_PROFILE_PIC_URI_PREFIX = "profile_pic_uri_";
    public static final String KEY_LOGGED_IN_FLAG = "logged_in";
    
    // Theme Preferences (from SettingsActivity)
    public static final String THEME_PREF_NAME = "ThemePrefs";
    public static final String KEY_THEME = "selectedThemeMode";
    
    // Firebase Database Paths
    public static final String FIREBASE_TABLES_PATH = "tables";
    public static final String FIREBASE_CONNECTION_TEST_PATH = "appSettings/connectionTest";
    
    // Notification Channels
    public static final String TABLE_STATUS_CHANNEL_ID = "TABLE_STATUS_UPDATES_CHANNEL";
    public static final String FCM_CHANNEL_ID = "TABLE_STATUS_CHANNEL";
    
    // Demo Login Credentials (replace with proper authentication later)
    public static final String DEMO_STAFF_ID = "12345";
    public static final String DEMO_PASSWORD = "password";
    
    // UI Configuration
    public static final int TABLE_GRID_COLUMNS = 3;
    public static final int ANIMATION_DURATION_MS = 800;
    
    // Request Codes
    public static final int REQUEST_CODE_NOTIFICATION = 1001;
    
    // Table Status Values (matching Firebase)
    public static final String STATUS_AVAILABLE = "AVAILABLE";
    public static final String STATUS_OCCUPIED = "OCCUPIED";
    public static final String STATUS_DIRTY = "DIRTY";
    
    // Logging Tags
    public static final String TAG_MAIN_ACTIVITY = "MainActivity";
    public static final String TAG_LOGIN_ACTIVITY = "LoginActivity";
    public static final String TAG_SETTINGS_ACTIVITY = "SettingsActivity";
    public static final String TAG_NOTIFICATION_HELPER = "NotificationHelper";
    public static final String TAG_FCM_SERVICE = "MyFirebaseMsgService";
    public static final String TAG_TABLE_ADAPTER = "TableAdapter";
} 