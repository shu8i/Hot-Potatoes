package view;

import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private boolean endButton = false;
    private boolean elseButton = false;

	public CodeBlockPanel(String buttonText, final boolean ACTIONABLE) {
        this.conditional = buttonText.equals("IF") || buttonText.equals("WHILE");
        this.endButton = buttonText.equals("END");
        this.elseButton = buttonText.equals("ELSE");
        setText(buttonText);
        setFocusable(false);
        setFont(Constants.OPEN_SANS_14);
        setForeground(Color.WHITE);

        if (!ACTIONABLE) {
            setPreferredSize(new Dimension(116, 41));
            setBorderPainted(false);
            if (!buttonText.equals("")) {
                setBackground(Constants.SMOOTH_GREEN);
                setOpaque(true);
            } else {
                setOpaque(false);
            }
        } else {
            setPreferredSize(new Dimension(110, 30));
            setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                    Constants.CONDITIONALS : Constants.ACTIONS));
            setBackground(Color.GRAY);
        }

        if (ACTIONABLE) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                            Constants.CONDITIONALS_HOVER : Constants.ACTIONS_HOVER));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                            Constants.CONDITIONALS : Constants.ACTIONS));
                }
            });
        } else {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    setBackground(Constants.SMOOTH_GREEN);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(Constants.ACTIONS);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Constants.SMOOTH_GREEN);
                }
            });
            setBorder(new EmptyBorder(2, 2, 2, 2));
        }
    }

    public boolean isConditional() {
        return conditional;
    }

    public boolean isEndButton() {
        return endButton;
    }

    public boolean isElseButton() {
        return elseButton;
    }

}
