package control;

import view.ActionPanel;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ActionPanelController {

	ActionPanel actionPanel;

	/**
	 * Creates a new Action Panel Controller
	 * 
	 * @param actionPanel
	 *            the Action Panel
	 */
	public ActionPanelController(ActionPanel actionPanel) {
		this.actionPanel = actionPanel;
	}

	/**
	 * Updates the Action Panel
	 * 
	 * @return whether the Action Panel is successfully updated
	 */
	public boolean update() {
		return true;
	}

}
