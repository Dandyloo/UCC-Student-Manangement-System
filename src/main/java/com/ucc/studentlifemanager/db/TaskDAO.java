package com.ucc.studentlifemanager.db;

import com.ucc.studentlifemanager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (title, type, due_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getType());
            stmt.setString(3, task.getDueDate());
            stmt.setString(4, task.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("type"),
                        rs.getString("due_date"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }

        return tasks;
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, type = ?, due_date = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getType());
            stmt.setString(3, task.getDueDate());
            stmt.setString(4, task.getStatus());
            stmt.setInt(5, task.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }
}