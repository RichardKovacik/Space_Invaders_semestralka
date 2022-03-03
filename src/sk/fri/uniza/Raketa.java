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
 *
 * @author richard
 */
public class Raketa {
    private BufferedImage obrazok;
    private Pozicia pozicia;
    private boolean jeZnicena;
    private boolean jeHracova;
    private boolean jeExplozia;

    public Raketa(int x, int y , boolean jeHracova) {
        this.pozicia = new Pozicia(x, y);
        this.initObrazok();
        this.jeZnicena = false;
        this.jeExplozia = false;
        this.jeHracova = jeHracova;
    }

    private ActionListener timerListener = evt -> {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Raketa.this.vybuchni();
            }
        });
    };

    private void initObrazok() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/bomb.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initObrazokExplozie() {
        try {
            this.obrazok = ImageIO.read(new File("src/sk/fri/uniza/images/explosion.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void vybuchni() {
        this.jeZnicena = true;
        this.jeExplozia = true;
        this.initObrazokExplozie();
    }

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

    public void kresli(Graphics2D g2d) {
        this.tik();
        if (!this.jeZnicena) {
            g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 3, 10, null);
        } else if (this.jeExplozia) {
            g2d.drawImage(this.obrazok, this.pozicia.getX(), this.pozicia.getY(), 12, 12, null);
        }
    }

    public BufferedImage getObrazok() {
        return this.obrazok;
    }

    public void setObrazok(BufferedImage obrazok) {
        this.obrazok = obrazok;
    }

    public Pozicia getPozicia() {
        return this.pozicia;
    }

    public void setPozicia(Pozicia pozicia) {
        this.pozicia = pozicia;
    }

    public boolean jeZnicena() {
        return this.jeZnicena;
    }

    public void setJeZnicena(boolean jeZnicena) {
        this.jeZnicena = jeZnicena;
    }

    public boolean jeExplozia() {
        return this.jeExplozia;
    }

    public void setJeExplozia(boolean jeExplozia) {
        this.jeExplozia = jeExplozia;
    }

}
