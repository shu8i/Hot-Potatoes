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
    private Map<Integer, List<CodeBlockPanel>> references;
    private List<CodeBlockPanel> previouslyBeingProcessed;
    private boolean current;

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
        this.current = false;
    }

    public void addCodeBlock(CodeBlock codeBlock)
    {
        CodeBlockPanel codeBlockPanel;
        List<CodeBlockPanel> value = new ArrayList<CodeBlockPanel>();
        this.panel.remove(this.filler);

        this.c = new GridBagConstraints();
        this.c.anchor = GridBagConstraints.NORTHWEST;

        this.c.gridx = this.col;
        this.c.gridy = this.row;

        switch(codeBlock.getCodetype().getType())
        {
            case IF:
            case WHILE:
                codeBlockPanel = new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), this.controller, this.playPanel);
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                
                if(this.current)
                {	
                	codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
                }
                
                this.panel.add(codeBlockPanel, this.c);
                this.col++;
                if (codeBlock.getCondition() != null)
                {
                    this.c.gridx = this.col;
                    codeBlockPanel = new CodeBlockPanel(codeBlock.getCondition(), codeBlock.getId(), this.controller, this.playPanel);
                    

                    if(this.current)
                    {	
                    	codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
                    }

                    this.panel.add(codeBlockPanel, this.c);
                    value.add(codeBlockPanel);
                    this.row++;
                }
                break;
            	case ELSE:
                this.c.gridx = --this.col;
                codeBlockPanel = new CodeBlockPanel("ELSE", codeBlock.getId(), this.controller, this.playPanel);

                if(this.current)
                {	
                	codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
                }

                this.panel.add(codeBlockPanel, this.c);
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                this.col++;
                this.row++;
                break;
            	case END:
                this.c.gridx = --this.col;
                codeBlockPanel = new CodeBlockPanel("END", codeBlock.getId(), this.controller, this.playPanel);
                
                if(this.current)
                {	
                	codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
                }

                this.panel.add(codeBlockPanel, this.c);
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                this.row++;
                break;
            case ACTION:
                codeBlockPanel = new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), this.controller, this.playPanel);

                if(this.current)
                {	
                	codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
                }

                this.panel.add(codeBlockPanel, this.c);
                value.add(codeBlockPanel);
                this.references.put(codeBlock.getId(), value);
                this.row++;
                break;
            default: break;
        }
        

        this.c = new GridBagConstraints();
        this.c.gridy = this.row + 1;
        this.c.weightx = 1;
        this.c.weighty = 1;
        this.c.gridwidth = 1000;
        this.c.fill = GridBagConstraints.BOTH;
        this.panel.add(this.filler, this.c);

        this.panel.revalidate();
    }
    
    public void removeCodeBlock(){
        refreshPanel();
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

    public int getEditableCodeId()
    {
        return this.blockBeingEdited.getId();
    }

    public CodePanel markBeingProcessed(int id)
    {
        for (final CodeBlockPanel codeBlockPanel : this.previouslyBeingProcessed)
        {
            codeBlockPanel.updateBorderColor(null);
        }
        this.previouslyBeingProcessed.clear();

        List<CodeBlockPanel> currentlyBeingProcessed = this.references.get(id);

        for (final CodeBlockPanel codeBlockPanel : currentlyBeingProcessed)
        {
            codeBlockPanel.updateBorderColor(Constants.COLOR_DARK_GREEN);
        }

        this.previouslyBeingProcessed = currentlyBeingProcessed;
        return this;
    }
    
    public List<CodeBlockPanel> getPreviouslyProcessed(){
        return this.previouslyBeingProcessed;
    }
	
}
