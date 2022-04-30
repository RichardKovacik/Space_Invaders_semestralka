package sk.fri.uniza.gui;

import sk.fri.uniza.HraciaPlocha;
import sk.fri.uniza.enums.Narodnost;
import sk.fri.uniza.enums.Obtiaznost;

import javax.swing.*;
import java.awt.*;

/**
 * 27. 2. 2022 - 15:54
 * Trieda hra reprezentuje novu hru ktora vytvori novu hraciu plochu
 * @author richard
 */
public class HraOkno {
    private JFrame frame;
    private HraciaPlocha hraciaPlocha;
    private HraciaPlocha.VykreslovaniePlochy vykreslovacuThread;

    /**
     * Konstruktor vytvori novu hru s hracou plochou, nastavi atributy zadaneho hracaw
     * @param menoHraca zadaane pred spustenim hry
     * @param narodnost zadana pred spustenim hry
     * @param obtiaznost zadana pred spustenim hry
     */
    public HraOkno(String menoHraca, Narodnost narodnost, Obtiaznost obtiaznost) {
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
        //spusti vykreslovaci thread hracej plochy
        new Thread(this.vykreslovacuThread).start();
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
