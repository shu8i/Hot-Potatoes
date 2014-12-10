package control;

import static model.Direction.DOWN;
import static model.Direction.LEFT;
import static model.Direction.RIGHT;
import static model.Direction.UP;

import java.awt.Color;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.SwingWorker;

import model.Code;
import model.CodeBlock;
import model.User;
import util.Constants;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 * 
 * @see Code
 * @see view.CodePanel
 */
public class CodeController extends SwingWorker<Void, Void> {

	private Code code;
	private User user;
	Controller controller;
	private boolean stepper;
	private CodeBlock ptr, current;

	/**
	 * Constructor for the codeController class
	 * 
	 * @param user
	 *            the current user for this codecontroller
	 * @param controller
	 *            the main controller that controls this controller
	 */
	public CodeController(User user, Controller controller) {
		this.controller = controller;
		this.user = user;
		this.stepper = false;
		this.code = new Code();
	}

	/**
	 * Main class that will control the code and view
	 */
	public void runCode() {
		runPartial(this.code.getHead(), false);
		this.stepper = false;
	}

	@Override
	public void done() {
		if (this.controller.robotController.getRobot().getCoordinate()
				.equals(this.controller.playPanel.grid.getHome().coordinates())) {
			this.controller.userController.addGridPlayed(
					this.controller.playPanel.grid,
					this.controller.robotController.backpackSize());
			this.controller.playPanel.hintPanel
					.updateHint(
							"Level Completed. "
									+ 100 * this.controller.robotController.getRobot().getBackpackSize() / this.controller.getCurrent_grid().numPotatoes()
									+ "% potatoes collected.",
							Constants.COLOR_DARK_GREEN);
			new java.util.Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					CodeController.this.controller.playPanel.goBack();
				}
			}, 5000);
		}
		this.controller.playPanel.gridPanel.softRefresh();
	}

	/**
	 * Run the code step by step.
	 */
	/*
	 * public void step() { Iterator<CodeBlock> iterator =
	 * this.code.iterator(this.ptr);
	 * 
	 * if(this.stepper == false) { this.ptr = this.code.getHead();
	 * runCodeBlock(this.ptr); iterator = this.code.iterator(this.ptr);
	 * 
	 * this.setCurrentBlock(this.ptr);
	 * 
	 * 
	 * this.setCurrentBlock(this.ptr);
	 * 
	 * this.ptr = iterator.next(); this.stepper = true; } else {
	 * runCodeBlock(this.ptr);
	 * 
	 * this.setCurrentBlock(this.ptr);
	 * 
	 * 
	 * 
	 * this.setCurrentBlock(this.ptr);
	 * 
	 * this.ptr = iterator.next(); } }
	 */

	private void runPartial(CodeBlock head, boolean stepByStep) {
		runCodeBlock(head, stepByStep);
		Iterator<CodeBlock> iterator = this.code.iterator(head);
		while (iterator.hasNext()) {
			runCodeBlock(iterator.next(), stepByStep);
		}
	}

	private void runCodeBlock(CodeBlock codeBlock, boolean stepByStep) {

		// TODO Implement worker threads if we want step by step code running.
		if (stepByStep) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			this.controller.playPanel.gridPanel.softRefresh();
			this.controller.playPanel.codePanel.markBeingProcessed(codeBlock
					.getId());
		}
		if (codeBlock != null && !codeBlock.getCodetext().isEmpty()) {
			if (codeBlock.isConditional()) {
				if (conditionalIsTrue(codeBlock)) {
					runPartial(codeBlock.getTrueCondition(), stepByStep);
				} else {
					if (codeBlock.getFalseCondition() != null)
						runPartial(codeBlock.getFalseCondition(), stepByStep);
				}
			} else if (codeBlock.isLoop()) {
				while (conditionalIsTrue(codeBlock)) {
					runPartial(codeBlock.getTrueCondition(), stepByStep);
				}
			} else {
				runAction(codeBlock);
			}
		}
	}

	private boolean conditionalIsTrue(CodeBlock codeBlock) {
		String condition = codeBlock.getCondition();
		System.out
				.println("EVALUATING CONDITIONAL " + codeBlock.getCondition());
		switch (condition) {
		case "FACING LEFT":
			return this.controller.robotController.facing(LEFT);
		case "FACING RIGHT":
			return this.controller.robotController.facing(RIGHT);
		case "FACING DOWN":
			return this.controller.robotController.facing(DOWN);
		case "FACING UP":
			return this.controller.robotController.facing(UP);
		default:
			return this.controller.robotController.dirIsFree();
		}
	}

	private void runAction(CodeBlock codeBlock) {
		String codeText = codeBlock.getCodetext();
		System.out.println("EXECUTING " + codeBlock.getCodetext());
		switch (codeText) {
		case "MOVE":
			this.controller.robotController.move();
			break;
		case "TURN LEFT":
			this.controller.robotController.turnLeft();
			break;
		case "PUT POTATO":
			this.controller.robotController.drop();
			break;
		case "PICK POTATO":
			this.controller.robotController.pickup();
			this.controller.playPanel.hintPanel.updateHint("SCORE:" + this.controller.robotController.getRobot().getBackpackSize() + "     "
					+ "HIGH SCORE:" + this.controller.userController
					.getGridScore(this.controller.playPanel.grid), Color.blue);
			break;
		case "END":
		default:
			break;
		}
	}

	/**
	 * Edit part of the codeblock
	 * 
	 * @param code
	 * @return 1 if fails 0 if not
	 */
	public int editCodeBlock(CodeBlock code) {
		return 0;
	}

	/**
	 * remove entire code block from Code
	 * 
	 * @param code
	 * @return 1 if fails 0 if not
	 */
	public int removeCodeBlock(CodeBlock code) {
		return 0;
	}

	/**
	 * add codeblock to the end of the code
	 * 
	 * @param codeBlock
	 * @return 1 if fails 0 if not
	 */
	public CodeController addCodeBlock(CodeBlock codeBlock) {
		this.code.add(codeBlock);
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(),
				this.code);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.ptr = this.code.getHead();
		return this;
	}

	public CodeController undo() {
		this.code.undo();
		return this;
	}

	/**
	 * @return Iterator for the CodeBlock
	 */
	public Iterator<CodeBlock> viewIterator() {
		return this.code.viewIterator();
	}

	/**
	 * @param id
	 * @return CodeController without the block specified. Same codeController
	 *         otherwise
	 */
	public CodeController removeBlock(int id) {
		this.code.removeBlock(id);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.ptr = this.code.getHead();
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(),
				this.code);
		return this;
	}

	/**
	 * Edit the current code in panel
	 * 
	 * @param id
	 *            id of the code
	 * @param newContent
	 *            new content for the code
	 * @return code controller with modified code. Same codeController if fails
	 *         otherwise.
	 */
	public CodeController editCode(int id, String newContent) {
		this.code.edit(id, newContent);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.stepper = false;
		this.ptr = this.code.getHead();

		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(),
				this.code);

		return this;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(),
				this.code);
	}

	/**
	 * @return current Code
	 */
	public Code getCode() {
		return this.code;
	}

	/**
	 * @param code
	 * @return CodeController with merged code.
	 */
	public CodeController mergeCode(Code code) {
		this.code.merge(code);
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(),
				this.code);
		return this;
	}

	/**
	 * @return CodeController with a new empty Code.
	 */
	public CodeController clear() {
		this.code = new Code();
		this.user.resetCodePlayedinGrid(this.controller.getCurrent_grid());
		this.stepper = false;
		this.ptr = this.code.getHead();
		return this;
	}

	/**
	 * @return CodeBlock that is currently selected
	 */
	public CodeBlock getCurrentBlock() {
		return this.current;
	}

	/**
	 * @param curr
	 */
	public void setCurrentBlock(CodeBlock curr) {
		this.current = curr;
	}

	@Override
	public Void doInBackground() throws Exception {
		runPartial(this.code.getHead(), true);
		return null;
	}
}
