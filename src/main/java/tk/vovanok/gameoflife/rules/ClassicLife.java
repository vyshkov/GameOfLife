package tk.vovanok.gameoflife.rules;

public final class ClassicLife implements LifeRule {

    @Override
    public boolean willDie(int neighbors) {
        return neighbors < 2 || neighbors > 3;
    }

    @Override
    public boolean willAlive(int neighbors) {
        return neighbors == 3;
    }
}
