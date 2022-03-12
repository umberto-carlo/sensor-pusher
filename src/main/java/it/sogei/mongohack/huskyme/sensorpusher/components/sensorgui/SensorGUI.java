package it.sogei.mongohack.huskyme.sensorpusher.components.sensorgui;

import it.sogei.mongohack.huskyme.sensorpusher.components.anagraficamanager.ifaces.IAnagraficaManager;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensorgui.ifaces.ISensorGUI;
import it.sogei.mongohack.huskyme.sensorpusher.components.sensormanager.ifaces.ISensorManager;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.Padrone;
import it.sogei.mongohack.huskyme.sensorpusher.model.Peso;
import it.sogei.mongohack.huskyme.sensorpusher.model.enums.ERazza;
import it.sogei.mongohack.huskyme.sensorpusher.model.enums.ETipo;
import lombok.Setter;

import java.io.Console;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class SensorGUI implements ISensorGUI {
    private static class Comando {
        String nomeComando;
        Consumer<Scanner> comando;

        Comando(String nome, Consumer<Scanner> com) {
            this.nomeComando = nome;
            this.comando = com;
        }

        public String getNomeComando() {
            return nomeComando;
        }

        public Consumer<Scanner> getComando() {
            return comando;
        }
    }

    @Setter
    private ISensorManager sensorManager;

    @Setter
    private IAnagraficaManager anagraficaManager;

    private Map<String, Comando> mappaComandi;

    {
        this.mappaComandi = new HashMap<>();

        this.mappaComandi.put("1", new Comando("Aggiungi padrone", this::aggiungiPadrone));
        this.mappaComandi.put("2", new Comando("Aggiungi animale", this::aggiungiAnimale));
        this.mappaComandi.put("3",new Comando("Misurazioni random",this::misurazioniRandom));
    }

    private <T> T readValue(Scanner console, String format, Function<String, T> func) {
        System.out.printf(String.format("%1$s\n>>", format));
        return func.apply(console.nextLine());
    }

    private void aggiungiPadrone(Scanner console) {
        Padrone padrone = new Padrone();

        padrone.setCodPadrone(this.readValue(console, "Inserisci codice padrone", String::valueOf));
        padrone.setNome(this.readValue(console, "Inserisci nome padrone", String::valueOf));
        padrone.setCognome(this.readValue(console, "Inserisci cognome padrone", String::valueOf));
        padrone.setUserName(this.readValue(console, "Inserisci userName padrone", String::valueOf));
        padrone.setPassword(this.readValue(console, "Inserisci password padrone", String::valueOf));
        padrone.setTelefono(this.readValue(console, "Inserisci telefono padrone", String::valueOf));
        padrone.setEmail(this.readValue(console, "Inserisci email padrone", String::valueOf));

        this.anagraficaManager.aggiungiPadrone(padrone);
    }

    private void aggiungiAnimale(Scanner console) {
        System.out.println("Padroni");
        List<Padrone> listaPadroni = this.anagraficaManager.getPadroni();
        AtomicInteger padIsx = new AtomicInteger(0);
        listaPadroni.stream().map(p -> String.format("%1$s) %2$s", padIsx.addAndGet(1), p.getCodPadrone())).forEach(System.out::println);
        System.out.print(">>");
        int numPad = console.nextInt() - 1;
        Padrone p = listaPadroni.get(numPad);

        Animale a = new Animale();

        a.setCodPadrone(p.getCodPadrone());

        this.readValue(console, "", String::valueOf);

        a.setIdAnimale(this.readValue(console, "Inserisci id animale", String::valueOf));
        System.out.println(a.getIdAnimale());
        a.setNome(this.readValue(console, "Inserisci nome animale", String::valueOf));
        a.setDataNascita(this.readValue(console, "Inserisci data di nascita (yyyy-mm-dd)", String::valueOf));

        Peso peso = new Peso();
        a.setPeso(peso);

        peso.setValore(this.readValue(console, "Inserisci il peso", Double::parseDouble));
        peso.setUnita(this.readValue(console, "Inserisci l'unita di peso", String::valueOf));

        System.out.println("Scegliere il tipo");
        Arrays.stream(ETipo.values()).map(t -> String.format("%1$s) %2$s", t.getCodTipoAnimale(), t.getTipo())).forEach(System.out::println);
        System.out.print(">>");
        a.setTipo(ETipo.getTipo(console.nextInt()));

        System.out.println("Scegliere la razza");
        Arrays.stream(ERazza.values()).map(r -> String.format("%1$s) %2$s", r.getCodRazza(), r.getRazza())).forEach(System.out::println);
        System.out.print(">>");
        a.setRazza(ERazza.getRazza(console.nextInt()));

        this.anagraficaManager.aggiungiAnimale(p, a);
    }

    private void misurazioniRandom(Scanner console)
    {
        System.out.println("Padroni");
        List<Padrone> listaPadroni = this.anagraficaManager.getPadroni();
        AtomicInteger padIsx = new AtomicInteger(0);
        listaPadroni.stream().map(p -> String.format("%1$s) %2$s", padIsx.addAndGet(1), p.getCodPadrone())).forEach(System.out::println);
        System.out.print(">>");
        int numPad = console.nextInt() - 1;
        Padrone p = listaPadroni.get(numPad);

        this.readValue(console, "", String::valueOf);

        System.out.println("Animali");
        List<Animale> listaAnimali = this.anagraficaManager.getAnimaleDi(p);
        padIsx.set(0);
        listaAnimali.stream().map(a->String.format("%1$s) %2$s", padIsx.addAndGet(1), a.getIdAnimale())).forEach(System.out::println);
        System.out.print(">>");
        int numAnim = console.nextInt() - 1;
        Animale a = listaAnimali.get(numAnim);

        this.readValue(console, "", String::valueOf);

        int deltaMisurazioni = this.readValue(console,"Delta tra le misurazioni (ms)",Integer::parseInt);
        int minutiMisurazioni = this.readValue(console,"Minuti da misurare",Integer::parseInt);

        this.sensorManager.misurazioniRandom(a,deltaMisurazioni,minutiMisurazioni);
    }

    private void stampaComandi() {
        System.out.println("Inserire comando");

        System.out.println("0) Esci");
        this.mappaComandi.entrySet().stream().sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getKey()))).map(e -> String.format("%1$s) %2$s\n", e.getKey(), e.getValue().nomeComando)).forEach(System.out::printf);

        System.out.printf(">>");
    }

    @Override
    public void startGUI() {
        Scanner console = new Scanner(System.in);

        this.stampaComandi();

        for (String input = console.nextLine(); !input.equals("0"); input = console.nextLine()) {
            Optional.ofNullable(this.mappaComandi.get(input)).map(Comando::getComando).orElse(cons -> System.out.printf("Inserire un comando valido\n>>")).accept(console);
            this.stampaComandi();
        }
    }
}
