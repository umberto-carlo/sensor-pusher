package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class PosizioneMisurazione extends AMisurazione {
    private List<Double> coordinate;

    {
        this.coordinate = new LinkedList<>();
    }

    public PosizioneMisurazione() {
        super(1);
    }
}
