package view;

import control.Controller;
import util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class AdminPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected GridBagConstraints c = new GridBagConstraints();
    private LoginPanel loginPanel;
    private JPanel predecessor;
    private JFrame parent;
    private Controller controller;
    private JButton highScoreButton, userManagementButton, worldManagementButton;
    private JLabel highScoreLabel, userManagementLabel, worldManagementLabel;

    public AdminPanel(JFrame parent, LoginPanel loginPanel, JPanel predecessor, Controller controller) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.predecessor.setVisible(false);

        this.highScoreButton = new JButton();
        this.highScoreButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new ScorePanel(AdminPanel.this.parent, AdminPanel.this.loginPanel, AdminPanel.this, AdminPanel.this.controller);
        	}
        }); 
        this.userManagementButton = new JButton();
        this.userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationPanel(AdminPanel.this.parent, AdminPanel.this.loginPanel, AdminPanel.this, AdminPanel.this.controller);
            }
        });

        this.worldManagementButton = new JButton();
        this.worldManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuildPanel(AdminPanel.this.parent, AdminPanel.this.loginPanel, AdminPanel.this, AdminPanel.this.controller);
            }
        });

        this.highScoreLabel = new JLabel("High Scores");
        this.userManagementLabel = new JLabel("Manage Users");
        this.worldManagementLabel = new JLabel("Manage Worlds");

        try {
            Image img = ImageIO.read(getClass().getResource("/resources/images/high_score.png"));
            Image scaledImage = img.getScaledInstance(Constants.ICON_WIDTH, Constants.ICON_HEIGHT, Image.SCALE_SMOOTH);
            this.highScoreButton.setIcon(new ImageIcon(scaledImage));
            img = ImageIO.read(getClass().getResource("/resources/images/manage_users.png"));
            scaledImage = img.getScaledInstance(Constants.ICON_WIDTH, Constants.ICON_HEIGHT, Image.SCALE_SMOOTH);
            this.userManagementButton.setIcon(new ImageIcon(scaledImage));
            img = ImageIO.read(getClass().getResource("/resources/images/manage_worlds.png"));
            scaledImage = img.getScaledInstance(Constants.ICON_WIDTH, Constants.ICON_HEIGHT, Image.SCALE_SMOOTH);
            this.worldManagementButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            System.out.println("Couldn't read image file.");
        }

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 3;
        c.weighty = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        highScoreButton.setFocusable(false);
        add(highScoreButton, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 2;
        c.weightx = 3;
        c.weighty = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        userManagementButton.setFocusable(false);
        add(userManagementButton, c);

        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 2;
        c.weightx = 3;
        c.weighty = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        worldManagementButton.setFocusable(false);
        add(worldManagementButton, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 3;
        c.anchor = GridBagConstraints.CENTER;
        highScoreLabel.setFont(Constants.FONT_OPEN_SANS_20);
        highScoreLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
        add(highScoreLabel, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 3;
        c.weightx = 3;
        c.anchor = GridBagConstraints.CENTER;
        userManagementLabel.setFont(Constants.FONT_OPEN_SANS_20);
        userManagementLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
        add(userManagementLabel, c);

        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 3;
        c.weightx = 3;
        c.anchor = GridBagConstraints.CENTER;
        worldManagementLabel.setFont(Constants.FONT_OPEN_SANS_20);
        worldManagementLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
        add(worldManagementLabel, c);

        updateMenu();
        this.parent.add(this, c);
        this.parent.pack();
        this.parent.setLocationRelativeTo(null);
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this));
    }

}