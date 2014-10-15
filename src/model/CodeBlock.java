package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeBlock {
	
	private CodeType codetype;
	private int codeposition;
	private String codetext;
	
	/**
     * Gets the codetype of this block
	 * @return the codetype
	 */
	public CodeType getCodetype() {
		return codetype;
	}

    /**
     * Sets the codetype for this block
	 * @param codetype the codetype to set
	 */
	public void setCodetype(CodeType codetype) {
		this.codetype = codetype;
	}

	/**
     * Gets the position of this codeblock within the entire code structure
	 * @return the codeposition
	 */
	public int getCodeposition() {
		return codeposition;
	}

	/**
     * Sets the codeposition of this codeblock within the entire code structure
	 * @param codeposition the codeposition to set
	 */
	public void setCodeposition(int codeposition) {
		this.codeposition = codeposition;
	}

	/**
     * Gets the text contained within this code block
	 * @return the codetext
	 */
	public String getCodetext() {
		return codetext;
	}

    /**
     * Sets the text for this codeblock
	 * @param codetext the codetext to set
	 */
	public void setCodetext(String codetext) {
		this.codetext = codetext;
	}

}
