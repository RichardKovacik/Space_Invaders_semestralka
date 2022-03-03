package sk.fri.uniza;

import sk.fri.uniza.gui.Menu;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by IntelliJ IDEA.
 * User: richard
 * Date: 27. 2. 2022
 * Time: 15:54
 */
public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        new Menu();
    }
}

