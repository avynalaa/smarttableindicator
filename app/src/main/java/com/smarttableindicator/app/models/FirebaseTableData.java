package com.smarttableindicator.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseTableData {
    public String status;
    public double clientTimestamp;
    public long serverTimestamp;

    public FirebaseTableData() {
    }

    public FirebaseTableData(String status, double clientTimestamp, long serverTimestamp) {
        this.status = status;
        this.clientTimestamp = clientTimestamp;
        this.serverTimestamp = serverTimestamp;
    }
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