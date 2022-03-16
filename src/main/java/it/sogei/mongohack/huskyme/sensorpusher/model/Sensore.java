package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sensore {
    private String timeStamp;


    private String idAnimale;

    private PosizioneMisurazione sensorePosizione;

    private FitBeastMisurazione sensoreFit;
}
