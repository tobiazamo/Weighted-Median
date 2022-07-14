public class RandomArrayGenerator {

	private double seed;

	public RandomArrayGenerator(double s) {
		seed = s;
	}

	public double random() {

		final int a = 16087;
		final int m = 2147483647;
		final int q = 127773;
		final int r = 2836;

		double lo, hi, test;

		hi = Math.ceil(seed / q);
		lo = seed - q * hi;
		test = a * lo - r * hi;
		
		if (test < 0.0) {
			seed = test + m;
		} else {
			seed = test;
		}
		return seed / m;
	}


}
