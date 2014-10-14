package control;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class DatabaseController {

    /**
     * Loads all available user accounts (username/user obj)
     * from a file saved on disk.
     * @return a hashmap of <username, user obj>
     */
    public static Map<String, User> loadUserAccounts() {
        //TODO implement
        return new HashMap<String, User>();
    }

    /**
     * Saves a new file containing <username, user obj> entries.
     * This happens whenever we add/remove a user account.
     * @param userAccounts valid user accounts
     */
    public static void saveUserAccounts(Map<String, User> userAccounts) {
        //TODO implement
    }

    /**
     * Loads (from file) the GameController of a given user
     * @param user the user whose GameController is to be loaded
     * @return the GameController
     */
    public GameController load(User user) {
        //TODO implement
        return new GameController();
    }

    /**
     * Saves (to file) the GameController of a given user
     * @param gameController the GameController to be saved
     * @return whether the save was successful
     */
    public boolean save(GameController gameController) {
        //TODO implement
        return true;
    }

}
