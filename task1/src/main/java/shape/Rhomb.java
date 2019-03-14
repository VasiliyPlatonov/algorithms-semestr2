package shape;

import screen.Point;

public class Rhomb extends Rectangle {

    public Rhomb(Point sw, Point ne) {
        super(sw, ne);
    }

    @Override
    public void draw() {
        screen.putLine(south(), west());
        screen.putLine(west(), north());
        screen.putLine(north(), east());
        screen.putLine(east(), south());
    }

}
