package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.Division;
import utilities.SceneMovements;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates new customer and saves them to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the data meet the
 * specifications.
 */
public class NewCustomer implements Initializable {
    public TextField newCustomerName;
    public TextField newAddress;
    public TextField newPostalCode;
    public TextField newPhoneNumber;
    public ComboBox<Country> newCountryCombo;
    public ComboBox<Division> newDivisionCombo;
    ObservableList<Country> countries = CountryDAO.getAllCountries();
    Country country = null;
    Division division = null;

    /**
     *Initializes the combo boxes in the <code>NewCustomer</code> form. The <code>newDivisionCombo</code> is set to
     * invisible.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newDivisionCombo.setVisible(false);
        newCountryCombo.setItems(countries);
        newCountryCombo.setVisibleRowCount(5);
        newCountryCombo.setPromptText("Select country.");
    }

    /**
     * Sets <code>newDivisionCombo</code> to visible after a selection is made in the <code>newCountryCombo</code>.
     * Filters the list of divisions in the <code>newDivisionCombo</code> combo box based on the country selection.
     * @param actionEvent Pressing the #newCountrySelection combo box
     */
    public void newCountrySelection(ActionEvent actionEvent) {
        if (newCountryCombo != null ) {
            newDivisionCombo.setVisible(true);
        }
        assert newCountryCombo != null;
        Country selectedCountry = newCountryCombo.getSelectionModel().getSelectedItem();
        newDivisionCombo.setItems(DivisionDAO.filterDivisionCombo(selectedCountry.getId()));
        newDivisionCombo.setVisibleRowCount(5);
        newDivisionCombo.setPromptText("Select division.");
    }

    /**
     * Verifies that all fields are completed. If data is missing, an alert shows requesting user to complete all of the
     * fields.
     * Pulls data from the fields and saves to the customer table in the database.
     * Alerts the user that the customer information was saved.
     * Opens the <code>CustomerList</code> fxml.
     * @param actionEvent Pressing the #onActSaveCustList button
     */
    public void onActSaveCustList(ActionEvent actionEvent) throws IOException {


        try {
            String name = String.valueOf(newCustomerName.getText());
            String address = String.valueOf(newAddress.getText());
            String postalCode = String.valueOf(newPostalCode.getText());
            String phone = String.valueOf(newPhoneNumber.getText());
            country = newCountryCombo.getValue();
            division = newDivisionCombo.getValue();

            if (name.isEmpty() | address.isEmpty() | postalCode.isEmpty() | phone.isEmpty() | country == null | division == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("");
                alert.showAndWait();
            } else {
                Customer c = new Customer(name, address, postalCode, phone, country.getId(), division.getId());
                CustomerDAO.createCustomer(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer information saved!", ButtonType.OK);
                alert.showAndWait();
                SceneMovements.goToPage(actionEvent, "CustomerList", 1000, 600, "Customer List");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alerts the user that any data in the form will be lost.
     * Opens the <code>CustomerList</code> fxml if the user selects "Yes".
     * @param actionEvent Pressing the #onActNoSaveCustList button
     */
    public void onActNoSaveCustList(ActionEvent actionEvent) throws IOException {
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
