package org.agohr.harmony.math;

import java.util.Random;

public class PoissonDistribution {

    private final Random rnd;

    public PoissonDistribution(Random rnd) {
        this.rnd = rnd;
    }

    /** @return a poisson(1) random value. */
    public final int value() {
        final double d = this.rnd.nextDouble();
        if (d < 0.37) {
            return 0;
        } else if (d < 0.74) {
            return 1;
        } else if (d < 0.92) {
            return 2;
        } else if (d < 0.98) {
            return 3;
        } else {
            return 4;
        }
    }

}
