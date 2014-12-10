package model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Code implements Serializable {

	private CodeBlock head;
	private CodeBlock curr;
	private Stack<CodeBlock> conditionals, macroStack;
	private enum Mode{  IF,
		IF_DECLARATION,
		FIRST_IF,
		ELSE,
		WHILE,
		WHILE_DECLARATION,
		FIRST_WHILE,
		ACTION,
		MACRO,
		END}
	private Stack<Mode> mode;
	private int ids;
	private Map<Integer, CodeBlock> references;

	public Code() {
		this.head = new CodeBlock();
		this.curr = head;
		this.conditionals = new Stack<CodeBlock>();
		this.macroStack = new Stack<CodeBlock>();
		this.mode = new Stack<Mode>();
		this.ids = 0;
		this.references = new HashMap<Integer, CodeBlock>();
	}

	public CodeBlock getHead()
	{
		return this.head.defaultCondition;
	}


	public Iterator<CodeBlock> iterator(final CodeBlock codeBlock)
	{
		return new Iterator<CodeBlock>() {

			private CodeBlock current = codeBlock;

			@Override
			public boolean hasNext() {
				return current.defaultCondition != null;
			}

			@Override
			public CodeBlock next()                 //TODO fix "if x then nothing else something"
			{
				current = current.defaultCondition;
				return current;
			}

			@Override
			public void remove() {
				//TODO implement... maybe
			}
		};
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
				 if ((current.defaultCondition != null && !visited.contains(current.defaultCondition) )||
						 (current.trueCondition != null && !visited.contains(current.trueCondition)) ||
						 (current.falseCondition != null && !visited.contains(current.falseCondition)) ||
						 (current.macroBranch != null && !visited.contains(current.macroBranch))){
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

				 if (!mode.isEmpty() && mode.peek().equals(Mode.ELSE) && !elseReturned) {
					 return true;
				 }

				 return false;
			 }

			 @Override
			 public CodeBlock next()                 //TODO fix "if x then nothing else something"
			 {
				 if (current.trueCondition != null && !visited.contains(current.trueCondition))
				 {
					 current = current.trueCondition;
					 elseReturned = false;
				 }
				 else if (current.falseCondition != null && !visited.contains(current.falseCondition) && !elseReturned)
				 {
					 elseReturned = true;
					 return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
				 }
				 else if (current.falseCondition != null && !visited.contains(current.falseCondition) && elseReturned)
				 {
					 current = current.falseCondition;
					 elseReturned = false;
				 }
				 else if (current.macroBranch != null && !visited.contains(current.macroBranch))
				 {
					 current = current.macroBranch;
					 elseReturned = false;
				 }
				 else if (current.defaultCondition != null && !visited.contains(current.defaultCondition))
				 {
					 current = current.defaultCondition;
					 elseReturned = false;
				 }
				 else if (current.condParent != null)
				 {
					 current = current.condParent;
					 if ((current.getCodetype().getType().equals(CodeType.Type.IF) &&
							 visited.contains(current.trueCondition) && visited.contains(current.falseCondition)) ||
							 (current.getCodetype().getType().equals(CodeType.Type.WHILE) &&
									 visited.contains(current.trueCondition)))
					 {
						 current = current.defaultCondition;
						 elseReturned = false;
					 }
					 else {
						 if (current.falseCondition != null && elseReturned)
						 {
							 current = current.falseCondition;
							 elseReturned = false;
						 }
						 else if (((mode.peek().equals(Mode.ELSE) && current.defaultCondition == null) ||
								 (current.falseCondition != null && !visited.contains(current.falseCondition)))
								 && !elseReturned)
						 {
							 elseReturned = true;
							 return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
						 }
						 else
						 {
							 current = current.defaultCondition;
							 elseReturned = false;
						 }
					 }
				 }
				 else
				 {
					 if (((mode.peek().equals(Mode.ELSE) && current.defaultCondition == null) ||
							 current.falseCondition != null) && !elseReturned)
					 {
						 elseReturned = true;
						 return new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE));
					 } else {
						 throw new NoSuchElementException();
					 }
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
		 case MACRO:
			 if(!this.conditionals.isEmpty())
			 {
				 codeBlock.condParent = this.conditionals.peek();
			 }
			 addActionCodeBlock(codeBlock, Mode.MACRO);
			 break;
		 default: break;
		 }
	 }

	 private void addActionCodeBlock(CodeBlock codeBlock, Mode mode) {

		 codeBlock.setId(++ids);
		 this.references.put(codeBlock.getId(), codeBlock);

		 switch (mode) {
		 case IF_DECLARATION:
		 case WHILE_DECLARATION:
			 if (!this.mode.isEmpty() &&
					 (this.mode.peek().equals(Mode.FIRST_IF) || this.mode.peek().equals(Mode.FIRST_WHILE))) {
				 codeBlock.parent = this.curr;

				 this.curr.trueCondition = codeBlock;
				 this.curr = this.curr.trueCondition;
			 } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {
				 codeBlock.parent = this.conditionals.peek();

				 this.conditionals.peek().falseCondition = codeBlock;
				 this.curr = this.conditionals.peek().falseCondition;
			 } else {
				 codeBlock.parent = this.curr;

				 this.curr.defaultCondition = codeBlock;
				 this.curr = this.curr.defaultCondition;
			 }
			 this.mode.push(mode);
			 break;
		 case END:
			 codeBlock.parent = this.conditionals.peek();

			 this.conditionals.peek().defaultCondition = codeBlock;
			 this.curr = this.conditionals.pop().defaultCondition;
			 if (!this.conditionals.isEmpty()) {
				 codeBlock.condParent = this.conditionals.peek();
			 }
			 this.mode.push(Mode.END);
			 break;
		 case MACRO:	 
		 case ACTION:
			 if (!this.mode.isEmpty() &&
					 (this.mode.peek().equals(Mode.IF_DECLARATION) || this.mode.peek().equals(Mode.WHILE_DECLARATION))) {
				 this.curr.setCondition(codeBlock.getCodetext());
				 this.mode.push(this.mode.peek().equals(Mode.IF_DECLARATION) ? Mode.FIRST_IF : Mode.FIRST_WHILE);
			 } else if (!this.mode.isEmpty() &&
					 (this.mode.peek().equals(Mode.FIRST_IF) || this.mode.peek().equals(Mode.FIRST_WHILE))) {
				 codeBlock.parent = this.curr;

				 this.curr.trueCondition = codeBlock;
				 this.curr = this.curr.trueCondition;
				 this.mode.push(mode);
			 } else if (!this.mode.isEmpty() && this.mode.peek().equals(Mode.ELSE)) {
				 codeBlock.parent = this.conditionals.peek();

				 this.conditionals.peek().falseCondition = codeBlock;
				 this.curr = this.conditionals.peek().falseCondition;
				 this.mode.push(mode);
			 } else {
				 codeBlock.parent = this.curr;

				 this.curr.defaultCondition = codeBlock;
				 this.curr = this.curr.defaultCondition;
				 this.mode.push(mode);
			 }
			 break;
		 default: break;
		 }

	 }

	 public Code removeBlock(int id)
	 {
		 CodeBlock block = this.references.get(id);

		 switch (block.getCodetype().getType())
		 {
		 case IF:
		 case WHILE:
		 case ELSE:
			 //                removeConditionalBlock(block);
			 break;
		 case END:
			 //                removeConditionalByEndBlock(block);
			 break;
		 case MACRO:	 
		 case ACTION:
			 removeActionBlock(block);
			 this.references.remove(id);
			 break;
		 default: break;
		 }
		 return this;
	 }

	 private void removeConditionalByEndBlock(CodeBlock block)
	 {
		 //        if (block.defaultCondition == null)
		 //        {
		 //            block.parent.parent.defaultCondition = null;
		 //            curr = block.parent.parent;
		 //        }
		 //        else
		 //        {
		 //            block.defaultCondition.parent = block.parent.parent;
		 //            if (block.parent.parent.trueCondition == block.parent)
		 //            {
		 //                block.parent.parent.trueCondition = block.defaultCondition;
		 //            }
		 //            else if (block.parent.parent.falseCondition == block.parent)
		 //            {
		 //                block.parent.parent.falseCondition = block.defaultCondition;
		 //            }
		 //            else
		 //            {
		 //                block.parent.parent.defaultCondition = block.defaultCondition;
		 //            }
		 //        }
	 }

	 private void removeConditionalBlock(CodeBlock block)
	 {

		 //        if (block.defaultCondition.defaultCondition == null)    //if last conditional in code
		 //        {
		 //            if (block.parent.defaultCondition == null)
		 //            {
		 //                block.parent.defaultCondition = null;
		 //                curr = block.parent;
		 //            }
		 //        }
		 //        else
		 //        {
		 //            block.defaultCondition.defaultCondition.parent = block.parent;
		 //            if (block.parent.trueCondition == block)
		 //            {
		 //                block.parent.trueCondition = block.defaultCondition.defaultCondition;
		 //            }
		 //            else if (block.parent.falseCondition == block)
		 //            {
		 //                block.parent.falseCondition = block.defaultCondition.defaultCondition;
		 //            }
		 //            else
		 //            {
		 //                block.parent.defaultCondition = block.defaultCondition.defaultCondition;
		 //            }
		 //        }
	 }

	 private void removeActionBlock(CodeBlock block)
	 {
		 if (block.parent.trueCondition == block)            //if first if/while condition
		 {
			 if (block.defaultCondition == null)             //if the only statement in if/while
			 {
				 block.parent.trueCondition = null;
			 }
			 else
			 {
				 block.defaultCondition.parent = block.parent;
				 block.parent.trueCondition = block.defaultCondition;
			 }
		 }
		 else if (block.parent.falseCondition == block)      //if in first else condition
		 {
			 if (block.defaultCondition == null)             //if the only statement in else (remove entire if/else)
			 {
				 block.parent.falseCondition = null;
			 }
			 else
			 {
				 block.defaultCondition.parent = block.parent;
				 block.parent.falseCondition = block.defaultCondition;
			 }
		 }

		 else
		 {
			 if (block.defaultCondition == null)         //if last codeblock in code
			 {
				 block.parent.defaultCondition = null;
				 curr = block.parent;
			 }
			 else
			 {
				 block.defaultCondition.parent = block.parent;
				 block.parent.defaultCondition = block.defaultCondition;
			 }
		 }
	 }

	 public Code edit(int id, String newContent)
	 {
		 CodeBlock block = this.references.get(id);
		 if (block.getCondition() != null)
		 {
			 block.setCondition(newContent);
		 }
		 else
		 {
			 block.setCodeText(newContent);
		 }
		 return this;
	 }

	 public boolean isEmpty()
	 {
		 return head.defaultCondition == null;
	 }

	 public Code merge(Code code)
	 {
		 Code newCode = copy(code);
		 for (final CodeBlock block : newCode.references.values())
		 {
			 block.setId(++ids);
			 this.references.put(ids, block);
		 }
		 add(newCode.head.defaultCondition);
		 if (this.mode.size() > 1) this.mode.pop();
		 this.mode.addAll(newCode.mode);
		 this.curr = newCode.curr;
		 return this;
	 }
	 
	 public Code macroAdd(Code code, String name)
	 {
		 CodeBlock macroBlock = new CodeBlock(name, new CodeType(CodeType.Type.MACRO));
		 Code macroCode = copy(code);
		 for (final CodeBlock block : macroCode.references.values())
		 {
			 block.setId(++ids);
			 block.setMacroParent(macroBlock);
			 this.references.put(ids, block);
		 }
		 macroBlock.macroBranch = macroCode.head.defaultCondition;
		 
		 add(macroBlock);
		 if (this.mode.size() > 1) this.mode.pop();
		 this.mode.addAll(macroCode.mode);
		 this.curr = macroCode.curr;
		 return this;
	 }

	 private Code copy(Code oldCode)
	 {
		 Code code = new Code();
		 CodeBlock codeBlock;
		 //create new blocks
		 for (Map.Entry<Integer, CodeBlock> entry : oldCode.references.entrySet())
		 {
			 codeBlock = new CodeBlock(entry.getValue().getCodetext(), entry.getValue().getCodetype());
			 if (entry.getValue().getCondition() != null)
			 {
				 codeBlock.setCondition(entry.getValue().getCondition());
			 }
			 code.references.put(entry.getKey(), codeBlock);
		 }

		 //restore links.
		 for (Map.Entry<Integer, CodeBlock> entry : code.references.entrySet())
		 {
			 codeBlock = entry.getValue();
			 if (oldCode.references.get(entry.getKey()).defaultCondition != null)
				 codeBlock.defaultCondition = code.references.get(oldCode.references.get(entry.getKey()).defaultCondition.getId());

			 if (oldCode.references.get(entry.getKey()).trueCondition != null)
				 codeBlock.trueCondition = code.references.get(oldCode.references.get(entry.getKey()).trueCondition.getId());

			 if (oldCode.references.get(entry.getKey()).falseCondition != null)
				 codeBlock.falseCondition = code.references.get(oldCode.references.get(entry.getKey()).falseCondition.getId());

			 if (oldCode.references.get(entry.getKey()).parent != null)
				 codeBlock.parent = code.references.get(oldCode.references.get(entry.getKey()).parent.getId());

			 if (oldCode.references.get(entry.getKey()).condParent != null)
				 codeBlock.condParent = code.references.get(oldCode.references.get(entry.getKey()).condParent.getId());
		 }

		 Stack<CodeBlock> reversedConditionals = new Stack<CodeBlock>();
		 Stack<Mode> reversedModes = new Stack<Mode>();
		 while (!oldCode.conditionals.isEmpty())
			 reversedConditionals.push(code.references.get(oldCode.conditionals.pop().getId()));

		 while (!oldCode.mode.isEmpty())
			 reversedModes.push(oldCode.mode.pop());

		 //        if (!reversedModes.isEmpty())
		 //            reversedModes.pop();

		 while (!reversedConditionals.isEmpty())
			 code.conditionals.push(reversedConditionals.pop());

		 while (!reversedModes.isEmpty())
			 code.mode.push(reversedModes.pop());

		 code.head.defaultCondition = code.references.get(oldCode.head.defaultCondition.getId());
		 code.curr = code.references.get(oldCode.curr.getId());
		 return code;
	 }

	 public Code undo()
	 {
		 Iterator<CodeBlock> iterator = viewIterator();
		 CodeBlock secondToLast, last;
		 while (iterator.hasNext())
		 {
			 secondToLast = iterator.next();
			 if (iterator.hasNext())
				 last = iterator.next();
		 }
		 if (this.mode.pop().equals(Mode.ELSE)) return this;
		 if (curr == curr.parent.defaultCondition)
		 {
			 curr = curr.parent;
			 curr.defaultCondition = null;
		 }
		 else if (curr == curr.parent.trueCondition)
		 {
			 curr = curr.parent;
			 curr.trueCondition = null;
		 }
		 else
		 {
			 curr = curr.parent;
			 curr.falseCondition = null;
		 }
		 return this;
	 }
}
