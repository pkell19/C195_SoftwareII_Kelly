package model;

/**
 * This class defines the variables, constructors, setters, and getters of the User class.
 */
public class User implements ModelInterface{
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return userId;
    }

    public String getName() {
        return userName;
    }

    @Override
    public String toString() {
        return (userName + " (" + userId + ")");
    }
}
