import java.math.BigDecimal;

public class TempoEsecuzione {
	private RandomArrayGenerator mRnd = new RandomArrayGenerator(937162211 );

	public TempoEsecuzione(int arraySize) {
		final float za = 1.96f;
		final float delta = 0.05f;
		final int c = 10;

		BigDecimal[] newData = prepara(arraySize);
		long g = granularita();
		double tMin = (g / 0.05); //errore pari a 0.05
		double[] valori = misurazione(newData, arraySize, c, za, tMin, delta);

		System.out.println(arraySize + ", " + valori[0] + ", " + valori[1]);

	}

	/**
	 * Metodi prepara ed execute per calcolo ripetizioni
	 */

	public BigDecimal[] prepara(int size) {
		System.gc();
		return new ArrayGenerator(size, mRnd.random() * 10000).getArray();
	}

	private void execute (BigDecimal[] array) {
		Algoritmo.WeightedMedian(array);
	}


	/**
	 * Algoritmi implementati dagli appunti
	 */

	public long granularita() {
		long t0 = System.currentTimeMillis();
		long t1 = System.currentTimeMillis();

		while (t1 == t0) {
			t1 = System.currentTimeMillis();
		}
		return (t1 - t0);
	}


	public long calcolaRipTara (BigDecimal[] e, int size, double tMin) {
		long t0 = 0;
		long t1 = 0;
		int rip = 1;

		while (t1 - t0 <= tMin) {
			rip = rip * 2; //stime di rip con crescita esponenziale
			t0 = System.currentTimeMillis();

			for (int i = 0; i < rip; i++) {
				prepara(size);
			}

			t1 = System.currentTimeMillis();
		}

		// ricerca esatta del numero di ripetizioni per bisezione
		// approssimiamo a 5 cicli

		int max = rip;
		float min = (rip / 2.0f);
		int cicliErrati = 5;

		while (max - min >= cicliErrati) {
			rip = (int) ((max + min) / 2);
			t0 = System.currentTimeMillis();

			for (int i = 0; i < rip; i++) {
				prepara(size);
			}

			t1 = System.currentTimeMillis();
			if (t1 - t0 <= tMin) {
				min = rip;
			}
			else {
				max = rip;
			}
		}
		return max;
	}

	public long calcolaRipLordo (BigDecimal[] e, int size, double tMin) {
		long t0 = 0;
		long t1 = 0;
		int rip = 1;

		while (t1 - t0 <= tMin) {
			rip = rip * 2;
			t0 = System.currentTimeMillis();

			for (int i = 0; i < rip; i++) {
				e = prepara(size);
				execute(e);
			}
			t1 = System.currentTimeMillis();
		}

		int max = rip;
		int min = rip / 2;
		int cicliErrati = 5;

		while (max - min >= cicliErrati) {
			rip = (max + min) / 2;
			t0 = System.currentTimeMillis();

			for (int i = 0; i < rip; i++) {
				e = prepara(size);
				execute(e);
			}
			t1 = System.currentTimeMillis();

			if (t1 - t0 <= tMin) {
				min = rip;
			}
			else {
				max = rip;
			}
		}
		return max;
	}

	public double tempoMedioNetto (BigDecimal[] e, int d, double tMin) {
		long ripTara = calcolaRipTara(e, d, tMin);
		long ripLordo = calcolaRipLordo(e, d, tMin);
		long t0 = System.currentTimeMillis();

		for (int i = 1; i < ripTara; i++) {
			prepara(d);
		}

		long t1 = System.currentTimeMillis();
		long tTara = t1 - t0; //tempo totale di esecuzione della tara
		t0 = System.currentTimeMillis();

		for (int i = 0; i < ripLordo; i++) {
			e = prepara(d);
			execute(e);
		}

		t1 = System.currentTimeMillis();
		long tLordo = t1 - t0; //tempo totale di esecuzione del lordo

		// tempo medio di esecuzione
		double tMedio = (((tLordo) / (ripLordo) ) - ( (tTara) / ripTara));

		return tMedio;
	}



	public double[] misurazione (BigDecimal[] data, int d, int c, float za, double tMin, float delta) {
		double t = 0.0;
		double sum2 = 0.0;
		int cn = 0;
		double delta1;
		double e;

		do {
			for (int i = 0; i < c; i++) {
				double m = tempoMedioNetto(data, d, tMin);
				t = t + m;
				sum2 = sum2 + (m * m);
			}

			cn = cn + c;
			e =  (t / (double) cn);

			double e2 = Math.pow(e, 2);

			double s = Math.sqrt((sum2 /cn) - e2);

			delta1 =  (1/Math.sqrt(cn)) * za * s;

		} while (delta1 < delta);

		return new double[] { e, delta1 };
	}
}
