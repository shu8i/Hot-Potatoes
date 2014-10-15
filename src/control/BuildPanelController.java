package control;

import view.BuildPanelView;
import view.GridView;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class BuildPanelController {
    
    GridView grid;
    
    BuildPanelView buildPanel;
    
    /**
     * Creates a new Build Panel Controller
     * @param buildPanel the build panel
     * @param grid the grid
     */
    public BuildPanelController(BuildPanelView buildPanel, GridView grid){
        this.buildPanel=buildPanel;
        this.grid=grid;
    }
    
    /**
     * Updates the Grid View
     * @return whether the Grid View is successfully updated.
     */
    public boolean updateGV(){
        return true;
    }
    
    /**
     * Updates the Build Panel View
     * @return whether the Build Panel View is successfully updated.
     */
    public boolean updateBP(){
        return true;
    }
    
}
