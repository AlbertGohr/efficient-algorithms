package org.agohr.harmony.math;

import java.util.Random;

public class PoissonDistribution {

    // cumulative distribution function
    private static double[] cdf = computeCDF(6);

    private final Random rnd;

    public PoissonDistribution(Random rnd) {
        this.rnd = rnd;
    }

    private static double[] computeCDF(int length) {
        double[] border = new double[length];
        double sum = 0;
        for (int i=0; i<border.length; ++i) {
            sum += poisson(i);
            border[i] = sum;
        }
        return border;
    }

    /**
     * Poisson Distribution: <br/>
     * P_lambda(k) = lambda^k/(k!*e^lambda) <br/>
     * here lambda = 1, thus <br/>
     * P(k) = 1/(k!*e)
     */
    private static double poisson(int i) {
        assert i >= 0;
        return 1.0 / (Math.exp(1) * fak(i));
    }

    private static int fak(int n) {
        assert n >= 0;
        int fak = 1;
        for (int i=2; i<=n; ++i) {
            fak *= i;
        }
        return fak;
    }

    /** @return a poisson(lambda=1) random value. */
    public final int value() {
        final double d = this.rnd.nextDouble();
        for (int i = 0; i< cdf.length; ++i) {
            if (d < cdf[i]) {
                return i;
            }
        }
        return cdf.length;
    }

}
