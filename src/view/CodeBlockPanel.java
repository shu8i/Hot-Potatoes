package view;

import util.Constants;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeBlockPanel extends JButton {

    private boolean conditional = false;
    private boolean conditionalAggrigator = false;

	public CodeBlockPanel(String buttonText, final boolean ACTIONABLE) {
        this.conditional = buttonText.equals("IF") || buttonText.equals("WHILE");
        this.conditionalAggrigator = buttonText.equals("AND") || buttonText.equals("OR");
        setText(buttonText);
        setFocusable(false);
        setFont(Constants.OPEN_SANS_14);
        setForeground(Color.WHITE);

        if (!ACTIONABLE) {
            setPreferredSize(new Dimension(155, 41));
            setBackground(Constants.SMOOTH_GREEN);
            setBorderPainted(false);
            setOpaque(true);
        } else {
            setPreferredSize(new Dimension(100, 30));
            setBorder(new MatteBorder(2, 2, 2, 2, conditional ? Constants.CONDITIONALS : Constants.ACTIONS));
            setBackground(Color.GRAY);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(new MatteBorder(2, 2, 2, 2, conditional ? Constants.CONDITIONALS_HOVER : Constants.ACTIONS_HOVER));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(new MatteBorder(2, 2, 2, 2, conditional ? Constants.CONDITIONALS : Constants.ACTIONS));
            }
        });
    }

    public boolean isConditional() {
        return conditional;
    }

    public boolean isConditionalAggrigator() {
        return conditionalAggrigator;
    }

}
