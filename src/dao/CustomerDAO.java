package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utilities.JDBC;

import java.sql.*;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class CustomerDAO {

    /**
     * Gets all customers from the database.
     * @return Customers in an ObservableList.
     */
    public static ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID ORDER BY Customer_ID ASC";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customers.Customer_ID");
                String name = resultSet.getString("customers.Customer_Name");
                String address = resultSet.getString("customers.Address");
                String postalCode = resultSet.getString("customers.Postal_Code");
                String phone = resultSet.getString("customers.Phone");
                int countryId = resultSet.getInt("countries.Country_ID");
                int divisionId = resultSet.getInt("customers.Division_ID");

                Customer c = new Customer(customerId, name, address, postalCode, phone, countryId, divisionId);
                customerList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Gets one customer from the database that matches the passed id.
     * @param id Passed customer Id
     * @return Customer
     */
    public static Customer getCustomer(int id) {
        Customer customer = null;
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                int divisionId = resultSet.getInt("Division_ID");
                customer = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    /**
     * Updates the customer database with the customer passed in the parameter.
     * @param customer Updated customer
     */
    public static void updateCustomer(Customer customer) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getCustomerPostalCode());
            preparedStatement.setString(4, customer.getCustomerPhone());
            preparedStatement.setInt(5, customer.getDivisionId());
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.executeUpdate();

            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Total" + preparedStatement.getUpdateCount() + "rows updated.");
            } else  {
                System.out.println("No rows update.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Searches for the passed customer ID and deletes it from the database.
     * @param customerId  passed customer id
     */
    public static void deleteCustomer(int customerId) {
        try {
            String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Creates a new customer with the data from the passed customer.
     * @param customer Passed customer.
     */
    public static void createCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getCustomerPostalCode());
            preparedStatement.setString(4, customer.getCustomerPhone());
            preparedStatement.setInt(5, customer.getDivisionId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
