package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import util.Constants;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class MacroPanel extends JPanel {

	private Controller controller;
	private PlayPanel playPanel;

	public MacroPanel(PlayPanel playPanel, Controller controller) {
		super(new FlowLayout());
		this.controller = controller;
		this.playPanel = playPanel;

		setPreferredSize(new Dimension(500, 100));
		setBorder(new MatteBorder(1, 1, 1, 1, Constants.COLOR_SMOOTH_GREEN));
		refreshPanel();
	}

	public void refreshPanel() {
		removeAll();

		CodeBlockPanel codeBlockPanel;
		int i = 0;
		for (final String name : controller.userController.getMacros()) {
			codeBlockPanel = new CodeBlockPanel(name);
			codeBlockPanel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!MacroPanel.this.playPanel.actionPanel.inConditional()) {
						MacroPanel.this.controller.codeController
								.mergeCode(MacroPanel.this.controller.userController
										.getMacro(name));
						MacroPanel.this.playPanel.actionPanel
								.mergeStack(MacroPanel.this.controller.userController
										.getMacroPanelMode(name));
						MacroPanel.this.playPanel.actionPanel
								.repaintActionPanel();
						MacroPanel.this.playPanel.codePanel.refreshPanel();
					}
				}
			});
			add(codeBlockPanel);
			i++;
		}

		while (i++ < 8)
			add(new CodeBlockPanel(""));

		repaint();
		revalidate();
	}

}
