package view;

import model.Block;
import model.Grid;
import model.Coordinate;
import util.Constants;

import static model.BlockState.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class GridPanel extends JPanel {

    protected static GridBagLayout layout = new GridBagLayout();
    protected GridBagConstraints c = new GridBagConstraints();
    private Grid grid;
    private BuildPanel buildPanel;

    public GridPanel(final int GRID_SIZE, final BuildPanel buildPanel) {
        setLayout(layout);
        this.grid = new Grid(GRID_SIZE);
        this.buildPanel = buildPanel;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                c.gridx = col;
                c.gridy = row;

                GridCell cellPane = new GridCell(GRID_SIZE, buildPanel, row, col);
                Border border;
                if (row < 4) {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                cellPane.setBorder(border);
                add(cellPane, c);
            }
        }
    }

    public GridPanel(final Grid grid, final BuildPanel buildPanel) {
        setLayout(layout);
        this.grid = grid;
        this.buildPanel = buildPanel;

        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                c.gridx = col;
                c.gridy = row;

                GridCell gridCell = new GridCell(grid.getSize(), buildPanel, row, col);
                Border border;
                if (row < 4) {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                gridCell.setBorder(border);

                Block block = grid.getBlock(gridCell.coordinate);

                if (!block.isEmpty()) {
                    if (block.is(KAREL)) {
                        gridCell.addKarel(true);
                    } else if (block.is(HOME)) {
                        gridCell.addHome(true);
                    } else if (block.is(POTATO)) {
                        gridCell.addPotato();
                    } else if (block.is(WALL)) {
                        gridCell.addWall();
                    }
                }

                add(gridCell, c);
            }
        }
    }

    public GridPanel(final Grid grid) {
        setLayout(layout);
        this.grid = grid;

        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                c.gridx = col;
                c.gridy = row;

                GridCell gridCell = new GridCell(grid.getSize(), row, col);
                Border border;
                if (row < 4) {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                gridCell.setBorder(border);

                Block block = grid.getBlock(gridCell.coordinate);

                if (!block.isEmpty()) {
                    if (block.is(KAREL)) {
                        gridCell.addKarel(false);
                    } else if (block.is(HOME)) {
                        gridCell.addHome(false);
                    } else if (block.is(POTATO)) {
                        gridCell.addPotato();
                    } else if (block.is(WALL)) {
                        gridCell.addWall();
                    }
                }

                add(gridCell, c);
            }
        }
    }

    public Grid getGrid() {
        return this.grid;
    }

    private class GridCell extends JPanel {

        private Color backgroundColor;
        private int cellSize;
        private BuildPanelSelection content = null;
        private Coordinate coordinate;
        ImageIcon karelIcon, potatoIcon, homeIcon;
        JLabel karelWrapper, potatoWrapper, homeWrapper;

        public GridCell(int gridSize, final BuildPanel buildPanel, int row, int col) {
            this.cellSize = 500 / gridSize;
            this.coordinate = new Coordinate(col + 1, gridSize - row);
            ((FlowLayout)GridCell.this.getLayout()).setVgap(0);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    backgroundColor = getBackground();
                    setBackground(Constants.SMOOTH_GREEN);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(backgroundColor);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (content != null) {
                        switch (content) {
                            case KAREL:
                                GridCell.this.remove(GridCell.this.karelWrapper);
                                setBackground(null);
                                GridCell.this.revalidate();
                                content = null;
                                buildPanel.switchKarelActiveStatus();
                                GridPanel.this.grid.removeKarel(GridCell.this.coordinate);
                                break;
                            case HOME:
                                GridCell.this.remove(GridCell.this.homeWrapper);
                                setBackground(null);
                                GridCell.this.revalidate();
                                content = null;
                                buildPanel.switchHomeActiveStatus();
                                GridPanel.this.grid.removeHome(GridCell.this.coordinate);
                                break;
                            case POTATO:
                                GridCell.this.remove(GridCell.this.potatoWrapper);
                                setBackground(null);
                                GridCell.this.revalidate();
                                content = null;
                                GridPanel.this.grid.removePotato(GridCell.this.coordinate);
                                break;
                            case WALL:
                                setBackground(null);
                                backgroundColor = null;
                                content = null;
                                GridPanel.this.grid.removeWall(GridCell.this.coordinate);
                                break;
                            default: break;
                        }
                    } else {
                        switch (buildPanel.getSelection()) {
                            case KAREL:
                                addKarel(true);
                                break;
                            case HOME:
                                addHome(true);
                                break;
                            case POTATO:
                                addPotato();
                                break;
                            case WALL:
                                addWall();
                                break;
                            default:
                                break;
                        }
                    }
                    System.out.println(GridPanel.this.grid);
                }
            });
        }

        public GridCell(int gridSize, int row, int col) {
            this.cellSize = 500 / gridSize;
            this.coordinate = new Coordinate(col + 1, gridSize - row);
            ((FlowLayout)GridCell.this.getLayout()).setVgap(0);
        }

        private void addKarel(boolean EDIT_MODE) {
            try {
                Image image = ImageIO.read(getClass().getResource("/resources/images/walle.png"));
                Image scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                GridCell.this.karelIcon = new ImageIcon(scaledImage);
                GridCell.this.karelWrapper = new JLabel(GridCell.this.karelIcon);
                GridCell.this.add(GridCell.this.karelWrapper);
                GridCell.this.revalidate();
                content = BuildPanelSelection.KAREL;
                GridPanel.this.grid.setKarel(GridCell.this.coordinate);
                if (EDIT_MODE) buildPanel.switchKarelActiveStatus();
            } catch(IOException ioe) {}
        }

        private void addHome(boolean EDIT_MODE) {
            try {
                Image image = ImageIO.read(getClass().getResource("/resources/images/home.png"));
                Image scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                GridCell.this.homeIcon = new ImageIcon(scaledImage);
                GridCell.this.homeWrapper = new JLabel(GridCell.this.homeIcon);
                GridCell.this.add(GridCell.this.homeWrapper);
                GridCell.this.revalidate();
                content = BuildPanelSelection.HOME;
                GridPanel.this.grid.setHome(GridCell.this.coordinate);
                if (EDIT_MODE) buildPanel.switchHomeActiveStatus();
            } catch(IOException ioe) {}
        }

        private void addPotato() {
            try {
                Image image = ImageIO.read(getClass().getResource("/resources/images/potato.png"));
                Image scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                GridCell.this.potatoIcon = new ImageIcon(scaledImage);
                GridCell.this.potatoWrapper = new JLabel(GridCell.this.potatoIcon);
                GridCell.this.potatoWrapper.setPreferredSize(new Dimension(cellSize, cellSize));
                GridCell.this.add(GridCell.this.potatoWrapper);
                GridCell.this.revalidate();
                content = BuildPanelSelection.POTATO;
                GridPanel.this.grid.addPotato(GridCell.this.coordinate);
            } catch(IOException ioe) {}
        }

        private void addWall() {
            setBackground(Color.GRAY);
            backgroundColor = Color.GRAY;
            content = BuildPanelSelection.WALL;
            GridPanel.this.grid.addWall(GridCell.this.coordinate);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(this.cellSize, this.cellSize);
        }
    }
}