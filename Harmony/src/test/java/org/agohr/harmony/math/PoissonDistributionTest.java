package org.agohr.harmony.math;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class PoissonDistributionTest {

	// poisson distribution
	private double[] poisson = {
			0.36787944117144233,
			0.36787944117144233,
			0.18393972058572117,
			0.061313240195240384,
			0.015328310048810096
	};

	@Test
	public void testDistribution() {
		// given
		Random rnd = new Random(0);
		// when
		PoissonDistribution poissonDistribution = new PoissonDistribution(rnd, 1);
		List<Integer> values = new ArrayList<>();
		int n = 100000;
		for (int i = 0; i< n; ++i) {
			values.add(poissonDistribution.value());
		}
		// then
		Map<Integer, Long> count = values.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (int i=0; i<=4; ++i) {
			double actual = count.get(i) * 1.0 / n;
			assertTrue(Math.abs(actual - poisson[i]) < 0.01);
		}
	}

	@Test
	public void testLambda() {
		Random rnd = new Random(0);
		new PoissonDistribution(rnd, 11);
	}

}