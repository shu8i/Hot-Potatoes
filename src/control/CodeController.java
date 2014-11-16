package control;

import model.Code;
import model.CodeBlock;
import model.User;
import view.CodePanel;

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

    public CodeController(User user) {
        this.user = user;
        this.code = new Code();
    }
	
	/**
	 * Main class that will control the code and view
	 */	
	public void run () {
		//TODO add some code here
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

}
