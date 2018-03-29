package org.agohr.mandelbrot;

import java.awt.Color;

/** computes an image of the mandelbrot set. */
public class Main {

    // TODO interactive mode
    // TODO histogram class

    /** main method.
     * @param args unused */
    public static void main(final String[] args) {
        final Main main = new Main();
        main.run();
    }

    private final Screen screen = new Screen(1920);

    /** if exceeded divergence is assumed. */
    private final int maxNumIterations = 1000;
    /** if exceeded convergence is assumed. */
    private final double bailout = 2 * 2;
    /** count the number of occurrences of number of iterations before a pixel escapes. */
    private final int[] histogram = new int[this.maxNumIterations + 1]; // init with zero.
    /** hue values based on integral over the histogram. */
    private final float[] hues = new float[this.maxNumIterations + 1];
    /** number of iterations before a pixel escapes. */
    private final double[][] iterations = new double[this.screen.width()][this.screen.height()];

    //private final org.agohr.mandelbrot.ComplexArea ca = new org.agohr.mandelbrot.ComplexArea(-0.015, -0.815, 0.07);
    private final ComplexArea ca = new ComplexArea(-0.6, 0, 32.0 / 9.0);

    private final Mapper mapper = new Mapper(this.ca, this.screen);

    private final ColorConfig cc = new ColorConfig();

    /** compute and display the image. */
    private void run() {
        System.out.println(this.ca);
        // compute convergence for each pixel, update histogram.
        for (int x = 0; x < this.screen.width(); ++x) {
            for (int y = 0; y < this.screen.height(); ++y) {
                final Pixel p = new Pixel(x, y);
                final ComplexNumber c = this.mapper.mapPixelToComplexNumber(p);
                final double i = this.converges(c);
                if (i < this.maxNumIterations) {
                    final int intI = (int) i;
                    ++this.histogram[intI];
                    ++this.histogram[intI + 1];
                }
                this.iterations[x][y] = i;
            }
        }

        // compute hues based on histogram
        int total = 0;
        for (int i = 0; i < this.maxNumIterations + 1; ++i) {
            total += this.histogram[i];
        }
        int integral = 0;
        for (int i = 0; i < this.maxNumIterations + 1; ++i) {
            integral = integral + this.histogram[i];
            this.hues[i] = integral * this.cc.hueRange() / total + this.cc.hueOffset();
        }

        // compute color of each pixel

        for (int x = 0; x < this.screen.width(); ++x) {
            for (int y = 0; y < this.screen.height(); ++y) {
                final double iteration = this.iterations[x][y];
                int color;
                if (iteration >= this.maxNumIterations) {
                    color = 0; // black
                } else {
                    final int intIteration = (int) iteration;
                    final float hue = this.linearInterpolate(this.hues[intIteration], this.hues[intIteration + 1],
                            (float) iteration - intIteration);
                    color = Color.HSBtoRGB(hue, this.cc.saturation(), this.cc.brightness());
                }
                this.screen.image().setRGB(x, y, color);
            }
        }

        this.screen.displayImage();
    }

    private float linearInterpolate(final float min, final float max, final float i) {
        return (max - min) * i + min;
    }

    /** Let z0 = x0+y0*i.
     * Let z = x+y*i, with x,y=0 initial.
     * Does z = z^2 + z0 converge?
     * Approximation: iteration diverges iff ||z||^2 exceeds bailout after maxNumIterations (or earlier).
     * @return number of iterations until convergence or maxNumIterations */
    private double converges(final ComplexNumber c) {

        final double x0 = c.real();
        final double y0 = c.imaginary();
        int i = 0;
        double x = 0;
        double y = 0;
        double x2 = 0;
        double y2 = 0;
        double tmp;
        while (x2 + y2 < this.bailout && i++ < this.maxNumIterations) {
            tmp = x2 - y2 + x0;
            y = 2 * x * y + y0;
            x = tmp;
            x2 = x * x;
            y2 = y * y;
        }
        if (i >= this.maxNumIterations) {
            return Double.POSITIVE_INFINITY;
        }
        return i + 1 - Math.log(Math.log(Math.sqrt(x2 + y2)) / Math.log(2)) / Math.log(2);
    }

}
