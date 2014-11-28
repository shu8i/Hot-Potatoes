package model;

import java.io.Serializable;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeBlock implements Serializable {

	private CodeType codeType;
	private String codeText, condition;
    protected CodeBlock defaultCondition, trueCondition, falseCondition, condParent, parent;
    private int id;

    public CodeBlock() {
        this.defaultCondition = null;
    }

    public CodeBlock(String codeText, CodeType codeType) {
        this.codeText = codeText;
        this.codeType = codeType;
        this.defaultCondition = null;
        this.trueCondition = null;
        this.falseCondition = null;
    }

    public CodeBlock setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getCondition() {
        return this.condition;
    }
	
	/**
     * Gets the codetype of this block
	 * @return the codetype
	 */
	public CodeType getCodetype() {
		return codeType;
	}

	/**
     * Gets the text contained within this code block
	 * @return the codetext
	 */
	public String getCodetext() {
		return codeText;
	}

    public CodeBlock setCodeText(String codeText)
    {
        this.codeText = codeText;
        return this;
    }

    public CodeBlock setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }

        CodeBlock block = (CodeBlock) o;
        return this.id == block.getId();
    }

    public boolean isConditional()
    {
        return this.codeType.getType().equals(CodeType.Type.IF);
    }

    public boolean isLoop()
    {
        return this.codeType.getType().equals(CodeType.Type.WHILE);
    }

    public CodeBlock getTrueCondition()
    {
        return this.trueCondition;
    }

    public CodeBlock getFalseCondition()
    {
        return this.falseCondition;
    }

}
