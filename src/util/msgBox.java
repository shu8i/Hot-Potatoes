package util;

import javax.swing.JOptionPane;

/**
 * @author Allan Class used to show a message box in the screen.
 */
public class msgBox {

	/**
	 * Will show a message box dialog on the screen with the specified
	 * parameters.
	 * 
	 * @param infoMessage
	 *            message to print
	 * @param titleBar
	 *            title of the message box
	 */
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage,
				"InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}
