package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Demo program for searching algorithms and others methods as well as
 * {@link SearchUtil}, {@link Slagalica}, {@link KonfiguracijaSlagalice} classes.
 */
public class SlagalicaDemo {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Slagalica slagalica = new Slagalica(
                new KonfiguracijaSlagalice(new int[]{2, 3, 0, 1, 4, 6, 7, 5, 8})
                //  new KonfiguracijaSlagalice(new int[] {1,6,4,5,0,2,8,7,3})  //impossible task
        );

        Node<KonfiguracijaSlagalice> rješenje =
                SearchUtil.bfs(slagalica, slagalica, slagalica);

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
        }
    }
}