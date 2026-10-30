package tk.vovanok.gameoflife.core;

public class Field {

    final Cell[][] gamefield;
    final int sizeX;
    final int sizeY;

    Field(int x, int y) {
        gamefield = new Cell[x][y];
        sizeX = x;
        sizeY = y;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gamefield[i][j] = new Cell();
            }
        }
    }

    int getCheckedX(int current) {
        if (current < 0) {
            return sizeX - 1;
        }
        return current >= sizeX ? 0 : current;
    }

    int getCheckedY(int current) {
        if (current < 0) {
            return sizeY - 1;
        }
        return current >= sizeY ? 0 : current;
    }

    int getCell(int x, int y) {
        return getCellInstance(x, y).getLiveStatus();
    }

    void setCell(int x, int y, int value) {
        getCellInstance(x, y).setLiveStatus(value);
    }

    public Cell[][] getGameArray() {
        return gamefield;
    }

    public Cell getCellInstance(int x, int y) {
        return gamefield[getCheckedX(x)][getCheckedY(y)];
    }

    public void reSetCell(int x, int y, Cell c) {
        gamefield[x][y] = new Cell(c);
    }

    public void clearCell(int x, int y) {
        getCellInstance(x, y).reset();
    }

    public void setRandCell(int x, int y) {
        gamefield[x][y].random();
    }
}
