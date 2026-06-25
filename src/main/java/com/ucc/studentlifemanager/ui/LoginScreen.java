package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.UserDAO;
import com.ucc.studentlifemanager.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
        errorLabel.setWrapText(true);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("primary-button");

        Button goToSignupButton = new Button("Don't have an account? Sign Up");
        goToSignupButton.getStyleClass().add("secondary-button");

        UserDAO userDAO = new UserDAO();

        loginButton.setOnAction(e -> {
            String studentId = studentIdField.getText().trim();
            String password = passwordField.getText().trim();

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

            User authenticatedUser = userDAO.authenticate(studentId, password);

            if (authenticatedUser == null) {
                errorLabel.setText("Incorrect Student ID or Password.");
                return;
            }

            errorLabel.setText("");

            MainLayout mainLayout = new MainLayout(stage, authenticatedUser);
            Scene mainScene = new Scene(mainLayout.getRoot(), 900, 600);
            mainScene.getStylesheets().add(LoginScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(mainScene);
        });

        goToSignupButton.setOnAction(e -> {
            Scene signupScene = new Scene(SignupScreen.build(stage), 450, 500);
            signupScene.getStylesheets().add(LoginScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(signupScene);
        });

        VBox layout = new VBox(12, logo, title, subtitle, studentIdField, passwordField,
                loginButton, errorLabel, goToSignupButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        return layout;
    }
}