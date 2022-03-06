package sk.fri.uniza.gui;

import sk.fri.uniza.enums.Narodnost;
import sk.fri.uniza.enums.Obtiaznost;

import javax.swing.*;
import java.awt.event.*;

public class NastavenieObtiaznostiDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel obtiaznostLbl;
    private JComboBox comboBox1;
    private Obtiaznost obtiaznost;

    public NastavenieObtiaznostiDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

    private void createUIComponents() {
        this.comboBox1 = new JComboBox<>(Obtiaznost.values());
    }

    public Obtiaznost getObtiaznost() {
        return this.obtiaznost;
    }
}
