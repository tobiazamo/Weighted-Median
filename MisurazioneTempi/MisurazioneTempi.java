public class MisurazioneTempi {
    public static void main(String[] args) {
        int j = 10;
        for (int i = 10; i <= 20000; i += j) {
            new TempoEsecuzione(i);
            if (i >= 1000) {
                j = 1000;
            } else if (i >= 100) {
                j = 100;
            }
        }
        new TempoEsecuzione(j);
    }
}
