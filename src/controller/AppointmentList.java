package controller;

import dao.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import utilities.SceneMovements;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The AppointmentList controller class builds and populates the <code>AppointmentList</code>> tableview. Navigation buttons are
 * included in the header for easy navigation between scenes. There are buttons for modifying existing
 * appointments, creating new appointments, and deleting appointments. The table is initialized with all appointments
 * available in the SQL database. Radio buttons offer filtering by week or month for the user. The methods that define
 * the filtering are located in the <code>AppointmentDAO</code> Java class.
 */
public class AppointmentList implements Initializable {
    public TableColumn <Appointment, Integer> apptIdCol;
    public TableColumn <Appointment, String> apptTitleCol;
    public TableColumn <Appointment, String> apptDescriptionCol;
    public TableColumn <Appointment, String> apptLocationCol;
    public TableColumn <Appointment, String> apptTypeCol;
    public TableColumn <Appointment, LocalDateTime> apptStartTimeCol;
    public TableColumn <Appointment, LocalDateTime> apptEndTimeCol;
    public TableColumn <Appointment, Integer> apptCustomerIdCol;
    public TableColumn <Appointment, Integer> apptUserIdCol;
    public TableColumn <Appointment, Integer> apptContactIdCol;
    public TableView <Appointment> apptListTableView;
    public RadioButton rbtnWeekView;
    public RadioButton rbtnMonthView;
    public RadioButton rbtnAllAppts;
    public ToggleGroup apptListViewTG;
    public TextField apptSearch;

    /**
     * Initializes the <code>AppointmentList</code> tableview with all appointments in the database. A ChangeListener is used to
     * monitor when the user switches between the radio buttons in the toggle group. The selection of the radio buttons
     * determine which method in the <code>AppointmentDAO</code> Java class to call for filtering the appointment data:
     * <code>getCurrentWeekAppts()</code> or <code>getCurrentMonthAppts()</code> or <code>getAllAppointments()</code>.
     * <p>
     *     <b>Lambda Expression Usage</b>
     *      This lambda expression uses the <code>ChangeListener</code> functional interface and defines the
     *      <code>changed</code> method to monitor when the user selects between the three radio buttons.
     * </p>
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> apptAllList = AppointmentDAO.getAllAppointments();
        setApptListTableView(apptAllList);

        FilteredList<Appointment> filteredList = new FilteredList<>(apptAllList, b -> true);
        apptSearch.textProperty().addListener(((obs, oldValue, newValue) -> filteredList.setPredicate(appt -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (String.valueOf(appt.getId()).contains(lowerCaseFilter)) {
                return true;
            } else if (appt.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (appt.getApptDescription().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (appt.getApptType().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (appt.getApptLocation().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(appt.getApptStartDateTime()).contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(appt.getApptEndDateTime()).contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(appt.getApptCustomerId()).contains(lowerCaseFilter)) {
                return true;
            }else if (String.valueOf(appt.getApptUserId()).contains(lowerCaseFilter)) {
                return true;
            }else return String.valueOf(appt.getApptContactId()).contains(lowerCaseFilter);
        })));

        SortedList <Appointment> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(apptListTableView.comparatorProperty());
        apptListTableView.setItems(sortedList);

        apptListViewTG.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton selectedButton = (RadioButton) apptListViewTG.getSelectedToggle();
            if (selectedButton == rbtnWeekView) {
                ObservableList<Appointment> apptWeekList = AppointmentDAO.getCurrentWeekAppts();
                setApptListTableView(apptWeekList);
                apptSearch.clear();

                FilteredList<Appointment> filteredWeekList = new FilteredList<>(apptWeekList, b -> true);
                apptSearch.textProperty().addListener(((obs, oldValue, newValue) -> filteredWeekList.setPredicate(appt -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(appt.getId()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptLocation().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptStartDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptEndDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptCustomerId()).contains(lowerCaseFilter)) {
                        return true;
                    }else if (String.valueOf(appt.getApptUserId()).contains(lowerCaseFilter)) {
                        return true;
                    }else return String.valueOf(appt.getApptContactId()).contains(lowerCaseFilter);
                })));

                SortedList <Appointment> sortedWeekList = new SortedList<>(filteredWeekList);
                sortedWeekList.comparatorProperty().bind(apptListTableView.comparatorProperty());
                apptListTableView.setItems(sortedWeekList);
            }
            if (selectedButton == rbtnMonthView) {
                ObservableList<Appointment> apptMonthList = AppointmentDAO.getCurrentMonthAppts();
                setApptListTableView(apptMonthList);
                apptSearch.clear();

                FilteredList<Appointment> filteredMonthList = new FilteredList<>(apptMonthList, b -> true);
                apptSearch.textProperty().addListener(((obs, oldValue, newValue) -> filteredMonthList.setPredicate(appt -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(appt.getId()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptLocation().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptStartDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptEndDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptCustomerId()).contains(lowerCaseFilter)) {
                        return true;
                    }else if (String.valueOf(appt.getApptUserId()).contains(lowerCaseFilter)) {
                        return true;
                    }else return String.valueOf(appt.getApptContactId()).contains(lowerCaseFilter);
                })));

                SortedList <Appointment> sortedMonthList = new SortedList<>(filteredMonthList);
                sortedMonthList.comparatorProperty().bind(apptListTableView.comparatorProperty());
                apptListTableView.setItems(sortedMonthList);
            }
            if (selectedButton == rbtnAllAppts) {
                ObservableList<Appointment> apptAllList1 = AppointmentDAO.getAllAppointments();
                setApptListTableView(apptAllList1);
                apptSearch.clear();

                FilteredList<Appointment> filteredAllList = new FilteredList<>(apptAllList1, b -> true);
                apptSearch.textProperty().addListener(((obs, oldValue, newValue) -> filteredAllList.setPredicate(appt -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(appt.getId()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (appt.getApptLocation().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptStartDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptEndDateTime()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(appt.getApptCustomerId()).contains(lowerCaseFilter)) {
                        return true;
                    }else if (String.valueOf(appt.getApptUserId()).contains(lowerCaseFilter)) {
                        return true;
                    }else return String.valueOf(appt.getApptContactId()).contains(lowerCaseFilter);
                })));

                SortedList <Appointment> sortedAllList = new SortedList<>(filteredAllList);
                sortedAllList.comparatorProperty().bind(apptListTableView.comparatorProperty());
                apptListTableView.setItems(sortedAllList);
            }
        });
    }
    /**
     * This method sets the data to the tableview based on the ObservableList that is passed into the
     * parameter. This method is called when a radio button is selected and a method from <code>AppointmentDAO</code> creates the
     * ObservableList that is passed.
     * @param appointment An ObservableList from <code>AppointmentDAO</code> which is generated when the user selects
     *                    a radio button.
     */
    public void setApptListTableView (ObservableList <Appointment> appointment) {
        apptListTableView.setItems(appointment);
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStartDateTime"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEndDateTime"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("apptUserId"));
        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("apptContactId"));
    }

    /**
     * Passes the selected appointment from the tableview to the <code>UpdateAppt</code> class. After checking the user
     * selected a row in the table, the <code>SceneMovements.goToPage</code> method is used to open the Update Appointment scene.
     * @param actionEvent Selecting the #toUpdateAppt button.
     */

    public void toUpdateAppt(ActionEvent actionEvent) throws IOException {
        Appointment a = apptListTableView.getSelectionModel().getSelectedItem();

        if (a != null) {
            UpdateAppt.passingTheAppointment(a);
            SceneMovements.goToPage(actionEvent, "UpdateAppt", 600, 550, "Update Appointment");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "First select the appointment you want to modify");
            alert.setTitle("Select Appointment");
            alert.showAndWait();
        }
    }

    /**
     * Opens the NewAppt scene using <code>SceneMovements.goToPage</code> method.
     * @param actionEvent Selecting the #toNewAppt button.
     */
    public void toNewAppt(ActionEvent actionEvent) throws IOException {

        SceneMovements.goToPage(actionEvent, "NewAppt", 600, 550, "New Appointment");
    }

    /**
     * Verifies an appointment is selected, if one is not, an alert shows.
     * Gets the selected appointment and shows an alert confirming that the user wants to delete the specified appointment.
     * Shows an alert notifying the user that the selection was deleted.
     * Refreshes the <code>AppointmentList</code> tableview.
     * <p>
     *     <b>Lambda Expression Disclosure:</b>
     *     This lambda expression is used to define the response method when the user selects the confirmation button.
     *     Instead of creating a separate method that is only needed a few times and calling it in this method, a lambda
     *     expression is used for cleaner coding and easier reading.
     * </p>
     *
     * @param actionEvent Pressing the #deleteAppt button
     */
    public void deleteAppt(ActionEvent actionEvent) {
        Appointment a = apptListTableView.getSelectionModel().getSelectedItem();
        if (a == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "First select the appointment you want to delete.");
            alert.setTitle("Select Appointment");
            alert.showAndWait();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + a.getApptType() + " appointment?", ButtonType.YES, ButtonType.NO);
            alert2.setTitle("Verify Deletion");
            alert2.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    AppointmentDAO.deleteAppointment(a.getId());
                    apptListTableView.setItems(AppointmentDAO.getAllAppointments());
                    Alert alert3 = new Alert(Alert.AlertType.NONE, a.getId() + " " + a.getApptType() + " has been deleted.", ButtonType.OK);
                    alert3.showAndWait();
                }
            });
        }
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
