package sk.fri.uniza.gui;

import sk.fri.uniza.HraciaPlocha;

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

    public Hra(String menoHraca) {
        this.frame = new JFrame("SPACE INVADERS GAME");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.hraciaPlocha = new HraciaPlocha(this);
        this.vykreslovacuThread = new HraciaPlocha.VykreslovaniePlochy(this.hraciaPlocha);
        this.frame.add(this.hraciaPlocha);
        this.hraciaPlocha.getHrac().setMeno(menoHraca);
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
