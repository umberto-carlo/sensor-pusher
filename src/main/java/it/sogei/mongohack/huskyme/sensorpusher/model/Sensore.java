package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Sensore {
    private Date timeStamp;

    private String idAnimale;

    private boolean campione;

    private boolean allarme;

    private PosizioneMisurazione sensorePosizione;

    private FitBeastMisurazione sensoreFit;
}
