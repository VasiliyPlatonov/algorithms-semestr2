package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

/**
 * <pre>
 * nw----n----ne
 * |           |
 * |           |
 * w     c     e
 * |           |
 * |           |
 * sw----s----se
 * <pre/>
 */
public class Rectangle extends Shape implements Rotatable {
    protected Point sw;
    protected Point ne;


    public Rectangle(Point sw, Point ne) {
        super();
        this.sw = sw;
        this.ne = ne;
    }

    @Override
    Point north() {
        return new Point((sw.getX() + ne.getX()) / 2, ne.getY());
    }

    @Override
    Point south() {
        return new Point((sw.getX() + ne.getX()) / 2, sw.getY());
    }

    @Override
    Point east() {
        return new Point(ne.getX(), (sw.getY() + ne.getY()) / 2);
    }

    @Override
    Point west() {
        return new Point(sw.getX(), (sw.getY() + ne.getY()) / 2);
    }

    @Override
    Point nEast() {
        return ne;
    }

    @Override
    Point sEast() {
        return new Point(ne.getX(), sw.getY());
    }

    @Override
    Point nWest() {
        return new Point(sw.getX(), ne.getY());
    }

    @Override
    Point sWest() {
        return sw;
    }

    @Override
    public void draw() throws OutOfScreenException {
        screen.putLine(nWest(), ne);
        screen.putLine(ne, sEast());
        screen.putLine(sEast(), sw);
        screen.putLine(sw, nWest());
    }

    @Override
    public void move(int x, int y) {
        sw.setX(sw.getX() + x);
        sw.setY(sw.getY() + y);
        ne.setX(ne.getX() + x);
        ne.setY(ne.getY() + y);
    }

    @Override
    public void resize(int n) {
        ne.setX(ne.getX() + ((ne.getX() - sw.getX()) * (n - 1)));
        ne.setY(ne.getY() + ((ne.getY() - sw.getY()) * (n - 1)));
    }

    /**
     * Поворот влево относительно sw
     */
    @Override
    public void rotateLeft() {
        int w = ne.getX() - sw.getX();
        int h = ne.getY() - sw.getY();

        ne.setX(sw.getX() + h * 2);
        ne.setY(sw.getY() + w / 2);
    }

    /**
     * Поворот вправо относительно se
     */
    @Override
    public void rotateRight() {
        int w = ne.getX() - sw.getX();
        int h = ne.getY() - sw.getY();

        sw.setX(ne.getX() - h * 2);
        ne.setY(sw.getY() + w / 2);
    }
}
