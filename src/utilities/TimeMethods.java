package utilities;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * This class defines multiple methods that are used to validate time requirements.
 */
public class TimeMethods {

    private static final ZoneId EST_ZONE_ID = ZoneId.of("America/New_York");
    public static final LocalTime BUSINESS_OPENING = LocalTime.of(8, 0);
    public static final LocalTime BUSINESS_CLOSING = LocalTime.of(22, 0);

    /**
     * Checks the provided date and times and verify if they are within the EST business hours.
     * @param date Passed date
     * @param start Passed start time
     * @param end Passed end time
     * @return boolean if times are in or outside of the business hours.
     */
    public static boolean isOutsideBusinessHours (LocalDate date, LocalTime start, LocalTime end) {

        LocalDateTime startLDT = LocalDateTime.of(date, start);
        LocalDateTime endLDT = LocalDateTime.of(date, end);

        LocalDateTime apptStart = startLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(EST_ZONE_ID).toLocalDateTime();
        LocalDateTime apptEnd = endLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(EST_ZONE_ID).toLocalDateTime();

        LocalDateTime businessStart = LocalDateTime.of(date, BUSINESS_OPENING);
        LocalDateTime businessEnd = LocalDateTime.of(date, BUSINESS_CLOSING);

        return apptStart.isBefore(businessStart) | apptEnd.isAfter(businessEnd);
    }

    /**
     * Check for appointments associated with the provided user ID that start within in 15 minutes of the current time
     * or are currently in progress and alert the user of the appointment id, start date and time. If there are no
     * associated appointments, than the user is alerted that there are no upcoming appointments.
     * @param userId The user ID linked to the user name and password used to log in.
     */
    public static void checkApptTimeAtLogin (int userId) {

        ObservableList <Appointment> apptList = AppointmentDAO.sameDateApptList(userId);
        ObservableList<Appointment> upcomingAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> pastAppts = FXCollections.observableArrayList();

        for (Appointment a: apptList) {
            LocalTime startTime = a.getApptStartDateTime().toLocalTime();
            LocalTime endTime = a.getApptEndDateTime().toLocalTime();
            LocalTime currentTime = LocalTime.now();

            long startTimeDiff = ChronoUnit.MINUTES.between(currentTime, startTime);
            long endTimeDiff = ChronoUnit.MINUTES.between(currentTime, endTime);
            if (startTimeDiff <= 15 && startTimeDiff > 0) {
                upcomingAppts.add(a);
            } else if (startTimeDiff < 0 && endTimeDiff > 0) {
                pastAppts.add(a);
            }
        }
        if (upcomingAppts.size() > 0) {
            for (Appointment a : upcomingAppts) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, a.getId() + " starts at " + a.getApptStartDateTime(), ButtonType.OK);
                alert.setTitle("Appointment Soon");
                alert.showAndWait();
            }
        } else if (pastAppts.size() > 0) {
            for (Appointment a : pastAppts) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, a.getId() + " appointment has started and will end at " + a.getApptEndDateTime());
                alert.setTitle("Appointment Started");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There are no upcoming appointments.");
            alert.setTitle("No Appointments");
            alert.show();
        }
    }

    /**
     * Returns an ObservableList of times in 30 minute increments started at 0:00 and ending at 23:30.
     * @return ObservableList of times
     */
    public static ObservableList<LocalTime> timeList() {
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 0);
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        while(start.isBefore(end.plusSeconds(1))){
            start = start.plusMinutes(30);
            times.add(start);
        }
        return times;
    }

    /**
     * Returns ObservableList of month names of type String.
     * @return ObservableList of month names
     */
    public static ObservableList<String> monthList() {
        ObservableList<String> t = FXCollections.observableArrayList();
        String[] types = new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"};
        t.addAll(Arrays.asList(types));
        return t;
    }

    /**
     * Converts the provided month of type String to a LocalDate type.
     * @param month String
     * @return LocalDate type
     */
    public static Timestamp convertStringtoMonth (String month) {
        LocalDate m = null;
        LocalTime time = LocalTime.of(0,0);
        switch (month) {
            case "January" -> m = LocalDate.of(2022, 1, 1);
            case "February" -> m = LocalDate.of(2022, 2, 1);
            case "March" -> m = LocalDate.of(2022, 3, 1);
            case "April" -> m = LocalDate.of(2022, 4, 1);
            case "May" -> m = LocalDate.of(2022, 5, 1);
            case "June" -> m = LocalDate.of(2022, 6, 1);
            case "July" -> m = LocalDate.of(2022, 7, 1);
            case "August" -> m = LocalDate.of(2022, 8, 1);
            case "September" -> m = LocalDate.of(2022, 9, 1);
            case "October" -> m = LocalDate.of(2022, 10, 1);
            case "November" -> m = LocalDate.of(2022, 11, 1);
            case "December" -> m = LocalDate.of(2022, 12, 1);
        }
        return Timestamp.valueOf(LocalDateTime.of(m, time));
    }

    /**
     * Converts year of type String to LocalDateTime type
     * @param year String
     * @return LocalDateTime
     */
    public static Timestamp convertStringtoYear (String year) {
        int y = Integer.parseInt(year);
        LocalDate d = LocalDate.of(y, 1, 1);
        LocalTime t = LocalTime.of(0,0);
        return Timestamp.valueOf(LocalDateTime.of(d, t));
    }
}
