package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Country;
import model.Customer;
import model.Division;
import utilities.SceneMovements;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class update a customer and saves it to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the data meet the
 * specifications.
 */
public class UpdateCustomer implements Initializable {
    public TextField updateCustomerName;
    public TextField updateAddress;
    public TextField updatePostalCode;
    public TextField updatePhoneNumber;
    public ComboBox<Country> updateCountryCombo;
    public ComboBox<Division> updateDivisionCombo;
    public TextField customerId;
    ObservableList<Country> countries = CountryDAO.getAllCountries();
    private static Customer passedCustomer;
    Country country = null;
    Division division = null;

    /**
     * Passes the customer data from <code>CustomerList</code>
     * @param customer Passed customer
     */
    public static void passingTheCustomer(Customer customer){
        passedCustomer = customer;
    }

    /**
     * Initializes the tableview with the passed data from the <code>CustomerList</code> controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomerName.setText(String.valueOf(passedCustomer.getName()));
        updateAddress.setText(String.valueOf(passedCustomer.getCustomerAddress()));
        updatePostalCode.setText(String.valueOf(passedCustomer.getCustomerPostalCode()));
        updatePhoneNumber.setText(String.valueOf(passedCustomer.getCustomerPhone()));
        Country passedCountry = CountryDAO.getCountry(passedCustomer.getCountryId());
        Division passedDivision = DivisionDAO.getDivision(passedCustomer.getDivisionId());
        updateCountryCombo.setValue(passedCountry);
        updateDivisionCombo.setValue(passedDivision);
        customerId.setText(String.valueOf(passedCustomer.getId()));
    }

    /**
     * Clears the selection in the <code>updateDivisionCombo</code> combo box and sets the value to null after the
     * <code>updateCountrySelection</code> is selected.
     * Sets the <code>updateCountryCombo</code> combo box with the country list.
     * @param actionEvent Selecting the <code>updateCountryCombo</code> combo box.
     */
    public void updateCountrySelection(MouseEvent actionEvent) {
        updateDivisionCombo.getSelectionModel().clearSelection();
        updateDivisionCombo.setValue(null);
        updateCountryCombo.setItems(countries);
    }

    /**
     * Filters the list of divisions in the <code>newDivisionCombo</code> combo box based on the country selection in
     * the <code>updateCountryCombo</code> combo box.
     * @param actionEvent Selecting the <code>updateCountryCombo</code> combo box.
     */
    public void onSelectCustomer(ActionEvent actionEvent) {
        Country selectedCountry = updateCountryCombo.getSelectionModel().getSelectedItem();
        updateDivisionCombo.setItems(DivisionDAO.filterDivisionCombo(selectedCountry.getId()));
        updateDivisionCombo.setVisibleRowCount(5);
    }

    /**
     * Verifies that all fields are completed. If data is missing, an alert shows requesting user to complete all of the
     * fields.
     * Pulls data from the fields and saves to the customer table in the database.
     * Alerts the user that the customer information was saved.
     * Opens the <code>CustomerList</code> fxml.
     * @param actionEvent Pressing the #onActSaveCustList button
     */
    public void onActSaveCustList(ActionEvent actionEvent) {

        try {
            String name = String.valueOf(updateCustomerName.getText());
            String address = String.valueOf(updateAddress.getText());
            String postalCode = String.valueOf(updatePostalCode.getText());
            String phone = String.valueOf(updatePhoneNumber.getText());
            country = updateCountryCombo.getValue();
            division = updateDivisionCombo.getValue();
            int custId = Integer.parseInt(customerId.getText());

            if (name.isEmpty() | address.isEmpty() | postalCode.isEmpty() | phone.isEmpty() | country == null | division == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("");
                alert.showAndWait();
            } else {
                Customer c = new Customer(custId, name, address, postalCode, phone, country.getId(), division.getId());
                CustomerDAO.updateCustomer(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer information saved!", ButtonType.OK);
                alert.showAndWait();
                SceneMovements.goToPage(actionEvent, "CustomerList", 1000, 600, "Customer List");
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alerts the user that any data in the form will be lost.
     * Opens the <code>CustomerList</code> fxml if the user selects "Yes".
     * @param actionEvent Pressing the #onActNoSaveCustList button
     */
    public void onActNoSaveCustList (ActionEvent actionEvent) throws IOException {
        //DONE: Add alert stating customer data not saved
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? Data will be lost.", ButtonType.YES,ButtonType.NO);
        alert.setTitle("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            SceneMovements.goToPage(actionEvent, "CustomerList", 1000, 600, "Customer List");

        }
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>MainMenu</code> fxml.
     * @param actionEvent The #onActionToMainMenu button
     */
    public void onActionToMainMenu(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "MainMenu", 600, 400, "Main Menu");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>AppointmentList</code> fxml.
     * @param actionEvent The #onActionToApptList button
     */
    public void onActionToAppt(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "AppointmentList", 1200, 600, "Appointment List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>CustomerList</code> fxml.
     * @param actionEvent The #onActionToCustomer button
     */
    public void onActionToCustomer(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "CustomerList", 1000, 600, "Customer List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>ReportsList</code> fxml.
     * @param actionEvent The #onActionToReport button
     */
    public void onActionToReport(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportsList", 600, 400, "Reports List");
    }
}
