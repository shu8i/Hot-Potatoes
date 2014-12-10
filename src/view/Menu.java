package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Menu<T extends JPanel> {

	private JMenuBar menuBar;
	private JMenu gameMenu;
	private Controller controller;
	private LoginPanel loginPanel;
	private T currentPanel;

	public JMenuBar buildMenu(String menuTitle, LoginPanel loginPanel,
			Controller controller, T currentPanel, JMenuItem... menus) {
		this.menuBar = new JMenuBar();
		this.gameMenu = new JMenu(menuTitle);
		this.controller = controller;
		this.loginPanel = loginPanel;
		this.currentPanel = currentPanel;

		this.menuBar.add(gameMenu);
		for (final JMenuItem menu : menus) {
			this.gameMenu.add(menu);
		}
		JMenuItem logoutMenu = new JMenuItem("Logout");
		logoutMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.logout(Menu.this.loginPanel, Menu.this.currentPanel);
			}
		});
		this.gameMenu.add(logoutMenu);
		return menuBar;
	}

}
