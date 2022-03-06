package sk.fri.uniza;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Mimozemstan extends Bytost {
    private boolean vlavo;
    private boolean vpravo;
    private int rychlost;

    public Mimozemstan(int x, int y) {
        super(x, y);
        this.jeNaZive = true;
        this.vpravo = true;
        this.zivoty = 1;
        this.rychlost = 2;
        this.initObrazok();
    }

    private void initObrazok() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/alien.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void vystrel() {
        this.rakety.add(new Raketa(this.pozicia.getX() - 5, this.pozicia.getY(), false));
    }

    //update pozicie mimozemstana
    private void tik() {
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


    public void kresli(Graphics2D g2d) {
        this.tik();
        g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 20, 20, null);
    }
}
