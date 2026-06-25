package com.ucc.studentlifemanager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileScreen {

    public static VBox build(Stage stage) {
        Label title = new Label("Profile");
        title.getStyleClass().add("screen-title");

        // Student info is static for now — could later be loaded from a real
        // student record created at login/registration
        Label nameLabel = new Label("Name: Student");
        Label idLabel = new Label("Student ID: (not yet linked to login)");
        Label programmeLabel = new Label("Programme: BSc. Computer Science");

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("secondary-button");
        logoutButton.setOnAction(e -> {
            Scene loginScene = new Scene(LoginScreen.build(stage), 450, 350);
            loginScene.getStylesheets().add(ProfileScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(loginScene);
        });

        VBox layout = new VBox(12, title, nameLabel, idLabel, programmeLabel, logoutButton);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(30));

        return layout;
    }
}