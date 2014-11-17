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
    private Map<Integer, CodeBlock> references;

    public Code() {
        this.head = new CodeBlock();
        this.curr = head;
        this.conditionals = new Stack<CodeBlock>();
        this.mode = new Stack<Mode>();
        this.ids = 0;
        this.references = new HashMap<Integer, CodeBlock>();
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

                if (current.condParent != null &&
                        current.condParent.falseCondition != null &&
                        !visited.contains(current.condParent.falseCondition)) {
                    return true;
                }

                if (current.condParent != null &&
                        current.condParent.defaultCondition != null &&
                        !visited.contains(current.condParent.defaultCondition)) {
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
                } else if (current.condParent != null) {

                    if (mode.peek().equals(Mode.ELSE)) {
                        elseReturned = true;
                        return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
                    }

                    current = current.condParent;
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
                    codeBlock.condParent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.IF_DECLARATION);
                this.conditionals.push(codeBlock);
                break;
            case ELSE:
                this.mode.push(Mode.ELSE);
                break;
            case WHILE:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.condParent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.WHILE_DECLARATION);
                this.conditionals.push(codeBlock);
                break;
            case END:
                addActionCodeBlock(codeBlock, Mode.END);
                break;
            case ACTION:
                if (!this.conditionals.isEmpty()) {
                    codeBlock.condParent = this.conditionals.peek();
                }
                addActionCodeBlock(codeBlock, Mode.ACTION);
                break;
            default: break;
        }

        System.out.println();
    }

    private void addActionCodeBlock(CodeBlock codeBlock, Mode mode) {

        codeBlock.setId(++ids);
        this.references.put(codeBlock.getId(), codeBlock);

        switch (mode) {
            case IF_DECLARATION:
            case WHILE_DECLARATION:
                if (!this.mode.isEmpty() &&
                        (this.mode.peek().equals(Mode.FIRST_IF) || this.mode.peek().equals(Mode.FIRST_WHILE))) {

                    if (this.curr.trueCondition != null) {
                        this.curr.trueCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.curr.trueCondition;
                    }
                    codeBlock.parent = this.curr;

                    this.curr.trueCondition = codeBlock;
                    this.curr = this.curr.trueCondition;
                } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {

                    if (this.conditionals.peek().falseCondition != null) {
                        this.conditionals.peek().falseCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.conditionals.peek().falseCondition;
                    }
                    codeBlock.parent = this.conditionals.peek();

                    this.conditionals.peek().falseCondition = codeBlock;
                    this.curr = this.conditionals.peek().falseCondition;
                } else {

                    if (this.curr.defaultCondition != null) {
                        this.curr.defaultCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.curr.defaultCondition;
                    }
                    codeBlock.parent = this.curr;

                    this.curr.defaultCondition = codeBlock;
                    this.curr = this.curr.defaultCondition;
                }
                this.mode.push(mode);
                break;
            case END:

                if (this.conditionals.peek().defaultCondition != null) {
                    this.conditionals.peek().defaultCondition.parent = codeBlock;
                    codeBlock.defaultCondition = this.conditionals.peek().defaultCondition;
                }
                codeBlock.parent = this.conditionals.peek();

                this.conditionals.peek().defaultCondition = codeBlock;
                this.curr = this.conditionals.pop().defaultCondition;
                if (!this.conditionals.isEmpty()) {
                    codeBlock.condParent = this.conditionals.peek();
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

                    if (this.curr.trueCondition != null) {
                        this.curr.trueCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.curr.trueCondition;
                    }
                    codeBlock.parent = this.curr;

                    this.curr.trueCondition = codeBlock;
                    this.curr = this.curr.trueCondition;
                    this.mode.push(mode);
                } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {

                    if (this.conditionals.peek().falseCondition != null) {
                        this.conditionals.peek().falseCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.conditionals.peek().falseCondition;
                    }
                    codeBlock.parent = this.conditionals.peek();

                    this.conditionals.peek().falseCondition = codeBlock;
                    this.curr = this.conditionals.peek().falseCondition;
                    this.mode.push(mode);
                } else {

                    if (this.curr.defaultCondition != null) {
                        this.curr.defaultCondition.parent = codeBlock;
                        codeBlock.defaultCondition = this.curr.defaultCondition;
                    }
                    codeBlock.parent = this.curr;

                    this.curr.defaultCondition = codeBlock;
                    this.curr = this.curr.defaultCondition;
                    this.mode.push(mode);
                }
                break;
            default: break;
        }

    }

    public Code removeBlock(int id) {
        CodeBlock block = this.references.get(id);
        if (block.parent == null) {
            if (block.defaultCondition != null) {
                head.defaultCondition = block.defaultCondition;
            } else {
                head.defaultCondition = null;
            }
        } else if (block.parent != null) {
            if (block.defaultCondition.getCodetype().getType().equals(CodeType.Type.END)) {
                if (block.defaultCondition.defaultCondition != null) {
                    block.defaultCondition.defaultCondition.parent = block.parent;
                    block.parent.defaultCondition = block.defaultCondition.defaultCondition;
                } else {
                    block.parent.defaultCondition = null;
                }
            } else {
                if (block.defaultCondition != null) {
                    block.defaultCondition.parent = block.parent;
                    block.parent.defaultCondition = block.defaultCondition;
                } else {
                    block.parent.defaultCondition = null;
                }
            }
        }
        this.references.remove(id);
        System.out.println();
        return this;
    }
	
}
