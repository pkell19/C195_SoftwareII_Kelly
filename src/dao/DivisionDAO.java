package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds all methods used for accessing the SQL database.
 */
public class DivisionDAO {

    /**
     * Returns filtered list of divisions from the database that have the passed country id.
     * @return Divisions in an ObservableList.
     */
    public static ObservableList<Division> filterDivisionCombo (int countryId) {
        ObservableList<Division> filteredDivision = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions, countries" +
                    " WHERE first_level_divisions.Country_ID = countries.Country_ID" +
                    " AND countries.Country_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, countryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("first_level_divisions.Division_ID");
                String division = resultSet.getString("first_level_divisions.Division");
                Division d = new Division(divisionId, division);
                filteredDivision.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filteredDivision;
    }

    /**
     * Returns a single division from the database that has the passed division id.
     * @return Division.
     */
    public static Division getDivision(int id) {
        Division division = null;
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_Id");
                division = new Division(divisionId, divisionName, countryId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return division;
    }
}
