package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

public class MyShape extends Rectangle {

    private int w;
    private int h;
    private Line leftEye;
    private Line rightEye;
    private Line mouth;


    public MyShape(Point a, Point b) {
        super(a, b);
        this.w = nEast().getX() - sWest().getX() + 1;
        this.h = nEast().getY() - sWest().getY() + 1;
        leftEye =
                new Line(
                        new Point(
                                sWest().getX() + 2,
                                sWest().getY() + h * 3 / 4),
                        3);

        rightEye =
                new Line(
                        new Point(
                                sWest().getX() + w - 4,
                                sWest().getY() + h * 3 / 4),
                        3);


        mouth =
                new Line(
                        new Point(
                                sWest().getX() + 2,
                                sWest().getY() + h / 4),
                        w - 2
                );
    }

    @Override
    public void draw() throws OutOfScreenException {
        super.draw();
        int a = (sWest().getX() + nEast().getX()) / 2;
        int b = (sWest().getY() + nEast().getY()) / 2;
        screen.putPoint(new Point(a, b));
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        leftEye.move(x, y);
        rightEye.move(x, y);
        mouth.move(x, y);
    }

    @Override
    public void resize(int n) {
        super.resize(n);
        this.w = nEast().getX() - sWest().getX() + 1;
        this.h = nEast().getY() - sWest().getY() + 1;

        leftEye.move(
                ((sWest().getX() + 6)) - leftEye.getW().getX(),
                ((sWest().getY() + h * 3 / 4)) - leftEye.getW().getY());

        rightEye.move(
                ((sWest().getX() + w - 5)) - rightEye.getW().getX(),
                ((sWest().getY() + h * 3 / 4)) - rightEye.getW().getY());

        mouth.move(mouth.getW().getX() - (sWest().getX() + 10),
                (sWest().getY() + h / 4) - mouth.getW().getY());
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Line getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(Line leftEye) {
        this.leftEye = leftEye;
    }

    public Line getRightEye() {
        return rightEye;
    }

    public void setRightEye(Line rightEye) {
        this.rightEye = rightEye;
    }

    public Line getMouth() {
        return mouth;
    }

    public void setMouth(Line mouth) {
        this.mouth = mouth;
    }
}
