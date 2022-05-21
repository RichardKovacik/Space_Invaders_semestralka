package sk.fri.uniza;

import sk.fri.uniza.gui.Menu;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * Created by IntelliJ IDEA.
 * User: richard
 * Date: 27. 2. 2022
 * Time: 15:54
 * hlavna trieda v ktorej sa spusta apka
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

