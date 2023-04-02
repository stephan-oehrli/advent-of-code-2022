package day_14;

import day_14.DayFourteen.Coordinate;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame {

    public Visualizer(Panel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Setter
    public static class Panel extends JPanel {
        private Cell[][] cells;
        private final int panelWidth = 600;
        private final int panelHeight = 600;

        Panel(Coordinate[][] coordinates) {
            this.setPreferredSize(new Dimension(panelWidth, panelHeight));
            this.setBackground(Color.BLACK);
            this.cells = createCells(coordinates);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(Color.LIGHT_GRAY);
            for (int y = 0; y < cells.length; y++) {
                for (int x = 0; x < cells[0].length; x++) {
                    drawCell(g2, cells[y][x]);
                }
            }
        }

        public void drawCell(Graphics2D g2, Cell cell) {
            g2.setPaint(cell.getColor());
            if (cell.isFilled) {
                g2.fill(cell);
            } else {
                g2.draw(cell);
            }
        }

        public Cell[][] createCells(Coordinate[][] coordinates) {
            int height = coordinates.length;
            int width = coordinates[0].length;
            Cell[][] cells = new Cell[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (coordinates[y][x].isSolid()) {
                        Color color = coordinates[y][x].isSandGrain() ? Color.ORANGE : Color.LIGHT_GRAY;
                        cells[y][x] = new Cell(x * 2 - 500, y * 2, 2, color, true);
                    } else {
                        cells[y][x] = new Cell(x * 2 - 500, y * 2, 2, Color.BLACK, false);
                    }
                }
            }
            return cells;
        }
    }

    @Getter
    private static class Cell extends Rectangle {
        private final Color color;
        private final boolean isFilled;

        Cell(int x, int y, int size, Color color, boolean isFilled) {
            super(x, y, size, size);
            this.color = color;
            this.isFilled = isFilled;
        }
    }
}
