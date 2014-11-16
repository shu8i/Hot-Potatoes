package model;

import java.util.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 * 
 */
public class Code{
	
	private CodeBlock head;
    private CodeBlock curr;
    private Stack<CodeBlock> conditionals;
    private CodeType.Type lastType = CodeType.Type.ACTION;

    public Code() {
        this.head = new CodeBlock();
        this.curr = head;
        this.conditionals = new Stack<CodeBlock>();
    }

//	/**
//	 * @return the list
//	 */
//	public Iterator<CodeBlock> getList() {
//		return this.code.iterator();
//	}


	public void add(CodeBlock codeBlock){
        CodeType type = codeBlock.getCodetype();


        switch (type.getType()) {
            case IF:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                this.conditionals.push(codeBlock);
                addActionCodeBlock(codeBlock);
                this.lastType = CodeType.Type.IF;
                break;
            case ELSE:
                this.lastType = CodeType.Type.ELSE;
                break;
            case WHILE:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                this.conditionals.push(codeBlock);
                addActionCodeBlock(codeBlock);
                this.lastType = CodeType.Type.WHILE;
                break;
            case END:
                this.curr = this.conditionals.pop();
                this.lastType = CodeType.Type.END;
                break;
            case ACTION:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock);
                this.lastType = CodeType.Type.ACTION;
                break;
            default: break;
        }
        System.out.println();
    }

    private void addActionCodeBlock(CodeBlock codeBlock) {
        if (this.lastType.equals(CodeType.Type.IF) || this.lastType.equals(CodeType.Type.WHILE)) {
            this.curr.trueCondition = codeBlock;
            this.curr = this.curr.trueCondition;
        } else if (this.lastType.equals(CodeType.Type.ELSE)) {
            this.conditionals.peek().falseCondition = codeBlock;
            this.curr = this.conditionals.peek().falseCondition;
        } else {
            this.curr.defaultCondition = codeBlock;
            this.curr = this.curr.defaultCondition;
        }
    }
	
//	/**
//	 * Removes the first occurrence of the specified
//	 * element from this list, if it is present.
//	 * @param code
//	 */
//	public void remove (CodeBlock code){
//		this.code.remove(code);
//	}
//
//
//	/**
//	 */
//	public void clear (){
//		this.code.clear();
//	}
//
//
//	/**
//	 * @param code
//	 */
//	public void contains (CodeBlock code){
//		this.code.contains(code);
//	}
	
}
