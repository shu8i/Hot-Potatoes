package view;

import util.Constants;

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
public class MacroPanel extends JPanel {

    public MacroPanel() {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(400, 100));
        setBorder(new MatteBorder(1, 1, 1, 1, Constants.SMOOTH_GREEN));
    }
     
}
