package view;

import control.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private GameController controller;
    private LoginPanel loginPanel;
    private T currentPanel;

    public JMenuBar buildMenu(String menuTitle, LoginPanel loginPanel, GameController controller, T currentPanel, JMenuItem... menus) {
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
                Menu.this.controller.logout(Menu.this.loginPanel, Menu.this.currentPanel);
            }
        });
        this.gameMenu.add(logoutMenu);
        return menuBar;
    }
    
}
