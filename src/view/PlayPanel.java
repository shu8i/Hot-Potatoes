package view;

import control.Controller;
import model.Grid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class PlayPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private LoginPanel loginPanel;
    private StudentPanel predecessor;
    private JFrame parent;
    private Controller controller;
    private Grid grid;
    private JMenuItem runMenu, undoMenu, clearMenu, saveMacroMenu, saveGameMenu, backMenu, stepMenu;
    public HintPanel hintPanel;
    public GridPanel gridPanel;
    public CodePanel codePanel;
    public MacroPanel macroPanel;
    public ActionPanel actionPanel;

    public PlayPanel(JFrame parent, LoginPanel loginPanel, StudentPanel predecessor, Controller controller, Grid grid) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.parent = parent;
        this.controller = controller;
        this.grid = grid;
        this.predecessor.setVisible(false);
        this.hintPanel = new HintPanel(new Dimension(500, 30));
        this.controller.initRobot(this.grid);
        this.controller.initPlay(this);
        
        final Integer highScore = controller.userController.getGridScore(grid);
        final Integer score = 0;
        String scoreString = score.toString(); 
        hintPanel.updateHint("SCORE:" + scoreString + "     " + "HIGH SCORE:" + highScore, Color.blue);

        initPanels();

        this.saveMacroMenu = new JMenuItem("Save As Macro");
        this.saveMacroMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SaveDialog(PlayPanel.this, PlayPanel.this.controller);
            }
        });
        
        this.undoMenu = new JMenuItem("Undo Move");
        this.undoMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int blockID = PlayPanel.this.controller.codeController.getCode().getlastID();
                PlayPanel.this.controller.codeController.removeBlock(blockID);
                PlayPanel.this.codePanel.removeCodeBlock();
            }
        });

        this.runMenu = new JMenuItem("Run");
        this.runMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayPanel.this.controller.codeController.run();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        PlayPanel.this.controller.playPanel.gridPanel.refresh();
                    }
                });
            }
        });
        
        this.stepMenu = new JMenuItem("Take Step");
        this.stepMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayPanel.this.controller.codeController.step();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        PlayPanel.this.controller.playPanel.gridPanel.refresh();
                        PlayPanel.this.codePanel.refreshPanel();
                    }
                });
            }
        });

        this.clearMenu = new JMenuItem("Clear");
        this.clearMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayPanel.this.controller.codeController.clear();
                PlayPanel.this.actionPanel.reset();
                PlayPanel.this.codePanel.refreshPanel();
            }
        });

        this.backMenu = new JMenuItem("Back");
        this.backMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayPanel.this.setVisible(false);
                PlayPanel.this.predecessor.updateMenu();
                PlayPanel.this.predecessor.setVisible(true);
                PlayPanel.this.parent.pack();
                PlayPanel.this.parent.setLocationRelativeTo(null);
            }
        });

        add(this.gridPanel, c);

        c.gridx = 1;
        c.weightx = 1;
        c.weighty = 1;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 2;
        JPanel panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        panel.setPreferredSize(new Dimension(300, 500));
        add(this.codePanel, c);

        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(new JPanel(), c);


        c = new GridBagConstraints();
        c.gridy = 2;
        c.gridx = 0;
        panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        panel.setPreferredSize(new Dimension(500, 100));
        add(this.actionPanel, c);

        c.gridx = 2;
        panel = new JPanel();
        add(this.macroPanel, c);

        c.gridx = 0;
        c.gridy = 3;
        add(this.hintPanel, c);

        updateMenu();
        this.parent.add(this, c);
        this.parent.pack();
        this.parent.setLocationRelativeTo(null);
    }

    private void initPanels() {
        this.gridPanel = new GridPanel(this.grid, this.controller.robotController.getRobot());
        this.codePanel = new CodePanel(new JPanel(), this.controller, this);
        this.actionPanel = new ActionPanel(this.codePanel, this.controller);
        this.macroPanel = new MacroPanel(this, this.controller);
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this,
                clearMenu, runMenu, stepMenu, undoMenu, saveMacroMenu, backMenu));
    }

}