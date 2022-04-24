package sk.fri.uniza;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 27. 2. 2022 - 15:54
 * Trieda mimozemstan je potomkom triedy bytost, sluzi na reprezentaciu mimozemstana v hre
 * @author richard
 */
public class Mimozemstan extends Bytost {
    private int rychlost;
    /**
     * Konstruktor vytvori mimozemstana na zadanej pozicii
     * @param x pozicia mimozemstana
     * @param y pozicia mimozemstana
     */
    public Mimozemstan(int x, int y) {
        super(x, y);
        this.jeNaZive = true;
        this.vpravo = true;
        this.zivoty = 1;
        this.rychlost = 2;
        this.initObrazok();
    }

    /**
     * metoda inicializuje obrazok mimozestana, ktory sa bude zobrazovat na hracej ploche
     */
    @Override
    public void initObrazok() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/alien.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * metoda zabezpeci vystrelenie rakety mimozemstanom
     */
    @Override
    public void vystrel() {
        if (this.jeNaZive) {
            this.rakety.add(new Raketa(this.pozicia.getX() - 5, this.pozicia.getY(), false));
        }
    }

    /**
     * metoda tik vykonava update pozicie daneho mimozemstana a kotroluje ci je nazive
     */
    //update pozicie mimozemstana
    @Override
    public void tik() {
        // skotroluj pravy okraj
        if (Math.abs(this.pozicia.getX() - HraciaPlocha.SIRKA) <= 20) {
            this.vlavo = true;
            this.vpravo = false;
            this.pozicia.setY(this.pozicia.getY() + 4);
        }
        //skotroluj lavy okraj
        if (this.pozicia.getX() <= 5) {
            this.vlavo = false;
            this.vpravo = true;
            this.pozicia.setY(this.pozicia.getY() + 4);
        }
        //update pozcie na X-vej suradnici
        if (this.vpravo) {
            this.pozicia.setX(this.pozicia.getX() + this.rychlost);
        }
        if (this.vlavo) {
            this.pozicia.setX(this.pozicia.getX() - this.rychlost);
        }
        if (this.zivoty <= 0) {
            this.zomri();
        }
    }


    /**
     * metoda vykresli daneho mimozemstana s aktualnymi suradnicami na hraciu plochu
     * @param g2d sluzi na vykreslovanie na hraciu plochu
     */
    @Override
    public void zobraz(Graphics2D g2d) {
        this.tik();
        g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 20, 20, null);
    }
}
