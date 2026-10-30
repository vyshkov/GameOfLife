package tk.vovanok.gameoflife.rules;

public interface LifeRule {
    boolean willDie(int neighbors);

    boolean willAlive(int neighbors);
}

