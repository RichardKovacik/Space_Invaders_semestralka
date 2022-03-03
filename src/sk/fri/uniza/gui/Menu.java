package sk.fri.uniza.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Menu {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel menuTxt;
    private JButton startGamebutton;
    private JButton settingsBtn;
    private JButton quitBtn;

    public Menu() {
        this.frame = new JFrame("SPACE INVADERS MENU");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.add(this.mainPanel);
        this.frame.setPreferredSize(new Dimension(700, 500));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.quitBtn.addActionListener(e -> this.closeFrame());
        this.startGamebutton.addActionListener(e -> this.showDialog());
    }

    public void closeFrame() {
        this.frame.dispose();
    }

    private void showDialog() {
        new ZadanieMenaDialog(this);
    }
}
