package control;

import model.User;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class UserController {

    private User user;

    /**
     * Creates a new user controller
     * @param user the user
     */
    public UserController(User user) {
        this.user = user;
    }

    /**
     * Logs out a user (saves the user file to disk).
     * @return whether the save was successful
     */
    public boolean logout() {
        //TODO implement
        return true;
    }

    /**
     * Attempts to login a user.
     * Calls DatabaseController and loads the list of existing user accounts.
     * If the username/password is valid, it'll get the user's GameController.
     * @param username the username
     * @param password the password
     * @return  null if the login was unsuccessful
     *          GameController of the logged in user, if login was successful
     */
    public static GameController login(String username, String password) {
        //TODO implement
        return null;
    }

    /**
     * Adds a new user
     * @param username the username
     * @param password the password
     * @param isAdmin whether the new user has admin priviliges
     * @return whether the save (to file) was successful
     */
    public static boolean addUser(String username, String password, boolean isAdmin) {
        //TODO implement
        return true;
    }

    /**
     * Removes a user and his/her corresponding files
     * @param username the username of the user to be deleted
     * @return whether the delete (of the user's file) was successful
     */
    public static boolean removeUser(String username) {
        //TODO implement
        return true;
    }

}