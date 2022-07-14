import java.math.BigDecimal;

public class Algoritmo {
    /**
     * Calcola la mediana inferiore pesata
     *
     * @param arr the array
     * @return the weighted (lower) median
     */

    public static BigDecimal WeightedMedian(BigDecimal[] arr) {
        final BigDecimal target = (sum(arr, 0, arr.length).multiply(BigDecimal.valueOf(0.5d)));
        if (target.doubleValue() == 0d) {
            return BigDecimal.valueOf(0d);
        } else {
            return findWeightedMedian(arr, target, BigDecimal.valueOf(0d), BigDecimal.valueOf(0d));
        }
    }

    public static BigDecimal findWeightedMedian(BigDecimal[] arr, final BigDecimal target, BigDecimal leftWeight, BigDecimal rightWeight) {

        if (arr.length == 1) {
            return arr[0];
        } else if (arr.length == 2) {
            if (arr[0].compareTo(arr[1]) >= 0) {
                return arr[0];
            } else {
                return arr[1];
            }

        } else {

            int middle = arr.length / 2 + 1;

            // Usa Select per trovare la mediana e partizionare attorno ad essa
            int medianPos = select(arr, 0, arr.length - 1, middle);

            BigDecimal m = arr[medianPos];

            // Calcola i pesi delle due partizioni
            BigDecimal leftSum = sum(arr, 0, medianPos).add(leftWeight);

            BigDecimal rightSum = sum(arr, medianPos + 1, arr.length).add(rightWeight);


            // Se m soddisfa la definizione...
            if (leftSum.compareTo(target) < 0 && rightSum.compareTo(target) <= 0) {

                return m;

            }

            // Altrimenti la ricerca prosegue ricorsivamente nella partizione appopriata
            if (leftSum.compareTo(target) >= 0) {
                BigDecimal[] arrayCopy = new BigDecimal[medianPos + 1];
                for (int i = 0; i < arrayCopy.length; i++) {
                    arrayCopy[i] = arr[i];
                }

                rightWeight = rightSum;

                return findWeightedMedian(arrayCopy, target, leftWeight, rightWeight);
            } else {

                BigDecimal[] arrayCopy = new BigDecimal[arr.length - medianPos];
                for (int i = 0; i < arrayCopy.length; i++) {
                    arrayCopy[i] = arr[(medianPos) + i];
                }

                leftWeight = leftSum;

                return findWeightedMedian(arrayCopy, target, leftWeight, rightWeight);
            }

        }
    }

    /**
     * Selection in worst-case linear time
     *
     * @param arr   array as input
     * @param start the starting index
     * @param end   the ending index
     * @param k     the kth smallest element
     * @return the position of the kth value
     */

    static int select(BigDecimal[] arr, int start, int end, int k) {
        if (k > 0 && k <= end - start + 1) {
            int n = end - start + 1;
            int i;
            BigDecimal[] median = new BigDecimal[(n + 4) / 5];
            for (i = 0; i < n / 5; i++) {
                median[i] = findMedian(arr, start + i * 5, 5);

            }
            if (i * 5 < n) {
                median[i] = findMedian(arr, start + i * 5, n % 5);
                i++;
            }

            // Trova la mediana delle mediane
            BigDecimal medOfMed = (i == 1) ? median[i - 1] : BigDecimal.valueOf(select(median, 0, i - 1, i / 2));

            // Applica partition attorno alla mediana delle mediane
            int indexPos = partition(arr, start, end, medOfMed);

            if (indexPos - start == k - 1) {
                return indexPos;
            }
            if (indexPos - start > k - 1) {
                return select(arr, start, indexPos - 1, k);
            }
            return select(arr, indexPos + 1, end, k - indexPos + start - 1);
        }
        return Integer.MAX_VALUE;
    }


    public static int partition(BigDecimal[] arr, int left, int right, BigDecimal val) {
        int i;
        for (i = left; i < right; i++) {
            if (arr[i].equals(val)) {
                break;
            }
        }
        swap(arr, i, right);
        BigDecimal pivotValue = arr[right];
        int storeIndex = left;
        for (i = left; i <= right; i++) {
            if (arr[i].compareTo(pivotValue) < 0) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, right, storeIndex);
        return storeIndex;
    }


    static BigDecimal findMedian(BigDecimal[] arr, int l, int len) {
        insertionSort(arr, l, l + len);

        return arr[l + len / 2];
    }


    /**
     * Helper methods
     */

    public static void swap(BigDecimal[] arr, int i, int j) {
        if (arr[i] == arr[j])
            return;
        BigDecimal temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void insertionSort(BigDecimal[] input, int l, int len) {
        for (int i = l; i < len; i++) {
            BigDecimal key = input[i];
            int j = i - 1;
            while (j >= l && input[j].compareTo(key) > 0) {
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = key;
        }

    }

    public static BigDecimal sum(BigDecimal[] a, int start, int end) {
        BigDecimal weightSum = BigDecimal.valueOf(0);

        for (int i = start; i < end; i++) {
            weightSum = weightSum.add(a[i]);
        }

        return weightSum;
    }
}