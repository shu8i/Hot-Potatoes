package control;

import model.Code;
import model.CodeBlock;
import model.User;
import static model.Direction.*;

import util.Constants;
import view.CodePanel;

import javax.swing.*;
import java.util.Iterator;
import java.util.TimerTask;

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
	private CodePanel codeview;
	private User user;
	private Controller controller;

	public CodeController(User user, Controller controller) {
		this.controller = controller;
		this.user = user;
		this.code = new Code();
	}

	@Override
	public Void doInBackground()
	{
		runPartial(code.getHead(), true);
		return null;
	}

	@Override
	public void done()
	{
		if (controller.robotController.getRobot().getCoordinate().equals(controller.playPanel.grid.getHome().coordinates())) {
			controller.userController.addGridPlayed(
					controller.playPanel.grid, controller.robotController.backpackSize());
			controller.playPanel.hintPanel.updateHint("Level Completed. " +
					controller.userController.getGridScore(controller.playPanel.grid) +
					"% potatoes collected.", Constants.COLOR_DARK_GREEN);
			new java.util.Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					controller.playPanel.goBack();
				}
			}, 5000);
		}
		controller.playPanel.gridPanel.softRefresh();
		controller.reinitCodeController();
	}

	/**
	 * Main class that will control the code and view
	 */	
	public void runCode() {

		if(code.getHead() != null)
			runPartial(code.getHead(), false);
	}

	private void runPartial(CodeBlock head, boolean stepByStep)
	{
		runCodeBlock(head, stepByStep);
		Iterator<CodeBlock> iterator = code.iterator(head);
		while (iterator.hasNext())
		{
			runCodeBlock(iterator.next(), stepByStep);
		}
	}

	private void runCodeBlock(CodeBlock codeBlock, boolean stepByStep)
	{
		boolean inMacro = codeBlock.isInMacro();

		if (stepByStep) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}

			controller.playPanel.gridPanel.softRefresh();

			if(inMacro || codeBlock.isMacro())
			{
				controller.playPanel.codePanel.markBeingProcessed(codeBlock.getId(), true);
			}
			else
				controller.playPanel.codePanel.markBeingProcessed(codeBlock.getId(), false);

		}
		if (codeBlock != null && !codeBlock.getCodetext().isEmpty())
		{
			if (codeBlock.isConditional())
			{
				if (conditionalIsTrue(codeBlock))
				{
					runPartial(codeBlock.getTrueCondition(), stepByStep);
				}
				else
				{
					if (codeBlock.getFalseCondition() != null)
					{	
						runPartial(codeBlock.getFalseCondition(), stepByStep);
					}	
				}
			}
			else if (codeBlock.isLoop())
			{
				while (conditionalIsTrue(codeBlock))
				{
					runPartial(codeBlock.getTrueCondition(), stepByStep);
				}
			}
			else if (codeBlock.isMacro())
			{
				runPartial(codeBlock.getMacroBranch(), stepByStep);
				inMacro = true;
			}
			else
			{
				runAction(codeBlock);
			}
		}
	}

	private boolean conditionalIsTrue(CodeBlock codeBlock)
	{
		String condition = codeBlock.getCondition();
		System.out.println("EVALUATING CONDITIONAL " + codeBlock.getCondition());
		switch (condition)
		{
		case "FACING LEFT":
			return controller.robotController.facing(LEFT);
		case "FACING RIGHT":
			return controller.robotController.facing(RIGHT);
		case "FACING DOWN":
			return controller.robotController.facing(DOWN);
		case "FACING UP":
			return controller.robotController.facing(UP);
		default:
			return controller.robotController.dirIsFree();
		}
	}

	private void runAction(CodeBlock codeBlock)
	{
		String codeText = codeBlock.getCodetext();
		System.out.println("EXECUTING " + codeBlock.getCodetext());
		switch (codeText)
		{
		case "MOVE":
			controller.robotController.move();
			break;
		case "TURN LEFT":
			controller.robotController.turnLeft();
			break;
		case "PUT POTATO":
			controller.robotController.drop();
			break;
		case "PICK POTATO":
			controller.robotController.pickup();
			break;
		case "END":
		default:
			break;
		}
	}


	/**
	 * Edit part of the codeblock
	 * @param code 
	 * @return  1 if fails 0 if not
	 */	
	public int editCodeBlock(CodeBlock code){
		return 0;
	}

	/**
	 * remove entire code block from Code
	 * @param code 
	 * @return 1 if fails 0 if not
	 */	
	public int removeCodeBlock(CodeBlock code){
		return 0;
	}


	/**
	 * add codeblock to the end of the code
	 * @param codeBlock
	 * @return  1 if fails 0 if not
	 */	
	public CodeController addCodeBlock(CodeBlock codeBlock){
		this.code.add(codeBlock);
		return this;
	}
        
    public Iterator<CodeBlock> viewIterator() {
        return this.code.viewIterator();
    }

    public CodeController removeBlock(int id) {
        this.code.removeBlock(id);
        return this;
    }

    public CodeController editCode(int id, String newContent)
    {
        this.code.edit(id, newContent);
        return this;
    }
    
    public CodeController insertCode(int id, CodeBlock newCodeBlock) {
                this.code.insert(id, newCodeBlock);
                return this;
    }

    public Code getCode()
    {
        return this.code;
    }

    public CodeController mergeCode(Code code)
    {
        this.code.merge(code);
        return this;
    }
    
    public CodeController macroAdd(Code code, String name)
    {
    	this.code.macroAdd(code, name);
    	return this;
    }

    public CodeController clear()
    {
        this.code = new Code();
        return this;
    }

    public CodeController undo()
    {
        this.code.undo();
        return this;
    }

    public CodeController setCode(Code code)
    {
        this.code = code;
        return this;
    }

}
