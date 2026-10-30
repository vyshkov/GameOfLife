package tk.vovanok.gameoflife.core;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class ColorCalculator {

    // Order matters: when colours tie, the one met first wins.
    private static final int[][] NEIGHBOUR_OFFSETS = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}
    };

    private final Field gameField;

    ColorCalculator(Field gameField) {
        this.gameField = gameField;
    }

    public void calculateColors() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {
                Cell cell = gameField.getCellInstance(i, j);
                if (cell.getLiveStatus() == 0) {
                    cell.setCellColour(null);
                } else if (cell.getLiveStatus() != 1 || cell.getCellColour() == null) {
                    setColor(i, j);
                }
            }
        }
    }

    void setColor(int x, int y) {
        Cell cell = gameField.getCellInstance(x, y);
        if (cell.getCellColour() != null) {
            return;
        }

        var nearby = countColours(x, y, Cell::getCellColour);
        if (!nearby.isEmpty()) {
            cell.setCellColour(mostCommon(nearby));
            return;
        }

        var nearbyBefore = countColours(x, y, Cell::getBeforeColour);
        cell.setCellColour(nearbyBefore.isEmpty() ? Cell.randomColor() : mostCommon(nearbyBefore));
    }

    private Map<Color, Integer> countColours(int x, int y, Function<Cell, Color> colourOf) {
        var counts = new LinkedHashMap<Color, Integer>();
        for (int[] offset : NEIGHBOUR_OFFSETS) {
            Color colour = colourOf.apply(gameField.getCellInstance(x + offset[0], y + offset[1]));
            if (colour != null) {
                counts.merge(colour, 1, Integer::sum);
            }
        }
        return counts;
    }

    private static Color mostCommon(Map<Color, Integer> counts) {
        Color best = null;
        int bestCount = 0;
        for (var entry : counts.entrySet()) {
            if (entry.getValue() > bestCount) {
                best = entry.getKey();
                bestCount = entry.getValue();
            }
        }
        return best;
    }
}
