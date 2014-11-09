package model;

import util.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shahab Shekari on 11/9/14.
 */
public class Backend implements Serializable {

    static final long serialVersionUID = 1L;
    private Map<String, User> users;

    public Backend() {
        this.users = new HashMap<String, User>();
    }

    /**
     * Reads a backend object from file
     * @return  the backend object
     */
    public static Backend readDatabase() {
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream(Constants.DATABASE) );
            return (Backend)objectInputStream.readObject();
        } catch( Exception e ) {
            System.out.println( "Error: couldn't load database <" + Constants.DATABASE + ">" );
            return new Backend();
        }
    }

    public void saveDatabase() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream(Constants.DATABASE) );
            objectOutputStream.writeObject(this);
        } catch( IOException e ) {
            System.out.println( "Error: couldn't save database <" + Constants.DATABASE + ">" );
        }
    }

    public void removeUser(String username) {
        this.users.remove( username );
    }

    public void addUser( User user ) {
        this.users.put(user.getUsername(), user);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (final Map.Entry<String, User> entry : this.getUsers().entrySet()) {
            stringBuilder.append(entry.getValue().getUsername() + "\n");
        }
        return stringBuilder.toString().isEmpty() ? "No users Exist" : stringBuilder.toString();
    }

}
