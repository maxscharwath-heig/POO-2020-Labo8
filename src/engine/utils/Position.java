package engine.utils;

/**
 * classe qui représente des position
 */
public class Position {
    public int x;
    public int y;

    /**
     * Constructeur par défaut
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Constructeur avec paramètres
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        set(x, y);
    }

    /**
     * Setter et getter
     * @param x
     * @param y
     * @return
     */
    public Position set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
