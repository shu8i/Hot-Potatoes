import control.GameController;
import view.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Shahab Shekari on 11/9/14.
 */
public class HotPotatoes extends JFrame {

    private static GameController controller;

    public HotPotatoes(String title) {
        super(title);
        controller = new GameController();
        setLayout(new GridBagLayout());
        add(new LoginPanel(this, controller));
    }

    public static void main(String[] args) {
        JFrame game = new HotPotatoes("Hot Potatoes");
        game.pack();
        game.setVisible(true);
        game.setLocationRelativeTo(null);
        game.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.saveDatabase();
                System.exit(0);
            }
        });
    }

}