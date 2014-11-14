package view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class ActionPanel extends JPanel {

    private CodeBlockPanel whileButton, turnLeftButton, moveButton, ifButton,
                           putPotatoButton, pickPotatoButton, facingLeftButton,
                           facingRightButton, facingUpButton, facingDownButton,
                           endButton;
    private CodePanel codePanel;

    public ActionPanel(CodePanel codePanel) {
        super(new FlowLayout());
        this.codePanel = codePanel;
        setPreferredSize(new Dimension(500, 100));
        setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));

        this.whileButton = new CodeBlockPanel("WHILE", false);
        this.turnLeftButton = new CodeBlockPanel("TURN LEFT", false);
        this.moveButton = new CodeBlockPanel("MOVE", false);
        this.ifButton = new CodeBlockPanel("IF", false);
        this.putPotatoButton = new CodeBlockPanel("PUT POTATO", false);
        this.pickPotatoButton = new CodeBlockPanel("PICK POTATO", false);
        this.facingLeftButton = new CodeBlockPanel("FACING LEFT", false);
        this.facingRightButton = new CodeBlockPanel("FACING RIGHT", false);
        this.facingUpButton = new CodeBlockPanel("FACING UP", false);
        this.facingDownButton = new CodeBlockPanel("FACING DOWN", false);
        this.endButton = new CodeBlockPanel("END", false);

        this.whileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("WHILE", true));
            }
        });

        this.turnLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("TURN LEFT", true));
            }
        });

        this.moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("MOVE", true));
            }
        });

        this.ifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("IF", true));
            }
        });

        this.putPotatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("PUT POTATO", true));
            }
        });

        this.pickPotatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("PICK POTATO", true));
            }
        });
        add(whileButton);
        add(turnLeftButton);
        add(moveButton);
        add(ifButton);
        add(putPotatoButton);
        add(pickPotatoButton);
    }
    
}
