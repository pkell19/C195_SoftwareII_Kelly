package model;

/**
 * This class defines the variables, constructors, setters, and getters of the Division class.
 */
public class Division implements ModelInterface{
    private int divisionId;
    private String division;
    private int countryId;

    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    public int getId() {
        return divisionId;
    }

    public String getName() {
        return division;
    }

    @Override
    public String toString() {
        return (division + " (" + divisionId + ")");
    }
}
