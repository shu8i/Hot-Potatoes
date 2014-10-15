package control;

import view.ActionPanelView;
import view.CodeView;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ActionPanelController {
    
    ActionPanelView actionPanel;
    
    CodeView codeView;
    
    /**
     * Creates a new Action Panel Controller
     * @param actionPanel the Action Panel
     * @param codeView the Code View
     */
    public ActionPanelController(ActionPanelView actionPanel, CodeView codeView){
        this.actionPanel=actionPanel;
        this.codeView=codeView;
    }
    
    /**
     * Updates the Action Panel
     * @return whether the Action Panel is successfully updated
     */
    public boolean update(){
        return true;
    }
    
}
