package it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager;

import it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.ifaces.IMongoConnector;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.ifaces.ISensorManager;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.util.MisurazioneRandomBuilder;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.FitBeastMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.PosizioneMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.Sensore;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class SensorManager implements ISensorManager {
    @Setter
    private IMongoConnector mongoConnector;

    private String getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return LocalDateTime.now().format(formatter);
    }

    @Override
    public void misurazioniRandom(Animale a, int delta, int minutiMisurazioni) {
        LocalDateTime start = LocalDateTime.now();

        LocalDateTime to = start.plusMinutes(minutiMisurazioni);

        MisurazioneRandomBuilder misurazioneBuilder = new MisurazioneRandomBuilder();

        LocalDateTime dataUltimoCampione = LocalDateTime.now();

        int SECONDI_PER_CAMPIONE = 20;

        for (LocalDateTime now = LocalDateTime.now(); now.isBefore(to); now = LocalDateTime.now()) {
            PosizioneMisurazione posMis = misurazioneBuilder.getRandomPosizione(a);
            FitBeastMisurazione fitMis = misurazioneBuilder.getFitBeastMisurazione(a);

            Sensore sensore = new Sensore();

            if (now.isAfter(dataUltimoCampione.plusSeconds(SECONDI_PER_CAMPIONE))) {
                sensore.setCampione(true);
                dataUltimoCampione = now;
            } else {
                sensore.setCampione(false);
            }

            sensore.setAllarme(fitMis.isAllarmante());

            sensore.setIdAnimale(a.getIdAnimale());
            sensore.setTimeStamp(new Date());
            sensore.setSensorePosizione(posMis);
            sensore.setSensoreFit(fitMis);

            this.mongoConnector.aggiungiMisurazione(sensore);
            //this.mongoConnector.aggiungiMisurazione(sensore);

            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (now.isAfter(start.plusSeconds(30))) {
                start = now;
                System.out.println("30 secondi di misurazioni");
            }
        }
    }
}
