package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class PosizioneMisurazione {
    private List<Double> coordinate;

    {
        this.coordinate = new LinkedList<>();
    }
}
