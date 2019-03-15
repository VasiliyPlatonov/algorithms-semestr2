package screen;

import screen.exception.OutOfScreenException;

public class CharMatrixScreen extends Screen {
    private char[][] screenArea = new char[Y_MAX][X_MAX];

    public char[][] getScreenArea() {
        return screenArea;
    }

    @Override
    public void putPoint(int x, int y) throws OutOfScreenException {
        if (isItScreen(x, y))
            screenArea[y][x] = Color.BLACK.getSign();
        else throw new OutOfScreenException();
    }

    /**
     * Bresenham's line algorithm
     *
     * @see "https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm"
     */
    @Override
    public void putLine(int x0, int y0, int x1, int y1) throws OutOfScreenException {
        int dx = 1;
        int a = x1 - x0;
        if (a < 0) {
            dx = -1;
            a = -a;
        }

        int dy = 1;
        int b = y1 - y0;
        if (b < 0) {
            dy = -1;
            b = -b;
        }

        int twoA = 2 * a;
        int twoB = 2 * b;
        int xCrit = -b + twoA;
        int eps = 0;

        // Line drawing on points 
        // TODO: 16.02.2019 It necessary to check that point is put in screenArea
        while (true) {
            putPoint(x0, y0);

            if (x0 == x1 && y0 == y1)
                break;

            if (eps <= xCrit) {
                x0 += dx;
                eps += twoB;
            }

            if (eps >= a || a < b) {
                y0 += dy;
                eps -= twoA;
            }

        }

    }

    @Override
    public void init() {
        for (int y = Y_MAX - 1; y >= 0; y--) {
            for (int x = X_MAX - 1; x >= 0; x--) {
                screenArea[y][x] = Color.WHITE.getSign();
            }
        }
    }

    @Override
    public void destroy() {
        for (int y = Y_MAX - 1; y >= 0; y--) {
            for (int x = X_MAX - 1; x >= 0; x--) {
                screenArea[y][x] = Color.BLACK.getSign();
            }
        }
    }

    @Override
    public void refresh() {
        for (int y = Y_MAX - 1; y >= 0; y--) {
            for (int x = X_MAX - 1; x >= 0; x--) {
                System.out.print(screenArea[y][x]);
            }
            System.out.println();
        }
    }

    @Override
    public void clear() {
        this.init();
    }

    private boolean isItScreen(int x, int y) {
        return (x >= 0 && x <= X_MAX) && (y >= 0 && y <= Y_MAX);
    }

    enum Color {
        BLACK('*'), WHITE('.');
        private char sign;

        Color(char sign) {
            this.sign = sign;
        }

        public char getSign() {
            return sign;
        }
    }
}
