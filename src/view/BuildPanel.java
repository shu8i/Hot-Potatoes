package view;

import control.GameController;
import model.Grid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class BuildPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected GridBagConstraints c = new GridBagConstraints();
    private LoginPanel loginPanel;
    private AdminPanel predecessor;
    private JFrame parent;
    private GameController controller;
    private JRadioButton robotRadio, homeRadio, potatoRadio, obstacleRadio;
    private JLabel gridSizeLabel;
    private JTextField gridSizeTextField;
    private GridPanel gridPanel;
    private HintPanel hintPanel;
    private JMenuItem newMenu, backMenu, saveMenu;
    private JMenu loadMenu, deleteMenu;
    private boolean EDIT_MODE = false;

    public BuildPanel(JFrame parent, LoginPanel loginPanel, AdminPanel predecessor, GameController controller) {
        super(layout);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.parent = parent;
        this.loginPanel = loginPanel;
        this.predecessor = predecessor;
        this.controller = controller;
        this.predecessor.setVisible(false);

        this.robotRadio = new JRadioButton("Karel");
        this.homeRadio = new JRadioButton("Home");
        this.potatoRadio = new JRadioButton("Potato");
        this.obstacleRadio = new JRadioButton("Wall");
        this.gridSizeLabel = new JLabel("Grid Size");
        this.gridSizeTextField = new JTextField("10");
        this.gridPanel = new GridPanel(10, this);
        this.hintPanel = new HintPanel(new Dimension(1, 15));

        this.newMenu = new JMenuItem("New");
        this.newMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildPanel.this.gridSizeTextField.setText("10");
            }
        });

        this.backMenu = new JMenuItem("Back");
        this.backMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildPanel.this.setVisible(false);
                BuildPanel.this.predecessor.updateMenu();
                BuildPanel.this.predecessor.setVisible(true);
                BuildPanel.this.parent.pack();
                BuildPanel.this.parent.setLocationRelativeTo(null);
            }
        });

        this.saveMenu = new JMenuItem("Save");
        this.saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!BuildPanel.this.EDIT_MODE) {
                    new SaveDialog(BuildPanel.this, BuildPanel.this.gridPanel.getGrid(), BuildPanel.this.controller);
                } else {
                    BuildPanel.this.controller.updateGrid(BuildPanel.this.gridPanel.getGrid());
                    BuildPanel.this.controller.saveDatabase();
                    BuildPanel.this.hintPanel.updateHint("Save successful!", Color.GREEN);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            BuildPanel.this.hintPanel.removeHint();
                        }
                    }, 2000);
                }
            }
        });

        this.loadMenu = new JMenu("Load");
        updateLoadMenu();

        this.deleteMenu = new JMenu("Delete");
        updateDeleteMenu();

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(robotRadio);
        buttonGroup.add(homeRadio);
        buttonGroup.add(potatoRadio);
        buttonGroup.add(obstacleRadio);

        this.obstacleRadio.setSelected(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        c.weightx = 20;
        c.gridwidth = 20;
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        add(this.gridPanel, c);

        robotRadio.setFont(new Font("Open Sans", Font.PLAIN, 14));
        robotRadio.setForeground(Color.GRAY);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 3;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.WEST;
        add(robotRadio, c);

        homeRadio.setFont(new Font("Open Sans", Font.PLAIN, 14));
        homeRadio.setForeground(Color.GRAY);
        c.gridx = 4;
        add(homeRadio, c);

        potatoRadio.setFont(new Font("Open Sans", Font.PLAIN, 14));
        potatoRadio.setForeground(Color.GRAY);
        c.gridx = 7;
        add(potatoRadio, c);

        obstacleRadio.setFont(new Font("Open Sans", Font.PLAIN, 14));
        obstacleRadio.setForeground(Color.GRAY);
        c.gridx = 10;
        add(obstacleRadio, c);

        gridSizeLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
        gridSizeLabel.setForeground(Color.GRAY);
        c.gridx = 13;
        add(gridSizeLabel, c);

        gridSizeTextField.getDocument().addDocumentListener(getDocumentListener());
        c.gridx = 16;
        c.gridwidth = 5;
        c.weightx = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(gridSizeTextField, c);

        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 20;
        c.gridwidth = 20;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(hintPanel, c);

        updateMenu();
        BuildPanel.this.parent.add(this, c);
        BuildPanel.this.parent.pack();
        BuildPanel.this.parent.setLocationRelativeTo(null);

    }

    public BuildPanel updateLoadMenu() {
        this.loadMenu.removeAll();
        JMenuItem loadMenuSubItem;
        for (final Map.Entry<String, Grid> entry : this.controller.getGrids().entrySet()) {
            loadMenuSubItem = new JMenuItem(entry.getKey());
            loadMenuSubItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildPanel.this.parent.setTitle("Editing: " + entry.getKey());
                    BuildPanel.this.gridSizeTextField.setText(String.valueOf(entry.getValue().getSize()));
                    BuildPanel.this.updateGrid(entry.getValue());
                    BuildPanel.this.robotRadio.setEnabled(false);
                    BuildPanel.this.homeRadio.setEnabled(false);
                    BuildPanel.this.EDIT_MODE = true;
                }
            });
            BuildPanel.this.loadMenu.add(loadMenuSubItem);
        }
        this.loadMenu.setEnabled(this.loadMenu.getMenuComponentCount() != 0);
        return this;
    }

    public BuildPanel updateDeleteMenu() {
        this.deleteMenu.removeAll();
        JMenuItem loadMenuSubItem;
        for (final Map.Entry<String, Grid> entry : this.controller.getGrids().entrySet()) {
            loadMenuSubItem = new JMenuItem(entry.getKey());
            loadMenuSubItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildPanel.this.updateGrid();
                    BuildPanel.this.controller.removeGrid(entry.getValue());
                    BuildPanel.this.controller.saveDatabase();
                    BuildPanel.this.updateLoadMenu();
                    BuildPanel.this.updateDeleteMenu();
                }
            });
            BuildPanel.this.deleteMenu.add(loadMenuSubItem);
        }
        this.deleteMenu.setEnabled(this.deleteMenu.getMenuComponentCount() != 0);
        return this;
    }

    private DocumentListener getDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateGrid();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateGrid();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateGrid();
            }

        };
    }

    public void updateGrid() {
        try {
            Integer gridSize = Integer.parseInt(BuildPanel.this.gridSizeTextField.getText());
            if ( gridSize >= 10 && gridSize <= 50) {
                BuildPanel.this.EDIT_MODE = false;
                BuildPanel.this.parent.setTitle("Hot Potatoes");
                BuildPanel.this.remove(BuildPanel.this.gridPanel);
                BuildPanel.this.hintPanel.removeHint();

                BuildPanel.this.gridPanel = new GridPanel(gridSize, BuildPanel.this);
                c = new GridBagConstraints();
                c.weightx = 20;
                c.gridwidth = 20;
                c.gridx = 1;
                c.gridy = 1;
                c.fill = GridBagConstraints.BOTH;
                BuildPanel.this.add(BuildPanel.this.gridPanel, c);
                BuildPanel.this.robotRadio.setEnabled(true);
                BuildPanel.this.homeRadio.setEnabled(true);
                BuildPanel.this.revalidate();
            } else {
                BuildPanel.this.hintPanel.updateHint("Size must be between 10 and 50!", Color.ORANGE);
            }
        } catch (NumberFormatException e) {
            BuildPanel.this.hintPanel.updateHint("Size must be between 10 and 50!", Color.ORANGE);
        }
    }

    public void updateGrid(Grid grid) {
        this.remove(this.gridPanel);
        this.hintPanel.removeHint();

        this.gridPanel = new GridPanel(grid, BuildPanel.this);
        c = new GridBagConstraints();
        c.weightx = 20;
        c.gridwidth = 20;
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        BuildPanel.this.add(this.gridPanel, c);
        BuildPanel.this.robotRadio.setEnabled(true);
        BuildPanel.this.homeRadio.setEnabled(true);
        BuildPanel.this.revalidate();
    }

    public void updateMenu() {
        this.parent.setJMenuBar(new Menu().buildMenu("Menu", loginPanel, controller, this,
                this.newMenu, this.loadMenu, this.deleteMenu, this.saveMenu, this.backMenu));
    }

    public BuildPanelSelection getSelection() {
        if (this.robotRadio.isSelected()) {
            return BuildPanelSelection.KAREL;
        }
        if (this.homeRadio.isSelected()) {
            return BuildPanelSelection.HOME;
        }
        if (this.potatoRadio.isSelected()) {
            return BuildPanelSelection.POTATO;
        }

        return BuildPanelSelection.WALL;
    }

    public void switchKarelActiveStatus() {
        this.robotRadio.setEnabled(!this.robotRadio.isEnabled());
        if (!this.robotRadio.isEnabled()) {
            this.obstacleRadio.setSelected(true);
        }
    }

    public void switchHomeActiveStatus() {
        this.homeRadio.setEnabled(!this.homeRadio.isEnabled());
        if (!this.homeRadio.isEnabled()) {
            this.obstacleRadio.setSelected(true);
        }
    }

    public void enterEditMode(String gridName) {
        this.EDIT_MODE = true;
        this.parent.setTitle("Editing: " + gridName);
    }
    
}
