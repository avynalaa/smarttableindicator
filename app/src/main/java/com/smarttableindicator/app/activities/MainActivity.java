package com.smarttableindicator.app.activities;

import android.app.NotificationManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import com.smarttableindicator.app.R;
import com.smarttableindicator.app.adapters.TableAdapter;
import com.smarttableindicator.app.config.Constants;
import com.smarttableindicator.app.models.TableModel;
import com.smarttableindicator.app.utils.NetworkManager;
import com.smarttableindicator.app.utils.FirebaseErrorHandler;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TableAdapter tableAdapter;
    private List<TableModel> tableList;

    private static final String TAG = Constants.TAG_MAIN_ACTIVITY;

    private DatabaseReference tablesDatabaseReference;
    private ChildEventListener tablesChildEventListener;
    private NetworkManager networkManager;
    private boolean isNetworkAvailable = false;

    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Lifecycle START");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        String staffName = prefs.getString(Constants.KEY_STAFF_NAME, "Guest");
        String currentStaffId = prefs.getString(Constants.KEY_STAFF_ID, null);

        Log.d(TAG, "onCreate: staffName = " + staffName + ", staffId = " + currentStaffId);

        View headerView = navigationView.getHeaderView(0);
        TextView textViewStaffName = headerView.findViewById(R.id.textViewStaffName);
        CircleImageView imageViewProfile = headerView.findViewById(R.id.imageViewProfile);
        textViewStaffName.setText("Selamat Datang, " + staffName);

        if (currentStaffId != null) {
            String profilePicUriString = prefs.getString(Constants.KEY_PROFILE_PIC_URI_PREFIX + currentStaffId, null);
            Log.d(TAG, "onCreate: Loading PFP for staffId " + currentStaffId + ". URI: " + profilePicUriString);
            if (profilePicUriString != null) {
                try {
                    imageViewProfile.setImageURI(Uri.parse(profilePicUriString));
                } catch (Exception e) {
                    Log.e(TAG, "Error setting profile image URI: " + profilePicUriString, e);
                    imageViewProfile.setImageResource(R.drawable.ic_account_circle);
                    prefs.edit().remove(Constants.KEY_PROFILE_PIC_URI_PREFIX + currentStaffId).apply();
                }
            } else {
                imageViewProfile.setImageResource(R.drawable.ic_account_circle);
            }
        } else {
            Log.d(TAG, "onCreate: No staffId, showing default PFP.");
            imageViewProfile.setImageResource(R.drawable.ic_account_circle);
        }

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            Uri imageUri = data.getData();
                            Log.d(TAG, "Image URI received: " + imageUri.toString());
                            handleImageSelection(imageUri);
                        } else {
                            Log.w(TAG, "data.getData() is null");
                        }
                    } else {
                        Log.d(TAG, "Image picking cancelled or failed. ResultCode: " + result.getResultCode());
                    }
                });

        imageViewProfile.setOnClickListener(v -> {
            SharedPreferences clickPrefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            String clickStaffId = clickPrefs.getString(Constants.KEY_STAFF_ID, null);
            if (clickStaffId == null) {
                Toast.makeText(MainActivity.this, "Please log in to set a profile picture.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                Toast.makeText(MainActivity.this, "Dashboard selected", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_statistics) {
                Toast.makeText(MainActivity.this, "Statistics (placeholder)", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_help) {
                Log.d(TAG, "Help item selected. Launching HelpActivity.");
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpIntent);
            } else if (id == R.id.nav_settings) {
                Log.d(TAG, "Settings item selected. Launching SettingsActivity.");
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            } else if (id == R.id.nav_logout) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Log.d(TAG, "Logout initiated by user.");
                            SharedPreferences logoutPrefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = logoutPrefs.edit();
                            editor.remove(Constants.KEY_STAFF_ID);
                            editor.remove(Constants.KEY_STAFF_NAME);
                            editor.remove(Constants.KEY_LOGGED_IN_FLAG);
                            editor.apply();
                            Log.d(TAG, "Selective prefs removed for logout.");
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Log.d(TAG, "Starting LoginActivity. Finishing MainActivity.");
                            finish();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                drawerLayout.closeDrawers();
                return true;
            }
            drawerLayout.closeDrawers();
            return true;
        });

        RecyclerView recyclerViewTables = findViewById(R.id.recyclerViewTables);
        recyclerViewTables.setLayoutManager(new GridLayoutManager(this, Constants.TABLE_GRID_COLUMNS));
        tableList = new ArrayList<>();
        tableAdapter = new TableAdapter(tableList, this);
        recyclerViewTables.setAdapter(tableAdapter);

        setupNetworkMonitoring();
        setupFirebaseTableListener();
        Log.d(TAG, "onCreate: Lifecycle END");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("MyFCMToken", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("MyFCMToken", "Current FCM Token: " + token);
                });
    }

    private void handleImageSelection(Uri imageUri) {
        try {
            final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
            Log.d(TAG, "Successfully took persistable URI permission for: " + imageUri.toString());

            SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            String staffId = prefs.getString(Constants.KEY_STAFF_ID, null);

            if (staffId != null) {
                String oldUriString = prefs.getString(Constants.KEY_PROFILE_PIC_URI_PREFIX + staffId, null);
                if (oldUriString != null) {
                    try {
                        Uri oldUri = Uri.parse(oldUriString);
                        if ("content".equals(oldUri.getScheme())) {
                            List<UriPermission> persistedPermissions = getContentResolver().getPersistedUriPermissions();
                            for (UriPermission p : persistedPermissions) {
                                if (p.getUri().equals(oldUri) && p.isReadPermission()) {
                                    getContentResolver().releasePersistableUriPermission(oldUri, takeFlags);
                                    Log.d(TAG, "Released permission for old URI: " + oldUriString);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error releasing old URI permission: " + oldUriString, e);
                    }
                }
                prefs.edit().putString(Constants.KEY_PROFILE_PIC_URI_PREFIX + staffId, imageUri.toString()).apply();
                Log.d(TAG, "Saved new profile pic URI for staffId " + staffId + ": " + imageUri.toString());

                View headerView = navigationView.getHeaderView(0);
                CircleImageView imageViewProfileUI = headerView.findViewById(R.id.imageViewProfile);
                imageViewProfileUI.setImageURI(imageUri);
            } else {
                Log.w(TAG, "handleImageSelection: StaffId is null, cannot save profile picture URI.");
                Toast.makeText(this, "Error: User not identified. Cannot save profile picture.", Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException in handleImageSelection: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to secure permission for the selected image.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error processing image URI in handleImageSelection: " + e.getMessage(), e);
            Toast.makeText(this, "An unexpected error occurred with the image.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets up network connectivity monitoring
     */
    private void setupNetworkMonitoring() {
        Log.d(TAG, "Setting up network monitoring");
        
        networkManager = new NetworkManager(this);
        isNetworkAvailable = networkManager.isNetworkAvailable();
        
        networkManager.startNetworkMonitoring(new NetworkManager.NetworkStateListener() {
            @Override
            public void onNetworkAvailable() {
                Log.d(TAG, "Network became available");
                isNetworkAvailable = true;
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Koneksi internet tersedia", Toast.LENGTH_SHORT).show();
                    if (tablesDatabaseReference == null || tablesChildEventListener == null) {
                        setupFirebaseTableListener();
                    }
                });
            }
            
            @Override
            public void onNetworkLost() {
                Log.d(TAG, "Network lost");
                isNetworkAvailable = false;
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Koneksi internet terputus", Toast.LENGTH_LONG).show();
                });
            }
            
            @Override
            public void onNetworkCapabilitiesChanged(boolean isWifi, boolean isCellular) {
                Log.d(TAG, "Network capabilities changed - WiFi: " + isWifi + ", Cellular: " + isCellular);
                String networkType = isWifi ? "WiFi" : (isCellular ? "Data Seluler" : "Tidak diketahui");
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Menggunakan " + networkType, Toast.LENGTH_SHORT).show();
                });
            }
        });
        
        Log.d(TAG, "Network monitoring setup complete. Initial status: " + isNetworkAvailable);
    }

    private void setupFirebaseTableListener() {
        Log.d(TAG, "setupFirebaseTableListener: Initializing Firebase listener for /" + Constants.FIREBASE_TABLES_PATH);
        
        if (!isNetworkAvailable) {
            Log.w(TAG, "No network available, skipping Firebase setup");
            Toast.makeText(this, FirebaseErrorHandler.getNetworkErrorMessage(false), Toast.LENGTH_LONG).show();
            return;
        }
        
        try {
            tablesDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_TABLES_PATH);

            if (tablesChildEventListener == null) {
            tablesChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    String tableId = dataSnapshot.getKey();
                    FirebaseTableData firebaseTable = dataSnapshot.getValue(FirebaseTableData.class);
                    if (tableId != null && firebaseTable != null && firebaseTable.getStatus() != null) {
                        Log.i(TAG, "onChildAdded: tableId=" + tableId + ", status=" + firebaseTable.getStatus());
                        try {
                            int tableNumber = Integer.parseInt(tableId.replaceAll("[^0-9]", ""));
                            TableModel.Status newStatus = convertFirebaseStatusToEnum(firebaseTable.getStatus());
                            boolean exists = false;
                            for (TableModel tm : tableList) {
                                if (tm.getTableNumber() == tableNumber) {
                                    tm.setStatus(newStatus);
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                tableList.add(new TableModel(tableNumber, newStatus));
                            }
                            Collections.sort(tableList, Comparator.comparingInt(TableModel::getTableNumber));
                            tableAdapter.notifyDataSetChanged();

                            if (newStatus == TableModel.Status.DIRTY) {
                                int notificationId = tableNumber;
                                String title = "Table Alert!";
                                String body = "Table " + tableNumber + " needs cleaning!";
                                NotificationHelper.sendTableNotification(getApplicationContext(), title, body, tableId, notificationId);
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "onChildAdded: Error parsing table number from ID: " + tableId, e);
                        }
                    } else {
                        Log.w(TAG, "onChildAdded: Received null data for tableId: " + tableId);
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    String tableId = dataSnapshot.getKey();
                    FirebaseTableData firebaseTable = dataSnapshot.getValue(FirebaseTableData.class);
                    if (tableId != null && firebaseTable != null && firebaseTable.getStatus() != null) {
                        Log.i(TAG, "onChildChanged: tableId=" + tableId + ", newStatus=" + firebaseTable.getStatus());
                        try {
                            int tableNumber = Integer.parseInt(tableId.replaceAll("[^0-9]", ""));
                            TableModel.Status newStatus = convertFirebaseStatusToEnum(firebaseTable.getStatus());
                            boolean found = false;
                            for (int i = 0; i < tableList.size(); i++) {
                                if (tableList.get(i).getTableNumber() == tableNumber) {
                                    tableList.get(i).setStatus(newStatus);
                                    tableAdapter.notifyItemChanged(i);
                                    found = true;
                                    break;
                                }
                            }
                            if (newStatus == TableModel.Status.DIRTY) {
                                int notificationId = tableNumber;
                                String title = "Table Alert!";
                                String body = "Table " + tableNumber + " needs cleaning!";
                                NotificationHelper.sendTableNotification(getApplicationContext(), title, body, tableId, notificationId);
                            } else {
                                int notificationId = tableNumber;

                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                if (notificationManager != null) {
                                    notificationManager.cancel(notificationId);
                                    Log.d(TAG, "Attempted to cancel notification for tableId: " + tableId + " (Notification ID: " + notificationId + ")");
                                } else {
                                    Log.e(TAG, "Could not get NotificationManager to cancel notification for tableId: " + tableId);
                                }
                            }
                            if (!found) {
                                Log.w(TAG, "onChildChanged: Table " + tableNumber + " not found in list, adding it.");
                                tableList.add(new TableModel(tableNumber, newStatus));
                                Collections.sort(tableList, Comparator.comparingInt(TableModel::getTableNumber));
                                tableAdapter.notifyDataSetChanged();
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "onChildChanged: Error parsing table number from ID: " + tableId, e);
                        }
                    } else {
                        Log.w(TAG, "onChildChanged: Received null data for tableId: " + tableId);
                    }
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String tableId = dataSnapshot.getKey();
                    if (tableId != null) {
                        Log.i(TAG, "onChildRemoved: tableId=" + tableId);
                        try {
                            int tableNumber = Integer.parseInt(tableId.replaceAll("[^0-9]", ""));
                            for (int i = 0; i < tableList.size(); i++) {
                                if (tableList.get(i).getTableNumber() == tableNumber) {
                                    tableList.remove(i);
                                    tableAdapter.notifyItemRemoved(i);
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "onChildRemoved: Error parsing table number from ID: " + tableId, e);
                        }
                    }
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildMoved: " + dataSnapshot.getKey());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Firebase ChildEventListener was cancelled.", databaseError.toException());
                    
                    FirebaseErrorHandler.ErrorResult errorResult = FirebaseErrorHandler.handleDatabaseError(databaseError);
                    FirebaseErrorHandler.showErrorToUser(MainActivity.this, errorResult);
                    
                    if (errorResult.isRetryable() && isNetworkAvailable) {
                        Log.d(TAG, "Attempting to reconnect Firebase listener in " + errorResult.getRetryDelayMs() + "ms");
                        
                        new android.os.Handler(getMainLooper()).postDelayed(() -> {
                            Log.d(TAG, "Retrying Firebase connection");
                            setupFirebaseTableListener();
                        }, errorResult.getRetryDelayMs());
                    } else {
                        Log.w(TAG, "Not retrying Firebase connection - Retryable: " + errorResult.isRetryable() + 
                                  ", Network available: " + isNetworkAvailable);
                    }
                }
            };
                tablesDatabaseReference.addChildEventListener(tablesChildEventListener);
                Log.d(TAG, "setupFirebaseTableListener: ChildEventListener attached to /" + Constants.FIREBASE_TABLES_PATH);
            } else {
                Log.d(TAG, "setupFirebaseTableListener: ChildEventListener already attached.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up Firebase listener", e);
            FirebaseErrorHandler.ErrorResult errorResult = FirebaseErrorHandler.handleException(e);
            FirebaseErrorHandler.showErrorToUser(this, errorResult);
            
            if (errorResult.isRetryable() && isNetworkAvailable) {
                new android.os.Handler(getMainLooper()).postDelayed(() -> {
                    Log.d(TAG, "Retrying Firebase setup after exception");
                    setupFirebaseTableListener();
                }, errorResult.getRetryDelayMs());
            }
        }
    }

    private TableModel.Status convertFirebaseStatusToEnum(String firebaseStatus) {
        if (firebaseStatus == null) {
            Log.w(TAG, "convertFirebaseStatusToEnum: Firebase status is null, defaulting to AVAILABLE.");
            return TableModel.Status.AVAILABLE;
        }
        switch (firebaseStatus.toUpperCase()) {
            case Constants.STATUS_OCCUPIED:
                return TableModel.Status.OCCUPIED;
            case Constants.STATUS_DIRTY:
                return TableModel.Status.DIRTY;
            case "CLEAN":
            case Constants.STATUS_AVAILABLE:
                return TableModel.Status.AVAILABLE;
            default:
                Log.w(TAG, "convertFirebaseStatusToEnum: Unknown status '" + firebaseStatus + "', defaulting to AVAILABLE.");
                return TableModel.Status.AVAILABLE;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult (DEPRECATED for image picking): requestCode=" + requestCode + ", resultCode=" + resultCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Lifecycle event.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Lifecycle event.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Lifecycle event.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Lifecycle event.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Lifecycle event. Cleaning up resources.");
        
        if (tablesDatabaseReference != null && tablesChildEventListener != null) {
            tablesDatabaseReference.removeEventListener(tablesChildEventListener);
            tablesChildEventListener = null;
            Log.i(TAG, "onDestroy: Firebase ChildEventListener removed.");
        }
        
        if (networkManager != null) {
            networkManager.stopNetworkMonitoring();
            networkManager = null;
            Log.i(TAG, "onDestroy: Network monitoring stopped.");
        }
    }
}