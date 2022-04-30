package sk.fri.uniza;

import sk.fri.uniza.enums.Obtiaznost;
import sk.fri.uniza.gui.HraOkno;
import sk.fri.uniza.gui.UkoncenieHryOkno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * 27. 2. 2022 - 15:54
 * V triede Hracia plocha sa vykonava hlavna logika hry a vykresluju sa vsetky objekty vramci hracej plochy
 * @author richard
 */
public class HraciaPlocha extends JPanel {
    public static final int SIRKA = 700;
    public static final int VYSKA = 500;
    private ArrayList<Bytost> bytosti;
    private Timer timer;
    private Timer timer2;
    private Obtiaznost obtiaznost;
    private HraOkno hraOkno;
    private boolean jeKoniec;
    private int rychlostStrelby;

    /**
     * Konstruktor vytvori novu hraciu plochu so zadanou hrou a obtiaznostou
     * @param hraOkno znamena akutalnu hru
     * @param obtiaznost hry
     */
    public HraciaPlocha(HraOkno hraOkno, Obtiaznost obtiaznost) {
        //deafultne nastavi sirku a vysku hracej plochy v pixeloch
        this.setSize(SIRKA, VYSKA);
        this.setPreferredSize(new Dimension(SIRKA, VYSKA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Manazer());
        this.obtiaznost = obtiaznost;
        this.hraOkno = hraOkno;
        this.jeKoniec = false;
        this.initBytosti();
        //casovac pre strelbu mimozemstanova kazdu sekundu
        //urcuje interval v akom sa nahodne stridaju mimozemstanie pri strelbe
        this.timer = new Timer(this.rychlostStrelby, this.timerListener);
        this.timer.setRepeats(true);
        this.timer.start();
        //casovac pre odstranovanie znicenych rakiet, kozmickych lodi :)
        this.timer2 = new Timer(350, this.timerListener2);
        this.timer2.setRepeats(true);
        this.timer2.start();
    }

    /**
     * kazdych 350 milisekund sa vykona dany listener, ktory odstrani z hracej plochy bytosti, ktore nie su uz na zive a znicene rakety
     */
    //kazdych 250 milisekud odstranim znicene rakety a znicene mimozemske lode z listov
    private ActionListener timerListener2 = evt -> {
        //...Perform a task...
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HraciaPlocha.this.odstranZniceneRakety();
                HraciaPlocha.this.odstranMimozemstanov();
            }
        });
    };

    /**
     * listener sa opakuje v pravidelnom intervale ktoreho hodnota je ulozena v atribute rychlostStrelby
     */
    //napriklad ak bude rychlostStrelby = 1000 => kazdu sekundu vystreli nahodny mimozemstan raketu
    private ActionListener timerListener = evt -> {
        //...Perform a task...
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HraciaPlocha.this.vystrelRaketuNahodnemuMimozemastanovi();
            }
        });
    };

    /**
     * metoda vytvori bytosti na zaklade zvolenej obtiaznosti
     */
    //vytvor a inicialzuj bytosti potrebne pre hru
    //podla obtiaznosti vygeneruje pocet mimozemstanov, urci im rychlost pohybu
    private void initBytosti() {
        this.bytosti = new ArrayList<>();
        //hrac je vzdy na prvej pozicii v arrayliste bytosti
        this.bytosti.add(new Hrac(200, 430));

        if (this.obtiaznost == null){
            this.obtiaznost = Obtiaznost.LAHKA;
        }
        switch (this.obtiaznost) {
            case LAHKA -> this.initLahku();
            case STREDNA -> this.initStrednu();
            case TAZKA -> this.initTazku();
        }
    }

    /**
     * incializuje objekty mimozemstanov a rychlost strelby mimozemstanov pre tazku obtiaznost
     */
    private void initTazku() {
        this.rychlostStrelby = 500;
        this.bytosti.add(new Mimozemstan(199, 30));
        this.bytosti.add(new Mimozemstan(111, 200));
        this.bytosti.add(new Mimozemstan(200, 245));
        this.bytosti.add(new Mimozemstan(333, 99));
        this.bytosti.add(new Mimozemstan(99, 111));
        this.bytosti.add(new Mimozemstan(55, 312));
        this.bytosti.add(new Mimozemstan(288, 310));
        this.bytosti.add(new Mimozemstan(55, 55));
        this.bytosti.add(new Mimozemstan(200, 175));
        this.bytosti.add(new Mimozemstan(128, 130));
    }

    /**
     * incializuje objekty mimozemstanov a rychlost strelby mimozemstanov pre strednu obtiaznost
     */
    private void initStrednu() {
        this.rychlostStrelby = 700;
        this.bytosti.add(new Mimozemstan(33, 30));
        this.bytosti.add(new Mimozemstan(100, 200));
        this.bytosti.add(new Mimozemstan(50, 245));
        this.bytosti.add(new Mimozemstan(400, 99));
        this.bytosti.add(new Mimozemstan(300, 300));
        this.bytosti.add(new Mimozemstan(10, 77));
        this.bytosti.add(new Mimozemstan(250, 210));
    }

    /**
     * incializuje objekty mimozemstanov a rychlost strelby mimozemstanov pre lahku obtiaznost
     */
    private void initLahku() {
        this.rychlostStrelby = 1000;
        this.bytosti.add(new Mimozemstan(20, 30));
        this.bytosti.add(new Mimozemstan(100, 200));
        this.bytosti.add(new Mimozemstan(50, 245));
        this.bytosti.add(new Mimozemstan(400, 99));
        this.bytosti.add(new Mimozemstan(300, 300));
    }

    /**
     * metoda vykresluje vsetky objekty hracej plochy
     * @param g vykresluje objekty na aktualny panel(hraciu plochu)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        this.tik();
        this.kresliCiaru(g2d);
        this.kresliHraca(g2d);
        this.kresliMimozemstanov(g2d);
        this.kresliRakety(g2d);
        this.zobrazInfoHraca(g2d);
    }

    /**
     * metoda kotroluje zrazky rakiet s bytostami
     */
    //update vsetky objekty na hracej ploche
    private void tik() {
        //kotrola zrazky hracovej rakety s mimozemstanom
        for (Bytost b : this.bytosti) {
            if (b.jeNaZive && b instanceof Mimozemstan) {
                for (Raketa r : this.getHrac().getRakety()) {
                    //hrac trafil mimozemsku lod s raketou
                    if (b.jeZrazka(r.getPozicia().getX(), r.getPozicia().getY()) && !r.jeZnicena()) {
                        b.zivoty --;
                        r.vybuchni();
                        this.getHrac().setScore(this.getHrac().getScore() + 10);
                    }
                }
            }
        }
        //kotrola zrazky mimozemskej rakety s hracom
        for (int i = 1; i < this.bytosti.size(); i++) {
            for (Raketa raketa : this.bytosti.get(i).getRakety()) {
                if (this.getHrac().jeZrazka(raketa.getPozicia().getX(), raketa.getPozicia().getY()) && !raketa.jeZnicena()) {
                    raketa.vybuchni();
                    this.getHrac().uberZivot();
                }
            }
        }

        //ukonci hru ak je splenana aspon jedna z podmienok
        if (!this.getHrac().jeNaZive || this.bytosti.size() == 1) {
            this.jeKoniec = true;
            this.timer.stop();
            this.timer2.stop();
            new UkoncenieHryOkno(this.hraOkno, this.getHrac());
        }
    }

    /**
     * metoda zobrazuje meno hraca na danej pozici na hracej ploche
     * @param g vykresluje objekty na hraciu plochu
     */
    private void zobrazMenoHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Hrac: "+this.getHrac().getMeno(), 20, 480);
    }

    /**
     * metoda zobrazuje skore hraca na danej pozici na hracej ploche
     * @param g vykresluje objekty na hraciu plochu
     */
    private void zobrazSkoreHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Skore: "+this.getHrac().getScore(), 300, 20);
    }

    /**
     * metoda zobrazuje zivoty hraca na danej pozici na hracej ploche
     * @param g vykresluje objekty na hraciu plochu
     */
    private void zobrazZivotyHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Zivoty: ", 520, 480);
    }

    /**
     * metoda zobrazuje informacie hraca na danej pozici na hracej ploche
     * @param g vykresluje objekty na hraciu plochu
     */
    private void zobrazInfoHraca(Graphics g) {
        this.zobrazMenoHraca(g);
        this.zobrazZivotyHraca(g);
        this.zobrazSkoreHraca(g);
    }

    /**
     * metoda zobrazuje informacie hraca na danej pozici na hracej ploche
     * @param g2d vyjresluje 2D objetky na hraciu plochu
     */
    private void kresliCiaru(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.drawLine(0, 450, 700, 450);
    }

    /**
     * metoda zobrazuje 2D obrazok hraca na danej pozici na hracej ploche
     * @param g2d vyjresluje 2D obejekty na hraciu plochu
     */
    private void kresliHraca(Graphics2D g2d) {
        if (this.bytosti.get(0) instanceof Hrac) {
            ((Hrac) this.bytosti.get(0)).zobraz(g2d);
        }
    }

    /**
     * metoda zobrazuje rakety bytostiam na danej pozici na hracej ploche
     * @param g2d vyjresluje 2D obejtky na hraciu plochu
     */
    private void kresliRakety(Graphics2D g2d) {
        for (Bytost b : this.bytosti) {
            for (Raketa r : b.rakety) {
                if (!r.jeZnicena() || r.jeExplozia()) {
                    r.kresli(g2d);
                }
            }
        }
    }

    /**
     * metoda vystreli raketu nahodnemu mimozemstanovi
     */
    private void vystrelRaketuNahodnemuMimozemastanovi() {
        //vyber random mimozemstana z listu bytosti ktory vystreli raketu
        Random generator = new Random();
        if (this.bytosti.size() == 1) {
            return;
        }
        int randCislo = generator.nextInt(this.bytosti.size() - 1) + 1;
        Mimozemstan m = (Mimozemstan) this.bytosti.get(randCislo);
        m.vystrel();
    }

    /**
     * metoda odstrani mimozemstanov, ktori nie su uz na zive z listu bytosti
     */
    //ostranim mrtvych mimozemstanov z listu
    //nebudem ich uz kreslit
    private void odstranMimozemstanov() {
        Iterator<Bytost> iterator = this.bytosti.iterator();
        while (iterator.hasNext()) {
             Bytost bytost = iterator.next();
             //pokial je typu mimozemstan a nema z hre uz ziadne rakety odstranim ho
             // hra je ukoncena ak sa znici posledna raketa posledneho mimozemstana
             if (bytost instanceof Mimozemstan && bytost.getRakety().size() == 0 && !bytost.jeNaZive){
                iterator.remove();
             }
        }
    }

    /**
     * metoda odstrani znicene rakety z hracej plochy
     */
    private void odstranZniceneRakety() {
        for (Bytost b : this.bytosti) {
            b.odstranZniceneRakety();
        }
    }

    /**
     * metoda vykresluje vsetkych mimozemstanov ,ktorsi su nazive
     * @param g2d vyjresluje  objekty na hraciu plochu
     */
    private void kresliMimozemstanov(Graphics2D g2d) {
        for (Bytost b : this.bytosti) {
            if (b.jeNaZive && b instanceof Mimozemstan) {
                ((Mimozemstan) b).zobraz(g2d);
            }
        }
    }

    /**
     * meotda vrati hraca z listu bytosti
     * @return hrac
     */
    public Hrac getHrac() {
        return (Hrac) this.bytosti.get(0);
    }

    /**
     * Trieda VykreslovaniePlochy prekresluje nuestale v jednom vlakne(threade) objekty hracej plochy
     */
    public static class VykreslovaniePlochy extends Thread {
        private HraciaPlocha plocha;

        /**
         * Konstruktor vytvori novy vykreslovacie vlakno hracej plochy
         * @param plocha
         */
        public VykreslovaniePlochy(HraciaPlocha plocha) {
            this.plocha = plocha;
        }

        /**
         * metoda run je prepisana z triedy Thread a zabezpecuje neustale prekreslovanie hracej plochy
         */
        @Override
        public void run() {
            while (this.plocha.isVisible() && !this.plocha.jeKoniec) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        //metoda repaint vola metodu paintComponent() v triede Hracia plocha
                        VykreslovaniePlochy.this.plocha.repaint();
                    }
                });
                // kazdych 30 miliseund bude prebiehat update(prekreslovanie) prvkov hracej polochy
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * trieda Manzer menezuje pohyb a strelbu hraca na hracej ploche
     */
    private class Manazer implements KeyListener {
        /**
         * Konstruktor vytvori novy objekt Manazera
         */
        public Manazer() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        /**
         * metoda reaguje na stlacenie klavesnice a umoznuje pohyb hraca dolava alebo doprava pomocou stlacania sipok
         * umoznuje strlbu hraca pomocou stlacania medzernika
         * @param e aktualny key event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> HraciaPlocha.this.getHrac().setVlavo(true);
                case KeyEvent.VK_RIGHT -> HraciaPlocha.this.getHrac().setVpravo(true);
                case KeyEvent.VK_SPACE -> HraciaPlocha.this.getHrac().setVystrel(true);
            }
        }

        /**
         * metoda reaguje na pustanie sipky dolava alebo doprava
         * @param e aktualny key event
         */
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> HraciaPlocha.this.getHrac().setVlavo(false);
                case KeyEvent.VK_RIGHT -> HraciaPlocha.this.getHrac().setVpravo(false);
            }
        }
    }
}
