package it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.util;

import it.sogei.mongohack.huskyme.sensorpusher.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class MisurazioneRandomBuilder {
    private PosizioneMisurazione lastPosizione;
    private FitBeastMisurazione lastFitBeat;

    private Random rand;

    {
        this.rand = new Random();
    }

    private  String getTimeStamp()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return LocalDateTime.now().format(formatter);
    }

    public PosizioneMisurazione getRandomPosizione(Animale a) {
        PosizioneMisurazione toRet = new PosizioneMisurazione();
        toRet.setIdAnimale(a.getIdAnimale());

        toRet.setTimeStamp(this.getTimeStamp());

        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(0) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble())).orElse(rand.nextBoolean() ? 1 : -1 * rand.nextDouble() * 100));
        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(1) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble())).orElse(rand.nextBoolean() ? 1 : -1 * rand.nextDouble() * 100));

        this.lastPosizione = toRet;

        return toRet;
    }

    public FitBeastMisurazione getFitBeastMisurazione(Animale a) {
        FitBeastMisurazione toRet = new FitBeastMisurazione();
        toRet.setIdAnimale(a.getIdAnimale());

        toRet.setTimeStamp(this.getTimeStamp());

        toRet.setTemperatura(Optional.ofNullable(this.lastFitBeat).map(p -> p.getTemperatura() + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble())).orElse((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() * 40));

        Pressione press = new Pressione();
        toRet.setPressione(press);

        Pressione oldPress = Optional.ofNullable(this.lastFitBeat).map(p -> p.getPressione()).orElse(new Pressione(this.rand.nextInt(90), this.rand.nextInt(200)));
        press.setMin(oldPress.getMin() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(10)));
        press.setMax(oldPress.getMax() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(10)));

        toRet.setBattiti(Optional.ofNullable(this.lastFitBeat).map(p -> p.getBattiti() + ((this.rand.nextBoolean() ? 1 : -1) * this.rand.nextInt(5))).orElse(this.rand.nextInt(120)));

        this.lastFitBeat = toRet;

        return toRet;
    }
}
