package tk.vovanok.gameoflife.rules;

public final class Maze implements LifeRule {

    @Override
    public boolean willDie(int neighbors) {
        return neighbors < 1 || neighbors > 5;
    }

    @Override
    public boolean willAlive(int neighbors) {
        return neighbors == 3;
    }
}
