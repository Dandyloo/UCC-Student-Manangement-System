package com.ucc.studentlifemanager.db;

import com.ucc.studentlifemanager.model.Lecture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations for Lecture objects:
 * Create, Read, Update, Delete (CRUD).
 */
public class LectureDAO {

    // CREATE — adds a new lecture to the database
    public void addLecture(Lecture lecture) {
        String sql = "INSERT INTO lectures (course_name, day_of_week, start_time, end_time, venue) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lecture.getCourseName());
            stmt.setString(2, lecture.getDayOfWeek());
            stmt.setString(3, lecture.getStartTime());
            stmt.setString(4, lecture.getEndTime());
            stmt.setString(5, lecture.getVenue());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding lecture: " + e.getMessage());
        }
    }

    // READ — returns all lectures currently in the database
    public List<Lecture> getAllLectures() {
        List<Lecture> lectures = new ArrayList<>();
        String sql = "SELECT * FROM lectures";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lecture lecture = new Lecture(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("day_of_week"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("venue")
                );
                lectures.add(lecture);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching lectures: " + e.getMessage());
        }

        return lectures;
    }

    // UPDATE — modifies an existing lecture, matched by its id
    public void updateLecture(Lecture lecture) {
        String sql = "UPDATE lectures SET course_name = ?, day_of_week = ?, start_time = ?, end_time = ?, venue = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lecture.getCourseName());
            stmt.setString(2, lecture.getDayOfWeek());
            stmt.setString(3, lecture.getStartTime());
            stmt.setString(4, lecture.getEndTime());
            stmt.setString(5, lecture.getVenue());
            stmt.setInt(6, lecture.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating lecture: " + e.getMessage());
        }
    }

    // DELETE — removes a lecture by its id
    public void deleteLecture(int id) {
        String sql = "DELETE FROM lectures WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting lecture: " + e.getMessage());
        }
    }

    // Checks whether a lecture with the same course, day, and start time already exists.
    // Used to prevent accidental duplicate entries.
    public boolean lectureExists(String courseName, String dayOfWeek, String startTime) {
        String sql = "SELECT COUNT(*) FROM lectures WHERE course_name = ? AND day_of_week = ? AND start_time = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseName);
            stmt.setString(2, dayOfWeek);
            stmt.setString(3, startTime);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking for duplicate lecture: " + e.getMessage());
        }
        return false;
    }
}