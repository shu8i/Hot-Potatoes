package view;

import control.Controller;
import model.CodeBlock;
import model.CodeType;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Iterator;

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
    }

    public void addCodeBlock(CodeBlock codeBlock)
    {
        this.panel.remove(filler);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;

        c.gridx = col;
        c.gridy = row;

        switch(codeBlock.getCodetype().getType())
        {
            case IF:
            case WHILE:
                this.panel.add(new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), controller, this.playPanel), c);
                col++;
                if (codeBlock.getCondition() != null)
                {
                    c.gridx = col;
                    this.panel.add(new CodeBlockPanel(codeBlock.getCondition(), codeBlock.getId(), controller, this.playPanel), c);
                    row++;
                }
                break;
            case ELSE:
                c.gridx = --col;
                this.panel.add(new CodeBlockPanel("ELSE", codeBlock.getId(), controller, this.playPanel), c);
                col++;
                row++;
                break;
            case END:
                c.gridx = --col;
                this.panel.add(new CodeBlockPanel("END", codeBlock.getId(), controller, this.playPanel), c);
                row++;
                break;
            case ACTION:
                this.panel.add(new CodeBlockPanel(codeBlock.getCodetext(), codeBlock.getId(), controller, this.playPanel), c);
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


        Iterator<CodeBlock> iterator = controller.codeController.viewIterator();
        CodeBlock codeBlock;
        while (iterator.hasNext()) {
            codeBlock = iterator.next();
            addCodeBlock(codeBlock);
        }


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
	
}
