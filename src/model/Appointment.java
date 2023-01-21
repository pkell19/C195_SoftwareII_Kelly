package model;

import dao.AppointmentDAO;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * This class defines the variables, constructors, setters, and getters of the Appointment class.
 */
public class Appointment implements ModelInterface {
    private int apptId;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime apptStartDateTime;
    private LocalDateTime apptEndDateTime;
    private int apptCustomerId;
    private int apptUserId;
    private int apptContactId;

    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation, String apptType,
                       LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime, int apptCustomerId,
                       int apptUserId, int apptContactId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
        this.apptCustomerId = apptCustomerId;
        this.apptUserId = apptUserId;
        this.apptContactId = apptContactId;
    }

    public Appointment(String apptTitle, String apptDescription, String apptLocation, String apptType,
                       LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime, int apptCustomerId, int apptUserId,
                       int apptContactId) {
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
        this.apptCustomerId = apptCustomerId;
        this.apptUserId = apptUserId;
        this.apptContactId = apptContactId;
    }

    public Appointment(int apptId, String apptTitle, String apptDescription, String apptType,
                       LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime, int apptCustomerId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
        this.apptCustomerId = apptCustomerId;
    }

    public Appointment(int apptId, String apptTitle, String apptDescription, String apptType,
                       LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
    }

    public int getId() {
        return apptId;
    }

    public String getName() {
        return apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public String getApptType() {
        return apptType;
    }

    public LocalDateTime getApptStartDateTime() {
        return apptStartDateTime;
    }

    public LocalDateTime getApptEndDateTime() {
        return apptEndDateTime;
    }

    public int getApptCustomerId() {
        return apptCustomerId;
    }

    public int getApptUserId() {
        return apptUserId;
    }

    public int getApptContactId() {
        return apptContactId;
    }

    @Override
    public String toString() {
        return (apptType);
    }

    /**
     * Checks for appointments in the database with the same customer ID and start that are passed in the parameter.
     * @param customerID Passed customer ID
     * @param startDate Passed start date and time
     * @return boolean if there is a conflict or note
     */
    public static boolean checkConflictingAppointment(int apptId, int customerID, LocalDateTime startDate, LocalDateTime endDate) {
        ObservableList<Appointment> apptList = AppointmentDAO.getAllAppointments();

        for (Appointment a : apptList) {
            if (a.getId() != apptId && a.getApptCustomerId() == customerID && startDate.isAfter(a.getApptStartDateTime().minusMinutes(1)) && startDate.isBefore(a.getApptEndDateTime().plusMinutes(1))) {
                return true;
            } else if (a.getId() != apptId && a.getApptCustomerId() == customerID && endDate.isAfter(a.getApptStartDateTime().minusMinutes(1)) && endDate.isBefore(a.getApptEndDateTime().plusMinutes(1))) {
                return true;
            } else if (a.getId() != apptId && a.getApptCustomerId() == customerID && (startDate.equals(a.getApptStartDateTime()) || endDate.equals(a.getApptEndDateTime()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkConflictingAppointment(int customerID, LocalDateTime startDate, LocalDateTime endDate) {
        ObservableList<Appointment> apptList = AppointmentDAO.getAllAppointments();

        for (Appointment a : apptList) {
            if (a.getApptCustomerId() == customerID && startDate.isAfter(a.getApptStartDateTime().minusMinutes(1)) && startDate.isBefore(a.getApptEndDateTime().plusMinutes(1))) {
                return true;
            } else if (a.getApptCustomerId() == customerID && endDate.isAfter(a.getApptStartDateTime().minusMinutes(1)) && endDate.isBefore(a.getApptEndDateTime().plusMinutes(1))) {
                return true;
            } else if (a.getApptCustomerId() == customerID && (startDate.equals(a.getApptStartDateTime()) || endDate.equals(a.getApptEndDateTime()))) {
                return true;
            }
        }
        return false;
    }
}
