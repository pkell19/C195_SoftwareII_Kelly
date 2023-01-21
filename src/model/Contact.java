package model;

/**
 * This class defines the variables, constructors, setters, and getters of the Contact class.
 */

public class Contact implements ModelInterface{
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getId() {
        return contactId;
    }

    public String getName() {
        return contactName;
    }

    @Override
    public String toString(){
        return (contactName + " (" + contactId + ")");
    }
}
