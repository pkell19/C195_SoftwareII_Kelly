package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class UserDAO {

    /**
     * Gets all users from the database.
     * @return Users in an ObservableList.
     */
    public static ObservableList<User> getAllUsers() {

        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                User u = new User(userId, userName);
                userList.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    /**
     * Gets one user from the database that match with the passed user ID.
     * @param id Passed user ID
     * @return User
     */
    public static User getUser(int id) {
        User user = null;
        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                user = new User(userId, userName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    /**
     * Returns User that matches a user name and password with the passed data.
     * @param name Passed user name
     * @param password Passed password
     * @return Matching user
     */
    public static User checkUsernamePassword(String name, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                user = new User(userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
