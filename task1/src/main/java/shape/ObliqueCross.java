package shape;

import screen.Point;

public class ObliqueCross extends Rectangle {

    public ObliqueCross(Point sw, Point ne) {
        super(sw, ne);
    }


    @Override
    public void draw() {
        screen.putLine(sw, ne);
        screen.putLine(sEast(), nWest());
    }

}
