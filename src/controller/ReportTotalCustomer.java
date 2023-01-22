package controller;

import dao.ReportsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import utilities.SceneMovements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class shows a total number of customers in the database.
 */
public class ReportTotalCustomer implements Initializable {

    public Label totalCustomerLBL;

    /**
     * Initializes the customer combo box with the customer list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String count = String.valueOf(ReportsDAO.totalCustomer());
        totalCustomerLBL.setText(count);
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
