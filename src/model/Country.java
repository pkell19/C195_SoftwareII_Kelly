package model;

/**
 * This class defines the variables, constructors, setters, and getters of the Country class.
 */
public class Country implements ModelInterface{
    private int countryId;
    private String country;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getId() {
        return countryId;
    }

    public String getName() {
        return country;
    }

    @Override
    public String toString(){
        return (country);
    }
}
