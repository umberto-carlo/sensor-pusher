package it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.util;

import it.sogei.mongohack.huskyme.sensorpusher.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class MisurazioneRandomBuilder {
    private PosizioneMisurazione lastPosizione;
    private FitBeastMisurazione lastFitBeat;

    private int[] pressioniMinime;

    private int[] pressioniMassime;

    private int[] battiti;


    private Random rand;

    {
        this.rand = new Random();

        int pmMin = 50;
        int pmMax = 100;

        this.pressioniMinime = new int[pmMax - pmMin];

        for (int i = 0, k = pmMin; i < this.pressioniMinime.length; i++, k++) {
            this.pressioniMinime[i] = k;
        }

        int pmaMin = 100;
        int pmaMax = 200;

        this.pressioniMassime = new int[pmaMax - pmaMin];

        for (int i = 0, k = pmaMin; i < this.pressioniMassime.length; i++, k++) {
            pressioniMassime[i] = k;
        }

        this.battiti = new int[70];

        for (int i = 0, k = 50; i < this.battiti.length; i++, k++) {
            this.battiti[i] = k;
        }
    }

    private String getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return LocalDateTime.now().format(formatter);
    }

    public PosizioneMisurazione getRandomPosizione(Animale a) {
        PosizioneMisurazione toRet = new PosizioneMisurazione();


        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(0) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() / 1000)).orElse(12.4963655));
        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(1) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() / 1000)).orElse(41.9027835));

        this.lastPosizione = toRet;

        return toRet;
    }

    public FitBeastMisurazione getFitBeastMisurazione(Animale a) {
        FitBeastMisurazione toRet = new FitBeastMisurazione();


        toRet.setTemperatura(Optional.ofNullable(this.lastFitBeat).map(p -> p.getTemperatura() + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble())).orElse((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() * 40));

        Pressione press = new Pressione();
        toRet.setPressione(press);

        Pressione oldPress = Optional.ofNullable(this.lastFitBeat).map(p -> p.getPressione()).orElse(new Pressione(this.pressioniMinime[this.rand.nextInt(this.pressioniMinime.length)], this.pressioniMassime[this.rand.nextInt(this.pressioniMassime.length)]));
        press.setMin(oldPress.getMin() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(5)));
        press.setMax(oldPress.getMax() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(5)));

        toRet.setBattiti(Optional.ofNullable(this.lastFitBeat).map(p -> p.getBattiti() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(5))).orElse(this.battiti[this.rand.nextInt(this.battiti.length)]));

        this.lastFitBeat = toRet;

        return toRet;
    }
}
