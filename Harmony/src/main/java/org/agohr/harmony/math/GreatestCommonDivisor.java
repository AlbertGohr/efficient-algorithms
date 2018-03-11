package org.agohr.harmony.math;

public class GreatestCommonDivisor {

	public int compute(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		int min = Math.min(x, y);
		int ggT = 1;
		for (int i=2; i<=min; ++i) {
			if (x % i == 0 && y % i == 0) {
				ggT = i;
			}
		}
		return ggT;
	}

}
