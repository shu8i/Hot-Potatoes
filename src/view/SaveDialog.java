package view;

import control.Controller;
import model.Grid;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Shahab Shekari on 11/11/14.
 */
public class SaveDialog {


    protected static GridBagLayout layout = new GridBagLayout();
    protected  GridBagConstraints c = new GridBagConstraints();
    private BuildPanel buildPanel;
    private PlayPanel playPanel;
    private Controller controller;
    private Grid grid;
    private JFrame frame;
    private JPanel dialog;
    private JTextField nameTextField;
    private JButton saveButton, cancelButton;
    private JLabel nameLabel, confirmLabel;
    private HintPanel hintPanel;

    /** Creates the reusable dialog. */
    public SaveDialog(BuildPanel caller, Grid grid, Controller controller)
    {
        this.grid = grid;
        this.buildPanel = caller;
        setup(controller);
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SaveDialog.this.grid.setName(SaveDialog.this.nameTextField.getText());
                    SaveDialog.this.controller.addGrid(SaveDialog.this.grid);
                } catch (IllegalArgumentException ie) {
                    SaveDialog.this.hintPanel.updateHint(ie.getMessage(), Color.RED);
                    return;
                }

                SaveDialog.this.controller.saveDatabase();
                SaveDialog.this.buildPanel.updateLoadMenu().updateDeleteMenu();
                SaveDialog.this.buildPanel.enterEditMode(SaveDialog.this.nameTextField.getText());
                SaveDialog.this.frame.dispatchEvent(new WindowEvent(SaveDialog.this.frame, WindowEvent.WINDOW_CLOSING));
                SaveDialog.this.buildPanel.hintPanel.updateHint("Saved Successfully.", Constants.COLOR_DARK_GREEN, 3000);
            }
        });
    }

    public SaveDialog(PlayPanel playPanel, Controller controller, final PlayPanel.DialogType type)
    {
        this.playPanel = playPanel;
        setup(controller);
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (type == PlayPanel.DialogType.MACRO) {
                        SaveDialog.this.controller.userController.addMacro(
                                SaveDialog.this.nameTextField.getText(),
                                SaveDialog.this.controller.codeController.getCode(),
                                SaveDialog.this.playPanel.actionPanel.getActionStack());
                    } else {
                        SaveDialog.this.controller.userController.saveCode(
                                SaveDialog.this.nameTextField.getText(),
                                SaveDialog.this.controller.codeController.getCode(),
                                SaveDialog.this.playPanel.actionPanel.getActionStack());
                    }
                } catch (IllegalArgumentException ie) {
                    SaveDialog.this.hintPanel.updateHint(ie.getMessage(), Color.RED);
                    return;
                }

                SaveDialog.this.controller.saveDatabase();
                if (type == PlayPanel.DialogType.MACRO) {
                    SaveDialog.this.playPanel.macroPanel.refreshPanel();
                }
                SaveDialog.this.frame.dispatchEvent(new WindowEvent(SaveDialog.this.frame, WindowEvent.WINDOW_CLOSING));
                SaveDialog.this.playPanel.hintPanel.updateHint("Saved Successfully.", Constants.COLOR_DARK_GREEN, 3000);
            }
        });
    }

    private void setup(Controller controller)
    {
        frame = new JFrame("Save");

        this.controller = controller;
        dialog = new JPanel();
        dialog.setLayout(layout);
        dialog.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.nameTextField = new JTextField();
        this.nameLabel = new JLabel("Name");
        this.saveButton = new JButton("Save");
        this.cancelButton = new JButton("Cancel");
        this.confirmLabel = new JLabel("Please Confirm");
        this.hintPanel = new HintPanel(new Dimension(200, 20));

        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveDialog.this.frame.dispatchEvent(new WindowEvent(SaveDialog.this.frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 10;
        confirmLabel.setFont(Constants.FONT_OPEN_SANS_20);
        confirmLabel.setForeground(Constants.COLOR_SMOOTH_GREEN);
        dialog.add(confirmLabel, c);

        c = new GridBagConstraints();
        c.gridx = 10;
        c.gridwidth = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridy = 1;
        c.gridwidth = 5;
        c.anchor = GridBagConstraints.WEST;
        nameLabel.setFont(Constants.FONT_OPEN_SANS_14);
        nameLabel.setForeground(Color.GRAY);
        dialog.add(nameLabel, c);

        c = new GridBagConstraints();
        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 10;
        c.gridy = 1;
        c.gridwidth = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(nameTextField, c);

        c = new GridBagConstraints();
        c.gridy = 2;
        c.gridwidth = 15;
        dialog.add(hintPanel, c);

        c = new GridBagConstraints();
        c.gridx = 15;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        saveButton.setFocusable(false);
        dialog.add(saveButton, c);

        c = new GridBagConstraints();
        c.gridx = 17;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        saveButton.setFocusable(false);
        dialog.add(cancelButton, c);


        frame.setContentPane(dialog);
        frame.pack();
        frame.setLocationRelativeTo(this.buildPanel);
        frame.setVisible(true);
    }

}
