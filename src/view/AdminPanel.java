package view;

import control.GameController;
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
    private GameController controller;
    private JButton highScoreButton, userManagementButton, worldManagementButton;
    private JLabel highScoreLabel, userManagementLabel, worldManagementLabel;

    public AdminPanel(JFrame parent, LoginPanel loginPanel, LoginPanel predecessor, GameController controller) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.predecessor.setVisible(false);

        this.highScoreButton = new JButton();
        this.userManagementButton = new JButton();
        this.userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationPanel(AdminPanel.this.parent, AdminPanel.this.loginPanel, AdminPanel.this, AdminPanel.this.controller);
            }
        });

        this.worldManagementButton = new JButton();
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
        highScoreLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
        highScoreLabel.setForeground(new Color(0, 152, 185));
        add(highScoreLabel, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 3;
        c.weightx = 3;
        c.anchor = GridBagConstraints.CENTER;
        userManagementLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
        userManagementLabel.setForeground(new Color(0, 152, 185));
        add(userManagementLabel, c);

        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 3;
        c.weightx = 3;
        c.anchor = GridBagConstraints.CENTER;
        worldManagementLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
        worldManagementLabel.setForeground(new Color(0, 152, 185));
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