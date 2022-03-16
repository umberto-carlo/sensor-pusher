package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.Random;

@Setter
@Getter
public class FitBeastMisurazione {
    private static final int MIN_BATTITI = 40;

    private static final int MAX_BATTITI = 100;

    private static int[] arrayBattiti;

    private static Random rand;

    static {
        rand = new Random();

        arrayBattiti = new int[MAX_BATTITI - MIN_BATTITI - 20];

        for (int i = 0, k = MIN_BATTITI + 20; i < arrayBattiti.length; i++, k++) {
            arrayBattiti[i] = k;
        }
    }

    private double temperatura;
    private Pressione pressione;
    private int battiti;

    public void generaRandom(Optional<FitBeastMisurazione> old) {
        if (old.isPresent()) {
            this.battiti = old.get().battiti + ((rand.nextBoolean() ? 1 : -1) * rand.nextInt(3));

            this.temperatura = old.get().temperatura + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble());
        } else {
            this.battiti = arrayBattiti[rand.nextInt(arrayBattiti.length)];

            this.temperatura = 35 + 10 * rand.nextDouble();
        }

        if (this.battiti < MIN_BATTITI) {
            this.battiti = MIN_BATTITI;
        }

        if (this.battiti > MAX_BATTITI) {
            this.battiti = MAX_BATTITI;
        }

        if (this.temperatura < 35) {
            this.temperatura = 35;
        }

        if (this.temperatura > 43) {
            this.temperatura = 43;
        }
    }

    public boolean isAllarmante() {
        return this.pressione.isAllarmante() || this.battiti < 30 || this.battiti > 90 || this.temperatura < 30 || this.temperatura > 43;
    }
}
