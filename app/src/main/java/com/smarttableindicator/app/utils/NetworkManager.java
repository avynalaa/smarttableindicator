package com.smarttableindicator.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Manages network connectivity monitoring and provides callbacks for network state changes.
 * This helps the app handle offline/online states gracefully.
 */
public class NetworkManager {
    
    private static final String TAG = "NetworkManager";
    
    private final Context context;
    private final ConnectivityManager connectivityManager;
    private NetworkCallback networkCallback;
    private boolean isNetworkAvailable = false;
    
    public interface NetworkStateListener {
        void onNetworkAvailable();
        void onNetworkLost();
        void onNetworkCapabilitiesChanged(boolean isWifi, boolean isCellular);
    }
    
    private NetworkStateListener networkStateListener;
    
    public NetworkManager(Context context) {
        this.context = context.getApplicationContext();
        this.connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.isNetworkAvailable = checkCurrentNetworkStatus();
        Log.d(TAG, "NetworkManager initialized. Current network status: " + isNetworkAvailable);
    }
    
    /**
     * Starts monitoring network connectivity changes
     * @param listener Callback for network state changes
     */
    public void startNetworkMonitoring(NetworkStateListener listener) {
        Log.d(TAG, "Starting network monitoring");
        this.networkStateListener = listener;
        
        if (connectivityManager == null) {
            Log.e(TAG, "ConnectivityManager is null, cannot monitor network");
            return;
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = new NetworkCallback();
            
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
            Log.d(TAG, "Network callback registered");
        } else {
            Log.w(TAG, "Network monitoring not fully supported on API < 24");
        }
        
        
        if (isNetworkAvailable && listener != null) {
            listener.onNetworkAvailable();
        } else if (!isNetworkAvailable && listener != null) {
            listener.onNetworkLost();
        }
    }
    
    /**
     * Stops monitoring network connectivity changes
     */
    public void stopNetworkMonitoring() {
        Log.d(TAG, "Stopping network monitoring");
        
        if (connectivityManager != null && networkCallback != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
                Log.d(TAG, "Network callback unregistered");
            } catch (Exception e) {
                Log.e(TAG, "Error unregistering network callback", e);
            }
        }
        
        networkCallback = null;
        networkStateListener = null;
    }
    
    /**
     * Checks current network connectivity status
     * @return true if network is available, false otherwise
     */
    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
    
    /**
     * Checks current network status without relying on cached value
     * @return true if network is available, false otherwise
     */
    private boolean checkCurrentNetworkStatus() {
        if (connectivityManager == null) {
            Log.w(TAG, "ConnectivityManager is null");
            return false;
        }
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork == null) {
                    Log.d(TAG, "No active network");
                    return false;
                }
                
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                if (capabilities == null) {
                    Log.d(TAG, "No network capabilities");
                    return false;
                }
                
                boolean hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                
                Log.d(TAG, "Network status check: hasInternet=" + hasInternet);
                return hasInternet;
                
            } else {
                android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = networkInfo != null && networkInfo.isConnected();
                Log.d(TAG, "Network status check (legacy): isConnected=" + isConnected);
                return isConnected;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking network status", e);
            return false;
        }
    }
    
    /**
     * Gets detailed network information for debugging
     * @return Network information string
     */
    public String getNetworkInfo() {
        if (connectivityManager == null) {
            return "ConnectivityManager not available";
        }
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork == null) {
                    return "No active network";
                }
                
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                if (capabilities == null) {
                    return "No network capabilities available";
                }
                
                StringBuilder info = new StringBuilder();
                info.append("Network available: ").append(isNetworkAvailable).append("\n");
                info.append("WiFi: ").append(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).append("\n");
                info.append("Cellular: ").append(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).append("\n");
                info.append("Internet: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).append("\n");
                info.append("Validated: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED));
                
                return info.toString();
            } else {
                android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    return "No network info available (legacy)";
                }
                return "Connected: " + networkInfo.isConnected() + ", Type: " + networkInfo.getTypeName();
            }
        } catch (Exception e) {
            return "Error getting network info: " + e.getMessage();
        }
    }
    
    /**
     * Network callback implementation for API 24+
     */
    @NonNull
    private class NetworkCallback extends ConnectivityManager.NetworkCallback {
        
        @Override
        public void onAvailable(@NonNull Network network) {
            Log.d(TAG, "Network became available: " + network);
            isNetworkAvailable = true;
            
            if (networkStateListener != null) {
                networkStateListener.onNetworkAvailable();
            }
        }
        
        @Override
        public void onLost(@NonNull Network network) {
            Log.d(TAG, "Network lost: " + network);
            isNetworkAvailable = false;
            
            if (networkStateListener != null) {
                networkStateListener.onNetworkLost();
            }
        }
        
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            boolean isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            boolean isCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            boolean hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            boolean isValidated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            
            Log.d(TAG, "Network capabilities changed - WiFi: " + isWifi + ", Cellular: " + isCellular + 
                      ", Internet: " + hasInternet + ", Validated: " + isValidated);
            
            // Update network availability based on actual internet connectivity
            isNetworkAvailable = hasInternet && isValidated;
            
            if (networkStateListener != null) {
                networkStateListener.onNetworkCapabilitiesChanged(isWifi, isCellular);
            }
        }
    }
} 