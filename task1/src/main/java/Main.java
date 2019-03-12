import screen.CharMatrixScreen;
import screen.Point;
import shape.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n\t\t======== Generated ========");

        Rectangle hat = new Rectangle(new Point(0, 0), new Point(18, 7));
        Line brim = new Line(new Point(0, 15), 20);
        MyShape face = new MyShape(new Point(30, 10), new Point(47, 18));
        HalfCircle beard = new HalfCircle(new Point(40, 10), new Point(50, 20));
        Shape.refresh();


        System.out.println("\n\t\t======== Prepared ========");
        hat.rotateRight();
        brim.resize(2);
        face.resize(2);
        beard.flipVertically();
        beard.resize(2);

        Shape.refresh();


        System.out.println("\n\t\t======== Assembled ========");
        brim.up(face);
        hat.up(brim);
        beard.down(face);
        Shape.refresh();
    }
}
