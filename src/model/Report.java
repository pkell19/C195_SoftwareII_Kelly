package model;

/**
 * This class defines the variables, constructors, setters, and getters of the Report class.
 */
public class Report {
    private final String type;
    private final int count;

    public Report(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
