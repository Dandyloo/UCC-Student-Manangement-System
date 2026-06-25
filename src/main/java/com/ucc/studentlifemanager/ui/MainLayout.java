package com.ucc.studentlifemanager.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

/**
 * The persistent app shell shown after login.
 * Contains a sidebar (left) and a content area (center) that
 * gets swapped out depending on which screen is active.
 */
public class MainLayout {

    private final BorderPane root;
    private final Stage stage;

    public MainLayout(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();

        root.setLeft(buildSidebar());
        showDashboard(); // Dashboard is the default screen shown after login
    }

    public BorderPane getRoot() {
        return root;
    }

    private VBox buildSidebar() {
        ImageView logo = new ImageView(new Image(MainLayout.class.getResourceAsStream("/ucc-logo.png")));
        logo.setFitWidth(48);
        logo.setFitHeight(48);
        logo.setPreserveRatio(true);

        Label appTitle = new Label("UCC Life Manager");
        appTitle.getStyleClass().add("sidebar-title");

        VBox logoSection = new VBox(8, logo, appTitle);
        logoSection.setAlignment(Pos.CENTER);

        Button dashboardBtn = new Button("Dashboard");
        Button lecturesBtn = new Button("Lectures");
        Button tasksBtn = new Button("Assignments & Quizzes");
        Button progressBtn = new Button("Progress");
        Button profileBtn = new Button("Profile");

        for (Button btn : new Button[]{dashboardBtn, lecturesBtn, tasksBtn, progressBtn, profileBtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("sidebar-button");
        }

        dashboardBtn.setOnAction(e -> showDashboard());
        lecturesBtn.setOnAction(e -> showLectures());
        tasksBtn.setOnAction(e -> showTasks());
        progressBtn.setOnAction(e -> showProgress());
        profileBtn.setOnAction(e -> showProfile());

        VBox sidebar = new VBox(16, logoSection, dashboardBtn, lecturesBtn, tasksBtn, progressBtn, profileBtn);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.getStyleClass().add("sidebar");

        return sidebar;
    }

    private void showDashboard() {
        root.setCenter(DashboardScreen.build());
    }

    private void showLectures() {
        root.setCenter(LecturesScreen.build());
    }

    private void showTasks() {
        root.setCenter(TasksScreen.build());
    }

    private void showProgress() {
        root.setCenter(ProgressScreen.build());
    }

    private void showProfile() {
        root.setCenter(ProfileScreen.build(stage));
    }
}