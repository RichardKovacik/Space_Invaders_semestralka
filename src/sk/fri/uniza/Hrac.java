package sk.fri.uniza;

import sk.fri.uniza.enums.Narodnost;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * 27. 2. 2022 - 15:54
 * trieda hrac je potomkom triedy Bytost a reprezentuje hraca v hre
 * @author richard
 */
public class Hrac extends Bytost {
    private String meno;
    private int score;
    private Narodnost narodnost;
    private transient boolean jeVystrel;
    private transient Timer timer;
    //zobrazenie zivotov (3 srdiecka)
    private transient BufferedImage[] srdiecka;

    /**
     * konstruktor vytvori hraca na zadanej pozici a nastavi potrebne atributy
     * @param x pozicia hraca
     * @param y pozicia hraca
     */
    public Hrac(int x, int y) {
        super(x, y);
        this.zivoty = 3;
        this.jeNaZive = true;
        this.score = 0;
        this.initObrazok();
        this.srdiecka = new BufferedImage[3];
        this.initObrazkyZivotov();
        //dovol vystrelit maximalne kazdu pol sekundu
        this.timer = new Timer(500, this.timerListener);
        this.timer.setRepeats(true);
        this.timer.start();
    }

    /**
     * dany listener sa vykona kazdu pol sekundy a zavola metodu vystrel
     */
    // skotroluj vystrel kazdych 500 milis, hrac striekla pomocou stalcanie P/p na klavesnici
    private transient ActionListener timerListener = evt -> {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Hrac.this.vystrel();
            }
        });
    };
    /**
     * prepisana abstraktna metoda z triedy Bytost, kotara zabezpeci vystrel hraca
     */
    @Override
    void vystrel() {
        //pokial hrac stalcil medzernik tak sa vykona vystrel
        if (this.jeVystrel) {
            this.rakety.add(new Raketa(this.pozicia.getX() + 5, this.pozicia.getY(), true));
        }
        this.jeVystrel = false;
    }

    /**
     *  metoda zobrazi zivoty hraca v podobe obrazkov srdiecka
     * @param g2d pomoocu ktoreho vykreslujem objekty na hraciu plochu
     */
    public void kresliSrdcia(Graphics2D g2d) {
        int x = 610;
        for (int i = 0; i < this.srdiecka.length; i++) {
            if (this.srdiecka[i] != null) {
                g2d.drawImage(this.srdiecka[i], x, 465, 20, 20, null);
            }
            x += 25;
        }
    }

    /**
     * metoda icializuje pole obrazkov reprezentujucich zivot hraca
     */
    private void initObrazkyZivotov() {
        for (int i = 0; i < this.srdiecka.length; i++) {
            try {
                this.srdiecka[i] = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/images/heart.png")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * metoda inicializuje obrazok ktory bude reprezentovat hraca na hracej ploche
     */
    @Override
    public void initObrazok() {
        try {
            this.obrazok = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/images/player.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * metoda uberie zivoty hracovi a zabezpeci zmiznutie prave jedneho srdiecka z obrazovky
     */
    public void uberZivot() {
        if (this.zivoty > 0) {
            this.zivoty--;
            this.srdiecka[this.zivoty] = null;
        }
    }

    /**
     * metoda tik updatuje poziciu hraca
     */
    @Override
    public void tik() {
        //update pozicie hraca
        int stareX = this.pozicia.getX();
        if (this.vlavo && stareX - 3 > 0) {
            this.pozicia.setX(this.pozicia.getX() - 3);
        }
        if (this.vpravo && stareX + 3 < HraciaPlocha.SIRKA - 15) {
            this.pozicia.setX(this.pozicia.getX() + 3);
        }
        if (this.zivoty <= 0) {
            this.zomri();
        }
    }

    /**
     * metoda zobrazi(vykresli) hraca na aktualnej pozicii na hraciu plochu
     * @param g2d parameter
     */
    @Override
    public void zobraz(Graphics2D g2d) {
        this.tik();
        this.kresliSrdcia(g2d);
        g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 20, 20, null);
    }

    public String getMeno() {
        return this.meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVlavo(boolean vlavo) {
        this.vlavo = vlavo;
    }

    public void setVpravo(boolean vpravo) {
        this.vpravo = vpravo;
    }

    public void setVystrel(boolean vystrel) {
        this.jeVystrel = vystrel;
    }

    public Narodnost getNarodnost() {
        return this.narodnost;
    }

    public void setNarodnost(Narodnost narodnost) {
        this.narodnost = narodnost;
    }

    @Override
    public String toString() {
        return "Hrac{" +
                "meno='" + this.meno + '\'' +
                ", score=" + this.score +
                ", narodnost=" + this.narodnost.getReprezentacia() +
                '}';
    }
}

