package tk.vovanok.gameoflife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import tk.vovanok.gameoflife.core.Cell;
import tk.vovanok.gameoflife.core.FieldCalculator;



public class FieldPanel extends JPanel {

    private Graphics2D g2d;

    private final int delta;
    private final int width;
    private final int height;
    
    int cursorX;
    int cursorY;
    
    private boolean eraser;
    
    int eraserWidth = 9;

    final FieldCalculator fCalc;

    FieldPanel(int x, int y) {
        delta = 10;
        width = x;
        height = y;
        fCalc = new FieldCalculator(x, y);
        setPreferredSize(new Dimension(width * delta + 1, height * delta + 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);

        drawBorder();
        drawGameFrame(fCalc.getGameArray());
        drawGrid();
        drawCursor();

    }
    
    private void drawBorder() {
        g2d.setColor(Color.BLUE);
        g2d.drawRect(0, 0, width * delta, height * delta);

    }

    private void drawGrid() {
        g2d.setColor(new Color(20, 20, 20));
        for (int i = 1; i < width; i++) {
            g2d.drawLine(i * delta, 0, i * delta, delta * height);

        }

        for (int i = 1; i < height; i++) {
            g2d.drawLine(0, i * delta, width * delta, i * delta);

        }
    }

    public void drawGameFrame(Cell[][] game) {
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length; j++) {
                if (game[i][j].getLiveStatus() == 1) {
                    drawCell(i, j);
                } else {
                    freeCell(i, j);
                }
            }
        }
    }

    public void drawCell(int x, int y) {
        g2d.setColor(Color.black);
        g2d.fill(new Rectangle2D.Double(delta * x + 1, delta * y + 1, delta - 1, delta - 1));
        g2d.setColor(fCalc.gameField.getCellInstance(x, y).getCellColour());

        g2d.fill(new RoundRectangle2D.Float(delta * x + 1, delta * y + 1, delta - 1, delta - 1, 0, 0));
    }
    


    public void freeCell(int x, int y) {
        g2d.setColor(Color.black);
        g2d.fill(new Rectangle2D.Double(delta * x + 1, delta * y + 1, delta - 1, delta - 1));

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

    private void drawCursor() {
        Color def = g2d.getColor();
        if (!eraser) {
            g2d.setColor(Color.WHITE);
            g2d.drawRect(cursorX * delta, cursorY * delta, delta, delta);
        } else {
            g2d.setColor(Color.RED);
            int half = eraserWidth / 2;
            g2d.drawRect((cursorX - half) * delta, (cursorY - half) * delta,
                    delta * eraserWidth, delta * eraserWidth);
        }

        g2d.setColor(def);
    }

    public void updateCursorPosition(int x, int y) {
        cursorX = x / delta;
        cursorY = y / delta;
    }

    public boolean isEraser() {
        return eraser;
    }

    public void setEraser(boolean eraser) {
        this.eraser = eraser;
    }
}
