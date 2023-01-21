package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.JDBC;

import java.sql.*;
import java.util.Arrays;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class AppointmentDAO {

    /**
     * Gets all appointments from the database.
     * @return Appointments in an ObservableList.
     */
    public static ObservableList<Appointment> getAllAppointments (){
        ObservableList<Appointment> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment a = new Appointment(apptId, title, description, location, type, start.toLocalDateTime(),
                        end.toLocalDateTime(), customerId, userId, contactId);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Pulls all appointments that occur within the current week.
     * @return Appointments in an ObservableList.
     */
    public static ObservableList<Appointment> getCurrentWeekAppts (){
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE YEARWEEK(NOW()) = YEARWEEK(Start) AND YEAR(NOW()) = YEAR(Start)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment a = new Appointment(apptId, title, description, location, type, start.toLocalDateTime(),
                        end.toLocalDateTime(), customerId, userId, contactId);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Pulls all appointments that occur within the current month.
     * @return Appointments in an ObservableList.
     */
    public static ObservableList<Appointment> getCurrentMonthAppts() {
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(NOW()) = MONTH(Start) AND YEAR(NOW()) = YEAR(Start)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment a = new Appointment(apptId, title, description, location, type, start.toLocalDateTime(),
                        end.toLocalDateTime(), customerId, userId, contactId);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Updates the appointment database with the appointment passed in the parameter.
     * @param appointment Updated appointment
     */
    public static void updateAppointment(Appointment appointment) {

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?," +
                    " Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment.getName());
            preparedStatement.setString(2, appointment.getApptDescription());
            preparedStatement.setString(3, appointment.getApptLocation());
            preparedStatement.setString(4, appointment.getApptType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getApptStartDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getApptEndDateTime()));
            preparedStatement.setInt(7, appointment.getApptCustomerId());
            preparedStatement.setInt(8, appointment.getApptUserId());
            preparedStatement.setInt(9, appointment.getApptContactId());
            preparedStatement.setInt(10, appointment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Searched for the passed appointment and deletes it from the database.
     * @param apptId  passed appointment id
     */
    public static void deleteAppointment(int apptId) {
        try {
            String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, apptId);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Creates a new appointment with the data from the passed appointment.
     * @param appointment Passed appointment.
     */
    public static void createAppointment(Appointment appointment) {

        try {
            String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End," +
                    " Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment.getName());
            preparedStatement.setString(2, appointment.getApptDescription());
            preparedStatement.setString(3, appointment.getApptLocation());
            preparedStatement.setString(4, appointment.getApptType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getApptStartDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getApptEndDateTime()));
            preparedStatement.setInt(7, appointment.getApptCustomerId());
            preparedStatement.setInt(8, appointment.getApptUserId());
            preparedStatement.setInt(9, appointment.getApptContactId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    /**
     * Returns a list of Appointments that have the same start date as the current date.
     * @param id passed appointment id
     * @return Appointments in an ObservableList
     */
    public static ObservableList<Appointment> sameDateApptList (int id  ) {
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE CAST(Start AS date) = cast(current_timestamp() AS date) AND User_ID = ?;";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment a = new Appointment(apptId, title, description, location, type, start.toLocalDateTime(),
                        end.toLocalDateTime(), customerId, userId, contactId);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Checks if there are any appointments with the passed customer Id
     * @param customerId passed customer ID
     * @return boolean if there is an appointment or not
     */
    public static int checkForCustomerAppointmentLinks (int customerId){
        int check = 0;
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                check = resultSet.getInt("Appointment_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return check;
    }

    /**
     * Returns an ObservableList that contain a list of appointment types.
     * @return ObservableList of appointment types
     */
    public static ObservableList<String> getApptTypes() {
        ObservableList<String> t = FXCollections.observableArrayList();
        String[] types = new String[]{
                "Customer Introduction",
                "Quote/Bid/Tender",
                "Project Development",
                "Project Execution",
                "Risk/Roadblock",
                "Process Improvement",
                "Customer Check-in",
                "Customer Feedback",
                "Quality Improvement",
                "Lessons Learned",
                "Project Wrap-up"};
        t.addAll(Arrays.asList(types));
        return t;
    }
}
