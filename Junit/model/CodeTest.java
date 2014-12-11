package model;

import static org.junit.Assert.*;

import java.util.Iterator;
import model.Code;
import model.CodeBlock;
import model.CodeType;

import org.junit.Test;

/**
 * @author Allan
 * Junit test for the code class
 * @see Code
 */
public class CodeTest {

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testCode() {
		Code test = new Code();	
		assertNotNull(test);
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testGetHead() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));		
		assertNotNull(test.getHead());
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testIterator() {
		Code test = new Code();		
		assertNotNull(test.iterator(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION))));
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testViewIterator() {
		Code test = new Code();	
		test.insert(0, new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		test.insert(1, new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		test.insert(2, new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		test.insert(3, new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		assertNotNull(test.viewIterator().hasNext());
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testAdd() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));		
		assertNotNull(test.getHead());
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testRemoveBlock() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		test.removeBlock(1);
		assertNull(test.getHead());
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testEdit() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));	
		test.edit(1, "MOVE");
		assertEquals(test.getHead().getCodetext(), "MOVE");
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testInsert() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.insert(4, new CodeBlock("MOVE",new CodeType(CodeType.Type.ACTION)));
		
		Iterator<CodeBlock> iter = test.viewIterator();
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testIsEmpty() {
		Code test = new Code();		
		assertNull(test.getHead());
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testMerge() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		Code merge = new Code();	
		merge.add(new CodeBlock("MOVE",new CodeType(CodeType.Type.ACTION)));
		merge.add(new CodeBlock("MOVE",new CodeType(CodeType.Type.ACTION)));
		merge.add(new CodeBlock("MOVE",new CodeType(CodeType.Type.ACTION)));
		
		test.merge(merge);
		Iterator<CodeBlock> iter = test.viewIterator();
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "MOVE");
		assertEquals(iter.next().getCodetext(), "MOVE");
		assertEquals(iter.next().getCodetext(), "MOVE");
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testMacroAdd() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		
		Code macro = test.macroAdd(test, "test");
		Iterator<CodeBlock> iter = macro.viewIterator();
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
	}

	@SuppressWarnings({ "javadoc", "static-method" })
	@Test
	public void testUndo() {
		Code test = new Code();	
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		test.add(new CodeBlock("TURN LEFT",new CodeType(CodeType.Type.ACTION)));
		
		test.undo();
		Iterator<CodeBlock> iter = test.viewIterator();
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
		assertEquals(iter.next().getCodetext(), "TURN LEFT");
	}

}
