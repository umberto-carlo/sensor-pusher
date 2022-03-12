package it.sogei.mongohack.huskyme.sensorpusher.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ERazza {
    NORVEGESE(1, "Norvegese delle foreste");

    private static Map<Integer, ERazza> mappone;

    static {
        mappone = Arrays.stream(ERazza.values()).collect(Collectors.toMap(ERazza::getCodRazza, Function.identity()));
    }

    private int codRazza;
    private String razza;

    private ERazza(int cod, String des) {
        this.codRazza = cod;
        this.razza = des;
    }

    public int getCodRazza() {
        return codRazza;
    }

    public String getRazza() {
        return razza;
    }

    public static ERazza getRazza(int code) {
        return mappone.get(code);
    }
}
