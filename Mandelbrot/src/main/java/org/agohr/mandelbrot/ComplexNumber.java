package org.agohr.mandelbrot;

public class ComplexNumber {

    private final double real;
    private final double imaginary;

    ComplexNumber(final double real, final double imaginary) {
        super();
        this.real = real;
        this.imaginary = imaginary;
    }

    /** @return the real */
    double real() {
        return this.real;
    }

    final double imaginary() {
        return this.imaginary;
    }

    @Override
    public final String toString() {
        return "(" + this.real() + "+" + this.imaginary() + "*i)";
    }

}
