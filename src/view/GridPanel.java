package view;

import model.*;
import model.Robot;
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
import java.util.HashMap;
import java.util.Map;

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
    private Robot robot;
    private BuildPanel buildPanel;
    private Map<Coordinate, GridCell> references;

    public GridPanel(final int GRID_SIZE, final BuildPanel buildPanel) {
        setLayout(layout);
        this.references = new HashMap<Coordinate, GridCell>();
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
        this.references = new HashMap<Coordinate, GridCell>();
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
                        gridCell.addPotato(true);
                    } else if (block.is(WALL)) {
                        gridCell.addWall();
                    }
                }

                add(gridCell, c);
            }
        }
    }

    public GridPanel(final Grid grid, final Robot robot) {
        setLayout(layout);
        this.references = new HashMap<Coordinate, GridCell>();
        this.grid = grid;
        this.robot = robot;
        refresh();
    }

    public void refresh()
    {
        removeAll();
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
                    }
                    if (block.is(HOME)) {
                        gridCell.addHome(false);
                    }
                    if (block.is(POTATO)) {
                        gridCell.addPotato(false);
                    }
                    if (block.is(WALL)) {
                        gridCell.addWall();
                    }
                }

                add(gridCell, c);
            }
        }
        repaint();
        revalidate();
    }

    public void softRefresh()
    {

        for (final Map.Entry<Coordinate, GridCell> entry : this.references.entrySet()) {

            if (entry.getValue().getContent() != null &&
                    entry.getValue().getContent().equals(BuildPanelSelection.KAREL)) {
                entry.getValue().removeContent();
            }

            if (!this.grid.getBlock(entry.getKey()).is(POTATO) &&
                    entry.getValue().getContent() != null &&
                    entry.getValue().getContent().equals(BuildPanelSelection.POTATO)) {
                entry.getValue().removeContent();
            }

            if (this.grid.getBlock(entry.getKey()).is(KAREL)) {
                entry.getValue().addKarel(false);
            }

            if (this.grid.getBlock(entry.getKey()).is(POTATO)) {
                entry.getValue().addPotato(false);
            }
        }
        repaint();
        revalidate();
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
            references.put(this.coordinate, this);
            ((FlowLayout)GridCell.this.getLayout()).setVgap(0);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    backgroundColor = getBackground();
                    setBackground(Constants.COLOR_SMOOTH_GREEN);
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
                                GridPanel.this.grid.removeKarel();
                                break;
                            case HOME:
                                GridCell.this.remove(GridCell.this.homeWrapper);
                                setBackground(null);
                                GridCell.this.revalidate();
                                content = null;
                                buildPanel.switchHomeActiveStatus();
                                GridPanel.this.grid.removeHome();
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
                                addPotato(true);
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
            if (grid.getBlock(this.coordinate).is(KAREL)) this.content = BuildPanelSelection.KAREL;
            else if (grid.getBlock(this.coordinate).is(WALL)) this.content = BuildPanelSelection.WALL;
            else if (grid.getBlock(this.coordinate).is(POTATO)) this.content = BuildPanelSelection.POTATO;
            else if (grid.getBlock(this.coordinate).is(HOME)) this.content = BuildPanelSelection.HOME;
            references.put(this.coordinate, this);
            ((FlowLayout)GridCell.this.getLayout()).setVgap(0);
        }

        private void addKarel(boolean EDIT_MODE) {
            String robotImg;
            if (robot != null) {
                switch (robot.getDirection()) {
                    case UP:
                        robotImg = "/resources/images/walle_u.png";
                        break;
                    case DOWN:
                        robotImg = "/resources/images/walle_d.png";
                        break;
                    case LEFT:
                        robotImg = "/resources/images/walle_l.png";
                        break;
                    default:
                        robotImg = "/resources/images/walle_r.png";
                        break;
                }
            } else {
                robotImg = "/resources/images/walle_r.png";
            }
            try {
                Image image = ImageIO.read(getClass().getResource(robotImg));
                Image scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                GridCell.this.karelIcon = new ImageIcon(scaledImage);
                GridCell.this.karelWrapper = new JLabel(GridCell.this.karelIcon);
                GridCell.this.add(GridCell.this.karelWrapper);
                GridCell.this.revalidate();
                content = BuildPanelSelection.KAREL;
                if (EDIT_MODE)
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
                if (EDIT_MODE) GridPanel.this.grid.setHome(GridCell.this.coordinate);
                if (EDIT_MODE) buildPanel.switchHomeActiveStatus();
            } catch(IOException ioe) {}
        }

        private void addPotato(boolean EDIT_MODE) {
            try {
                Image image = ImageIO.read(getClass().getResource("/resources/images/potato.png"));
                Image scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                GridCell.this.potatoIcon = new ImageIcon(scaledImage);
                GridCell.this.potatoWrapper = new JLabel(GridCell.this.potatoIcon);
                GridCell.this.potatoWrapper.setPreferredSize(new Dimension(cellSize, cellSize));
                GridCell.this.add(GridCell.this.potatoWrapper);
                GridCell.this.revalidate();
                content = BuildPanelSelection.POTATO;
                if (EDIT_MODE) GridPanel.this.grid.addPotato(GridCell.this.coordinate);
            } catch(IOException ioe) {}
        }

        private void addWall() {
            setBackground(Color.GRAY);
            backgroundColor = Color.GRAY;
            content = BuildPanelSelection.WALL;
            GridPanel.this.grid.addWall(GridCell.this.coordinate);
        }

        public BuildPanelSelection getContent()
        {
            return this.content;
        }

        public GridCell removeContent()
        {
            this.removeAll();
            this.content = null;
            repaint();
            revalidate();
            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(this.cellSize, this.cellSize);
        }
    }
}