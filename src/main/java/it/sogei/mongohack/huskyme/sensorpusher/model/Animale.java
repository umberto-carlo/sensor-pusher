package it.sogei.mongohack.huskyme.sensorpusher.model;

import it.sogei.mongohack.huskyme.sensorpusher.model.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Animale {
    private String idAnimale;
    private String nome;
    private String dataNascita;
    private Peso peso;
    private ERazza razza;
    private ETipo tipo;
    private String codPadrone;
}
