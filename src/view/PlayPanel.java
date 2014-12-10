package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

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
public class PlayPanel extends JPanel {

	protected static GridBagLayout layout = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	private LoginPanel loginPanel;
	private StudentPanel predecessor;
	private JFrame parent;
	Controller controller;
	public Grid grid;
	private JMenuItem runMenu, undoMenu, clearMenu, saveMacroMenu,
			saveGameMenu, backMenu, stepMenu;
	public HintPanel hintPanel;
	public GridPanel gridPanel;
	public CodePanel codePanel;
	public MacroPanel macroPanel;
	public ActionPanel actionPanel;
	protected Integer score;

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return this.score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @param parent
	 * @param loginPanel
	 * @param predecessor
	 * @param controller
	 * @param grid
	 */
	public PlayPanel(JFrame parent, LoginPanel loginPanel,
			StudentPanel predecessor, Controller controller, Grid grid) {
		super(layout);
		super.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.loginPanel = loginPanel;
		this.predecessor = predecessor;
		this.parent = parent;
		this.controller = controller;
		this.grid = grid;
		this.predecessor.setVisible(false);
		this.hintPanel = new HintPanel(new Dimension(500, 30));
		this.controller.initRobot(this.grid);
		this.controller.initPlay(this);
		this.controller.setCode(); // if user had previous code then set it.
		final Integer highScore;
		

		highScore = controller.userController.getGridScore(grid);
		this.score = this.controller.robotController.getRobot().getBackpackSize();
		String scoreString = this.score.toString();
		this.hintPanel.updateHint("SCORE:" + scoreString + "     "
				+ "HIGH SCORE:" + highScore, Color.blue);

		initPanels();

		// refresh the panel in case the user had code before.
		this.codePanel.refreshPanel();

		this.saveMacroMenu = new JMenuItem("Save As Macro");
		this.saveMacroMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SaveDialog(PlayPanel.this, PlayPanel.this.controller);
			}
		});

		this.undoMenu = new JMenuItem("Undo Move");
		this.undoMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int blockID = PlayPanel.this.controller.codeController
						.getCode().getlastID();
				PlayPanel.this.controller.codeController.removeBlock(blockID);
				PlayPanel.this.codePanel.removeCodeBlock();
			}
		});

		this.runMenu = new JMenuItem("Run");
		this.runMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayPanel.this.controller.codeController.runCode();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PlayPanel.this.gridPanel.refresh();
						if (PlayPanel.this.controller.robotController
								.levelFinished()) {
							PlayPanel.this.controller.userController
									.addGridPlayed(
											PlayPanel.this.grid,
											PlayPanel.this.controller.robotController
													.backpackSize());
							PlayPanel.this.hintPanel
									.updateHint(
											"Level Completed. "
													+ 100 * PlayPanel.this.controller.robotController.getRobot().getBackpackSize() / PlayPanel.this.grid.numPotatoes()
													+ "% potatoes collected.",
											Constants.COLOR_DARK_GREEN);
							new java.util.Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									PlayPanel.this.goBack();
								}
							}, 5000);
						}
					}
				});
			}
		});

		/*
		 * this.stepMenu = new JMenuItem("Take Step");
		 * this.stepMenu.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * PlayPanel.this.controller.codeController.step();
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() {
		 * PlayPanel.this.controller.playPanel.gridPanel.refresh(); if
		 * (PlayPanel.this.controller.robotController .levelFinished()) {
		 * PlayPanel.this.controller.userController .addGridPlayed(
		 * PlayPanel.this.grid, PlayPanel.this.controller.robotController
		 * .backpackSize()); PlayPanel.this.hintPanel .updateHint(
		 * "Level Completed. " + PlayPanel.this.controller.userController
		 * .getGridScore(PlayPanel.this.grid) + "% potatoes collected.",
		 * Constants.COLOR_DARK_GREEN); new java.util.Timer().schedule(new
		 * TimerTask() {
		 * 
		 * @Override public void run() { PlayPanel.this.goBack(); } }, 5000); }
		 * } }); } });
		 */

		this.stepMenu = new JMenuItem("Run Step by Step");
		this.stepMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayPanel.this.controller.codeController.execute();
			}
		});

		this.clearMenu = new JMenuItem("Clear");
		this.clearMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayPanel.this.controller.codeController.clear();
				PlayPanel.this.actionPanel.reset();
				PlayPanel.this.codePanel.refreshPanel();
			}
		});

		this.backMenu = new JMenuItem("Back");
		this.backMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayPanel.this.goBack();
			}
		});

		add(this.gridPanel, this.c);

		this.c.gridx = 1;
		this.c.weightx = 1;
		this.c.weighty = 1;
		add(new JPanel(), this.c);

		this.c = new GridBagConstraints();
		this.c.gridx = 2;
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.setPreferredSize(new Dimension(300, 500));
		add(this.codePanel, this.c);

		this.c.gridy = 1;
		this.c.gridx = 0;
		this.c.weightx = 1;
		this.c.weighty = 1;
		add(new JPanel(), this.c);

		this.c = new GridBagConstraints();
		this.c.gridy = 2;
		this.c.gridx = 0;
		panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.setPreferredSize(new Dimension(500, 100));
		add(this.actionPanel, this.c);

		this.c.gridx = 2;
		panel = new JPanel();
		add(this.macroPanel, this.c);

		this.c.gridx = 0;
		this.c.gridy = 3;
		add(this.hintPanel, this.c);

		updateMenu();
		this.parent.add(this, this.c);
		this.parent.pack();
		this.parent.setLocationRelativeTo(null);
	}

	private void initPanels() {
		this.gridPanel = new GridPanel(this.grid,
				this.controller.robotController.getRobot());
		this.codePanel = new CodePanel(new JPanel(), this.controller, this);
		this.actionPanel = new ActionPanel(this.codePanel, this.controller);
		this.macroPanel = new MacroPanel(this, this.controller);
		this.controller.codeController.clear();
		this.codePanel.refreshPanel();
		this.actionPanel.reset();
	}

	public void updateMenu() {
		this.parent.setJMenuBar(new Menu().buildMenu("Menu", this.loginPanel,
				this.controller, this, this.clearMenu, this.runMenu, this.saveMacroMenu, this.backMenu));
	}

	public void goBack() {
		this.setVisible(false);
		this.predecessor.updateMenu();
		this.predecessor.refresh();
		this.predecessor.setVisible(true);
		this.parent.pack();
		this.parent.setLocationRelativeTo(null);
		this.parent.remove(this);

	}

}