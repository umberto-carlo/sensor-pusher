package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FitBeastMisurazione{
    private double temperatura;
    private Pressione pressione;
    private int battiti;
}
