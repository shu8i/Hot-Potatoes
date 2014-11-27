package view;

import control.Controller;
import model.CodeBlock;
import model.CodeType;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
    protected enum PanelMode{CONDITIONAL_DECLARATION, WHILE_DECLARATION, IN_CONDITIONAL, IN_WHILE, ACTION, IN_ELSE}
    protected Stack<PanelMode> mode;
    protected boolean editMode = false;

    public ActionPanel(final CodePanel codePanel, Controller controller) {
        super(new FlowLayout());
        this.codePanel = codePanel;
        this.controller = controller;
        this.mode = new Stack<PanelMode>();
        setPreferredSize(new Dimension(500, 100));
        setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));

        this.whileButton = new CodeBlockPanel("WHILE");
        this.turnLeftButton = new CodeBlockPanel("TURN LEFT");
        this.moveButton = new CodeBlockPanel("MOVE");
        this.ifButton = new CodeBlockPanel("IF");
        this.putPotatoButton = new CodeBlockPanel("PUT POTATO");
        this.pickPotatoButton = new CodeBlockPanel("PICK POTATO");
        this.facingLeftButton = new CodeBlockPanel("FACING LEFT");
        this.facingRightButton = new CodeBlockPanel("FACING RIGHT");
        this.facingUpButton = new CodeBlockPanel("FACING UP");
        this.facingDownButton = new CodeBlockPanel("FACING DOWN");
        this.endButton = new CodeBlockPanel("END");
        this.elseButton = new CodeBlockPanel("ELSE");

        this.whileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("WHILE", new CodeType(CodeType.Type.WHILE)));

                mode.push(PanelMode.WHILE_DECLARATION);
                repaintActionPanel();
                codePanel.refreshPanel();
            }
        });

        this.turnLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("TURN LEFT", new CodeType(CodeType.Type.ACTION)));
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "TURN LEFT");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("MOVE", new CodeType(CodeType.Type.ACTION)));
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "MOVE");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.ifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("IF", new CodeType(CodeType.Type.IF)));

                mode.push(PanelMode.CONDITIONAL_DECLARATION);
                repaintActionPanel();
                codePanel.refreshPanel();
            }
        });

        this.elseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("ELSE", new CodeType(CodeType.Type.ELSE)));

                mode.pop();
                mode.push(PanelMode.IN_ELSE);
                repaintActionPanel();
                codePanel.refreshPanel();
            }
        });

        this.putPotatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("PUT POTATO", new CodeType(CodeType.Type.ACTION)));
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "PUT POTATO");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.pickPotatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("PICK POTATO", new CodeType(CodeType.Type.ACTION)));
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "PICK POTATO");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.facingLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("FACING LEFT", new CodeType(CodeType.Type.ACTION)));

                    mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                            PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "FACING LEFT");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.facingRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode)
                {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("FACING RIGHT", new CodeType(CodeType.Type.ACTION)));

                    mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                            PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                }
                else
                {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "FACING RIGHT");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.facingUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("FACING UP", new CodeType(CodeType.Type.ACTION)));

                    mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                            PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "FACING UP");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.facingDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    ActionPanel.this.controller.codeController
                            .addCodeBlock(new CodeBlock("FACING DOWN", new CodeType(CodeType.Type.ACTION)));

                    mode.push(mode.pop().equals(PanelMode.CONDITIONAL_DECLARATION) ?
                            PanelMode.IN_CONDITIONAL : PanelMode.IN_WHILE);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                } else {
                    ActionPanel.this.controller.codeController
                            .editCode(codePanel.getEditableCodeId(), "FACING DOWN");
                    codePanel.updateBlockForEdit(null);
                    repaintActionPanel();
                    codePanel.refreshPanel();
                    editMode = !editMode;
                }
            }
        });

        this.endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionPanel.this.controller.codeController
                        .addCodeBlock(new CodeBlock("END", new CodeType(CodeType.Type.END)));

                mode.pop();
                repaintActionPanel();
                codePanel.refreshPanel();
            }
        });


        mode.push(PanelMode.ACTION);
        repaintActionPanel();
    }

    public void repaintActionPanel() {
        removeAll();
        switch(mode.peek()) {
            case WHILE_DECLARATION:
            case CONDITIONAL_DECLARATION:
                add(facingLeftButton);
                add(facingRightButton);
                add(facingUpButton);
                add(facingDownButton);
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
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
                add(new CodeBlockPanel(""));
                break;
            case IN_ELSE:
                add(endButton);
                add(ifButton);
                add(whileButton);
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                add(new CodeBlockPanel(""));
                break;
            case ACTION:
                add(whileButton);
                add(ifButton);
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
            default: break;
        }
        repaint();
        revalidate();
    }

    private void repaintActionPanel(PanelMode mode) {
        removeAll();
        switch(mode) {
            case CONDITIONAL_DECLARATION:
                add(facingLeftButton);
                add(facingRightButton);
                add(facingUpButton);
                add(facingDownButton);
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                break;
            case ACTION:
                add(turnLeftButton);
                add(moveButton);
                add(putPotatoButton);
                add(pickPotatoButton);
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
                add(new CodeBlockPanel(""));
            default: break;
        }
        repaint();
        revalidate();
    }

    public void updateActionPanel(CodeBlockPanel codeBlock)
    {
        if (codeBlock.isConditional() || codeBlock.isElseButton() ||
                codeBlock.isEndButton())
        {
            editMode = false;
            return;
        }
        if (codeBlock.getText().contains("FACING"))
        {
            repaintActionPanel(PanelMode.CONDITIONAL_DECLARATION);
            editMode = true;
        }
        else
        {
            repaintActionPanel(PanelMode.ACTION);
            editMode = true;
        }
    }
    
}
