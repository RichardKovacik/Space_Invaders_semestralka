package sk.fri.uniza.gui;

import sk.fri.uniza.enums.Obtiaznost;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/**
 * Trieda reprezentuje dialog pre nastavenie obtiaznosti hry
 * @author Richard Kovacik
 */
public class NastavenieObtiaznostiDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel obtiaznostLbl;
    private JComboBox comboBox1;
    private Obtiaznost obtiaznost;

    /**
     * Konstruktor vytvori nove dialog okno pre zadavanie mena hraca a narodnosti hraca
     */
    public NastavenieObtiaznostiDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //vygenerovany kod intelijji idea

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setTitle("NASTAVENIE OBTIAZNOSTI HRY");
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.obtiaznost = (Obtiaznost)this.comboBox1.getSelectedItem();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * meotda vytvori combo box s hodnotami enumu Obtiaznost
     */
    private void createUIComponents() {
        this.comboBox1 = new JComboBox<>(Obtiaznost.values());
    }

    /**
     * metoda vrati danu obtiaznost
     * @return zadana obtiaznost
     */
    public Obtiaznost getObtiaznost() {
        return this.obtiaznost;
    }
}
