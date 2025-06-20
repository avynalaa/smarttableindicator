package com.smarttableindicator.app.models;

public class TableModel {
    public enum Status { AVAILABLE, OCCUPIED, DIRTY }

    private int tableNumber;
    private Status status;

    public TableModel(int tableNumber, Status status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    public int getTableNumber() { return tableNumber; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}