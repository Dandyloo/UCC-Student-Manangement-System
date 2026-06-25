package com.ucc.studentlifemanager.db;

import com.ucc.studentlifemanager.model.User;

import java.sql.*;

public class UserDAO {

    // Creates a new account. Returns false if the student ID is already taken.
    public boolean registerUser(User user) {
        if (studentIdExists(user.getStudentId())) {
            return false;
        }

        String sql = "INSERT INTO users (full_name, student_id, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getStudentId());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // Checks credentials at login time. Returns the User if valid, null if not.
    public User authenticate(String studentId, String password) {
        String sql = "SELECT * FROM users WHERE student_id = ? AND password = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("student_id"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        return null; // no matching account found
    }

    private boolean studentIdExists(String studentId) {
        String sql = "SELECT COUNT(*) FROM users WHERE student_id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking student ID: " + e.getMessage());
        }
        return false;
    }
}