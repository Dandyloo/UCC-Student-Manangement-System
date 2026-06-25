package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.TaskDAO;
import com.ucc.studentlifemanager.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TasksScreen {

    private static TaskDAO taskDAO = new TaskDAO();
    private static TableView<Task> table;
    private static Task selectedTask = null;

    public static VBox build() {
        Label title = new Label("Assignments & Quizzes");
        title.getStyleClass().add("screen-title");

        table = new TableView<>();

        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Task, String> dueCol = new TableColumn<>("Due Date");
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(titleCol, typeCol, dueCol, statusCol);

        TextField searchField = new TextField();
        searchField.setPromptText("Search by title...");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList("Assignment", "Quiz"));
        typeBox.setPromptText("Type");

        TextField dueDateField = new TextField();
        dueDateField.setPromptText("Due date (YYYY-MM-DD)");

        ComboBox<String> statusBox = new ComboBox<>(FXCollections.observableArrayList("Pending", "Completed"));
        statusBox.setPromptText("Status");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setWrapText(true);

        Button saveButton = new Button("Add Task");
        saveButton.getStyleClass().add("primary-button");

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedTask = newVal;
                titleField.setText(newVal.getTitle());
                typeBox.setValue(newVal.getType());
                dueDateField.setText(newVal.getDueDate());
                statusBox.setValue(newVal.getStatus());
                saveButton.setText("Update Task");
            }
        });

        saveButton.setOnAction(e -> {
            if (titleField.getText().isBlank() || typeBox.getValue() == null
                    || dueDateField.getText().isBlank() || statusBox.getValue() == null) {
                errorLabel.setText("Please fill in all fields.");
                return;
            }

            // Validate the due date is an actual date in YYYY-MM-DD format
            try {
                LocalDate.parse(dueDateField.getText().trim());
            } catch (DateTimeParseException ex) {
                errorLabel.setText("Please enter a valid date in YYYY-MM-DD format (e.g. 2026-06-30).");
                return;
            }

            if (selectedTask == null) {
                Task newTask = new Task(
                        titleField.getText().trim(),
                        typeBox.getValue(),
                        dueDateField.getText().trim(),
                        statusBox.getValue()
                );
                taskDAO.addTask(newTask);
            } else {
                selectedTask.setTitle(titleField.getText().trim());
                selectedTask.setType(typeBox.getValue());
                selectedTask.setDueDate(dueDateField.getText().trim());
                selectedTask.setStatus(statusBox.getValue());
                taskDAO.updateTask(selectedTask);
            }

            refreshTable(searchField.getText());
            clearForm(titleField, typeBox, dueDateField, statusBox);
            saveButton.setText("Add Task");
            errorLabel.setText("");
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().add("secondary-button");
        deleteButton.setOnAction(e -> {
            Task selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                errorLabel.setText("Please select a task to delete.");
                return;
            }
            taskDAO.deleteTask(selected.getId());
            refreshTable(searchField.getText());
            clearForm(titleField, typeBox, dueDateField, statusBox);
            saveButton.setText("Add Task");
            errorLabel.setText("");
        });

        Button clearButton = new Button("Clear Form");
        clearButton.getStyleClass().add("secondary-button");
        clearButton.setOnAction(e -> {
            clearForm(titleField, typeBox, dueDateField, statusBox);
            saveButton.setText("Add Task");
            errorLabel.setText("");
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> refreshTable(newVal));

        refreshTable("");

        HBox formRow = new HBox(10, titleField, typeBox, dueDateField, statusBox);
        HBox buttonRow = new HBox(10, saveButton, deleteButton, clearButton);

        VBox layout = new VBox(15, title, searchField, table, formRow, buttonRow, errorLabel);
        layout.setPadding(new Insets(30));

        return layout;
    }

    private static void refreshTable(String searchTerm) {
        ObservableList<Task> data;
        if (searchTerm == null || searchTerm.isBlank()) {
            data = FXCollections.observableArrayList(taskDAO.getAllTasks());
        } else {
            data = FXCollections.observableArrayList(
                    taskDAO.getAllTasks().stream()
                            .filter(task -> task.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                            .toList()
            );
        }
        table.setItems(data);
    }

    private static void clearForm(TextField title, ComboBox<String> type, TextField dueDate, ComboBox<String> status) {
        title.clear();
        type.setValue(null);
        dueDate.clear();
        status.setValue(null);
        selectedTask = null;
    }
}