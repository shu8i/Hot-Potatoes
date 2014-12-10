package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.Grid;
import model.User;
import util.Constants;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ScoreList extends JPanel {
	protected static GridBagLayout layout = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	private LoginPanel loginPanel;
	private JPanel predecessor;
	private JFrame parent;
	private Controller controller;
	private JLabel scoreList;
	private JPanel studentPanel;
	private JScrollPane scoreListScrollPane;
	private JMenuItem backMenu;
	private AdminPanel adminPanel;
	private Grid grid;

	public ScoreList(JFrame parent, LoginPanel loginPanel, JPanel predecessor,
			Controller controller, AdminPanel adminPanel, Grid grid) {
		super(layout);
		super.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.loginPanel = loginPanel;
		this.predecessor = predecessor;
		this.parent = parent;
		this.controller = controller;
		this.predecessor.setVisible(false);

		this.scoreList = new JLabel("Select A Student");
		this.studentPanel = new JPanel();
		this.scoreListScrollPane = new JScrollPane(this.studentPanel);
		this.studentPanel.setAutoscrolls(true);
		this.scoreListScrollPane.setPreferredSize(new Dimension(620, 410));
		this.scoreListScrollPane.setBorder(null);
		this.studentPanel.setLayout(layout);
		this.adminPanel = adminPanel;
		this.grid = grid;

		Integer highScore = 0;

		this.backMenu = new JMenuItem("Back");
		this.backMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreList.this.setVisible(false);
				ScoreList.this.adminPanel.updateMenu();
				ScoreList.this.adminPanel.setVisible(true);
				ScoreList.this.loginPanel.parent().pack();
				ScoreList.this.loginPanel.parent().setLocationRelativeTo(null);
			}
		});

		int i = 0, j = 0, numStudents = Controller.getGrids().size();
		JButton studentSelectButton;
		for (final Map.Entry<String, User> entry : Controller.getUsers()
				.entrySet()) {
			if (entry.getValue().isAdmin() == true) {
				continue;
			} else {
				if (highScore != Controller.getUsers().get(entry.getKey())
						.getGridScore(this.grid)) {
					highScore = Controller.getUsers().get(entry.getKey())
							.getGridScore(this.grid);
				}
				studentSelectButton = new JButton("<html><center>"
						+ entry.getKey() + "<br>" + "HIGH SCORE: "
						+ highScore.toString() + "</center></html>");
			}

			studentSelectButton.setPreferredSize(new Dimension(180, 120));
			studentSelectButton.setBackground(Constants.COLOR_SMOOTH_GREEN);
			studentSelectButton.setOpaque(true);
			studentSelectButton.setBorderPainted(false);
			studentSelectButton.setFocusable(false);
			studentSelectButton.setFont(Constants.FONT_OPEN_SANS_20);
			studentSelectButton.setForeground(Color.WHITE);

			studentSelectButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					e.getComponent()
							.setBackground(Constants.COLOR_SMOOTH_GREEN);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					e.getComponent().setBackground(Constants.COLOR_ACTIONS);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					e.getComponent()
							.setBackground(Constants.COLOR_SMOOTH_GREEN);
				}
			});

			c = new GridBagConstraints();
			c.gridx = i;
			c.gridwidth = 2;
			c.gridy = j;
			c.gridheight = 2;
			c.anchor = GridBagConstraints.NORTHWEST;
			this.studentPanel.add(studentSelectButton, c);

			i += 2;

			if (i % 8 == 0) {
				if (numStudents == 6 && j == 3)
					continue;
				i = 0;
				j += 2;
				c.gridx = i;
				c.gridwidth = 8;
				c.weighty = 1;
				c.gridheight = 1;
				c.gridy = j++;
				c.fill = GridBagConstraints.BOTH;
				this.studentPanel.add(new JPanel(), c);
				continue;
			}

			c.gridx = i++;
			c.gridwidth = 1;
			c.weightx = (numStudents == 2) ? (i == 5 ? 1 : 0) : 1;
			c.gridy = j;
			c.gridheight = 2;
			c.fill = GridBagConstraints.BOTH;
			this.studentPanel.add(new JPanel(), c);
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
			this.studentPanel.add(new JPanel(), c);
		}

		add(this.scoreListScrollPane, c);

		updateMenu();
		this.parent.add(this, c);
		this.parent.pack();
		this.parent.setLocationRelativeTo(null);
	}

	public void updateMenu() {
		this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel,
				controller, this, backMenu));
	}
}