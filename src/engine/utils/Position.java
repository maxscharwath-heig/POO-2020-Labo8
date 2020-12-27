package engine.utils;

public class Position {
    public int x;
    public int y;

    /**
     *
     */
    public Position() {
        this(0, 0);
    }

    /**
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        set(x, y);
    }

    /**
     *
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
