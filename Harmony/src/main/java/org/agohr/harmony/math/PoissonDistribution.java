package org.agohr.harmony.math;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PoissonDistribution {

    /** cumulative distribution function */
    private final List<Double> cdf;

    private final Random rnd;

    private final int lambda;

    public PoissonDistribution(Random rnd, int lambda) {
    	assert lambda > 0;
        this.rnd = rnd;
        this.lambda = lambda;
		cdf = computeCDF();
    }

    /**
     * computes the cumulative distribution function F: [0,n] -> [0,1] of the poisson distribution. <br/>
     * F is monotonous increasing, F(i) = list[i], 0<=i<n. <br/>
     * Since we don't compute F on values above n, we define: F(n) = 1 <br/>
	 * it holds: F(n-1) > 1 - eps.
     * @return list[i] = F(i)
     */
    private List<Double> computeCDF() {
		List<Double> border = new ArrayList<>();
		double eps = 0.01;
        double sum = 0;
        try {
			do {
				sum += poisson(border.size());
				border.add(sum);
			} while (sum <= 1.0 - eps);
		} catch (MathArithmeticException e) {
        	e.printStackTrace();
			int last = border.size() - 1;
			System.out.println("F("+last+") = " + border.get(last));
		}
		return Collections.unmodifiableList(border);
    }

    /**
     * Poisson Distribution: <br/>
     * P_lambda(k) = lambda^k/(k!*e^lambda) <br/>
     * 0 < P(k) < 1
     */
    private double poisson(int k) {
        assert k >= 0;
        return Math.pow(lambda, k) / (ArithmeticUtils.factorial(k) * Math.exp(lambda));
    }

    /** @return a poisson random value. */
    public final int value() {
        /*
         * only efficient for small lambda
         */
        final double d = this.rnd.nextDouble();
        for (int i = 0; i< cdf.size(); ++i) {
            if (d < cdf.get(i)) {
                return i;
            }
        }
        return cdf.size();
    }

}
