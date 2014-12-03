package control;

import model.Code;
import model.CodeBlock;
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

    public CodeController(User user, Controller controller) {
        this.controller = controller;
        this.user = user;
        this.code = new Code();
    }
	
	/**
	 * Main class that will control the code and view
	 */	
	public void run () {
		runPartial(code.getHead());
    }

    private void runPartial(CodeBlock head)
    {
        runCodeBlock(head);
        Iterator<CodeBlock> iterator = code.iterator(head);
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
    
    /**
	 * remove entire code block from Code
	 * @param code 
	 * @return 1 if fails 0 if not
    */	

    public CodeController removeBlock(int id) {
        this.code.removeBlock(id);
        return this;
    }

    public CodeController editCode(int id, String newContent)
    {
        this.code.edit(id, newContent);
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

    public CodeController clear()
    {
        this.code = new Code();
        return this;
    }

}
