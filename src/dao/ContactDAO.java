package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class ContactDAO {

    /**
     * Gets all contacts from the database.
     * @return Contacts in an ObservableList.
     */
    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");

                Contact c = new Contact(contactId, contactName, contactEmail);
                contactList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }

    /**
     * Gets one contacts from the database that matches the passed id.
     * @param id Passed customer Id
     * @return Contact
     */
    public static Contact getContact(int id) {
        Contact contact = null;
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                contact = new Contact(contactId,contactName, contactEmail);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }
}
