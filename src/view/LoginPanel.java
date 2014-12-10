package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import util.Constants;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class LoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2867724725971487756L;
	protected static GridBagLayout layout = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	JFrame parent;
	JTextField usernameTextfield;
	JPasswordField passwordTextfield;

	/**
	 * Represents the panel, where a user can log in.
	 * 
	 * @param parent
	 *            the parent (JFrame)
	 * @param controller
	 *            the controller
	 */
	public LoginPanel(JFrame parent, final Controller controller) {
		super(layout);
		super.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.parent = parent;

		JLabel titleLabel = new JLabel("Please Login"), usernameLabel = new JLabel(
				"Username"), passwordLabel = new JLabel("Password");
		this.usernameTextfield = new JTextField();
		this.passwordTextfield = new JPasswordField();

		final HintPanel hintPanel = new HintPanel(new Dimension(250, 20));

		final JButton loginButton = new JButton("Login");

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hintPanel.removeHint();
				String username = LoginPanel.this.usernameTextfield.getText();
				String password = new String(LoginPanel.this.passwordTextfield
						.getPassword());

				if (!username.equals(Constants.DEFAULT_USERNAME)
						&& !Controller.doesUserExist(username)) {
					hintPanel.updateHint("User \"" + username
							+ "\" does not exist!", Color.RED);
				} else if (username.equals(Constants.DEFAULT_USERNAME)) {
					if (Controller.doesAdminExist()) {
						hintPanel.updateHint("Admin account expired!",
								Color.RED);
					} else {
						if (password.equals(Constants.DEFAULT_PASSWORD)) {
							new AdminPanel(LoginPanel.this.parent,
									LoginPanel.this, LoginPanel.this,
									controller);
						} else {
							hintPanel.updateHint("Incorrect Password.",
									Color.RED);
						}
					}
				} else if (Controller.doesUserExist(username)
						&& !Controller.doesPasswordMatch(username, password)) {
					hintPanel.updateHint("Incorrect Password.", Color.RED);
				} else {
					if (Controller.getUsers().get(username).isAdmin()) {
						new AdminPanel(LoginPanel.this.parent, LoginPanel.this,
								LoginPanel.this, controller);
					} else {
						controller.login(username);
						new StudentPanel(LoginPanel.this.parent,
								LoginPanel.this, LoginPanel.this, controller);
					}
				}
			}
		});
		KeyListener l = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// do nothing
			}
		};

		this.usernameTextfield.addKeyListener(l);
		this.passwordTextfield.addKeyListener(l);

		this.c.anchor = GridBagConstraints.WEST;
		titleLabel.setFont(Constants.FONT_OPEN_SANS_20);
		titleLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
		add(titleLabel, this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 1;
		this.c.gridwidth = 23;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		add(new JPanel(), this.c);

		this.c = new GridBagConstraints();
		this.c.gridy = 1;
		this.c.anchor = GridBagConstraints.WEST;
		usernameLabel.setFont(Constants.FONT_OPEN_SANS_14);
		usernameLabel.setForeground(Color.GRAY);
		add(usernameLabel, this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 1;
		this.c.gridy = 1;
		this.c.gridwidth = 6;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		add(new JPanel(), this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 7;
		this.c.gridy = 1;
		this.c.gridwidth = 16;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		add(this.usernameTextfield, this.c);

		this.c = new GridBagConstraints();
		this.c.gridy = 2;
		this.c.anchor = GridBagConstraints.WEST;
		passwordLabel.setFont(Constants.FONT_OPEN_SANS_14);
		passwordLabel.setForeground(Color.GRAY);
		add(passwordLabel, this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 1;
		this.c.gridy = 2;
		this.c.gridwidth = 6;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		add(new JPanel(), this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 7;
		this.c.gridy = 2;
		this.c.gridwidth = 16;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		add(this.passwordTextfield, this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 0;
		this.c.gridy = 3;
		this.c.gridwidth = 21;
		add(hintPanel, this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 22;
		this.c.gridy = 3;
		this.c.fill = GridBagConstraints.HORIZONTAL;
		loginButton.setFocusable(false);
		add(loginButton, this.c);

	}

	/**
	 * Empties out the username field.
	 */
	public void resetTextFields() {
		this.usernameTextfield.setText("");
		this.passwordTextfield.setText("");
	}

	/**
	 * @return parent object
	 */
	public JFrame parent() {
		return this.parent;
	}

}