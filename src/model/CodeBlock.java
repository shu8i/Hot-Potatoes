package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeBlock {

	private CodeType codeType;
	private String codeText, condition;
    CodeBlock defaultCondition, trueCondition, falseCondition, parent;

    public CodeBlock() {
        this.defaultCondition = this;
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

}
