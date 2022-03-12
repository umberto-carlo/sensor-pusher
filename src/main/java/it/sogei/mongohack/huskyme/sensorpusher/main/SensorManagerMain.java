package it.sogei.mongohack.huskyme.sensorpusher.main;

import it.sogei.mongohack.huskyme.sensorpusher.components.anagraficamanager.AnagraficaManager;
import it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.MongoConnector;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensorgui.SensorGUI;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.SensorManager;

public class SensorManagerMain {
    public static void main(String[] args) {
        MongoConnector mongoConnector = new MongoConnector();
        SensorManager sensorManager = new SensorManager();
        AnagraficaManager anagraficaManager = new AnagraficaManager();
        SensorGUI sensorGUI = new SensorGUI();

        sensorManager.setMongoConnector(mongoConnector);
        anagraficaManager.setMongoConnector(mongoConnector);

        sensorGUI.setSensorManager(sensorManager);
        sensorGUI.setAnagraficaManager(anagraficaManager);

        sensorGUI.startGUI();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> mongoConnector.close()));
    }
}
