package view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

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
    private int conditionalLevel = 0;
    private boolean inConditional = false;
    private boolean inConditionalChecker = false;
    private JPanel panel;

    public CodePanel(JPanel panel) {
        super(panel);
        this.panel = panel;
        this.panel.setLayout(layout);
        this.filler = new JPanel();
        this.filler.setBackground(Color.GRAY);
        setPreferredSize(new Dimension(400, 500));
        setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        this.panel.setBackground(Color.GRAY);
    }

    public void addCodeBlock(CodeBlockPanel codeBlock) {
        this.panel.remove(filler);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;

        if (codeBlock.isConditional()) {
            inConditional = true;
            inConditionalChecker = true;
            c.gridx = conditionalLevel;
            c.gridy = ++row;
            this.panel.add(codeBlock, c);

            c = new GridBagConstraints();
            c.gridy = row + 1;
            c.gridwidth = 1000;
            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            this.panel.add(filler, c);
            this.panel.revalidate();
            return;
        }

        if (inConditional && !inConditionalChecker) {
            inConditional = false;
            conditionalLevel++;
        }

        if (inConditional) {
            c.gridx = conditionalLevel + ++col;
            c.gridy = row;
            this.panel.add(codeBlock, c);
            inConditionalChecker = false;
        } else {
            col = 0;
            if (codeBlock.isEndButton() || codeBlock.isElseButton()) {
                c.gridx = --conditionalLevel;
                c.gridy = ++row;
                this.panel.add(codeBlock,c );
            } else {
                c.gridx = conditionalLevel;
                c.gridy = ++row;
                this.panel.add(codeBlock, c);
            }
            if (codeBlock.isElseButton()) {
                conditionalLevel++;
            }
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
	
}
