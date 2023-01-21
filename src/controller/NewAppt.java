package controller;

import dao.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import utilities.SceneMovements;
import utilities.TimeMethods;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates new appointments and saves them to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the selected times meet the
 * specifications.
 */
public class NewAppt implements Initializable {

    public TextField newApptId;
    public TextField newApptTitle;
    public TextField newApptDescription;
    public TextField newApptLocation;
    public ComboBox <Customer> newApptCustomerCombo;
    public DatePicker newApptDatePicker;
    public ComboBox <LocalTime> newApptStartCombo;
    public ComboBox <LocalTime> newApptEndCombo;
    public ComboBox <User> newApptUserCombo;
    public ComboBox <Contact> newApptContactCombo;
    public ComboBox <String> newApptTypeCombo;

    ObservableList<String> types = AppointmentDAO.getApptTypes();
    ObservableList<Customer> customerList = CustomerDAO.getAllCustomer();
    ObservableList<Contact> contactList = ContactDAO.getAllContacts();
    ObservableList<User> userList = UserDAO.getAllUsers();
    ObservableList<LocalTime> times = TimeMethods.timeList();
    LocalDate date = null;
    LocalTime start = null;
    LocalTime end =  null;
    Customer customer = null;
    User user = null;
    Contact contact = null;

    /**
     * Initializes the combo boxes in the <code>NewAppt</code> form.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting type combo box
        newApptTypeCombo.setItems(types);
        newApptTypeCombo.setVisibleRowCount(5);
        newApptTypeCombo.setPromptText("Select type.");

        //Setting start time combo box
        newApptStartCombo.setItems(times);
        newApptStartCombo.setVisibleRowCount(5);
        newApptStartCombo.setPromptText("Enter start time.");

        //Setting end time combo box
        newApptEndCombo.setItems(times);
        newApptEndCombo.setVisibleRowCount(5);
        newApptEndCombo.setPromptText("Enter end time.");

        //Setting customer combo box
        newApptCustomerCombo.setItems(customerList);
        newApptCustomerCombo.setVisibleRowCount(5);
        newApptCustomerCombo.setPromptText("Select customer.");

        //Setting user combo box
        newApptUserCombo.setItems(userList);
        newApptUserCombo.setVisibleRowCount(5);
        newApptUserCombo.setPromptText("Select user.");

        //Setting contact combo box
        newApptContactCombo.setItems(contactList);
        newApptContactCombo.setVisibleRowCount(5);
        newApptContactCombo.setPromptText("Select contact.");
    }

    /**
     * Collects the data from the text fields, date picker, and combo boxes and makes the following checks:
     *  - All fields are completed.
     *  - The start time is before the end time.
     *  - The customer ID does not have any conflicting appointments.
     *  - The start and/or end times do not fall outside of the EST business hours.
     *  Saves the new appointment to the database and shows alert stating that the save was successful.
     * @param actionEvent Pressing the #saveNewAppt button.
     */
    public void saveNewAppt(ActionEvent actionEvent) {

        try {
            String title = String.valueOf(newApptTitle.getText());
            String description = String.valueOf(newApptDescription.getText());
            String location = String.valueOf(newApptLocation.getText());
            String type = String.valueOf(newApptTypeCombo.getValue());
            date = newApptDatePicker.getValue();
            start = newApptStartCombo.getValue();
            end = newApptEndCombo.getValue();
            customer = newApptCustomerCombo.getValue();
            user = newApptUserCombo.getValue();
            contact = newApptContactCombo.getValue();

            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);


            if (title.isEmpty() | description.isEmpty() | location.isEmpty() | type.isEmpty() | date == null | start == null | end == null | customer == null | user == null | contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("Every Field Must Be Completed");
                alert.showAndWait();
            } else if (start.isAfter(end)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start time must come before the end time.", ButtonType.OK);
                alert.setTitle("Start Time Before End Time");
                alert.showAndWait();
            } else if (Appointment.checkConflictingAppointment(customer.getId(), startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This customer has a conflicting appointment. Please pick a different date and/or time.", ButtonType.OK);
                alert.setTitle("Conflicting Appointment");
                alert.showAndWait();
            } else if (startDateTime.equals(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments can't have the same start and end times.", ButtonType.OK);
                alert.setTitle("Conflicting Appointment");
                alert.showAndWait();
            }else if (TimeMethods.isOutsideBusinessHours(date, start, end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment hours must be between 8:00 AM and 10:00 PM EST.", ButtonType.OK);
                alert.setTitle("Not During Business Hours");
                alert.showAndWait();
            } else {
                LocalDateTime startDate = LocalDateTime.of(date, start);
                LocalDateTime endDate = LocalDateTime.of(date, end);
                Appointment a = new Appointment(title, description, location, type, startDate, endDate, customer.getId(), user.getId(), contact.getId());
                AppointmentDAO.createAppointment(a);
                SceneMovements.goToPage(actionEvent, "AppointmentList", 1200, 600, "Appointment List");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment saved!", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows alert verifying that the user wants to leave the new appointment form.
     * Opens the <code>AppointmentList</code> fxml.
     * @param actionEvent Pressing the #cancelNewAppt button.
     */
    public void cancelNewAppt(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? Data will be lost.", ButtonType.YES,ButtonType.NO);
        alert.setTitle("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            SceneMovements.goToPage(actionEvent, "AppointmentList", 1200, 600, "Appointment List");
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
        SceneMovements.goToPage(actionEvent, "ReportsList", 600, 400, "Appointment List");
    }


}
