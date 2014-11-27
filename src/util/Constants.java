package util;

import java.awt.*;
import java.io.File;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public abstract class Constants {

    public static final int ICON_WIDTH = 200, ICON_HEIGHT = 200;

    public static final String DATABASE = "data" + File.separator + "database.dat";
    public static final String DEFAULT_USERNAME = "root", DEFAULT_PASSWORD = "admin";

    public static final Font FONT_OPEN_SANS_14 = new Font("Open Sans", Font.PLAIN, 14);
    public static final Font FONT_OPEN_SANS_20 = new Font("Open Sans", Font.PLAIN, 20);

    public static final Color COLOR_DARK_GREEN = new Color(0, 100, 0);
    public static final Color COLOR_SMOOTH_GREEN = new Color(0, 152, 185);
    public static final Color COLOR_CONDITIONALS = new Color(255, 99, 71);
    public static final Color COLOR_CONDITIONALS_HOVER = new Color(255, 69, 0);
    public static final Color COLOR_ACTIONS = new Color(70, 130, 180);
    public static final Color COLOR_ACTIONS_HOVER = new Color(65, 105, 255);

}
