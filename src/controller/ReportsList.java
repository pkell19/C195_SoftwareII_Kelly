package controller;

import javafx.event.ActionEvent;
import utilities.SceneMovements;

import java.io.IOException;

/**
 * This class shows the list of reports available for the user to select from.
 */
public class ReportsList {

    /**
     * Opens the <code>ReportApptsByTypeMonth</code> fxml.
     * @param actionEvent Pressing the # onActOpenApptByTypeMonthbutton
     */
    public void onActOpenApptByTypeMonth(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportApptsByTypeMonth", 600, 400, "Total Appointments By Type and Month");
    }

    /**
     * Opens the <code>ReportApptListByContact</code> fxml.
     * @param actionEvent Pressing the #openApptByContact button.
     */
    public void openApptByContact(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportApptListByContact", 1200, 600, "Appointment List by Contact");
    }

    /**
     * Opens the <code>ReportTotalCustomer</code> fxml.
     * @param actionEvent Pressing the #openTotalCustomer button.
     */
    public void openTotalCustomer(ActionEvent actionEvent) throws IOException {
        SceneMovements.goToPage(actionEvent, "ReportTotalCustomer", 600, 400, "Total Customer");
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
}
