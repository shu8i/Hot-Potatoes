package view;

import control.Controller;
import model.Grid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class PlayPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private LoginPanel loginPanel;
    private JPanel predecessor;
    private JFrame parent;
    private Controller controller;
    private Grid grid;
    private MenuItem runMenu, undoMenu, clearMenu, saveMacroMenu, saveGameMenu, backMenu;
    private GridPanel gridPanel;
    private CodePanel codePanel;
    private MacroPanel macroPanel;
    private ActionPanel actionPanel;

    public PlayPanel(JFrame parent, LoginPanel loginPanel, JPanel predecessor, Controller controller, Grid grid) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.grid = grid;
        this.predecessor.setVisible(false);

        initPanels();

        add(this.gridPanel, c);

        c.gridx = 1;
        c.weightx = 1;
        c.weighty = 1;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 2;
        JPanel panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        panel.setPreferredSize(new Dimension(300, 500));
        add(this.codePanel, c);

        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(new JPanel(), c);


        c = new GridBagConstraints();
        c.gridy = 2;
        c.gridx = 0;
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        panel.setPreferredSize(new Dimension(500, 100));
        add(this.actionPanel, c);

        c.gridx = 2;
        panel = new JPanel();
        add(this.macroPanel, c);



        updateMenu();
        this.parent.add(this, c);
        this.parent.pack();
        this.parent.setLocationRelativeTo(null);
    }

    private void initPanels() {
        this.gridPanel = new GridPanel(this.grid);
        this.codePanel = new CodePanel(new JPanel(), this.controller);
        this.actionPanel = new ActionPanel(this.codePanel, this.controller);
        this.macroPanel = new MacroPanel();
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this));
    }

}