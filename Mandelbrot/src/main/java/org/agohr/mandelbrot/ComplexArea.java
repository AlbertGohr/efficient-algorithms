package org.agohr.mandelbrot;

/** defines an rectangular area in the complex plan. */
public class ComplexArea {

    /** minimum real number. */
    private final double minRe;

    /** minimum imaginary number. */
    private final double minIm;

    /** width = |[min real, max real]|. */
    private final double width;

    /** height = |[min imaginary, max imaginary]|. */
    private final double height;

    /** Constructor, based on center point (cRe, cIm) and width.
     * height results from default ratio 16:9.
     * @param cRe real part of center point
     * @param cIm imaginary part of center point
     * @param width_ width, greater zero */
    ComplexArea(final double cRe, final double cIm, final double width_) {
        assert width_ > 0;

        this.width = width_;
        this.height = width_ * 9.0 / 16.0;
        this.minRe = cRe - this.width / 2.0;
        this.minIm = cIm - this.height / 2.0;
    }

    /** @return minimum real number. */
    final double minRe() {
        return this.minRe;
    }

    /** @return maximum real number. */
    final double maxRe() {
        return this.minRe + this.width;
    }

    /** @return minimum complex number. */
    final double minIm() {
        return this.minIm;
    }

    /** @return maximum complex number. */
    final double maxIm() {
        return this.minIm + this.height;
    }

    /** @return width = |[min real, max real]|. */
    final double width() {
        return this.width;
    }

    /** @return height = |[min imaginary, max imaginary]|. */
    final double height() {
        return this.height;
    }

    @Override
    public final String toString() {
        return "org.agohr.mandelbrot.ComplexArea: ( Re( " + this.minRe() + " / " + this.maxRe() + " ) , Im(  " + this.minIm + " / "
                + this.maxIm() + " ) )";
    }

}
