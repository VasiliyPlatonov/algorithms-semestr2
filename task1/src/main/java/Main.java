import screen.Point;
import shape.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n\t\t======== Generated ========");

        Rectangle hat = new Rectangle(new Point(0, 0), new Point(18, 7));
        Line brim = new Line(new Point(0, 15), 20);
        MyShape face = new MyShape(new Point(30, 10), new Point(47, 18));
        RhombWithObliqueCross tie = new RhombWithObliqueCross(new Point(90, 15), new Point(80, 20));
        RhombWithObliqueCross star = new RhombWithObliqueCross(new Point(90, 15), new Point(80, 20));
        RhombWithObliqueCross lEar = new RhombWithObliqueCross(new Point(90, 25), new Point(80, 32));
        RhombWithObliqueCross rEar = new RhombWithObliqueCross(new Point(20, 35), new Point(30, 42));
        Shape.refresh();


        System.out.println("\n\t\t======== Prepared ========");
        hat.rotateRight();
        brim.resize(2);
        face.resize(2);
        face.move(2, 11);
        tie.resize(2);
        Shape.refresh();


        System.out.println("\n\t\t======== Assembled ========");
        brim.up(face);
        hat.up(brim);
        lEar.putToWest(face);
        rEar.putToEast(face);
        tie.down(face);
        star.up(brim);
        star.move(0, 2);
        Shape.refresh();
    }
}
