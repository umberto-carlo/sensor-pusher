package it.sogei.mongohack.huskyme.sensorpusher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Padrone {
    private String codPadrone;
    private String nome;
    private String cognome;
    private String userName;
    private String password;
    private String telefono;
    private String email;
    private List<Animale> animali;

    {
        this.animali = new LinkedList<>();
    }
}
