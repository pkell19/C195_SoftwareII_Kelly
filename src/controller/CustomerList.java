package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import utilities.SceneMovements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller class builds and populates the Customer List tableview. Navigation buttons are
 * included in the header for easy navigation between scenes. There are also buttons for modifying existing
 * customers, adding new customers, and deleting customers. The table is initialized with all customers
 * available in the SQL database.
 */

public class CustomerList implements Initializable {
    public TableColumn <Customer, Integer> customerIdCol;
    public TableColumn <Customer, String> customerNameCol;
    public TableColumn <Customer, String> customerAddressCol;
    public TableColumn <Customer, String> customerPostalCodeCol;
    public TableColumn <Customer, String> customerPhoneCol;
    public TableColumn <Customer, Integer> customerCountryCol;
    public TableColumn <Customer, Integer> customerDivisionCol;
    public TableView<Customer> customerListTable;
    public TextField customerSearch;
    ObservableList<Customer> customerList = CustomerDAO.getAllCustomer();

    /**
     * Initializes the <code>CustomerList</code> tableview with all customers in the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerListTable.setItems(customerList);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        FilteredList<Customer> filteredCustomerList = new FilteredList<>(customerList, b -> true);
        customerSearch.textProperty().addListener(((obs, oldValue, newValue) -> filteredCustomerList.setPredicate(customer -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (String.valueOf(customer.getId()).contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getCustomerAddress().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getCustomerPostalCode().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getCustomerPhone().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(customer.getCountryId()).contains(lowerCaseFilter)) {
                return true;
            } else return String.valueOf(customer.getDivisionId()).contains(lowerCaseFilter);
        })));

        SortedList<Customer> sortedList = new SortedList<>(filteredCustomerList);
        sortedList.comparatorProperty().bind(customerListTable.comparatorProperty());
        customerListTable.setItems(sortedList);
        }

    /**
     * Alerts user if no selection was made.
     * Passes the selected customer to the <code>UpdateCustomer</code> class.
     * Opens the <code>UpdateCustomer</code> fxml.
     * @param actionEvent Pressing the modify button.
     */
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer c = customerListTable.getSelectionModel().getSelectedItem();

        if (c != null) {
            UpdateCustomer.passingTheCustomer(c);
            SceneMovements.goToPage(actionEvent, "UpdateCustomer", 800, 600, "Update Customer");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "First select the customer you want to modify");
            alert.setTitle("Select Customer");
            alert.showAndWait();
        }
    }

    /**
     * Opens the <code>NewCustomer</code> fxml.
     * @param actionEvent Pressing the New button
     */
    public void toNewCustomer(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "NewCustomer", 800, 600, "New Customer");
    }

    /**
     * Alerts user if no selection was made.
     * Checks for any appointments associated with the customer and, if one is found, will show an error alert.
     * Shows an alert verifying that the user wants to delete the selected customer.
     * Shows an alert notifying the user that the deletion was successful.
     * Deletes selected customer from the database and refreshes the <code>CustomerList</code> tableview
     * <p>
     *     <b>Lambda Expression Notification</b>
     *     This lambda expression is used to define the response method when the user selects the confirmation button.
     *     Instead of creating a separate method that is only needed a few times and calling it in this method, a lambda
     *     expression is used for cleaner coding and easier reading.
     * </p>
     * @param actionEvent Pressing the delete button.
     */
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer c = customerListTable.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "First select the customer you want to delete.");
            alert.setTitle("Select Customer");
            alert.showAndWait();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + c.getName() + "?", ButtonType.YES, ButtonType.NO);
            alert2.setTitle("Verify Deletion");
            alert2.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    if (AppointmentDAO.checkForCustomerAppointmentLinks(c.getId()) != 0) {
                        AppointmentDAO.deleteAppointment(AppointmentDAO.checkForCustomerAppointmentLinks(c.getId()));
                    }
                    CustomerDAO.deleteCustomer(c.getId());
                    customerListTable.setItems(CustomerDAO.getAllCustomer());
                    Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION, c.getName() + " has been deleted.", ButtonType.OK);
                    alert3.setTitle("Delete Completed");
                    alert3.showAndWait();
                    }
                });
        }
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>AppointmentList</code> fxml.
     * @param actionEvent The #onActionToApptList button
     */
    public void onActionToAppt(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "AppointmentList", 1200, 600, "Appointment List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>ReportsList</code> fxml.
     * @param actionEvent The #onActionToReport button
     */
    public void onActionToReport(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportsList", 600, 400, "Report List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>MainMenu</code> fxml.
     * @param actionEvent The #onActionToMainMenu button
     */
    public void onActionToMainMenu(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "MainMenu", 600, 400, "Main Menu");
    }
}
