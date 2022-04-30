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
 * Trieda repezentuje okno kotre sa zobrazi po ukonceni hry(pokial hrac zabije vsetkych mimozems. alebo zomrie)
 * @author richa
 */
public class UkoncenieHryOkno {
    private JFrame frame;
    private Parser parser;
    private JPanel mainPanel;
    private JTable table1;
    private JButton spatBtn;
    private JButton ukonciHruBtn;

    /**
     * Konstruktor vytvori nove okno po ukonceni hry a nastavu jednotlive prvky a objekty okna
     * @param hraOkno aktualna
     * @param hrac aktulany
     */
    public UkoncenieHryOkno(HraOkno hraOkno, Hrac hrac) {
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
        //nacitaj znova vsetkych hracov uz aj s aktaulnym hracom do tabulky
        this.parser.nacitajHracovZoSuboru();
        this.initTabulkaHracov();
        //pri stalceni tlacitdla spat sa opat zobrazi prve okno menu
        this.spatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UkoncenieHryOkno.this.frame.dispose();
                hraOkno.getFrame().dispose();
                new Menu();
            }
        });
        //pri stalceni tlacitdla ukoncenie hry sa ukonci aplikacia
        this.ukonciHruBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * meotoda vytvori tabulku hracov s hracmi nacitancyh zo suboru, cize takych ktori uz hrali hru
     */
    private void initTabulkaHracov() {
        TabulkaHracov tabulkaHracov = new TabulkaHracov(this.parser.getZoznamHracov().getHraci());
        this.table1.setModel(tabulkaHracov);

    }
}
