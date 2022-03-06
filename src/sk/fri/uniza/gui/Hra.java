package sk.fri.uniza.gui;

import sk.fri.uniza.HraciaPlocha;
import sk.fri.uniza.enums.Narodnost;
import sk.fri.uniza.enums.Obtiaznost;

import javax.swing.*;
import java.awt.*;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Hra {
    private JFrame frame;
    private HraciaPlocha hraciaPlocha;
    private HraciaPlocha.VykreslovaniePlochy vykreslovacuThread;

    public Hra(String menoHraca, Narodnost narodnost, Obtiaznost obtiaznost) {
        this.frame = new JFrame("SPACE INVADERS GAME");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.hraciaPlocha = new HraciaPlocha(this, obtiaznost);
        this.vykreslovacuThread = new HraciaPlocha.VykreslovaniePlochy(this.hraciaPlocha);
        this.frame.add(this.hraciaPlocha);
        this.hraciaPlocha.getHrac().setMeno(menoHraca);
        this.hraciaPlocha.getHrac().setNarodnost(narodnost);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        //start vykreslovaci thread hracej plochy
        new Thread(this.vykreslovacuThread).start();
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
