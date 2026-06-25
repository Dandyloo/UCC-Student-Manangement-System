package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileScreen {

    public static VBox build(Stage stage, User currentUser) {
        Label title = new Label("Profile");
        title.getStyleClass().add("screen-title");

        Label nameLabel = new Label("Name: " + currentUser.getFullName());
        Label idLabel = new Label("Student ID: " + currentUser.getStudentId());
        Label programmeLabel = new Label("Programme: BSc. Computer Science");

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("secondary-button");
        logoutButton.setOnAction(e -> {
            Scene loginScene = new Scene(LoginScreen.build(stage), 450, 400);
            loginScene.getStylesheets().add(ProfileScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(loginScene);
        });

        VBox layout = new VBox(12, title, nameLabel, idLabel, programmeLabel, logoutButton);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(30));

        return layout;
    }
}