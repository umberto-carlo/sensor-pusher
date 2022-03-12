package it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager;

import it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.ifaces.IMongoConnector;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.ifaces.ISensorManager;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.util.MisurazioneRandomBuilder;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.FitBeastMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.PosizioneMisurazione;
import lombok.Setter;

import java.time.LocalDateTime;

public class SensorManager implements ISensorManager {
    @Setter
    private IMongoConnector mongoConnector;

    @Override
    public void misurazioniRandom(Animale a, int delta, int minutiMisurazioni) {
        LocalDateTime start = LocalDateTime.now();

        LocalDateTime to = start.plusMinutes(minutiMisurazioni);

        MisurazioneRandomBuilder misurazioneBuilder = new MisurazioneRandomBuilder();

        for (LocalDateTime now = LocalDateTime.now(); now.isBefore(to);now = LocalDateTime.now()){
            PosizioneMisurazione posMis = misurazioneBuilder.getRandomPosizione(a);
            FitBeastMisurazione fitMis = misurazioneBuilder.getFitBeastMisurazione(a);

            this.mongoConnector.aggiungiMisurazione(posMis);
            this.mongoConnector.aggiungiMisurazione(fitMis);

            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (now.isAfter(start.plusSeconds(30)))
            {
                start = now;
                System.out.println("30 secondi di misurazioni");
            }
        }
    }
}
