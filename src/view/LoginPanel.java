package view;

import control.Controller;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class LoginPanel extends JPanel
{

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private JFrame parent;
    private JTextField usernameTextfield;
    private JPasswordField passwordTextfield;

    /**
     * Represents the panel, where a user can log in.
     * @param parent        the parent (JFrame)
     * @param controller    the controller
     */
    public LoginPanel(JFrame parent, final Controller controller) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.parent = parent;

        JLabel titleLabel = new JLabel("Please Login"),
               usernameLabel = new JLabel("Username"),
               passwordLabel = new JLabel("Password");
        this.usernameTextfield = new JTextField();
        this.passwordTextfield = new JPasswordField();

        final HintPanel hintPanel = new HintPanel(new Dimension(250, 20));

        final JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hintPanel.removeHint();
                String username = LoginPanel.this.usernameTextfield.getText();
                String password = new String(LoginPanel.this.passwordTextfield.getPassword());

                if(!username.equals(Constants.DEFAULT_USERNAME) && !controller.doesUserExist(username)) {
                    hintPanel.updateHint("User \"" + username + "\" does not exist!", Color.RED);
                } else if (username.equals(Constants.DEFAULT_USERNAME)) {
                    if (controller.doesAdminExist()) {
                        hintPanel.updateHint("Admin account expired!", Color.RED);
                    } else {
                        if (password.equals(Constants.DEFAULT_PASSWORD)){
                            new AdminPanel(LoginPanel.this.parent, LoginPanel.this, LoginPanel.this, controller);
                        } else {
                            hintPanel.updateHint("Incorrect Password.", Color.RED);
                        }
                    }
                } else if (controller.doesUserExist(username) && !controller.doesPasswordMatch(username, password)) {
                    hintPanel.updateHint("Incorrect Password.", Color.RED);
                } else {
                    if (controller.getUsers().get(username).isAdmin()) {
                        new AdminPanel(LoginPanel.this.parent, LoginPanel.this, LoginPanel.this, controller);
                    } else {
                        controller.login(username);
                        new StudentPanel(LoginPanel.this.parent, LoginPanel.this, LoginPanel.this, controller, controller.getUsers().get(username));
                    }
                }
            }
        });
        KeyListener l =
                new KeyListener(){
                    public void keyPressed(KeyEvent e){
                    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    		loginButton.doClick();
                    	}  
                    }
					@Override
					public void keyTyped(KeyEvent e) {
						// do nothing
					}
					@Override
					public void keyReleased(KeyEvent e) {
						//do nothing
					}
                };            
        
        this.usernameTextfield.addKeyListener(l);
        this.passwordTextfield.addKeyListener(l);

        c.anchor = GridBagConstraints.WEST;
        titleLabel.setFont(Constants.FONT_OPEN_SANS_20);
        titleLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
        add(titleLabel, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridwidth = 23;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        usernameLabel.setFont(Constants.FONT_OPEN_SANS_14);
        usernameLabel.setForeground(Color.GRAY);
        add(usernameLabel, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 1;
        c.gridwidth = 16;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(usernameTextfield, c);

        c = new GridBagConstraints();
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        passwordLabel.setFont(Constants.FONT_OPEN_SANS_14);
        passwordLabel.setForeground(Color.GRAY);
        add(passwordLabel, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 2;
        c.gridwidth = 16;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(passwordTextfield, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 21;
        add(hintPanel, c);

        c = new GridBagConstraints();
        c.gridx = 22;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        loginButton.setFocusable(false);
        add(loginButton, c);

    }

    /**
     * Empties out the username field.
     */
    public void resetTextFields() {
        this.usernameTextfield.setText("");
        this.passwordTextfield.setText("");
    }

    public JFrame parent() {
        return this.parent;
    }

}