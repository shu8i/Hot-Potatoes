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
import util.Constants;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ScorePanel extends JPanel {

	// TODO When a teacher selects a level, this screen comes up and shows the
	// current scores of their students
	protected static GridBagLayout layout = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	private LoginPanel loginPanel;
	private AdminPanel predecessor;
	private JFrame parent;
	private Controller controller;
	private JLabel selectWorldLabel;
	private JPanel worldSelectionPanel;
	private JScrollPane worldSelectionScrollPane;
	private JMenuItem backMenu;

	public ScorePanel(JFrame parent, LoginPanel loginPanel,
			AdminPanel predecessor, Controller controller) {
		super(layout);
		super.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.loginPanel = loginPanel;
		this.predecessor = predecessor;
		this.parent = parent;
		this.controller = controller;
		this.predecessor.setVisible(false);

		this.selectWorldLabel = new JLabel("Select A World");
		this.worldSelectionPanel = new JPanel();
		this.worldSelectionScrollPane = new JScrollPane(
				this.worldSelectionPanel);
		this.worldSelectionPanel.setAutoscrolls(true);
		this.worldSelectionScrollPane.setPreferredSize(new Dimension(620, 410));
		this.worldSelectionScrollPane.setBorder(null);
		this.worldSelectionPanel.setLayout(layout);

		int i = 0, j = 0, numWorlds = Controller.getGrids().size();
		JButton worldSelectButton;
		for (final Map.Entry<String, Grid> entry : Controller.getGrids()
				.entrySet()) {
			worldSelectButton = new JButton("<html><center>" + entry.getKey());
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
					new StudentScorePanel(ScorePanel.this.parent,
							ScorePanel.this.loginPanel, ScorePanel.this,
							ScorePanel.this.controller, entry.getKey());
				}
			});

			worldSelectButton.addMouseListener(new MouseAdapter() {
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

			this.backMenu = new JMenuItem("Back");
			this.backMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScorePanel.this.setVisible(false);
					ScorePanel.this.predecessor.updateMenu();
					ScorePanel.this.predecessor.setVisible(true);
					ScorePanel.this.parent.pack();
					ScorePanel.this.parent.setLocationRelativeTo(null);
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
				if (numWorlds == 6 && j == 3)
					continue;
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
		this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel,
				controller, this, backMenu));
	}
}