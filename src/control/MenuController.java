package control;

import model.User;
import view.Menu;
import model.Grid;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class MenuController {
    
    private Menu menu;
    
    /**
     * Creates a new Menu Controller
     * @param menu the menu
     */
    public MenuController(Menu menu){
        this.menu=menu;
    }
    
    /**
     * Saves the state of the program
     * @return whether the state is successfully saved
     */
    public boolean saveState(){
        return true;
    }
    
    /**
     * Saves the world
     * @param grid the grid
     * @return whether the world is successfully saved
     */
    public boolean saveWorld(Grid grid){
        return true;
    }
    
    /**
     * Exits the program
     */
    public void exit(){
        
    }
    
    /**
     * Takes the program to the previous screen
     */
    public void back(){
        
    }
    
    /**
     * Logs out the current user
     * @param user the user
     * @return whether the user successfully logged out
     */
    public boolean logout(User user){
        return true;
    }
    
    /**
     * Brings up a help menu
     */
    public void help(){
        
    }
    
}
