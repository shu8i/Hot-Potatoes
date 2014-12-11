package view;

import control.Controller;
import model.CodeBlock;
import util.Constants;


import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodePanel extends JScrollPane {

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private int col = 0, row = 0;
    private JPanel filler;
    private JPanel panel;
    private Controller controller;
    private PlayPanel playPanel;
    private CodeBlockPanel blockBeingEdited;
    private CodeBlockPanel blockBeingInserted;
    private Map<Integer, List<CodeBlockPanel>> references;
    private List<CodeBlockPanel> previouslyBeingProcessed;

    public CodePanel(JPanel panel, Controller controller, PlayPanel playPanel) {
        super(panel);
        this.controller = controller;
        this.playPanel = playPanel;
        this.panel = panel;
        this.panel.setLayout(layout);
        this.filler = new JPanel();
        this.filler.setBackground(Color.GRAY);
        setPreferredSize(new Dimension(500, 500));
        setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        this.panel.setBackground(Color.GRAY);
        this.references = new HashMap<Integer, List<CodeBlockPanel>>();
        this.previouslyBeingProcessed = new ArrayList<CodeBlockPanel>();
    }

    public void addCodeBlock(CodeBlock codeBlock)
    {
        CodeBlockPanel codeBlockPanel;
        List<CodeBlockPanel> value = new ArrayList<CodeBlockPanel>();
        this.panel.remove(filler);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;

        c.gridx = col;
        c.gridy = row;

        switch(codeBlock.getCodetype().getType())
        {
            case IF:
            case WHILE:
                codeBlockPanel = new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), controller, this.playPanel);
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                
                if(codeBlock.getMacroParent() == null)
                	this.panel.add(codeBlockPanel, c);
                
                col++;
                if (codeBlock.getCondition() != null)
                {
                    c.gridx = col;
                    codeBlockPanel = new CodeBlockPanel(codeBlock.getCondition(), codeBlock.getId(), controller, this.playPanel);
                    
                    if(codeBlock.getMacroParent() == null)
                    	this.panel.add(codeBlockPanel, c);
                    
                    value.add(codeBlockPanel);
                    row++;
                }
                break;
            case ELSE:
                c.gridx = --col;
                codeBlockPanel = new CodeBlockPanel("ELSE", codeBlock.getId(), controller, this.playPanel);
                this.panel.add(codeBlockPanel, c);
                
                if(codeBlock.getMacroParent() == null)
                	this.panel.add(codeBlockPanel, c);
                
                this.references.put(codeBlock.getId(), value);
                col++;
                row++;
                break;
            case END:
                c.gridx = --col;
                codeBlockPanel = new CodeBlockPanel("END", codeBlock.getId(), controller, this.playPanel);
                
                if(codeBlock.getMacroParent() == null)
                	this.panel.add(codeBlockPanel, c);
                
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                row++;
                break;
            case MACRO:
            	codeBlockPanel = new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), controller, this.playPanel);
            	codeBlockPanel.updateBorderColor(Constants.COLOR_MACRO);
                
                if(codeBlock.getMacroParent() == null)
                	this.panel.add(codeBlockPanel, c);
                
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                row++;
                break;
            case ACTION:
                codeBlockPanel = new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), controller, this.playPanel);
                
                if(codeBlock.getMacroParent() == null)
                	this.panel.add(codeBlockPanel, c);
                
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                row++;
                break;
            default: break;
        }

        c = new GridBagConstraints();
        c.gridy = row + 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1000;
        c.fill = GridBagConstraints.BOTH;
        this.panel.add(filler, c);

        this.panel.revalidate();
    }

    public void refreshPanel() {
        this.panel.removeAll();
        col = 0;
        row = 0;
        this.references.clear();


        Iterator<CodeBlock> iterator = controller.codeController.viewIterator();
        CodeBlock codeBlock;
        while (iterator.hasNext()) {
            codeBlock = iterator.next();
            addCodeBlock(codeBlock);
        }

        this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMaximum());
        this.panel.revalidate();
        this.panel.repaint();
    }

    public void updateBlockForEdit(CodeBlockPanel codeBlockPanel)
    {
        if (this.blockBeingEdited != null)
        {
            this.blockBeingEdited.exitEditMode();
        }
        this.blockBeingEdited = codeBlockPanel;
    }
    
    public void updateBlockForInsert(CodeBlockPanel codeBlockPanel) {
        if (this.blockBeingInserted != null) {
            this.blockBeingInserted.exitInsertMode();
        }
	this.blockBeingInserted = codeBlockPanel;
    }

    public int getEditableCodeId()
    {
        return this.blockBeingEdited.getId();
    }

    public int getInsertableCodeId() {
        return this.blockBeingInserted.getId();
    }

    public CodePanel markBeingProcessed(int id, int macNum)
    {
        for (final CodeBlockPanel codeBlockPanel : this.previouslyBeingProcessed)
        {
            codeBlockPanel.updateBorderColor(null);
        }
        this.previouslyBeingProcessed.clear();

        List<CodeBlockPanel> currentlyBeingProcessed = this.references.get(id);
        
        if(macNum != -1)
        	currentlyBeingProcessed = (this.references.get(macNum));
        
        for (final CodeBlockPanel codeBlockPanel : currentlyBeingProcessed)
        {
            codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
        }
        if(macNum ==-1)
        	this.previouslyBeingProcessed = currentlyBeingProcessed;
        return this;
    }
	
}
