package sk.fri.uniza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author Richard Kovacik
 */
public class ZoznamHracov implements Serializable {
    private List<Hrac> zoznamHracov;

    /**
     * Konstruktor vytvori novy zoznam hracov a inicializuje atribut zoznamHracov
     */
    public ZoznamHracov() {
        this.zoznamHracov = new ArrayList<>();
    }

    /**
     * Metoda prida aktualneho hraca do zoznamu hracov
     * @param hrac aktualny
     */
    public void pridajHraca(Hrac hrac) {
        if (hrac != null) {
            this.zoznamHracov.add(hrac);
        }
    }

    /**
     * metda vrati utriedeny zoznam hracov
     * @return hodnoty arraylistu zoznamHracov
     */
    public List<Hrac> getZoznamHracov() {
        this.utriedHracovPodlaSkore();
        return new ArrayList<>(this.zoznamHracov);
    }

    /**
     * metoda utriedi list hracov podla skore od najvacsieho po najmesi
     */
    private void utriedHracovPodlaSkore() {
        this.zoznamHracov.sort(Comparator.comparingInt(Hrac::getScore).reversed());
    }
}
