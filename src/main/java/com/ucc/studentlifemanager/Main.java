package com.ucc.studentlifemanager;

import com.ucc.studentlifemanager.db.DatabaseHelper;
import com.ucc.studentlifemanager.ui.LoginScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the database (creates the .db file and tables if needed)
        DatabaseHelper.initializeDatabase();

        Scene scene = new Scene(LoginScreen.build(primaryStage), 450, 350);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());

        primaryStage.setTitle("Student Life Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}