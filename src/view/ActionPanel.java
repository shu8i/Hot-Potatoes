package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import model.CodeBlock;
import model.CodeType;
import control.Controller;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ActionPanel extends JPanel {

	private CodeBlockPanel whileButton, turnLeftButton, moveButton, ifButton,
			putPotatoButton, pickPotatoButton, facingLeftButton,
			facingRightButton, facingUpButton, facingDownButton, endButton,
			elseButton, dirIsFree;
	Controller controller;

	/**
	 * @author Shahab
	 * @author Allant
	 *	Code modes.
	 */
	public enum PanelMode {
		/**
		 * Condition to be set
		 */
		CONDITIONAL_DECLARATION,
		/**
		 * While is being used
		 */
		WHILE_DECLARATION,
		/**
		 * Code currently in condition
		 */
		IN_CONDITIONAL, 
		/**
		 * Code currently in While
		 */
		IN_WHILE,
		/**
		 * Code currently in Action
		 */
		ACTION, 
		/**
		 * Code currently in else
		 */
		IN_ELSE
	}

	protected Stack<PanelMode> mode;
	protected boolean editMode = false;

	public ActionPanel(final CodePanel codePanel, Controller controller) {
		super(new FlowLayout());
		this.controller = controller;
		this.mode = new Stack<PanelMode>();
		setPreferredSize(new Dimension(500, 100));
		setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));

		this.whileButton = new CodeBlockPanel("WHILE");
		this.turnLeftButton = new CodeBlockPanel("TURN LEFT");
		this.moveButton = new CodeBlockPanel("MOVE");
		this.ifButton = new CodeBlockPanel("IF");
		this.putPotatoButton = new CodeBlockPanel("PUT POTATO");
		this.pickPotatoButton = new CodeBlockPanel("PICK POTATO");
		this.facingLeftButton = new CodeBlockPanel("FACING LEFT");
		this.facingRightButton = new CodeBlockPanel("FACING RIGHT");
		this.facingUpButton = new CodeBlockPanel("FACING UP");
		this.facingDownButton = new CodeBlockPanel("FACING DOWN");
		this.dirIsFree = new CodeBlockPanel("DIR IS FREE");
		this.endButton = new CodeBlockPanel("END");
		this.elseButton = new CodeBlockPanel("ELSE");

		this.whileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionPanel.this.controller.codeController
						.addCodeBlock(new CodeBlock("WHILE", new CodeType(
								CodeType.Type.WHILE)));

				ActionPanel.this.mode.push(PanelMode.WHILE_DECLARATION);
				repaintActionPanel();
				codePanel.refreshPanel();
			}
		});

		this.turnLeftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("TURN LEFT",
									new CodeType(CodeType.Type.ACTION)));
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "TURN LEFT");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("MOVE", new CodeType(
									CodeType.Type.ACTION)));
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "MOVE");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.ifButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionPanel.this.controller.codeController
						.addCodeBlock(new CodeBlock("IF", new CodeType(
								CodeType.Type.IF)));

				ActionPanel.this.mode.push(PanelMode.CONDITIONAL_DECLARATION);
				repaintActionPanel();
				codePanel.refreshPanel();
			}
		});

		this.elseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionPanel.this.controller.codeController
						.addCodeBlock(new CodeBlock("ELSE", new CodeType(
								CodeType.Type.ELSE)));

				ActionPanel.this.mode.pop();
				ActionPanel.this.mode.push(PanelMode.IN_ELSE);
				repaintActionPanel();
				codePanel.refreshPanel();
			}
		});

		this.pickPotatoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("PICK POTATO",
									new CodeType(CodeType.Type.ACTION)));
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "PICK POTATO");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.putPotatoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("PUT POTATO",
									new CodeType(CodeType.Type.ACTION)));
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "PUT POTATO");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.dirIsFree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("DIR IS FREE",
									new CodeType(CodeType.Type.ACTION)));

					ActionPanel.this.mode.push(ActionPanel.this.mode.pop().equals(
							PanelMode.CONDITIONAL_DECLARATION) ? PanelMode.IN_CONDITIONAL
							: PanelMode.IN_WHILE);
					repaintActionPanel();
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "DIR IS FREE");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.facingLeftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("FACING LEFT",
									new CodeType(CodeType.Type.ACTION)));

					ActionPanel.this.mode.push(ActionPanel.this.mode.pop().equals(
							PanelMode.CONDITIONAL_DECLARATION) ? PanelMode.IN_CONDITIONAL
							: PanelMode.IN_WHILE);
					repaintActionPanel();
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "FACING LEFT");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.facingRightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("FACING RIGHT",
									new CodeType(CodeType.Type.ACTION)));

					ActionPanel.this.mode.push(ActionPanel.this.mode.pop().equals(
							PanelMode.CONDITIONAL_DECLARATION) ? PanelMode.IN_CONDITIONAL
							: PanelMode.IN_WHILE);
					repaintActionPanel();
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "FACING RIGHT");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.facingUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("FACING UP",
									new CodeType(CodeType.Type.ACTION)));

					ActionPanel.this.mode.push(ActionPanel.this.mode.pop().equals(
							PanelMode.CONDITIONAL_DECLARATION) ? PanelMode.IN_CONDITIONAL
							: PanelMode.IN_WHILE);
					repaintActionPanel();
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "FACING UP");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.facingDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionPanel.this.editMode) {
					ActionPanel.this.controller.codeController
							.addCodeBlock(new CodeBlock("FACING DOWN",
									new CodeType(CodeType.Type.ACTION)));

					ActionPanel.this.mode.push(ActionPanel.this.mode.pop().equals(
							PanelMode.CONDITIONAL_DECLARATION) ? PanelMode.IN_CONDITIONAL
							: PanelMode.IN_WHILE);
					repaintActionPanel();
					codePanel.refreshPanel();
				} else {
					ActionPanel.this.controller.codeController.editCode(
							codePanel.getEditableCodeId(), "FACING DOWN");
					codePanel.updateBlockForEdit(null);
					repaintActionPanel();
					codePanel.refreshPanel();
					ActionPanel.this.editMode = !ActionPanel.this.editMode;
				}
			}
		});

		this.endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionPanel.this.controller.codeController
						.addCodeBlock(new CodeBlock("END", new CodeType(
								CodeType.Type.END)));

				ActionPanel.this.mode.pop();
				repaintActionPanel();
				codePanel.refreshPanel();
			}
		});

		this.mode.push(PanelMode.ACTION);
		repaintActionPanel();
	}

	public void repaintActionPanel() {
		removeAll();
		switch (this.mode.peek()) {
		case WHILE_DECLARATION:
		case CONDITIONAL_DECLARATION:
			add(this.facingLeftButton);
			add(this.facingRightButton);
			add(this.facingUpButton);
			add(this.facingDownButton);
			add(this.dirIsFree);
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			break;
		case IN_CONDITIONAL:
			add(this.elseButton);
			add(this.endButton);
			add(this.ifButton);
			add(this.whileButton);
			add(this.turnLeftButton);
			add(this.moveButton);
			add(this.putPotatoButton);
			add(this.pickPotatoButton);
			break;
		case IN_WHILE:
			add(this.endButton);
			add(this.ifButton);
			add(this.whileButton);
			add(this.turnLeftButton);
			add(this.moveButton);
			add(this.putPotatoButton);
			add(this.pickPotatoButton);
			add(new CodeBlockPanel(""));
			break;
		case IN_ELSE:
			add(this.endButton);
			add(this.ifButton);
			add(this.whileButton);
			add(this.turnLeftButton);
			add(this.moveButton);
			add(this.putPotatoButton);
			add(this.pickPotatoButton);
			add(new CodeBlockPanel(""));
			break;
		case ACTION:
			add(this.whileButton);
			add(this.ifButton);
			add(this.turnLeftButton);
			add(this.moveButton);
			add(this.putPotatoButton);
			add(this.pickPotatoButton);
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
		default:
			break;
		}
		repaint();
		revalidate();
	}

	private void repaintActionPanel(PanelMode mode) {
		removeAll();
		switch (mode) {
		case CONDITIONAL_DECLARATION:
			add(this.facingLeftButton);
			add(this.facingRightButton);
			add(this.facingUpButton);
			add(this.facingDownButton);
			add(this.dirIsFree);
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			break;
		case ACTION:
			add(this.turnLeftButton);
			add(this.moveButton);
			add(this.putPotatoButton);
			add(this.pickPotatoButton);
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
			add(new CodeBlockPanel(""));
		default:
			break;
		}
		repaint();
		revalidate();
	}

	public void updateActionPanel(CodeBlockPanel codeBlock) {
		if (codeBlock.isConditional() || codeBlock.isElseButton()
				|| codeBlock.isEndButton()) {
			this.editMode = false;
			return;
		}
		if (codeBlock.getText().contains("FACING")) {
			repaintActionPanel(PanelMode.CONDITIONAL_DECLARATION);
			this.editMode = true;
		} else {
			repaintActionPanel(PanelMode.ACTION);
			this.editMode = true;
		}
	}

	public Stack<PanelMode> getActionStack() {
		return this.mode;
	}

	public ActionPanel mergeStack(Stack<PanelMode> mode) {
		this.mode.addAll(mode);
		return this;
	}

	public boolean inConditional() {
		return this.mode.peek().equals(PanelMode.CONDITIONAL_DECLARATION)
				|| this.mode.peek().equals(PanelMode.WHILE_DECLARATION);
	}

	public void reset() {
		this.mode.clear();
		this.mode.push(PanelMode.ACTION);
		repaintActionPanel();
	}

	public ActionPanel undo() {
		this.mode.pop();
		if (this.mode.isEmpty()) {
			this.mode.push(PanelMode.ACTION);
		}
		repaintActionPanel();
		return this;
	}
}
