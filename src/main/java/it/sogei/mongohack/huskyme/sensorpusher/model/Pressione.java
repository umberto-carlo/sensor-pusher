package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pressione {
    private static int MIN_PRESSIONE_MIN = 50;
    private static int MIN_PRESSIONE_MAX = 100;

    private static int MAX_PRESSIONE_MIN = 101;
    private static int MAX_PRESSIONE_MAX = 200;

    private static int[] pressioniMinime;

    private static int[] pressioniMassime;

    private static Random rand;

    static {
        rand = new Random();

        pressioniMinime = new int[MIN_PRESSIONE_MAX - MIN_PRESSIONE_MIN - 20];

        for (int i = 0, k = MIN_PRESSIONE_MIN; i < pressioniMinime.length; i++, k++) {
            pressioniMinime[i] = k;
        }

        pressioniMassime = new int[MAX_PRESSIONE_MAX - MAX_PRESSIONE_MIN - 70];

        for (int i = 0, k = MAX_PRESSIONE_MIN + 10; i < pressioniMassime.length; i++, k++) {
            pressioniMassime[i] = k;
        }
    }

    private int min;
    private int max;

    public void generaRandom(Optional<Pressione> old) {
        if (old.isPresent()) {
            int moltiplicatoreMin;
            if (old.get().min < MIN_PRESSIONE_MIN + 10) {
                moltiplicatoreMin = 1;
            } else {
                moltiplicatoreMin = rand.nextBoolean() ? 1 : -1;
            }

            int moltiplicatoreMax;
            if (old.get().max > MIN_PRESSIONE_MAX - 10) {
                moltiplicatoreMax = -1;
            } else {
                moltiplicatoreMax = rand.nextBoolean() ? 1 : -1;
            }

            this.min = old.get().min + (moltiplicatoreMin * rand.nextInt(5));
            this.max = old.get().max + (moltiplicatoreMax * rand.nextInt(5));
        } else {
            this.min = pressioniMinime[rand.nextInt(pressioniMinime.length)];
            this.max = pressioniMassime[rand.nextInt(pressioniMassime.length)];
        }

        if (this.min < MIN_PRESSIONE_MIN) {
            this.min = MIN_PRESSIONE_MIN;
        }

        if (this.min > MIN_PRESSIONE_MAX) {
            this.min = MIN_PRESSIONE_MAX;
        }

        if (this.max < MAX_PRESSIONE_MIN) {
            this.max = MAX_PRESSIONE_MIN;
        }

        if (this.max > MAX_PRESSIONE_MAX) {
            this.max = MAX_PRESSIONE_MAX;
        }

        if (this.min > this.max) {
            int tmp = this.min;
            this.min = this.max;
            this.max = tmp;
        }
    }

    public boolean isAllarmante() {
        return this.max > 150 || this.max < 100 || this.min > 90 || this.min < 51;
    }
}
