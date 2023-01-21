package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
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
 * This class updates appointments and saves them to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the selected times meet the
 * specifications.
 */
public class UpdateAppt implements Initializable {

    public TextField updateApptTitle;
    public TextArea updateApptDescription;
    public TextField updateApptLocation;
    public ComboBox <String> updateApptTypeCombo;
    public DatePicker updateApptDatePicker;
    public ComboBox <LocalTime> updateApptStartTimeCombo;
    public ComboBox <LocalTime> updateApptEndTimeCombo;
    public ComboBox <Customer> updateApptCustomerCombo;
    public ComboBox <User> updateApptUserCombo;
    public ComboBox <Contact> updateApptContactCombo;
    public TextField updateApptId;

    private static Appointment passedAppt;
    public static void passingTheAppointment(Appointment appointment){
        passedAppt = appointment;
    }

    ObservableList<String> types = AppointmentDAO.getApptTypes();
    ObservableList<Customer> customerList = CustomerDAO.getAllCustomer();
    ObservableList<Contact> contactList = ContactDAO.getAllContacts();
    ObservableList<User> userList = UserDAO.getAllUsers();
    ObservableList<LocalTime> times = TimeMethods.timeList();
    Customer passedCustomer = null;
    User passedUser = null;
    Contact passedContact = null;
    LocalDate date = null;
    LocalTime start = null;
    LocalTime end = null;
    Customer customer = null;
    User user = null;
    Contact contact = null;

    /**
     * Initializes the combo boxes in the <code>UpdateAppt</code> form.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting title, description, and location fields
        updateApptTitle.setText(String.valueOf(passedAppt.getName()));
        updateApptDescription.setText(String.valueOf(passedAppt.getApptDescription()));
        updateApptLocation.setText(String.valueOf(passedAppt.getApptLocation()));

        //Setting type combo box
        updateApptTypeCombo.setItems(types);
        updateApptTypeCombo.setValue(passedAppt.getApptType());
        updateApptTypeCombo.setVisibleRowCount(5);

        //Setting date picker
        updateApptDatePicker.setValue(passedAppt.getApptStartDateTime().toLocalDate());

        //Setting start time combo box
        updateApptStartTimeCombo.setItems(times);
        updateApptStartTimeCombo.setValue(passedAppt.getApptStartDateTime().toLocalTime());
        updateApptStartTimeCombo.setVisibleRowCount(5);

        //Setting end time combo box
        updateApptEndTimeCombo.setItems(times);
        updateApptEndTimeCombo.setValue(passedAppt.getApptEndDateTime().toLocalTime());
        updateApptEndTimeCombo.setVisibleRowCount(5);

        //Setting customer combo box
        updateApptCustomerCombo.setItems(customerList);
        passedCustomer = CustomerDAO.getCustomer(passedAppt.getApptCustomerId());
        updateApptCustomerCombo.setValue(passedCustomer);
        updateApptCustomerCombo.setVisibleRowCount(5);

        //Setting user combo box
        updateApptUserCombo.setItems(userList);
        passedUser = UserDAO.getUser(passedAppt.getApptUserId());
        updateApptUserCombo.setValue(passedUser);
        updateApptUserCombo.setVisibleRowCount(5);

        //Setting contact combo box
        updateApptContactCombo.setItems(contactList);
        passedContact = ContactDAO.getContact(passedAppt.getApptContactId());
        updateApptContactCombo.setValue(passedContact);
        updateApptContactCombo.setVisibleRowCount(5);

        //Setting appt id field
        updateApptId.setText(String.valueOf(passedAppt.getId()));
    }

    /**
     * Collects the data from the text fields, date picker, and combo boxes and makes the following checks:
     *  - All fields are completed.
     *  - The start time is before the end time.
     *  - The customer ID does not have any conflicting appointments.
     *  - The start and/or end times do not fall outside of the EST business hours.
     *  Saves the new appointment to the database and shows alert stating that the save was successful.
     * @param actionEvent Pressing the #saveUpdateAppt button.
     */
    public void saveUpdateAppt(ActionEvent actionEvent) {

        try {
            int apptId = Integer.parseInt(updateApptId.getText());
            String title = String.valueOf(updateApptTitle.getText());
            String description = String.valueOf(updateApptDescription.getText());
            String location = String.valueOf(updateApptLocation.getText());
            String type = String.valueOf(updateApptTypeCombo.getValue());
            date = updateApptDatePicker.getValue();
            start = updateApptStartTimeCombo.getValue();
            end = updateApptEndTimeCombo.getValue();
            customer = updateApptCustomerCombo.getValue();
            user = updateApptUserCombo.getValue();
            contact = updateApptContactCombo.getValue();
            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);

            if (title.isEmpty() | description.isEmpty() | location.isEmpty() | type.isEmpty()
                    | updateApptDatePicker == null | updateApptStartTimeCombo == null | updateApptEndTimeCombo == null
                    | customer == null | user == null | contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("Every Field Must Be Completed");
                alert.showAndWait();
            } else if (start.isAfter(end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start time must come before the end time.", ButtonType.OK);
                alert.setTitle("Start Time Before End Time");
                alert.showAndWait();
            } else if (Appointment.checkConflictingAppointment(apptId, customer.getId(), startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This customer has a conflicting appointment. Please pick a different date.", ButtonType.OK);
                alert.setTitle("Conflicting Appointment");
                alert.showAndWait();
            } else if (TimeMethods.isOutsideBusinessHours(date, start, end)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "The appointment hours must be between 8:00 AM and 10:00 PM EST.", ButtonType.OK);
                alert2.setTitle("Not During Business Hours");
                alert2.showAndWait();
            } else {
                LocalDateTime startDate = LocalDateTime.of(date, start);
                LocalDateTime endDate = LocalDateTime.of(date, end);
                Appointment a = new Appointment(apptId, title, description, location, type, startDate, endDate, customer.getId(), user.getId(), contact.getId());
                AppointmentDAO.updateAppointment(a);
                SceneMovements.goToPage(actionEvent, "AppointmentList", 1200, 600, "Appointment List");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment updated!", ButtonType.OK);
                alert.setTitle("Appointment Updated");
                alert.showAndWait();
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows alert verifying that the user wants to leave the update appointment form.
     * Opens the <code>AppointmentList</code> fxml.
     * @param actionEvent Pressing the #cancelUpdateAppt button.
     */
    public void cancelUpdateAppt(ActionEvent actionEvent) throws IOException {
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
