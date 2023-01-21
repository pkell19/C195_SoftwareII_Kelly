package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class CountryDAO {

    /**
     * Gets all countries from the database.
     * @return Countries in an ObservableList.
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String country = resultSet.getString("Country");

                Country c = new Country(countryId, country);
                countryList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }

    /**
     * Gets one country from the database that matches the passed id.
     * @param id Passed country Id
     * @return Country
     */
    public static Country getCountry(int id) {
        Country country = null;
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                country = new Country(countryId, countryName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }
}
