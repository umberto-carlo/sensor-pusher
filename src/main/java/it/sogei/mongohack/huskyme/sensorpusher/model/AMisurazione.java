package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class AMisurazione {
    @Getter
    @Setter
    private String timeStamp;

    private int codTipoSensore;

    @Getter
    @Setter
    private String idAnimale;

    public AMisurazione(int codTipoSensore) {
        this.codTipoSensore = codTipoSensore;
    }
}
