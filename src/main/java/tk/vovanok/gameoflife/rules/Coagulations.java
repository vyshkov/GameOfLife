package tk.vovanok.gameoflife.rules;

public final class Coagulations implements LifeRule {

    @Override
    public boolean willDie(int neighbors) {
        return switch (neighbors) {
            case 2, 3, 5, 6, 7, 8 -> false;
            default -> true;
        };
    }

    @Override
    public boolean willAlive(int neighbors) {
        return switch (neighbors) {
            case 3, 7, 8 -> true;
            default -> false;
        };
    }
}
