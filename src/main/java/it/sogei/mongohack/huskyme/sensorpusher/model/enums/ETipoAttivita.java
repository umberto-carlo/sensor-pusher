package it.sogei.mongohack.huskyme.sensorpusher.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ETipoAttivita {
    DORME(0,"Dorme"),

    CAMMINA(1,"Cammina"),

    CORRE(2,"Corre");

    private static Map<Integer, ETipoAttivita> mappone;

    static {
        mappone = Arrays.stream(ETipoAttivita.values()).collect(Collectors.toMap(ETipoAttivita::getCodTipoAttivita, Function.identity()));
    }

    private int codTipoAttivita;

    private String tipoAttivita;

    ETipoAttivita(int codTipoAttivita,String tipo) {
        this.codTipoAttivita = codTipoAttivita;
        this.tipoAttivita = tipo;
    }

    public int getCodTipoAttivita() {
        return codTipoAttivita;
    }

    public String getTipoAttivita() {
        return tipoAttivita;
    }

    public static ETipoAttivita getTipo(int code) {
        return mappone.get(code);
    }
}
