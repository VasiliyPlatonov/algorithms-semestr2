package screen;

abstract public class Screen {
    // size of screen
    protected static final int X_MAX = 100;
    protected static final int Y_MAX = 50;

    /**
     * Display a point
     */
    abstract public void putPoint(int x, int y);

    /**
     * Display a point
     */
    public void putPoint(Point p) {
        putPoint(p.getX(), p.getY());
    }

    /**
     * Display a line
     */
    abstract void putLine(int x0, int y0, int x1, int y1);

    /**
     * Display a line
     */
    public void putLine(Point a, Point b) {
        putLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Creating a screen
     */
    public abstract void init();

    /**
     * Destroying a screen
     */
    public abstract void destroy();

    /**
     * Refreshing a screen
     */
    public abstract void refresh();

    /**
     * Clearing a screen
     */
    public abstract void clear();

}
