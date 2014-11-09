package src.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 * 
 */
public class Code{
	
	private ArrayList<CodeBlock> list;

	/**
	 * @return the list
	 */
	public Iterator<CodeBlock>  getList() {
		return this.list.iterator();		
	}


	/**
	 * @param code 
	 */
	public void add(CodeBlock code){
		this.list.add(code);
	}
	
	/**
	 * Removes the first occurrence of the specified 
	 * element from this list, if it is present.	
	 * @param code 
	 */
	public void remove (CodeBlock code){
		this.list.remove(code);			
	}
	
	
	/**
	 */
	public void clear (){
		this.list.clear();		
	}
	
	
	/**
	 * @param code 
	 */
	public void contains (CodeBlock code){
		this.list.contains(code);		
	}
	
}
