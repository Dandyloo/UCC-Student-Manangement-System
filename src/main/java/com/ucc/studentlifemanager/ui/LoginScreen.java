package com.ucc.studentlifemanager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginScreen {

    public static VBox build(Stage stage) {
        ImageView logo = new ImageView(new Image(LoginScreen.class.getResourceAsStream("/ucc-logo.png")));
        logo.setFitWidth(80);
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);

        Label title = new Label("UCC Student Life Manager");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));


        Label subtitle = new Label("Welcome back! Please sign in.");

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        studentIdField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("primary-button");

        loginButton.setOnAction(e -> {
            String studentId = studentIdField.getText().trim();
            String password = passwordField.getText().trim();

            // Validation — give a specific message depending on what's missing
            if (studentId.isEmpty() && password.isEmpty()) {
                errorLabel.setText("Please enter your Student ID and Password.");
                return;
            } else if (studentId.isEmpty()) {
                errorLabel.setText("Please enter your Student ID.");
                return;
            } else if (password.isEmpty()) {
                errorLabel.setText("Please enter your Password.");
                return;
            }

            errorLabel.setText("");
            System.out.println("Login attempted with ID: " + studentId);

            // Switch to the main app shell (sidebar + dashboard)
            MainLayout mainLayout = new MainLayout(stage);
            Scene mainScene = new Scene(mainLayout.getRoot(), 900, 600);
            mainScene.getStylesheets().add(LoginScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(mainScene);
        });

        VBox layout = new VBox(12, logo, title, subtitle, studentIdField, passwordField, loginButton, errorLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        return layout;
    }
}