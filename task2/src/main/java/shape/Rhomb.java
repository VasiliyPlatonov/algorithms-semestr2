package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

public class Rhomb extends Rectangle {

    public Rhomb(Point sw, Point ne) {
        super(sw, ne);
    }

    @Override
    public void draw() throws OutOfScreenException {
        screen.putLine(south(), west());
        screen.putLine(west(), north());
        screen.putLine(north(), east());
        screen.putLine(east(), south());
    }

}
