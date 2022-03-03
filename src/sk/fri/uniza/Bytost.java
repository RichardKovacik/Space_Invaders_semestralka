package sk.fri.uniza;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public abstract class Bytost implements Serializable {
    protected transient int zivoty;
    protected transient BufferedImage obrazok;
    protected transient Pozicia pozicia;
    protected transient boolean jeNaZive;
    protected transient ArrayList<Raketa> rakety;

    public Bytost(int x, int y) {
        this.pozicia = new Pozicia(x, y);
        this.rakety = new ArrayList<>();
    }

    // je zrazka bytosti s raketou?
    public boolean jeZrazka(int x, int y) {
        //zlava zasiahne strela alebo zprava kedze stred obrazka je posunuty viac dolava
        if (((this.pozicia.getX() - x >= 0 && this.pozicia.getX() - x <= 5) || (this.pozicia.getX() - x < 0 && this.pozicia.getX() - x >= -20))
        && Math.abs(this.pozicia.getY() - y) <= 15) {
            return true;
        }
        return false;
    }

    public void odstranZniceneRakety() {
        Iterator<Raketa> iterator = this.rakety.iterator();
        while (iterator.hasNext()) {
            Raketa raketa = iterator.next();
            if (raketa.jeZnicena()) {
                iterator.remove();
            }
        }
    }

    //vraciam len hodnoty arralistu v novom arrayliste, nepracujem s existujucou referenciou objektu
    //neporusujem enkapsulaciu
    public ArrayList<Raketa> getRakety() {
        return new ArrayList<>(this.rakety);
    }

    protected void zomri() {
        this.jeNaZive = false;
    }
}
