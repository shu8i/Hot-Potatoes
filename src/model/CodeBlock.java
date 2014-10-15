/**
 * Class to hold the codeblock
 */
package src.model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 * 
 * @see Code
 */
public class CodeBlock {
	
	private int codetype;
	private int codeposition;
	private String codetext;
	
	/**
	 * @return the codetype
	 */
	public int getCodetype() {
		return codetype;
	}
	/**
	 * @param codetype the codetype to set
	 */
	public void setCodetype(int codetype) {
		this.codetype = codetype;
	}
	/**
	 * @return the codeposition
	 */
	public int getCodeposition() {
		return codeposition;
	}
	/**
	 * @param codeposition the codeposition to set
	 */
	public void setCodeposition(int codeposition) {
		this.codeposition = codeposition;
	}
	/**
	 * @return the codetext
	 */
	public String getCodetext() {
		return codetext;
	}
	/**
	 * @param codetext the codetext to set
	 */
	public void setCodetext(String codetext) {
		this.codetext = codetext;
	}

}
