package it.sogei.mongohack.huskyme.sensorpusher.components.anagraficamanager;

import it.sogei.mongohack.huskyme.sensorpusher.components.anagraficamanager.ifaces.IAnagraficaManager;
import it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.ifaces.IMongoConnector;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.Padrone;
import lombok.Setter;

import java.util.List;

public class AnagraficaManager implements IAnagraficaManager {
    @Setter
    private IMongoConnector mongoConnector;

    @Override
    public void aggiungiPadrone(Padrone p) {
        this.mongoConnector.aggiungiPadrone(p);
    }

    @Override
    public List<Padrone> getPadroni() {
        return this.mongoConnector.getPadroni();
    }

    @Override
    public void aggiungiAnimale(Padrone p, Animale a) {
        this.mongoConnector.aggiungiAnimale(p, a);
    }

    @Override
    public List<Animale> getAnimaleDi(Padrone p) {
        return this.mongoConnector.getAnimaleDi(p);
    }
}
