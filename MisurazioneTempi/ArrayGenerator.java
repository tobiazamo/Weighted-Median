import java.math.BigDecimal;

public class ArrayGenerator {
	private BigDecimal[] randomArray;
	public ArrayGenerator(int size, double seed) {
		randomArray = new BigDecimal[size];
		RandomArrayGenerator rnd = new RandomArrayGenerator(seed);
		for (int i=0; i<size;i++) {
			float randomValue = (float) (rnd.random());
			randomArray[i] = BigDecimal.valueOf(randomValue);
		}
	}

	public BigDecimal[] getArray(){
		return randomArray;
	}
}
