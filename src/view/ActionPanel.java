package view;

import control.Controller;
import model.CodeBlock;
import model.CodeType;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
                           endButton, elseButton;
    private CodePanel codePanel;
    private Controller controller;
    private enum PanelMode{CONDITIONAL_DECLARATION, WHILE_DECLARATION, IN_CONDITIONAL, IN_WHILE, ACTION}
//    private PanelMode mode;
    private Stack<PanelMode> mode;

    public ActionPanel(final CodePanel codePanel, Controller controller) {
        super(new FlowLayout());
        this.codePanel = codePanel;
        this.controller = controller;
        this.mode = new Stack<PanelMode>();
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
        this.elseButton = new CodeBlockPanel("ELSE", false);

        this.whileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("WHILE", true));

                mode.push(PanelMode.WHILE_DECLARATION);
                repaintActionPanel();
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
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("MOVE", new CodeType(CodeType.Type.ACTION)));
            }
        });

        this.ifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("IF", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("IF", new CodeType(CodeType.Type.IF)));

                mode.push(PanelMode.CONDITIONAL_DECLARATION);
                repaintActionPanel();
            }
        });

        this.elseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.codePanel.addCodeBlock(new CodeBlockPanel("ELSE", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE)));
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

        this.facingLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePanel.addCodeBlock(new CodeBlockPanel("FACING LEFT", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("FACING LEFT", new CodeType(CodeType.Type.ACTION)));

                mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                        PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                repaintActionPanel();
            }
        });

        this.facingRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePanel.addCodeBlock(new CodeBlockPanel("FACING RIGHT", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("FACING RIGHT", new CodeType(CodeType.Type.ACTION)));

                mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                        PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                repaintActionPanel();
            }
        });

        this.facingUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePanel.addCodeBlock(new CodeBlockPanel("FACING UP", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("FACING UP", new CodeType(CodeType.Type.ACTION)));

                mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                        PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                repaintActionPanel();
            }
        });

        this.facingDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePanel.addCodeBlock(new CodeBlockPanel("FACING DOWN", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("FACING DOWN", new CodeType(CodeType.Type.ACTION)));

                mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                        PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                repaintActionPanel();
            }
        });

        this.endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePanel.addCodeBlock(new CodeBlockPanel("END", true));
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("END", new CodeType(CodeType.Type.ACTION)));

                mode.pop();
                repaintActionPanel();
            }
        });


        mode.push(PanelMode.ACTION);
        repaintActionPanel();
    }

    private void repaintActionPanel() {
        removeAll();
        switch(mode.peek()) {
            case WHILE_DECLARATION:
            case CONDITIONAL_DECLARATION:
                add(facingLeftButton);
                add(facingRightButton);
                add(facingUpButton);
                add(facingDownButton);
                add(new CodeBlockPanel("", false));
                add(new CodeBlockPanel("", false));
                add(new CodeBlockPanel("", false));
                add(new CodeBlockPanel("", false));
                break;
            case IN_CONDITIONAL:
                add(elseButton);
                add(endButton);
                add(ifButton);
                add(whileButton);
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                break;
            case IN_WHILE:
                add(endButton);
                add(ifButton);
                add(whileButton);
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                add(new CodeBlockPanel("", false));
                break;
            case ACTION:
                add(whileButton);
                add(ifButton);
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                add(new CodeBlockPanel("", false));
                add(new CodeBlockPanel("", false));
            default: break;
        }
        repaint();
        revalidate();
    }
    
}
