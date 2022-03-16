package it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.util;

import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.FitBeastMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.PosizioneMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.Pressione;

import java.util.Optional;
import java.util.Random;

public class MisurazioneRandomBuilder {
    private PosizioneMisurazione lastPosizione;
    private FitBeastMisurazione lastFitBeat;

    public PosizioneMisurazione getRandomPosizione(Animale a) {
        PosizioneMisurazione toRet = new PosizioneMisurazione();

        Random rand = new Random();

        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(0) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() / 10000)).orElse(12.4963655));
        toRet.getCoordinate().add(Optional.ofNullable(this.lastPosizione).map(p -> p.getCoordinate().get(1) + ((rand.nextBoolean() ? 1 : -1) * rand.nextDouble() / 10000)).orElse(41.9027835));

        this.lastPosizione = toRet;

        return toRet;
    }

    public FitBeastMisurazione getFitBeastMisurazione(Animale a) {
        FitBeastMisurazione toRet = new FitBeastMisurazione();

        toRet.generaRandom(Optional.ofNullable(this.lastFitBeat));

        Pressione press = new Pressione();
        toRet.setPressione(press);

        Optional<Pressione> oldPress = Optional.ofNullable(this.lastFitBeat).map(FitBeastMisurazione::getPressione);
        press.generaRandom(oldPress);

        this.lastFitBeat = toRet;

        return toRet;
    }
}
