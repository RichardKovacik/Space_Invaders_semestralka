package sk.fri.uniza;

import sk.fri.uniza.gui.Hra;
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
 *
 * @author richard
 */
public class HraciaPlocha extends JPanel {
    public static final int SIRKA = 700;
    public static final int VYSKA = 500;
    private ArrayList<Bytost> bytosti;
    //caspvec pre strelbu mimozemstanova kazdu sekundu
    private Timer timer;
    //casovac pre odstranovanie znicenych rakiet, kozmickych lodi :)
    private Timer timer2;
    private boolean jeKoniec;
    private Hra hra;

    public HraciaPlocha(Hra hra) {
        this.setSize(SIRKA, VYSKA);
        this.setPreferredSize(new Dimension(SIRKA, VYSKA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Manazer());
        this.initBytosti();
        this.hra = hra;
        this.jeKoniec = false;
        this.timer = new Timer(1000, this.timerListener);
        this.timer.setRepeats(true);
        this.timer.start();
        this.timer2 = new Timer(350, this.timerListener2);
        this.timer2.setRepeats(true);
        this.timer2.start();
    }

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

    //kazdu sekundu vystreli nahodny mimozemstan svoju raketu
    private ActionListener timerListener = evt -> {
        //...Perform a task...
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HraciaPlocha.this.vystrelRaketuNahodnemuMimozemastanovi();
            }
        });
    };

    //vytvor a inicialzuj bytosti potrebne pre hru
    private void initBytosti() {
        this.bytosti = new ArrayList<>();
        //hrac je vzdy na prvej pozicii v arrayliste bytosti
        this.bytosti.add(new Hrac(200, 430));
        this.bytosti.add(new Mimozemstan(20, 30));
        this.bytosti.add(new Mimozemstan(100, 200));
        this.bytosti.add(new Mimozemstan(50, 245));
        this.bytosti.add(new Mimozemstan(400, 99));
        this.bytosti.add(new Mimozemstan(300, 300));
    }

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
    //update vsetky objekty na hracej ploche
    private void tik() {
        //kotrola zrazky rakety s mimozemstanom
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
            for (Raketa raketa : this.bytosti.get(i).rakety) {
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
            new UkoncenieHryOkno(this.hra, this.getHrac());
        }
    }

    private void zobrazMenoHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Hrac: "+this.getHrac().getMeno(), 20, 480);
    }

    private void zobrazSkoreHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Skore: "+this.getHrac().getScore(), 300, 20);
    }

    private void zobrazZivotyHraca(Graphics g) {
        Font font = new Font("Courier New",20,20);
        g.setFont(font);
        g.setColor(Color.magenta);
        g.drawString("Zivoty: ", 520, 480);
    }

    private void zobrazInfoHraca(Graphics g) {
        this.zobrazMenoHraca(g);
        this.zobrazZivotyHraca(g);
        this.zobrazSkoreHraca(g);
    }

    private void kresliCiaru(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.drawLine(0, 450, 700, 450);
    }

    private void kresliHraca(Graphics2D g2d) {
        if (this.bytosti.get(0) instanceof Hrac) {
            ((Hrac) this.bytosti.get(0)).kresli(g2d);
        }
    }

    private void kresliRakety(Graphics2D g2d) {
        for (Bytost b : this.bytosti) {
            if (b.jeNaZive) {
                for (Raketa r : b.rakety) {
                    if (!r.jeZnicena() || r.jeExplozia()) {
                        r.kresli(g2d);
                    }
                }
            }
        }
    }

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

    //ostranim mrtvych mimozemstanov z listu
    private void odstranMimozemstanov() {
        Iterator<Bytost> iterator = this.bytosti.iterator();
        while (iterator.hasNext()) {
             Bytost bytost = iterator.next();
             if (bytost instanceof Mimozemstan && !bytost.jeNaZive){
                 iterator.remove();
             }
        }
    }

    private void odstranZniceneRakety() {
        for (Bytost b : this.bytosti) {
            Iterator<Raketa> iterator = b.rakety.iterator();
            while (iterator.hasNext()) {
                Raketa raketa = iterator.next();
                if (raketa.jeZnicena()) {
                    iterator.remove();
                }
            }
        }
    }

    private void kresliMimozemstanov(Graphics2D g2d) {
        for (Bytost b : this.bytosti) {
            if (b.jeNaZive && b instanceof Mimozemstan) {
                ((Mimozemstan) b).kresli(g2d);
            }
        }
    }

    public Hrac getHrac() {
        return (Hrac) this.bytosti.get(0);
    }

    public static class VykreslovaniePlochy extends Thread {
        private HraciaPlocha plocha;

        public VykreslovaniePlochy(HraciaPlocha plocha) {
            this.plocha = plocha;
        }

        @Override
        public void run() {
            while (this.plocha.isVisible() && !this.plocha.jeKoniec) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        VykreslovaniePlochy.this.plocha.repaint();
                    }
                });
                // kazdych 15 miliseund bude prebiehat update prvkov hracej polochy
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private class Manazer implements KeyListener {
        public Manazer() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> HraciaPlocha.this.getHrac().setVlavo(true);
                case KeyEvent.VK_RIGHT -> HraciaPlocha.this.getHrac().setVpravo(true);
                case KeyEvent.VK_SPACE -> HraciaPlocha.this.getHrac().setVystrel(true);
            }
        }

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
