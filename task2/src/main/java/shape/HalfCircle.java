package shape;

import screen.Point;
import screen.exception.OutOfScreenException;

public class HalfCircle extends Rectangle implements Reflectable {


    private boolean reflected = true;

    public boolean isReflected() {
        return reflected;
    }

    public void setReflected(boolean reflected) {
        this.reflected = reflected;
    }

    public HalfCircle(Point sw, Point ne) {
        super(sw, ne);
    }

    public HalfCircle(Point sw, Point ne, boolean reflected) {
        super(sw, ne);
        this.reflected = reflected;
    }

    /**
     * @see "https://ru.wikipedia.org/wiki/Алгоритм_Брезенхэма"
     * @see "https://ru.wikibooks.org/wiki/Реализации_алгоритмов/Алгоритм_Брезенхэма"
     * @see "https://en.wikipedia.org/wiki/Midpoint_circle_algorithm"
     */
    @Override
    public void draw() throws OutOfScreenException {
        int x0 = (sw.getX() + ne.getX()) / 2;
        int y0 = reflected ? sw.getY() : ne.getY();
        int radius = (ne.getX() - sw.getX()) / 2;
        int x = 0;
        int y = radius;
        int delta = 2 - 2 * radius;
        int error = 0;

        // cycle for drawing
        while (y >= 0) {
            if (reflected) {
                screen.putPoint(x0 + x, y0 + ((int) (y * 0.7)));
                screen.putPoint(x0 - x, y0 + ((int) (y * 0.7)));
            } else {
                screen.putPoint(x0 + x, y0 - ((int) (y * 0.7)));
                screen.putPoint(x0 - x, y0 - ((int) (y * 0.7)));
            }

            error = 2 * (delta + y) - 1;

            if (delta < 0 && error <= 0) {
                ++x;
                delta += 2 * x + 1;
                continue;
            }

            error = 2 * (delta - x) - 1;
            if (delta > 0 && error > 0) {
                --y;
                delta += 1 - 2 * y;
                continue;
            }

            ++x;
            delta += 2 * (x - y);
            --y;
        }
    }


    @Override
    public void flipHorizontally() {

    }

    @Override
    public void flipVertically() {
        reflected = !reflected;
    }
}
