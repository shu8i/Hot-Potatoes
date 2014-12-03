package view;

import control.Controller;
import model.Grid;
import model.User;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

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
    private LoginPanel loginPanel;
    private JPanel predecessor;
    private JFrame parent;
    private Controller controller;
    private JLabel selectWorldLabel;
    private JPanel worldSelectionPanel;
    private JScrollPane worldSelectionScrollPane;
    private User user;

    public StudentPanel(JFrame parent, LoginPanel loginPanel, JPanel predecessor, Controller controller, User user) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.predecessor.setVisible(false);

        this.selectWorldLabel = new JLabel("Select A World");
        this.worldSelectionPanel = new JPanel();
        this.worldSelectionScrollPane = new JScrollPane(this.worldSelectionPanel);
        this.worldSelectionPanel.setAutoscrolls(true);
        this.worldSelectionScrollPane.setPreferredSize(new Dimension(620, 410));
        this.worldSelectionScrollPane.setBorder(null);
        this.worldSelectionPanel.setLayout(layout);
        
        this.user = user;
        

        int i = 0, j = 0, numWorlds = this.controller.getGrids().size();
        JButton worldSelectButton;
        for (final Map.Entry<String, Grid> entry : this.controller.getGrids().entrySet()) {
            worldSelectButton = new JButton("<html><center>" + entry.getKey() + "<br>"
                    + "HIGH SCORE: " + this.controller.userController.getGridScore(entry.getValue())  + "</center></html>");
            worldSelectButton.setPreferredSize(new Dimension(190, 190));
            worldSelectButton.setBackground(Constants.COLOR_SMOOTH_GREEN);
            worldSelectButton.setOpaque(true);
            worldSelectButton.setBorderPainted(false);
            worldSelectButton.setFocusable(false);
            worldSelectButton.setFont(Constants.FONT_OPEN_SANS_20);
            worldSelectButton.setForeground(Color.WHITE);
            worldSelectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PlayPanel(StudentPanel.this.parent, StudentPanel.this.loginPanel,
                            StudentPanel.this, StudentPanel.this.controller, entry.getValue(), StudentPanel.this.user);
                }
            });

            worldSelectButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    e.getComponent().setBackground(Constants.COLOR_SMOOTH_GREEN);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    e.getComponent().setBackground(Constants.COLOR_ACTIONS);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    e.getComponent().setBackground(Constants.COLOR_SMOOTH_GREEN);
                }
            });

            c = new GridBagConstraints();
            c.gridx = i;
            c.gridwidth = 2;
            c.gridy = j;
            c.gridheight = 2;
            c.anchor = GridBagConstraints.NORTHWEST;
            this.worldSelectionPanel.add(worldSelectButton, c);

            i += 2;

            if (i % 8 == 0) {
                if (numWorlds == 6 && j == 3) continue;
                i = 0;
                j += 2;
                c.gridx = i;
                c.gridwidth = 8;
                c.weighty = 1;
                c.gridheight = 1;
                c.gridy = j++;
                c.fill = GridBagConstraints.BOTH;
                this.worldSelectionPanel.add(new JPanel(), c);
                continue;
            }

            c.gridx = i++;
            c.gridwidth = 1;
            c.weightx = (numWorlds == 2) ? (i == 5 ? 1 : 0) : 1;
            c.gridy = j;
            c.gridheight = 2;
            c.fill = GridBagConstraints.BOTH;
            this.worldSelectionPanel.add(new JPanel(), c);
        }

        if (j == 0) {
            i = 0;
            j += 2;
            c.gridx = i;
            c.gridwidth = 6;
            c.weightx = 1;
            c.weighty = 1;
            c.gridheight = 1;
            c.gridy = j;
            c.fill = GridBagConstraints.BOTH;
            this.worldSelectionPanel.add(new JPanel(), c);
        }

        add(this.worldSelectionScrollPane, c);

        updateMenu();
        this.parent.add(this, c);
        this.parent.pack();
        this.parent.setLocationRelativeTo(null);
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this));
    }

}