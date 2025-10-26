package anniversaryparadox;

public class BirthdayParadoxService {

    private final int daysInYear;

    public BirthdayParadoxService(int daysInYear) {
        this.daysInYear = daysInYear;
    }

    /** Probabilidade de NÃO haver coincidência (0..1) para 'people' pessoas. */
    public double probabilityNoMatch(int people) {
        double p = 1.0;
        for (int i = 0; i < people; i++) {
            p *= (double) (daysInYear - i) / daysInYear;
        }
        return p;
    }

    /** Probabilidade de haver pelo menos uma coincidência (0..1). */
    public double probabilityMatch(int people) {
        return 1.0 - probabilityNoMatch(people);
    }

    /** Menor número de pessoas para atingir pelo menos 'target' (ex.: 0.5 = 50%). */
    public int minPeopleFor(double target) {
        int people = 0;
        while (probabilityMatch(people) < target) {
            people++;
        }
        return people;
    }

    /** Conveniência: cria um objeto de resultado pronto para imprimir. */
    public ProbabilityResult resultFor(int people) {
        return new ProbabilityResult(people, probabilityMatch(people));
    }

}
