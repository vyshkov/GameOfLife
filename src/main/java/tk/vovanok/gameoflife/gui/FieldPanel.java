package tk.vovanok.gameoflife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import tk.vovanok.gameoflife.core.Cell;
import tk.vovanok.gameoflife.core.FieldCalculator;

public class FieldPanel extends JPanel {

    private static final int PREFERRED_CELL_SIZE = 10;
    private static final int MIN_CELL_SIZE = 3;
    private static final Color GRID_COLOR = new Color(12, 12, 12);

    private final int cols;
    private final int rows;

    private int cursorX;
    private int cursorY;
    private boolean eraser;

    final int eraserWidth = 9;
    final FieldCalculator fCalc;

    /** Pixel layout of the field: square cells scaled to fit the panel and centred. */
    private record Grid(double cellSize, int originX, int originY) {
        int x(int col) {
            return originX + (int) Math.round(col * cellSize);
        }

        int y(int row) {
            return originY + (int) Math.round(row * cellSize);
        }

        int col(int px) {
            return (int) Math.floor((px - originX) / cellSize);
        }

        int row(int py) {
            return (int) Math.floor((py - originY) / cellSize);
        }
    }

    FieldPanel(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        fCalc = new FieldCalculator(cols, rows);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(cols * PREFERRED_CELL_SIZE + 1, rows * PREFERRED_CELL_SIZE + 1));
        setMinimumSize(new Dimension(cols * MIN_CELL_SIZE + 1, rows * MIN_CELL_SIZE + 1));
    }

    private Grid grid() {
        // The last pixel column/row is reserved for the closing border line.
        double size = Math.max(1, Math.min((getWidth() - 1.0) / cols, (getHeight() - 1.0) / rows));
        int originX = Math.max(0, (int) ((getWidth() - 1 - cols * size) / 2));
        int originY = Math.max(0, (int) ((getHeight() - 1 - rows * size) / 2));
        return new Grid(size, originX, originY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        var grid = grid();

        drawCells(g2d, grid);
        drawGrid(g2d, grid);
        drawBorder(g2d, grid);
        drawCursor(g2d, grid);
    }
    private void drawBorder(Graphics2D g2d, Grid grid) {
        g2d.setColor(Color.BLUE);
        g2d.drawRect(grid.x(0), grid.y(0), grid.x(cols) - grid.x(0), grid.y(rows) - grid.y(0));
    }

    private void drawGrid(Graphics2D g2d, Grid grid) {
        g2d.setColor(GRID_COLOR);
        int left = grid.x(0);
        int right = grid.x(cols);
        int top = grid.y(0);
        int bottom = grid.y(rows);
        for (int i = 1; i < cols; i++) {
            g2d.drawLine(grid.x(i), top, grid.x(i), bottom);
        }
        for (int j = 1; j < rows; j++) {
            g2d.drawLine(left, grid.y(j), right, grid.y(j));
        }
    }

    private void drawCells(Graphics2D g2d, Grid grid) {
        Cell[][] cells = fCalc.getGameArray();
        for (int i = 0; i < cols; i++) {
            int x = grid.x(i) + 1;
            int width = Math.max(1, grid.x(i + 1) - x);
            for (int j = 0; j < rows; j++) {
                int y = grid.y(j) + 1;
                Cell cell = cells[i][j];
                Color colour = cell.getLiveStatus() == 1 ? cell.getCellColour() : null;
                g2d.setColor(colour != null ? colour : Color.BLACK);
                g2d.fillRect(x, y, width, Math.max(1, grid.y(j + 1) - y));
            }
        }
    }

    private void drawCursor(Graphics2D g2d, Grid grid) {
        if (!isInGrid(cursorX, cursorY)) {
            return;
        }
        if (eraser) {
            g2d.setColor(Color.RED);
            int half = eraserWidth / 2;
            outline(g2d, grid, cursorX - half, cursorY - half, eraserWidth);
        } else {
            g2d.setColor(Color.WHITE);
            outline(g2d, grid, cursorX, cursorY, 1);
        }
    }

    private void outline(Graphics2D g2d, Grid grid, int col, int row, int span) {
        int x = grid.x(col);
        int y = grid.y(row);
        g2d.drawRect(x, y, grid.x(col + span) - x, grid.y(row + span) - y);
    }

    public void reverseGameArrayValue(int x, int y) {

        fCalc.reverseGameArrayValue(x, y);
    }

    public void calculate() {
        fCalc.calculate();
    }

    public void randomize() {
        fCalc.randomize();
        repaint();
    }

    public void clear() {
        fCalc.clearField();
        repaint();
    }

    public void setRule(int ruleId) {
        fCalc.setRule(ruleId);
    }

    public int cellX(int px) {
        return grid().col(px);
    }

    public int cellY(int py) {
        return grid().row(py);
    }

    public boolean isInGrid(int cellX, int cellY) {
        return cellX >= 0 && cellX < cols && cellY >= 0 && cellY < rows;
    }

    public void updateCursorPosition(int px, int py) {
        cursorX = cellX(px);
        cursorY = cellY(py);
    }

    public boolean isEraser() {
        return eraser;
    }

    public void setEraser(boolean eraser) {
        this.eraser = eraser;
    }
}
