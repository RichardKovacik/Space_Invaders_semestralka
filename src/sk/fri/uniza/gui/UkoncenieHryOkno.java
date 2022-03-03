package sk.fri.uniza.gui;

import sk.fri.uniza.Hrac;
import sk.fri.uniza.Parser;
import sk.fri.uniza.TabulkaHracov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public class UkoncenieHryOkno {
    private JFrame frame;
    private Parser parser;
    private JPanel mainPanel;
    private JTable table1;
    private JButton spatBtn;
    private JButton ukonciHruBtn;

    public UkoncenieHryOkno(Hra hra, Hrac hrac) {
        this.frame = new JFrame("TABULKA VYSLEDKOV");
        this.frame.add(this.mainPanel);
        this.frame.setPreferredSize(new Dimension(350, 350));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.parser = Parser.getInstance();
        //nacitaj vsetkych hracov zo suboru
        this.parser.nacitajHracovZoSuboru();
        //uloz aktualneho hraca
        this.parser.ulozHracaDoSub(hrac);
        //nacitaj znova vsetkych hracov uz aj s novym do tabulky
        this.parser.nacitajHracovZoSuboru();
        this.initTabulkaHracov();
        this.spatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UkoncenieHryOkno.this.frame.dispose();
                hra.getFrame().dispose();
                new Menu();
            }
        });
        this.ukonciHruBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    private void initTabulkaHracov() {
        TabulkaHracov tabulkaHracov = new TabulkaHracov(this.parser.getProfil().getZoznamHracov());
        this.table1.setModel(tabulkaHracov);

    }
}
