package sk.fri.uniza;

import sk.fri.uniza.enums.Narodnost;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Hrac extends Bytost {
    private String meno;
    private int score;
    private Narodnost narodnost;
    private transient boolean vlavo;
    private transient boolean vpravo;
    private transient boolean vystrel;
    private transient Timer timer;
    //zobrazenie zivotov (3 srdiecka)
    private transient BufferedImage[] srdiecka;

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
    // skotroluj vystrel kazdych 500 milis, hrac striekla pomocou stalcanie P/p na klavesnici
    private transient ActionListener timerListener = evt -> {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Hrac.this.skontrolujVystrel();
            }
        });
    };

    public void kresliSrdcia(Graphics2D g2d) {
        int x = 610;
        for (int i = 0; i < this.srdiecka.length; i++) {
            if (this.srdiecka[i] != null) {
                g2d.drawImage(this.srdiecka[i], x, 465, 20, 20, null);
            }
            x += 25;
        }
    }

    private void initObrazkyZivotov() {
        for (int i = 0; i < this.srdiecka.length; i++) {
            try {
                this.srdiecka[i] = ImageIO.read(new File("src/sk/fri/uniza/images/heart.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void initObrazok() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/player.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void uberZivot() {
        if (this.zivoty > 0) {
            this.zivoty--;
            this.srdiecka[this.zivoty] = null;
        }
    }

    public void skontrolujVystrel() {
        if (this.vystrel) {
            this.rakety.add(new Raketa(this.pozicia.getX() + 5, this.pozicia.getY(), true));
        }
        this.vystrel = false;

    }

    public void tik() {
        //update poziciu hraca
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

    public void kresli(Graphics2D g2d) {
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

    public boolean isVlavo() {
        return this.vlavo;
    }

    public void setVlavo(boolean vlavo) {
        this.vlavo = vlavo;
    }

    public boolean isVpravo() {
        return this.vpravo;
    }

    public void setVpravo(boolean vpravo) {
        this.vpravo = vpravo;
    }

    public boolean isVystrel() {
        return this.vystrel;
    }

    public void setVystrel(boolean vystrel) {
        this.vystrel = vystrel;
    }

    public void setRakety(ArrayList<Raketa> rakety) {
        this.rakety = rakety;
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

