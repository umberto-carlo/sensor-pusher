package it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.ifaces;

import it.sogei.mongohack.huskyme.sensorpusher.model.AMisurazione;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.Padrone;

import java.util.List;

public interface IMongoConnector {
    public void aggiungiPadrone(Padrone p);

    public List<Padrone> getPadroni();

    public void aggiungiAnimale(Padrone p, Animale a);

    public List<Animale> getAnimaleDi(Padrone p);

    public void aggiungiMisurazione(AMisurazione misurazione);
}
