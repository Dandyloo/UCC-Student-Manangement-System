package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.TaskDAO;
import com.ucc.studentlifemanager.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProgressScreen {

    public static VBox build() {
        Label title = new Label("Progress");
        title.getStyleClass().add("screen-title");

        TaskDAO taskDAO = new TaskDAO();
        List<Task> allTasks = taskDAO.getAllTasks();

        long completedCount = allTasks.stream()
                .filter(t -> t.getStatus().equalsIgnoreCase("Completed"))
                .count();
        long pendingCount = allTasks.size() - completedCount;

        double completionRatio = allTasks.isEmpty() ? 0 : (double) completedCount / allTasks.size();

        Label completionLabel = new Label(
                "Tasks completed: " + completedCount + " of " + allTasks.size()
        );

        ProgressBar progressBar = new ProgressBar(completionRatio);
        progressBar.setPrefWidth(300);

        Label percentLabel = new Label(String.format("%.0f%% complete", completionRatio * 100));

        VBox progressSection = new VBox(8, completionLabel, progressBar, percentLabel);

        PieChart.Data completedSlice = new PieChart.Data("Completed", completedCount);
        PieChart.Data pendingSlice = new PieChart.Data("Pending", pendingCount);

        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(completedSlice, pendingSlice);
        pieChart.setTitle("Task Status Breakdown");
        pieChart.setPrefSize(350, 300);

        HBox chartRow = new HBox(30, progressSection, pieChart);
        chartRow.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(20, title, chartRow);
        layout.setPadding(new Insets(30));

        return layout;
    }
}