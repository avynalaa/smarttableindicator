package com.smarttableindicator.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

// This annotation helps Firebase ignore any extra fields it might find in the database
// that are not defined in this class, preventing crashes.
@IgnoreExtraProperties
public class FirebaseTableData {
    public String status;
    public double clientTimestamp; // Matches the double we sent from ESP32 for millis()
    public long serverTimestamp;   // Firebase server timestamp is typically long

    // Default constructor is required for calls to DataSnapshot.getValue(FirebaseTableData.class)
    public FirebaseTableData() {
    }

    // Optional: Constructor with fields, useful for other purposes if needed
    public FirebaseTableData(String status, double clientTimestamp, long serverTimestamp) {
        this.status = status;
        this.clientTimestamp = clientTimestamp;
        this.serverTimestamp = serverTimestamp;
    }

    // Optional: Getters if you need to access these fields from outside directly
    public String getStatus() {
        return status;
    }

    public double getClientTimestamp() {
        return clientTimestamp;
    }

    public long getServerTimestamp() {
        return serverTimestamp;
    }
}