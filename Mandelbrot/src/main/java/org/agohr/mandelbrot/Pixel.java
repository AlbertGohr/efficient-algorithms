package org.agohr.mandelbrot;

public class Pixel {

    private final int x;
    private final int y;

    Pixel(final int x, final int y) {
        super();
        this.x = x;
        this.y = y;
    }

    /** @return the x */
    final int x() {
        return this.x;
    }

    /** @return the y */
    final int y() {
        return this.y;
    }

    @Override
    public final String toString() {
        return "pixel:(" + this.x() + "/" + this.y() + ")";
    }

}
