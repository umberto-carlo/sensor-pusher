package it.sogei.mongohack.huskyme.sensorpusher.components.anagraficamanager.ifaces;

import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.Padrone;

import java.util.List;

public interface IAnagraficaManager {
    public void aggiungiPadrone(Padrone p);

    public List<Padrone> getPadroni();

    public void aggiungiAnimale(Padrone p, Animale a);

    public List<Animale> getAnimaleDi(Padrone p);
}
