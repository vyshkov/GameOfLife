package tk.vovanok.gameoflife.core;

import java.util.concurrent.ThreadLocalRandom;

import tk.vovanok.gameoflife.rules.ClassicLife;
import tk.vovanok.gameoflife.rules.Coagulations;
import tk.vovanok.gameoflife.rules.Gnarl;
import tk.vovanok.gameoflife.rules.LifeRule;
import tk.vovanok.gameoflife.rules.Maze;

public class FieldCalculator {

    public static final int DIE = 0;
    public static final int ALIVE = 1;
    public static final int WILL_ALIVE = 2;
    public static final int WILL_DIE = 3;

    public static final int RULE_CLASSIC = 0;
    public static final int RULE_GNARL = 1;
    public static final int RULE_MAZE = 2;
    public static final int RULE_COAGULATIONS = 3;

    public final Field gameField;
    private final ColorCalculator colCalc;
    private LifeRule rule = new ClassicLife();

    public FieldCalculator(int x, int y) {
        gameField = new Field(x, y);
        colCalc = new ColorCalculator(gameField);
    }

    private boolean isOccupied(int x, int y) {
        int status = gameField.getCell(x, y);
        return status == ALIVE || status == WILL_DIE;
    }

    int calcNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx != 0 || dy != 0) && isOccupied(x + dx, y + dy)) {
                    count++;
                }
            }
        }
        return count;
    }

    void estimate() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == ALIVE || gameField.getCell(i, j) == WILL_DIE) {
                    if (rule.willDie(calcNeighbors(i, j))) {
                        gameField.setCell(i, j, WILL_DIE);
                    }
                }

                if (gameField.getCell(i, j) == DIE || gameField.getCell(i, j) == WILL_ALIVE) {
                    if (rule.willAlive(calcNeighbors(i, j))) {
                        gameField.setCell(i, j, WILL_ALIVE);
                    }

                }
            }
        }
    }

    public void calcCols() {
        colCalc.calculateColors();
    }

    public void calculate() {
        estimate();
        decrementColorsLifeTime();
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == WILL_DIE) {
                    gameField.setCell(i, j, DIE);
                    gameField.getCellInstance(i, j).setCellColour(null);
                }

                if (gameField.getCell(i, j) == WILL_ALIVE) {
                    gameField.setCell(i, j, ALIVE);
                }
            }
        }
        colCalc.calculateColors();
    }


    private void decrementColorsLifeTime() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == DIE) {
                    gameField.getCellInstance(i, j).updateTimeColorLive();
                }

            }
        }
    }

    public Cell[][] getGameArray() {
        return gameField.getGameArray();
    }

    public void reverseGameArrayValue(int x, int y) {
        if (gameField.getCell(x, y) == ALIVE) {
            gameField.clearCell(x, y);
        } else {
            gameField.setCell(x, y, ALIVE);
        }
    }

    public void erase(int x, int y, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField.clearCell(x + i - (size / 2), y + j - (size / 2));
            }
        }
    }

    public void randomize() {

        var r = ThreadLocalRandom.current();

        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (r.nextInt(5) == 1) {
                    gameField.setRandCell(i, j);
                }

            }
        }

    }

    public void clearField() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                gameField.clearCell(i, j);

            }
        }

    }

    public void setRule(int ruleId) {
        rule = switch (ruleId) {
            case RULE_CLASSIC -> new ClassicLife();
            case RULE_GNARL -> new Gnarl();
            case RULE_MAZE -> new Maze();
            case RULE_COAGULATIONS -> new Coagulations();
            default -> rule;
        };
    }

    public boolean isAlive(int x, int y) {
        return gameField.getCell(x, y) == ALIVE;
    }
}
