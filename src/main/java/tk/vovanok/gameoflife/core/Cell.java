package tk.vovanok.gameoflife.core;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {

    private int liveStatus;
    private Color cellColour;
    private Color beforeColour;
    private int colorLive;

    public Cell() {
    }

    public Cell(Cell c) {
        this.liveStatus = c.getLiveStatus();
        this.cellColour = c.getCellColour();
    }

    static Color randomColor() {
        var rnd = ThreadLocalRandom.current();
        return new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Color getCellColour() {
        if (liveStatus == 0) {
            return null;
        }
        return cellColour;
    }

    void setCellColour(Color cellColour) {
        if (this.cellColour != null) {
            this.beforeColour = this.cellColour;
        }
        this.cellColour = cellColour;
    }

    public Color getBeforeColour() {
        return beforeColour;
    }

    public void setBeforeColour(Color beforeColour) {
        this.beforeColour = beforeColour;
    }

    public void reset() {
        this.liveStatus = 0;
        this.cellColour = null;
        this.beforeColour = null;
    }

    public void random() {
        this.liveStatus = 1;
        this.cellColour = randomColor();
    }

    void updateTimeColorLive() {
        if (colorLive < 5) {
            colorLive++;
        } else {
            this.beforeColour = null;
            colorLive = 0;
        }
    }

    public int getColorLive() {
        return colorLive;
    }

    public void setColorLive(int colorLive) {
        this.colorLive = colorLive;
    }
}
