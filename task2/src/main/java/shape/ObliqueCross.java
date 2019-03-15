package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

public class ObliqueCross extends Rectangle {

    public ObliqueCross(Point sw, Point ne) {
        super(sw, ne);
    }


    @Override
    public void draw() throws OutOfScreenException {
        screen.putLine(sw, ne);
        screen.putLine(sEast(), nWest());
    }

}
