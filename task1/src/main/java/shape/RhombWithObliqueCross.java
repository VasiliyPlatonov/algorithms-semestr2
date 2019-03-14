package shape;

import screen.Point;

public class RhombWithObliqueCross extends Rhomb {
    private ObliqueCross cross;

    public RhombWithObliqueCross(Point sw, Point ne) {
        super(sw, ne);
        cross = new ObliqueCross(sw, ne);
    }

    public ObliqueCross getCross() {
        return cross;
    }

    public void setCross(ObliqueCross cross) {
        this.cross = cross;
    }
}
