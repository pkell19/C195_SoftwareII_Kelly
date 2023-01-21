package model;

/**
 * This class defines the variables, constructors, setters, and getters of the Contact class.
 */
public class Customer implements ModelInterface{
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private int countryId;
    private int divisionId;

    public Customer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int countryId, int divisionId) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int countryId, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode , String customerPhone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
    }


    public int getId() {
        return customerId;
    }

    public String getName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    @Override
    public String toString(){
        return (customerName);
    }
}
