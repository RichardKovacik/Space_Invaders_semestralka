package sk.fri.uniza;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 27. 2. 2022 - 15:54
 * abstraktna trieda bytost reprezuntuje vseobecne Bytosti v hre
 * @author richard
 */
public abstract class Bytost implements Serializable {
    protected transient int zivoty;
    protected transient Pozicia pozicia;
    protected transient boolean jeNaZive;
    protected transient boolean vlavo;
    protected transient boolean vpravo;
    protected transient BufferedImage obrazok;
    protected transient ArrayList<Raketa> rakety;

    /**
     * konstruktor vytvori novu bytost so zadanymi suradnicami x,y
     * @param x pozicia bytosti
     * @param y pozicia bytosti
     */
    public Bytost(int x, int y) {
        this.pozicia = new Pozicia(x, y);
        this.rakety = new ArrayList<>();
    }

    /**
     * abstraktna metoda vystrel je implementovana inak pre kazdeho potomka triedy Bytost
     */
    abstract void vystrel();

    /**
     * metoda zobrazi obrazok bytosti na plochu
     * @param graphics2D na zobrazenie bytosti na hraciu plochu
     */
    abstract void zobraz(Graphics2D graphics2D);

    /**
     * metoda updatne poziciu, atributy danej bytosti
     */
    abstract void tik();

    /**
     * metoda nastavi obrazok danej bytosti ktorym bude reprezentovana na hracej ploche
     */
    abstract void initObrazok();

    /**
     * metoda vrati true ak suradnice rakety x,y sa zhoduju so suradnicami bytosti plus interval v ktrom nastane zrazka
     * @param x pozicia rakety
     * @param y rakety
     * @return true ak je zrazka inak vrati false
     */
    // je zrazka bytosti s raketou?
    public boolean jeZrazka(int x, int y) {
        //zlava zasiahne strela alebo zprava kedze stred obrazka je posunuty viac dolava
        if (((this.pozicia.getX() - x >= 0 && this.pozicia.getX() - x <= 5) || (this.pozicia.getX() - x < 0 && this.pozicia.getX() - x >= -20))
        && Math.abs(this.pozicia.getY() - y) <= 15) {
            return true;
        }
        return false;
    }

    /**
     * metoda odstrani rakety z arraylistu ktore su znicene
     */
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
    /**
     * metoda vrati hodnoty arraylistu rakety
     * @return len hodnoty arraylistu rakety, nevracim referenciu na dany objekt
     */
    public ArrayList<Raketa> getRakety() {
        return new ArrayList<>(this.rakety);
    }

    /**
     * metoda nastavi atribut jeNazive na false
     */
    protected void zomri() {
        this.jeNaZive = false;
        this.obrazok = null;
    }
}
