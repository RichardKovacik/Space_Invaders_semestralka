package sk.fri.uniza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Profil implements Serializable {
    private List<Hrac> zoznamHracov;

    public Profil() {
        this.zoznamHracov = new ArrayList<>();
    }

    public void pridajHraca(Hrac hrac) {
        if (hrac != null) {
            this.zoznamHracov.add(hrac);
        }
    }

    public List<Hrac> getZoznamHracov() {
        return this.zoznamHracov;
    }

    public void setZoznamHracov(List<Hrac> zoznamHracov) {
        this.zoznamHracov = zoznamHracov;
    }
}
