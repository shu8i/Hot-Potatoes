package model;

import sun.rmi.rmic.iiop.Type;

import java.util.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 * 
 */
public class Code {
	
	private CodeBlock head;
    private CodeBlock curr;
    private Stack<CodeBlock> conditionals;
    private enum Mode{  IF,
                        IF_DECLARATION,
                        FIRST_IF,
                        ELSE,
                        WHILE,
                        WHILE_DECLARATION,
                        FIRST_WHILE,
                        ACTION,
                        END}
    private Stack<Mode> mode;
    private int ids;

    public Code() {
        this.head = new CodeBlock();
        this.curr = head;
        this.conditionals = new Stack<CodeBlock>();
        this.mode = new Stack<Mode>();
        this.ids = 0;
    }

	/**
     * Creates an iterator for the code
	 * @return an iterator for the tree list.
	 */
	public Iterator<CodeBlock> viewIterator() {
		return new Iterator<CodeBlock>() {

            private CodeBlock current = head;
            private List<CodeBlock> visited = new ArrayList<CodeBlock>();
            private boolean elseReturned = false;

            @Override
            public boolean hasNext() {
                if (current.defaultCondition != null ||
                        current.trueCondition != null ||
                        current.falseCondition != null) {
                    return true;
                }

                if (current.parent != null &&
                        current.parent.falseCondition != null &&
                        !visited.contains(current.parent.falseCondition)) {
                    return true;
                }

                if (current.parent != null &&
                        current.parent.defaultCondition != null &&
                        !visited.contains(current.parent.defaultCondition)) {
                    return true;
                }

                if (mode.peek().equals(Mode.ELSE) && !elseReturned) {
                    return true;
                }

                return false;
            }

            @Override
            public CodeBlock next() {
                if (current.trueCondition != null && !visited.contains(current.trueCondition)) {
                    current = current.trueCondition;
                } else if (current.falseCondition != null && !visited.contains(current.falseCondition)) {
                    current = current.falseCondition;
                } else if (current.defaultCondition != null && !visited.contains(current.defaultCondition)) {
                    current = current.defaultCondition;
                } else if (current.parent != null) {

                    if (mode.peek().equals(Mode.ELSE)) {
                        elseReturned = true;
                        return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
                    }

                    current = current.parent;
                    if (visited.contains(current.trueCondition) && visited.contains(current.falseCondition)) {
                        current = current.defaultCondition;
                    } else {
                        if (current.falseCondition != null && elseReturned) {
                            current = current.falseCondition;
                            elseReturned = false;
                        } else if (current.falseCondition != null && !elseReturned) {
                            elseReturned = true;
                            return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
                        } else {
                            current = current.defaultCondition;
                        }
                    }
                } else {
                    throw new NoSuchElementException();
                }
                visited.add(current);
                return current;
            }

            @Override
            public void remove() {
                //TODO implement... maybe
            }
        };
	}


	public void add(CodeBlock codeBlock){
        CodeType type = codeBlock.getCodetype();

        switch (type.getType()) {
            case IF:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.IF_DECLARATION);
                this.conditionals.push(codeBlock);
                break;
            case ELSE:
                this.mode.push(Mode.ELSE);
                break;
            case WHILE:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.WHILE_DECLARATION);
                this.conditionals.push(codeBlock);
                break;
            case END:
                addActionCodeBlock(codeBlock, Mode.END);
                break;
            case ACTION:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.ACTION);
                break;
            default: break;
        }

        System.out.println();
    }

    private void addActionCodeBlock(CodeBlock codeBlock, Mode mode) {

        codeBlock.setId(++ids);

        switch (mode) {
            case IF_DECLARATION:
            case WHILE_DECLARATION:
                if (!this.mode.isEmpty() &&
                        (this.mode.peek().equals(Mode.FIRST_IF) || this.mode.peek().equals(Mode.FIRST_WHILE))) {
                    this.curr.trueCondition = codeBlock;
                    this.curr = this.curr.trueCondition;
                } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {
                    this.conditionals.peek().falseCondition = codeBlock;
                    this.curr = this.conditionals.peek().falseCondition;
                } else {
                    this.curr.defaultCondition = codeBlock;
                    this.curr = this.curr.defaultCondition;
                }
                this.mode.push(mode);
                break;
            case END:
                this.conditionals.peek().defaultCondition = codeBlock;
                this.curr = this.conditionals.pop().defaultCondition;
                if (!this.conditionals.isEmpty()) {
                    codeBlock.parent = this.conditionals.peek();
                }
                this.mode.push(Mode.END);
                break;
            case ACTION:
                if (!this.mode.isEmpty() &&
                        (this.mode.peek().equals(Mode.IF_DECLARATION) || this.mode.peek().equals(Mode.WHILE_DECLARATION))) {
                    this.curr.setCondition(codeBlock.getCodetext());
                    this.mode.push(this.mode.peek().equals(Mode.IF_DECLARATION) ? Mode.FIRST_IF : Mode.FIRST_WHILE);
                } else if (!this.mode.isEmpty() &&
                        (this.mode.peek().equals(Mode.FIRST_IF) || this.mode.peek().equals(Mode.FIRST_WHILE))) {
                    this.curr.trueCondition = codeBlock;
                    this.curr = this.curr.trueCondition;
                    this.mode.push(mode);
                } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {
                    this.conditionals.peek().falseCondition = codeBlock;
                    this.curr = this.conditionals.peek().falseCondition;
                    this.mode.push(mode);
                } else {
                    this.curr.defaultCondition = codeBlock;
                    this.curr = this.curr.defaultCondition;
                    this.mode.push(mode);
                }
                break;
            default: break;
        }

    }
	
}
