package shape;

import screen.CharMatrixScreen;
import screen.Point;
import screen.Screen;
import screen.exception.ExceptionList;
import screen.exception.IllegalInitPointException;
import screen.exception.OutOfScreenException;

public abstract class Shape {

    public static Screen screen;
    private Shape next;
    private static Shape beginning;


    static {
        screen = new CharMatrixScreen();
    }

    // the shape will be linked to the list
    protected Shape() {
        next = beginning;
        beginning = this;
    }

    protected Shape(Point sw, Point ne) {
        try {
            // check the points
            checkInitPoint(sw, ne);
            // if the points are correctly - add new shape to the shape list
            next = beginning;
            beginning = this;
        } catch (IllegalInitPointException ex) {
            // if the points are NOT correctly - add new exceptions to the exception list
            ExceptionList.exceptions.add(ex.getMessage());
        }
    }

    private void checkInitPoint(Point sw, Point ne) throws IllegalInitPointException {
        String commonMsg = String.format(
                "Occurred an exception IllegalInitPointException. The shape [ %s ] cannot be drawn.\n", this.getClass().getSimpleName());

        if (sw.getX() < 0 || sw.getX() > Screen.X_MAX)
            throw new IllegalInitPointException(commonMsg + "The point sw was set incorrectly. sw.x should be more than 0 and less than " + Screen.X_MAX);
        if (sw.getY() < 0 || sw.getY() > Screen.Y_MAX)
            throw new IllegalInitPointException(commonMsg +"The point sw was set incorrectly. sw.y should be more than 0 and less than " + Screen.Y_MAX);
        if (ne.getX() < 0 || ne.getX() > Screen.X_MAX)
            throw new IllegalInitPointException(commonMsg +"The point ne was set incorrectly. ne.x should be more than 0 and less than " + Screen.X_MAX);
        if (ne.getY() < 0 || ne.getY() > Screen.Y_MAX)
            throw new IllegalInitPointException(commonMsg +"The point ne was set incorrectly. ne.y should be more than 0 and less than " + Screen.Y_MAX);
    }

    abstract Point north();

    abstract Point south();

    abstract Point east();

    abstract Point west();

    abstract Point nEast();

    abstract Point sEast();

    abstract Point nWest();

    abstract Point sWest();

    abstract void draw() throws OutOfScreenException;

    abstract void move(int x, int y);

    /**
     * Increase in n times
     */
    abstract void resize(int n);

    public static void refresh() {
        screen.clear();
        for (Shape p = beginning; p != null; p = p.next) {
            try {
                p.draw();
            } catch (OutOfScreenException e) {
                // todo: add log (logback or something like that) and write in files
                ExceptionList.exceptions.add(String.format(
                        "Occurred an exception %s. The shape [ %s ] cannot be drawn",
                        e, p.getClass().getSimpleName()));
            }
        }
        screen.refresh();
        // Display the exceptions if they are
        if (!ExceptionList.exceptions.isEmpty())
            ExceptionList.showAll();
    }

    /**
     * Put over the shape
     */
    public void up(Shape shape) {
        Point n = shape.north();
        Point s = this.south();
        this.move(n.getX() - s.getX(), n.getY() - s.getY() + 1);
    }

    /**
     * Put under the shape
     */
    public void down(Shape shape) {
        Point n = shape.south();
        Point s = this.north();
        this.move(n.getX() - s.getX(), n.getY() - s.getY() - 1);
    }

    public void putToWest(Shape shape) {
        Point w = shape.east();
        Point e = this.east();
        this.move(w.getX() - e.getX(), w.getY() - e.getY() - 1);
    }

    public void putToEast(Shape shape) {
        Point w = shape.west();
        Point e = this.east();
        this.move(w.getX() - e.getX(), w.getY() - e.getY() - 1);
    }
}

