package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.LectureDAO;
import com.ucc.studentlifemanager.model.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LecturesScreen {

    private static LectureDAO lectureDAO = new LectureDAO();
    private static TableView<Lecture> table;
    private static Lecture selectedLecture = null;

    public static VBox build() {
        Label title = new Label("Lectures");
        title.getStyleClass().add("screen-title");

        table = new TableView<>();

        TableColumn<Lecture, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Lecture, String> dayCol = new TableColumn<>("Day");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));

        TableColumn<Lecture, String> startCol = new TableColumn<>("Start");
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Lecture, String> endCol = new TableColumn<>("End");
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Lecture, String> venueCol = new TableColumn<>("Venue");
        venueCol.setCellValueFactory(new PropertyValueFactory<>("venue"));

        table.getColumns().addAll(courseCol, dayCol, startCol, endCol, venueCol);

        TextField searchField = new TextField();
        searchField.setPromptText("Search by course name...");

        TextField courseField = new TextField();
        courseField.setPromptText("Course name");

        TextField dayField = new TextField();
        dayField.setPromptText("Day (e.g. Monday)");

        TextField startField = new TextField();
        startField.setPromptText("Start time (e.g. 08:00)");

        TextField endField = new TextField();
        endField.setPromptText("End time (e.g. 10:00)");

        TextField venueField = new TextField();
        venueField.setPromptText("Venue");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setWrapText(true);

        Button saveButton = new Button("Add Lecture");
        saveButton.getStyleClass().add("primary-button");

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedLecture = newVal;
                courseField.setText(newVal.getCourseName());
                dayField.setText(newVal.getDayOfWeek());
                startField.setText(newVal.getStartTime());
                endField.setText(newVal.getEndTime());
                venueField.setText(newVal.getVenue());
                saveButton.setText("Update Lecture");
            }
        });

        saveButton.setOnAction(e -> {
            if (courseField.getText().isBlank() || dayField.getText().isBlank()
                    || startField.getText().isBlank() || endField.getText().isBlank()) {
                errorLabel.setText("Please fill in all required fields.");
                return;
            }

            if (selectedLecture == null) {
                // Prevent adding an exact duplicate (same course, day, and start time)
                if (lectureDAO.lectureExists(courseField.getText().trim(), dayField.getText().trim(), startField.getText().trim())) {
                    errorLabel.setText("This lecture already exists.");
                    return;
                }

                Lecture newLecture = new Lecture(
                        courseField.getText().trim(),
                        dayField.getText().trim(),
                        startField.getText().trim(),
                        endField.getText().trim(),
                        venueField.getText().trim()
                );
                lectureDAO.addLecture(newLecture);
            } else {
                selectedLecture.setCourseName(courseField.getText().trim());
                selectedLecture.setDayOfWeek(dayField.getText().trim());
                selectedLecture.setStartTime(startField.getText().trim());
                selectedLecture.setEndTime(endField.getText().trim());
                selectedLecture.setVenue(venueField.getText().trim());
                lectureDAO.updateLecture(selectedLecture);
            }

            refreshTable(searchField.getText());
            clearForm(courseField, dayField, startField, endField, venueField);
            saveButton.setText("Add Lecture");
            errorLabel.setText("");
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().add("secondary-button");
        deleteButton.setOnAction(e -> {
            Lecture selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                errorLabel.setText("Please select a lecture to delete.");
                return;
            }
            lectureDAO.deleteLecture(selected.getId());
            refreshTable(searchField.getText());
            clearForm(courseField, dayField, startField, endField, venueField);
            saveButton.setText("Add Lecture");
            errorLabel.setText("");
        });

        Button clearButton = new Button("Clear Form");
        clearButton.getStyleClass().add("secondary-button");
        clearButton.setOnAction(e -> {
            clearForm(courseField, dayField, startField, endField, venueField);
            saveButton.setText("Add Lecture");
            errorLabel.setText("");
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> refreshTable(newVal));

        refreshTable("");

        HBox formRow1 = new HBox(10, courseField, dayField);
        HBox formRow2 = new HBox(10, startField, endField, venueField);
        HBox buttonRow = new HBox(10, saveButton, deleteButton, clearButton);

        VBox layout = new VBox(15, title, searchField, table, formRow1, formRow2, buttonRow, errorLabel);
        layout.setPadding(new Insets(30));

        return layout;
    }

    private static void refreshTable(String searchTerm) {
        ObservableList<Lecture> data;
        if (searchTerm == null || searchTerm.isBlank()) {
            data = FXCollections.observableArrayList(lectureDAO.getAllLectures());
        } else {
            data = FXCollections.observableArrayList(
                    lectureDAO.getAllLectures().stream()
                            .filter(lecture -> lecture.getCourseName().toLowerCase().contains(searchTerm.toLowerCase()))
                            .toList()
            );
        }
        table.setItems(data);
    }

    private static void clearForm(TextField course, TextField day, TextField start, TextField end, TextField venue) {
        course.clear();
        day.clear();
        start.clear();
        end.clear();
        venue.clear();
        selectedLecture = null;
    }
}