package shape;

import screen.CharMatrixScreen;
import screen.Point;
import screen.Screen;
import screen.exception.ExceptionList;
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

