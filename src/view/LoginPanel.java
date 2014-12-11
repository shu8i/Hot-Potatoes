package view;

import control.Controller;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JButton loginButton = new JButton("Login");
        
        JButton helpButton = new JButton("Help");
        
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame help = new JFrame("Help");
                help.setResizable(false);
                JPanel panel = new JPanel();
            	help.setVisible(true);
            	help.setSize(600, 600);
                help.add(panel);
                JTextArea helpText = new JTextArea();
                helpText.setLineWrap(true);
                helpText.setWrapStyleWord(true);
                helpText.setText("For Admins: There are three options for admin activity. The registration button (the middle button) "
                		+ "allows a teacher to add new teachers to the program as admins and add new students to the program as players. "
                		+ "Additionally, teachers can delete students and teachers from the program here. The left button is the highscore "
                		+ "button which is used to track student progress in each level. Finally, the right button is the world edit button. "
                		+ "This is where teachers will spend a bulk of their time on this program editing new worlds for studets to play. "
                		+ "Each world must have only one Karel and only one home and have a grid size from 10 to 50. Everything else is up to "
                		+ "the teacher. Used to check fields at the bottom to select which item to place in the play grid and click to place "
                		+ "the item. Double clicking will delete an item and clicking over an object will replace it with the selected object. "
                		+ "When a teacher is done, he can save the world using the menu on the top left area of the play window. Here a teacher can "
                		+ "also load a world to further edit it or even delete a world.												"
                		+ "For Students: Students can select whichever world they wish to play upon logging in. Upon loading the world, "
                		+ "students will see a play grid, a blank side panel next to the grid, and buttons on the bottom of the play window. "
                		+ "The main objective for the student is to make programs for Wall-E to collect all the potatoes and reach the home on the grid. "
                		+ "A student may do this by clicking on the buttons they wish to add to his program. Once a button is clicked, "
                		+ "it will be added to the side panel next to the grid. A student may delete a piece of code by double clicking it "
                		+ "and he may also add after a specific piece of code by clicking on the piece of codes he wants to add after "
                		+ "and then click the code to be added itself. The buttons on the bottom will change depending on what a student's valid "
                		+ "options to add to a program are. For example, all IF and WHILE code pieces must end with an END. A student may save "
                		+ "a created program by using the save as macro option in the menu that is at the top of the play window. Once a macro is save, "
                		+ "it will appear in the macro area at the bottom right of the play window. A student may also use this menu to clear all "
                		+ " of his code from the side panel, run his code on the world, go back to select a new world, or run his code in "
                		+ "step by step mode. Here a student will be able to see exactly what his code does. Once a student completes a world, "
                		+ "a message will be displayed at the bottom to notify the student with his score and send him back to the "
                		+ "world selection screen.");
                helpText.setPreferredSize(new Dimension(600, 600));
                helpText.setEditable(false);
                panel.add(helpText);
            }
        });

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
                        new StudentPanel(LoginPanel.this.parent, LoginPanel.this, LoginPanel.this, controller);
                    }
                }
            }
        });

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
        c.gridwidth = 16;
        add(hintPanel, c);

        c = new GridBagConstraints();
        c.gridx = 22;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        loginButton.setFocusable(false);
        add(loginButton, c);
        
        c = new GridBagConstraints();
        c.gridx = 17;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        helpButton.setFocusable(false);
        add(helpButton, c);

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