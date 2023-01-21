package com.main;


import dao.CustomerDAO;
import dao.ReportsDAO;
import utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

/*
  @author Patty Kelly
 * @version 2.0
 */

/**
 * This application creates a software package that manages a SQL database used for collecting customer data
 * and appointment schedules. The database also includes user passwords and names which the software refers to
 * for validating access to the program. The software application creates, updates, and deletes entries in the SQL
 * database for customers and appointments only. User, contact, country, and division data is used for reference
 * only by the program.
 */
public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginActivity.fxml")));
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
