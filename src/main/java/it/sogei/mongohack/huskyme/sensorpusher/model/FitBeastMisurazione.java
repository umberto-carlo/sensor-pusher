package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FitBeastMisurazione extends AMisurazione {
    private double temperatura;
    private Pressione pressione;
    private int battiti;

    public FitBeastMisurazione() {
        super(2);
    }
}
