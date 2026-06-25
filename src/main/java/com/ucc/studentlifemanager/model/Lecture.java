package com.ucc.studentlifemanager.model;

/**
 * Represents a single lecture entry.
 * This class mirrors one row in the "lectures" table.
 */
public class Lecture {

    private int id;            // matches the database's auto-incremented id
    private String courseName;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String venue;

    // Constructor used when creating a NEW lecture (no id yet — the database assigns it)
    public Lecture(String courseName, String dayOfWeek, String startTime, String endTime, String venue) {
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    // Constructor used when LOADING an existing lecture from the database (id already exists)
    public Lecture(int id, String courseName, String dayOfWeek, String startTime, String endTime, String venue) {
        this.id = id;
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    // Getters — used to read each field
    public int getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getVenue() { return venue; }

    // Setters — used to update each field
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setVenue(String venue) { this.venue = venue; }

    // Makes the object print nicely if we ever need to debug it
    @Override
    public String toString() {
        return courseName + " (" + dayOfWeek + ", " + startTime + "-" + endTime + ") @ " + venue;
    }
}