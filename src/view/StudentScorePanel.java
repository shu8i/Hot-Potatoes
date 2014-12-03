package view;

import control.Controller;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Shahab Shekari on 12/3/14.
 */
public class StudentScorePanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private LoginPanel loginPanel;
    private ScorePanel predecessor;
    private JFrame parent;
    private Controller controller;
    private JMenuItem backMenu;

    public StudentScorePanel(JFrame parent, LoginPanel loginPanel, ScorePanel predecessor, Controller controller, String gridName)
    {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.predecessor.setVisible(false);
        setPreferredSize(new Dimension(500, 500));
        Map<String, Integer> scores = this.controller.getGridScores(gridName);

        this.backMenu = new JMenuItem("Back");
        this.backMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentScorePanel.this.setVisible(false);
                StudentScorePanel.this.predecessor.updateMenu();
                StudentScorePanel.this.predecessor.setVisible(true);
                StudentScorePanel.this.parent.pack();
                StudentScorePanel.this.parent.setLocationRelativeTo(null);
                StudentScorePanel.this.parent.remove(StudentScorePanel.this);
            }
        });

        JLabel nameLabel, scoreLabel;
        int row = 0;
        for (final Map.Entry<String, Integer> entry : scores.entrySet())
        {
            nameLabel = new JLabel(entry.getKey());
            nameLabel.setFont(Constants.FONT_OPEN_SANS_20);
            nameLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
            scoreLabel = new JLabel("\t\t\t" + String.valueOf(entry.getValue()) + "%");
            scoreLabel.setFont(Constants.FONT_OPEN_SANS_20);
            scoreLabel.setForeground(Color.GRAY);

            c.gridx = 0;
            c.gridy = row;
            add(nameLabel, c);
            c.gridx = 1;
            c.gridy = row++;
            add(scoreLabel, c);
        }

        c = new GridBagConstraints();
        c.gridy = row + 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1000;
        c.fill = GridBagConstraints.BOTH;
        add(new JPanel(), c);

        updateMenu();
        this.parent.add(this, c);
        this.parent.pack();
        this.parent.setLocationRelativeTo(null);
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this, backMenu));
    }

}
