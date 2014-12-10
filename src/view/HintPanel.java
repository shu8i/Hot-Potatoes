package view;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

/**
 * Created by Shahab Shekari on 11/9/14.
 */
public class HintPanel extends JPanel {

    private JLabel hint;

    /**
     * Represents a panel, where hints/error messages can be written out to.
     * @param dimension the dimensions of the hintpanel.
     */
    public HintPanel(Dimension dimension) {
        super(new GridLayout(1,1));
        this.hint = new JLabel("");
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setSize(dimension);
        this.hint.setFont(new Font("Open Sans", Font.PLAIN, 14));
        add(this.hint);
    }

    /**
     * Represents a panel, where hints/error messages can be written out to.
     */
    public HintPanel() {
        super(new GridLayout(1,1));
        this.hint = new JLabel("");
        this.hint.setFont(new Font("Open Sans", Font.PLAIN, 14));
        add(this.hint);
    }

    /**
     * Updates the current hint with a new hint
     * @param hint the new hint
     * @param color the hint color
     */
    public void updateHint(String hint, Color color) {
        this.hint.setForeground(color);
        this.hint.setText(hint);
    }

    /**
     * Updates the current hint with a new hint
     * @param hint the new hint
     * @param color the hint color
     * @param milliSeconds the time after which the hint should be removed.
     */
    public void updateHint(String hint, Color color, int milliSeconds) {
        this.hint.setForeground(color);
        this.hint.setText(hint);
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                HintPanel.this.removeHint();
            }
        }, milliSeconds);
    }

    /**
     * Removes the hint.
     */
    public void removeHint() {
        this.hint.setText("");
    }

}
