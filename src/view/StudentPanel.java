package view;

import control.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class StudentPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private LoginPanel predecessor;
    private JFrame parent;
    private GameController controller;


    public StudentPanel(JFrame parent, LoginPanel predecessor, GameController controller) {
        super(layout);
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.predecessor.setVisible(false);

    }

}