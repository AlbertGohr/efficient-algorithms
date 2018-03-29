package org.agohr.mandelbrot;

class Mapper {

    private final ComplexArea ca;
    private final Screen screen;

    Mapper(final ComplexArea ca, final Screen screen) {
        super();
        this.ca = ca;
        this.screen = screen;
    }

    ComplexNumber mapPixelToComplexNumber(final Pixel p) {
        final double real = this.ca.minRe() + p.x() * this.ca.width() / this.screen.width();
        final double imaginary = this.ca.minIm() + (this.screen.height() - 1 - p.y()) * this.ca.height()
                / this.screen.height();
        return new ComplexNumber(real, imaginary);
    }

}
