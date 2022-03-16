package it.sogei.mongohack.huskyme.sensorpusher.model.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum ETipo {
    GATTO(1, "Gatto"),
    CANE(2,"Cane"),
    STRUZZO(3,"Struzzo"),
    TARTARUGA(4,"Tartaruga");

    private static Map<Integer, ETipo> mappone;

    static {
        mappone = Arrays.stream(ETipo.values()).collect(Collectors.toMap(ETipo::getCodTipoAnimale, Function.identity()));
    }

    private int codTipoAnimale;
    private String tipo;

    private ETipo(int codice, String tipo) {
        this.codTipoAnimale = codice;
        this.tipo = tipo;
    }

    public int getCodTipoAnimale() {
        return codTipoAnimale;
    }

    public String getTipo() {
        return tipo;
    }

    public static ETipo getTipo(int code) {
        return mappone.get(code);
    }
}
