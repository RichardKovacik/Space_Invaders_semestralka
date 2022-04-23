package sk.fri.uniza.gui;

import sk.fri.uniza.enums.Obtiaznost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 27. 2. 2022 - 15:54
 * Trieda Menu zobrazuje menu okno s danymi prvkami
 * @author richard
 */
public class Menu {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel menuTxt;
    private JButton startGamebutton;
    private JButton settingsBtn;
    private JButton quitBtn;
    private Obtiaznost zvolenaObtiaznost;

    /**
     * Konstruktor vytvri nove menu okno o danej velkosti
     */
    public Menu() {
        this.frame = new JFrame("SPACE INVADERS MENU");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.add(this.mainPanel);
        this.frame.setPreferredSize(new Dimension(700, 500));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.settingsBtn.addActionListener(e -> this.showSettingDialog());
        this.quitBtn.addActionListener(e -> this.closeFrame());
        this.startGamebutton.addActionListener(e -> this.showDialog());
    }

    /**
     * metoda zavrie dane menu okno
     */
    public void closeFrame() {
        this.frame.dispose();
    }

    /**
     * metoda zobrazi dialog s nastaveniami obtiaznosti
     */
    private void showSettingDialog() {
        NastavenieObtiaznostiDialog dialog = new NastavenieObtiaznostiDialog();
        this.zvolenaObtiaznost = dialog.getObtiaznost();
    }

    /**
     * meotda vrati zvolenu obtiaznost
     * @return zvolena obtiaznost
     */
    public Obtiaznost getZvolenaObtiaznost() {
        return this.zvolenaObtiaznost;
    }

    /**
     * metoda zobrazi dialog pre zadavanie mena hraca
     */
    private void showDialog() {
        new ZadanieMenaDialog(this);
    }
}
