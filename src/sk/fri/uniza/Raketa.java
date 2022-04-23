package sk.fri.uniza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 27. 2. 2022 - 15:54
 * Treida Raketa rerezentuje rakety c hre
 * @author richard
 */
public class Raketa {
    private BufferedImage obrazok;
    private Pozicia pozicia;
    private boolean jeZnicena;
    private boolean jeHracova;
    private boolean jeExplozia;

    /**
     * Konstruktor vytvori novu raketu na danej pozici a urci podla parametra ci je hracova alebo nie
     * @param x suradnica rakety
     * @param y suradnia rakety
     * @param jeHracova ak je hracova - true inak false
     */
    public Raketa(int x, int y , boolean jeHracova) {
        this.pozicia = new Pozicia(x, y);
        this.initObrazok();
        this.jeZnicena = false;
        this.jeExplozia = false;
        this.jeHracova = jeHracova;
    }

    /**
     * metoda incializuje obrazok rakety
     */
    private void initObrazok() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/bomb.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * metoda incializuje obrazok explozie
     */
    private void initObrazokExplozie() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/explosion.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * meotoda nastavi atributy jeZnicena a jeExplozia na true a inicalizuje obrazok explozie
     */
    public void vybuchni() {
        this.jeZnicena = true;
        this.jeExplozia = true;
        this.initObrazokExplozie();
    }

    /**
     * metoda updatuje pozcie rakety a znici raketu pokial prekrocila hranice hracej plochy
     */
    //update atributy rakety
    private void tik() {
        if (!this.jeZnicena) {
            if (this.jeHracova) {
                this.pozicia.setY(this.pozicia.getY() - 5);
            } else {
                this.pozicia.setY(this.pozicia.getY() + 5);
            }
        }
        //znic raketu ktora prekrocila okraje hracej plochy
        if (this.pozicia.getY() <= 5 || this.pozicia.getY() >= 435) {
            this.jeZnicena = true;
        }
    }

    /**
     * metoda zobrazi raketu alebo exploziu na aktualnej pozicii
     * @param g2d sluzi na vykreslovanie 2D objektov
     */
    public void kresli(Graphics2D g2d) {
        this.tik();
        if (!this.jeZnicena) {
            g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 3, 10, null);
        } else if (this.jeExplozia) {
            g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 12, 12, null);
        }
    }

    public Pozicia getPozicia() {
        return this.pozicia;
    }

    public boolean jeZnicena() {
        return this.jeZnicena;
    }

    public boolean jeExplozia() {
        return this.jeExplozia;
    }

}
