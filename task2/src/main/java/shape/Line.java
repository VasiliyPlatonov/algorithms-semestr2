package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

public class Line extends Shape {
    private Point w;
    private Point e;


    public Line(Point w, Point e) {
        super();
        this.w = w;
        this.e = e;
    }

    public Line(Point w, int L) {
        super();
        this.w = new Point(w.getX() + L - 1, w.getY());
        this.e = w;

    }

    public Line() {
    }

    public Point getW() {
        return w;
    }

    public void setW(Point w) {
        this.w = w;
    }

    public Point getE() {
        return e;
    }

    public void setE(Point e) {
        this.e = e;
    }

    @Override
    Point north() {
        return new Point(((w.getX() + e.getX()) / 2), getGreater(e.getY(), w.getY()));
    }

    @Override
    Point south() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point east() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point west() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point nEast() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point sEast() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point nWest() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    Point sWest() {
        return new Point(((w.getX() + e.getX()) / 2), getSmaller(e.getY(), w.getY()));
    }

    @Override
    public void draw () throws OutOfScreenException {
        screen.putLine(w, e);
    }

    @Override
    public void move(int x, int y) {
        w.setX(w.getX() + x);
        w.setY(w.getY() + y);
        e.setX(e.getX() + x);
        e.setY(e.getY() + y);
    }

    @Override
    public void resize(int n) {
        e.setX(e.getX() + ((e.getX() - w.getX()) * (n - 1)));
        e.setY(e.getY() + ((e.getY() - w.getY()) * (n - 1)));
    }

    private int getSmaller(int a, int b) {
        return a < b ? a : b;
    }

    private int getGreater(int a, int b) {
        return a > b ? a : b;
    }
}
