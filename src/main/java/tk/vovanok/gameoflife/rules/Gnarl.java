package tk.vovanok.gameoflife.rules;

public final class Gnarl implements LifeRule {

    @Override
    public boolean willDie(int neighbors) {
        return neighbors != 1;
    }

    @Override
    public boolean willAlive(int neighbors) {
        return neighbors == 1;
    }
}
