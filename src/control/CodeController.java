package control;

import model.Code;
import model.CodeBlock;
import model.Grid;
import model.User;
import static model.Direction.*;
import view.CodePanel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class CodeController {

	private Code code;
	private CodePanel codeview;
	private User user;
	private Controller controller;
	private boolean stepper;
	private CodeBlock ptr, current;


	public CodeController(User user, Controller controller) {
		this.controller = controller;
		this.user = user;		
		this.stepper = false;
		this.code = new Code();
	}

	
	/**
	 * Main class that will control the code and view
	 */	
	public void run () {
		runPartial(this.code.getHead());
		this.stepper= false;
	}
	
	

	public void step()
	{
		Iterator<CodeBlock> iterator = this.code.iterator(this.ptr);
		
		if(this.stepper == false)
		{
			this.ptr = this.code.getHead();
			runCodeBlock(this.ptr);
			iterator = this.code.iterator(this.ptr);
			
			this.setCurrentBlock(this.ptr);
			
			
			this.setCurrentBlock(this.ptr);
			
			this.ptr = iterator.next();
			this.stepper = true;
		}
		else
		{
			runCodeBlock(this.ptr);
			
			this.setCurrentBlock(this.ptr);
			

			
			this.setCurrentBlock(this.ptr);
			
			this.ptr = iterator.next();
		}
	}

	private void runPartial(CodeBlock head)
	{
		runCodeBlock(head);
		Iterator<CodeBlock> iterator = this.code.iterator(head);
		while (iterator.hasNext())
		{
			runCodeBlock(iterator.next());
		}
	}

	private void runCodeBlock(CodeBlock codeBlock)
	{

		//        controller.playPanel.codePanel.markBeingProcessed(CODE_BLOCK.getId());
		//TODO Implement worker threads if we want step by step code running.
		if (codeBlock != null && !codeBlock.getCodetext().isEmpty())
		{
			if (codeBlock.isConditional())
			{
				if (conditionalIsTrue(codeBlock))
				{
					runPartial(codeBlock.getTrueCondition());
				}
				else
				{
					runPartial(codeBlock.getFalseCondition());
				}
			}
			else if (codeBlock.isLoop())
			{
				while (conditionalIsTrue(codeBlock))
				{
					runPartial(codeBlock.getTrueCondition());
				}
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
		switch (condition)
		{
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

	private void runAction(CodeBlock codeBlock)
	{
		String codeText = codeBlock.getCodetext();
		switch (codeText)
		{
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
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(), this.code);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.ptr = this.code.getHead();
		return this;
	}

	public Iterator<CodeBlock> viewIterator() {
		return this.code.viewIterator();
	}

	public CodeController removeBlock(int id) {
		this.code.removeBlock(id);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.ptr = this.code.getHead();
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(), this.code);
		return this;
	}

	public CodeController editCode(int id, String newContent)
	{
		this.code.edit(id, newContent);
		this.stepper = false;
		this.ptr = this.code.getHead();
		this.stepper = false;
		this.ptr = this.code.getHead();
		
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(), this.code);

		return this;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(), this.code);
	}


	public Code getCode()
	{
		return this.code;
	}

	public CodeController mergeCode(Code code)
	{
		this.code.merge(code);
		this.user.addCodePlayedinGrid(this.controller.getCurrent_grid(), this.code);
		return this;
	}


 	public CodeController clear()
 	{
 		this.code = new Code();
 		this.user.resetCodePlayedinGrid(this.controller.getCurrent_grid());
 		this.stepper = false;
		this.ptr = this.code.getHead();
        return this;
	}
	
	public CodeBlock getCurrentBlock()
	{
		return this.current;
	}
	
	public void setCurrentBlock(CodeBlock curr)
	{
		this.current = curr;
	}
}
