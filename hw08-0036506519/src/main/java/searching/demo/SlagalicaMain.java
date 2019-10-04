package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Demo program for searching algorithms and others methods as well as
 * {@link SearchUtil}, {@link Slagalica}, {@link KonfiguracijaSlagalice} classes.
 * <p>
 * Program works the same as {@link SlagalicaDemo} program, with minor changes such as
 * that user gives initial configuration as the command-lne argument.
 */
public class SlagalicaMain {

    /**
     * Main method of the program.
     *
     * @param args accepts one argument which should be
     *             initial configuration of the {@code Slagalica}
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Pogrešan broj argumenata.");
            System.exit(1);
        }

        String konfiguracija = args[0];
        checkIfGood(konfiguracija);

        int[] polje = new int[9];
        for (int i = 0; i < 9; i++) {
            polje[i] = konfiguracija.charAt(i) - '0';
        }

        Slagalica slagalica = new Slagalica(
                new KonfiguracijaSlagalice(polje)
        );

        Node<KonfiguracijaSlagalice> rješenje =
                SearchUtil.bfsv(slagalica, slagalica, slagalica);

        if (rješenje == null) {
            System.out.println("Nisam uspio pronaći rješenje.");
        } else {
            System.out.println(
                    "Imam rješenje. Broj poteza je: " + rješenje.getCost()
            );
            List<KonfiguracijaSlagalice> lista = new ArrayList<>();
            Node<KonfiguracijaSlagalice> trenutni = rješenje;
            while (trenutni != null) {
                lista.add(trenutni.getState());
                trenutni = trenutni.getParent();
            }
            Collections.reverse(lista);
            lista.stream().forEach(k -> {
                System.out.println(k);
                System.out.println();
            });
            SlagalicaViewer.display(rješenje);
        }
    }

    /**
     * Helping method for checking if given configuration is valid.
     *
     * @param konfiguracija initial configuration
     */
    private static void checkIfGood(String konfiguracija) {
        if (konfiguracija.length() != 9) {
            System.out.println("Pogrešna duljina konfiguracije.");
            System.exit(1);
        }
        for (int i = 0; i < 9; i++) {
            if (!konfiguracija.contains(String.valueOf(i))) {
                System.out.println("Konfiguracija nije dobra.");
                System.exit(1);
            }
        }
    }
}