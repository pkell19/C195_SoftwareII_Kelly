package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Report;
import utilities.JDBC;
import utilities.TimeMethods;

import java.sql.*;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class ReportsDAO {
    /**
     * Returns ObservableList with appointment types, total count, and year that are a start date that match the passed
     * month and year.
     * @param month Passed month
     * @param year Passed year
     * @return ObservableList of appointments
     */
    public static ObservableList <Report> totalApptsByTypeMonth (String month, String year) {
        Timestamp m = TimeMethods.convertStringtoMonth(month);
        Timestamp y = TimeMethods.convertStringtoYear(year);
        ObservableList <Report> reportReturn = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, COUNT(Type) AS \"Count\", YEAR(Start) AS \"Year\" FROM appointments WHERE MONTH(Start) = MONTH(?) AND YEAR(Start) = YEAR(?) GROUP BY Type, YEAR(start)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, m);
            preparedStatement.setTimestamp(2, y);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                int count = resultSet.getInt("Count");
                Report r = new Report(type, count);
                reportReturn.add(r);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reportReturn;
    }

    /**
     * Returns the total number of customers in the database.
     * @return integer with total number of customers
     */
    public static int totalCustomer() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(Customer_ID) AS \"Count\" FROM client_schedule.customers";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("Count");
            }
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Returns an ObservableList of appointments that match with the passed contact.
     * @param contact Passed contact
     * @return ObservableList of appointments
     */
    public static ObservableList <Appointment> apptListByContact (Contact contact) {
        int id = contact.getId();
        ObservableList <Appointment> reportReturn = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customerId = resultSet.getInt("Customer_ID");

                Appointment a = new Appointment(apptId, title, description, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId);
                reportReturn.add(a);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reportReturn;
    }
}
