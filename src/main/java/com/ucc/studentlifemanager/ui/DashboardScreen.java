package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.LectureDAO;
import com.ucc.studentlifemanager.db.TaskDAO;
import com.ucc.studentlifemanager.model.Lecture;
import com.ucc.studentlifemanager.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class DashboardScreen {

    public static VBox build() {
        Label title = new Label("Dashboard");
        title.getStyleClass().add("screen-title");

        Label welcome = new Label("Here's your overview for today.");

        LectureDAO lectureDAO = new LectureDAO();
        List<Lecture> lectures = lectureDAO.getAllLectures();

        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getAllTasks();
        long pendingTasks = tasks.stream()
                .filter(t -> t.getStatus().equalsIgnoreCase("Pending"))
                .count();

        HBox statsRow = new HBox(20,
                buildStatCard("Total Lectures", String.valueOf(lectures.size())),
                buildStatCard("Pending Tasks", String.valueOf(pendingTasks)),
                buildStatCard("Total Tasks", String.valueOf(tasks.size()))
        );

        // New row: calendar + upcoming sessions side by side
        HBox widgetsRow = new HBox(20, CalenderWidget.build(), buildUpcomingSessions());

        VBox layout = new VBox(20, title, welcome, statsRow, widgetsRow);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);

        return layout;
    }

    private static VBox buildStatCard(String label, String value) {
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        Label captionLabel = new Label(label);

        VBox card = new VBox(5, valueLabel, captionLabel);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(160);
        card.getStyleClass().add("stat-card");

        return card;
    }

    private static VBox buildUpcomingSessions() {
        Label header = new Label("Upcoming Sessions");
        header.getStyleClass().add("card-header");

        LectureDAO lectureDAO = new LectureDAO();
        List<Lecture> lectures = lectureDAO.getAllLectures();

        VBox listBox = new VBox(10);

        if (lectures.isEmpty()) {
            Label empty = new Label("No lectures added yet.");
            listBox.getChildren().add(empty);
        } else {
            for (Lecture lecture : lectures) {
                Label sessionLine = new Label(
                        lecture.getCourseName() + " — " + lecture.getDayOfWeek() +
                                ", " + lecture.getStartTime() + "–" + lecture.getEndTime()
                );
                sessionLine.getStyleClass().add("session-line");
                listBox.getChildren().add(sessionLine);
            }
        }

        VBox card = new VBox(12, header, listBox);
        card.getStyleClass().add("stat-card");
        card.setPrefWidth(280);

        return card;
    }
}