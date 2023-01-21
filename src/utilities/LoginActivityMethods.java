package utilities;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class holds methods used in the <code>LoginActivity</code> controller.
 */
public class LoginActivityMethods {

    /**
     * Creates or appends a FileWriter to the login_activity.txt file and prints a string logging a failed login attempt
     * with the date and time of the attempt.
     * @param date Passed date
     * @param time Passed time
     */
    public static void failedLoginAttemptWrite (String name, LocalDate date, LocalTime time) {
        String printItem = "Failed login attempt at: " + date.toString() + " " + time.toString() + " by " + name + ".";
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println(printItem);
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates or appends a FileWriter to the login_activity.txt file and prints a string logging a successful login attempt
     * with the date and time of the attempt.
     * @param date Passed date
     * @param time Passed time
     */
    public static void passedLoginAttemptWrite (String name, LocalDate date, LocalTime time) {
        String printItem = "Passed login attempt at: " + date.toString() + " " + time.toString() + " by " + name + ".";
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println(printItem);
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
