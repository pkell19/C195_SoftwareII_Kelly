package controller;

import dao.ContactDAO;
import dao.ReportsDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import utilities.SceneMovements;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class shows a tableview of all appointments that contain the contact id that is equal to the user's selection.
 * The tableview includes the appointment id, appointment title, type, description, start date/time, end date/time, and
 * the customer id number.
 */
public class ReportApptListByContact implements Initializable {
    public ComboBox <Contact> comboContact;
    public TableView <Appointment> apptByContactTable;
    public TableColumn <Appointment, Integer> apptIdCol;
    public TableColumn <Appointment, String> titleCol;
    public TableColumn <Appointment, String> typeCol;
    public TableColumn <Appointment, String> descriptionCol;
    public TableColumn <Appointment, LocalDateTime> startCol;
    public TableColumn <Appointment, LocalDateTime> endCol;
    public TableColumn <Appointment, Integer> customerIdCol;
    ObservableList <Contact> contactList = ContactDAO.getAllContacts();

    /**
     * Initializes the contact combo box with the contact list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboContact.setItems(contactList);
        comboContact.setVisibleRowCount(5);
        comboContact.setPromptText("Select contact");
    }

    /**
     * Pulls all appointments that have a contact ID that matches the selection.
     * Sets the tableview with the appointments.
     * @param actionEvent Pressing the #onActFindAppts button
     */
    public void onActFindAppts(ActionEvent actionEvent) {
        ObservableList <Appointment> apptList = ReportsDAO.apptListByContact(comboContact.getValue());

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("apptStartDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("apptEndDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));
        apptByContactTable.setItems(apptList);
    }

    /**
     * Clears all fields and the table.
     * @param actionEvent Pressing the #onActClearFields button
     */
    public void onActClearFields(ActionEvent actionEvent) {
        comboContact.getSelectionModel().clearSelection();
        apptByContactTable.getItems().clear();
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
    public void onActToReports(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportsList", 600, 400, "Report List");
    }
}
