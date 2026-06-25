package com.ucc.studentlifemanager.ui;

import com.ucc.studentlifemanager.db.UserDAO;
import com.ucc.studentlifemanager.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SignupScreen {

    public static VBox build(Stage stage) {
        ImageView logo = new ImageView(new Image(SignupScreen.class.getResourceAsStream("/ucc-logo.png")));
        logo.setFitWidth(70);
        logo.setFitHeight(70);
        logo.setPreserveRatio(true);

        Label title = new Label("Create Your Account");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setMaxWidth(260);

        TextField idField = new TextField();
        idField.setPromptText("Student ID (e.g. PS/CSC/23/0247)");
        idField.setMaxWidth(260);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(260);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(260);

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setWrapText(true);

        Button signupButton = new Button("Sign Up");
        signupButton.getStyleClass().add("primary-button");

        Button backToLoginButton = new Button("Already have an account? Login");
        backToLoginButton.getStyleClass().add("secondary-button");

        UserDAO userDAO = new UserDAO();

        signupButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String studentId = idField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            if (name.isEmpty() || studentId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                errorLabel.setText("Please fill in all fields.");
                return;
            }

            if (!isValidIndexNumber(studentId)) {
                errorLabel.setText("Please enter a valid index number, e.g. PS/CSC/23/0247.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match.");
                return;
            }

            User newUser = new User(name, studentId, password);
            boolean success = userDAO.registerUser(newUser);

            if (!success) {
                errorLabel.setText("That Student ID is already registered.");
                return;
            }

            // Success dialog — a real popup, not just an inline label
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Account Created");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Your account has been created successfully. Please log in.");
            successAlert.showAndWait();

            // Send the user to the Login screen
            Scene loginScene = new Scene(LoginScreen.build(stage), 450, 400);
            loginScene.getStylesheets().add(SignupScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(loginScene);
        });

        backToLoginButton.setOnAction(e -> {
            Scene loginScene = new Scene(LoginScreen.build(stage), 450, 400);
            loginScene.getStylesheets().add(SignupScreen.class.getResource("/app.css").toExternalForm());
            stage.setScene(loginScene);
        });

        VBox layout = new VBox(12, logo, title, nameField, idField, passwordField, confirmPasswordField,
                signupButton, errorLabel, backToLoginButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        return layout;
    }

    // Validates index number format like PS/CSC/23/0247
    private static boolean isValidIndexNumber(String id) {
        return id.matches("[A-Za-z]{2}/[A-Za-z]{2,4}/\\d{2}/\\d{4}");
    }
}