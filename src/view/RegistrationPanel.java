package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.User;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class RegistrationPanel extends JPanel {

	protected static GridBagLayout layout = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	private DefaultListModel<User> model;
	private JList<User> list;
	private LoginPanel loginPanel;
	private AdminPanel predecessor;
	private JFrame parent;
	private JPanel detailsPanel;
	private JButton add, delete;
	private JRadioButton isAdminRadio;
	private JTextField username;
	private JPasswordField password;
	private Controller controller;
	private JMenuItem backMenu;

	/**
	 * Represents the panel, when an admin user logs in
	 * 
	 * @param parent
	 *            the parent of this panel (the JFrame)
	 * @param predecessor
	 *            the predecessor (LoginPanel)
	 * @param controller
	 *            the controller
	 */
	public RegistrationPanel(JFrame parent, LoginPanel loginPanel,
			AdminPanel predecessor, Controller controller) {
		super(layout);
		this.loginPanel = loginPanel;
		this.predecessor = predecessor;
		this.parent = parent;
		this.controller = controller;
		this.detailsPanel = generateDetailsPanel();
		this.predecessor.setVisible(false);

		this.backMenu = new JMenuItem("Back");
		this.backMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistrationPanel.this.setVisible(false);
				RegistrationPanel.this.predecessor.updateMenu();
				RegistrationPanel.this.predecessor.setVisible(true);
				RegistrationPanel.this.loginPanel.parent().pack();
				RegistrationPanel.this.loginPanel.parent()
						.setLocationRelativeTo(null);
			}
		});

		// list
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 9;
		c.fill = GridBagConstraints.BOTH;
		add(generateListPanel(), c);

		// buttons
		c = new GridBagConstraints();
		c.weightx = 0;
		c.weighty = 0;
		c.gridheight = 4;
		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.NONE;
		add(generateActionPanel(), c);

		// username textfield
		c = new GridBagConstraints();
		c.gridy = 4;
		c.weightx = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 20, 10, 20);
		add(this.detailsPanel, c);

		// whitespace
		c = new GridBagConstraints();
		c.weighty = 1;
		c.gridwidth = 20;
		c.gridheight = 5;
		c.gridy = 4;
		c.fill = GridBagConstraints.BOTH;
		add(new JPanel(), c);

		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		updateMenu();
		this.parent.add(this, c);
		this.parent.pack();
		this.parent.setLocationRelativeTo(null);
	}

	/**
	 * Generates the listpanel, where users are displayed
	 * 
	 * @return the jpanel
	 */
	private JPanel generateListPanel() {
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 0));
		this.model = new DefaultListModel();

		for (final Map.Entry<String, User> entry : Controller.getUsers()
				.entrySet()) {
			this.model.addElement(entry.getValue());
		}

		this.list = new JList(this.model);
		this.list
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.list.clearSelection();
		JScrollPane listScroller = new JScrollPane(this.list);
		listPanel.add(listScroller);

		this.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()
						&& // Check for double firing.
						RegistrationPanel.this.model.size() > 0
						&& RegistrationPanel.this.list.getSelectedIndices().length > 0) {
					RegistrationPanel.this.delete.setEnabled(true);
				}
			}
		});

		return listPanel;
	}

	/**
	 * generates the panel, where the buttons are
	 * 
	 * @return the jpanel
	 */
	private JPanel generateActionPanel() {
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(3, 1));
		this.add = new JButton("Add");
		this.delete = new JButton("Delete");

		actionPanel.setLayout(new GridLayout(3, 1));

		this.add.setEnabled(false);
		this.add.setFocusable(false);
		this.delete.setEnabled(false);
		this.delete.setFocusable(false);

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField username = RegistrationPanel.this.username;
				JPasswordField password = RegistrationPanel.this.password;
				User user = new User(username.getText(), new String(password
						.getPassword()), RegistrationPanel.this.isAdminRadio
						.isSelected());

				RegistrationPanel.this.model.add(0, user);
				RegistrationPanel.this.controller.addUser(user);

				username.setText("");
				password.setText("");
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (RegistrationPanel.this.model.size() > 0
						&& RegistrationPanel.this.list.getSelectedIndices().length > 0) {
					RegistrationPanel.this.controller
							.deleteUser(RegistrationPanel.this.list
									.getSelectedValue().getUsername());
					RegistrationPanel.this.model
							.remove(RegistrationPanel.this.list
									.getSelectedIndex());
					RegistrationPanel.this.delete.setEnabled(false);
				}
			}
		});

		actionPanel.add(this.add);
		actionPanel.add(this.delete);

		return actionPanel;
	}

	/**
	 * Generates the panel where the textfields are (for username/fullname)
	 * 
	 * @return the jpanel
	 */
	private JPanel generateDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridBagLayout());

		DocumentListener documentListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				enableAddButton();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				enableAddButton();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				enableAddButton();
			}

			public void enableAddButton() {
				String username = RegistrationPanel.this.username.getText(), password = new String(
						RegistrationPanel.this.password.getPassword());

				if (!username.equals("") && !password.equals("")
						&& !Controller.doesUserExist(username)) {
					RegistrationPanel.this.add.setEnabled(true);
				} else {
					RegistrationPanel.this.add.setEnabled(false);
				}
			}
		};

		this.isAdminRadio = new JRadioButton("Admin", false);
		isAdminRadio.setFont(new Font("Open Sans", Font.PLAIN, 14));
		isAdminRadio.setForeground(Color.GRAY);
		isAdminRadio.setFocusable(false);
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
		usernameLabel.setForeground(Color.GRAY);
		this.username = new JTextField();
		this.username.getDocument().addDocumentListener(documentListener);
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
		passwordLabel.setForeground(Color.GRAY);
		this.password = new JPasswordField();
		this.password.getDocument().addDocumentListener(documentListener);

		c = new GridBagConstraints();
		c.ipadx = 30;
		c.anchor = GridBagConstraints.WEST;
		detailsPanel.add(isAdminRadio, c);

		c.gridy = 1;
		detailsPanel.add(usernameLabel, c);

		c.gridy = 2;
		detailsPanel.add(passwordLabel, c);

		c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.ipadx = 100;
		detailsPanel.add(this.username, c);

		c.gridy = 2;
		detailsPanel.add(this.password, c);

		return detailsPanel;
	}

	public void updateMenu() {
		this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel,
				controller, this, this.backMenu));
	}

}