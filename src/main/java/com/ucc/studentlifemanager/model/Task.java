package com.ucc.studentlifemanager.model;

/**
 * Represents a single task — either a Quiz or an Assignment.
 * Mirrors one row in the "tasks" table.
 */
public class Task {

    private int id;
    private String title;
    private String type;     // "Quiz" or "Assignment"
    private String dueDate;
    private String status;   // e.g. "Pending" or "Completed"

    // Constructor for a NEW task (no id yet)
    public Task(String title, String type, String dueDate, String status) {
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Constructor for LOADING an existing task from the database
    public Task(int id, String title, String type, String dueDate, String status) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return title + " (" + type + ") - Due: " + dueDate + " [" + status + "]";
    }
}