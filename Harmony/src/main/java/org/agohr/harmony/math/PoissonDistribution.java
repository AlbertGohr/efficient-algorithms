package org.agohr.harmony.math;

import java.util.Random;

public class PoissonDistribution {

    private static final double eInverse = Math.exp(-1.0);

    /** cumulative distribution function */
    private static final double[] cdf = computeCDF(10);

    private final Random rnd;

    public PoissonDistribution(Random rnd) {
        this.rnd = rnd;
    }

    /**
     * computes the cumulative distribution function F: [0,n] -> [0,1] of the poisson distribution. <br/>
     * F is monotonous increasing, F(i) = array[i], 0<=i<n. <br/>
     * Since we don't compute F on values above n, we define: F(n) = 1
     * @param n length of the result array.
     * @return array[i] = F(i)
     */
    private static double[] computeCDF(int n) {
        double[] border = new double[n];
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
     * P(k) = 1/(k!*e) <br/>
     * 0 < P(k) < 1
     */
    private static double poisson(int i) {
        assert i >= 0;
        return eInverse / fak(i);
    }

    /**
     * simple factorial algorithm for small values
     */
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
        /*
         * since the first values of the poisson distribution have the highest probability, this is already an efficient solution.
         */
        final double d = this.rnd.nextDouble();
        for (int i = 0; i< cdf.length; ++i) {
            if (d < cdf[i]) {
                return i;
            }
        }
        return cdf.length;
    }

}
