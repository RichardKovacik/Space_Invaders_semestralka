package sk.fri.uniza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 * Trieda uchovava zoznam vsetkych hracov
 * @author Richard Kovacik
 */
public class ZoznamHracov implements Serializable {
    private List<Hrac> hraci;

    /**
     * Konstruktor vytvori novy zoznam hracov a inicializuje atribut zoznamHracov
     */
    public ZoznamHracov() {
        this.hraci = new ArrayList<>();
    }


    /**
     * Metoda prida aktualneho hraca do zoznamu hracov
     * @param hrac aktualny
     */
    public void pridajHraca(Hrac hrac) {
        if (hrac != null) {
            this.hraci.add(hrac);
        }
    }

    /**
     * metda vrati utriedeny zoznam hracov
     * @return hodnoty arraylistu zoznamHracov
     */
    public List<Hrac> getHraci() {
        this.utriedHracovPodlaSkore();
        return new ArrayList<>(this.hraci);
    }

    /**
     * metoda utriedi list hracov podla skore od najvacsieho po najmesi
     */
    private void utriedHracovPodlaSkore() {
        this.hraci.sort(Comparator.comparingInt(Hrac::getScore).reversed());
    }

}
