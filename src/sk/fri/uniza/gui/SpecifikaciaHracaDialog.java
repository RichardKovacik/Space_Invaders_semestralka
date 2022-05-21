package sk.fri.uniza.gui;

import sk.fri.uniza.enums.Narodnost;

import javax.swing.JDialog;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Trieda repezentuje dialogove okno pre zadavanie mena a narodnosti hraca
 * @author Richard Kovacik
 */
public class SpecifikaciaHracaDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField menoField;
    private JComboBox<Narodnost> narodnostiComboBox;
    private String menoHraca;
    private Narodnost narodnostHraca;

    /**
     * Konstruktor vytvori nove dialogove okno pre zadavanie mena hraca a nastavi vsetky prvky vramci okna
     * @param menu aktualne
     */
    public SpecifikaciaHracaDialog(Menu menu) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SpecifikaciaHracaDialog.this.onOK();
                SpecifikaciaHracaDialog.this.menoHraca = SpecifikaciaHracaDialog.this.menoField.getText();
                SpecifikaciaHracaDialog.this.narodnostHraca = (Narodnost)SpecifikaciaHracaDialog.this.narodnostiComboBox.getSelectedItem();
                //kontrola ci zadanne meno nie je null alebo prazdny String
                if (SpecifikaciaHracaDialog.this.menoHraca != null && !SpecifikaciaHracaDialog.this.menoHraca.isBlank()) {
                    menu.zatvorMenuOkno();
                    new HraOkno(SpecifikaciaHracaDialog.this.menoHraca, SpecifikaciaHracaDialog.this.narodnostHraca, menu.getZvolenaObtiaznost());
                }

            }
        });
        //metody vygenerovane Itelijji

        this.buttonCancel.addActionListener(new ActionListener() {
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

        this.setTitle("ZADAVANIE MENA HRACA");
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
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
     * metoda vytvori combox s hodnotami enumu Naodnost a ku kazdej narodnosti priradi jeden obrazok
     */
    private void createUIComponents()  {
        Map<Object, Icon> icons = new HashMap<>();
        icons.put(Narodnost.CZ, new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/flags/cz_flag.png"))));
        icons.put(Narodnost.PL,  new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/flags/pl_flag.png"))));
        icons.put(Narodnost.USA,  new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/flags/usa_flag.png"))));
        icons.put(Narodnost.DE,  new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/flags/nem_flag.png"))));
        this.narodnostiComboBox = new JComboBox<>(Narodnost.values());
        this.narodnostiComboBox.setRenderer(new IconListRenderer(icons));

    }

    /**
     * Trieda  kotra zebepecuje renderovanie(vykreslovanie) obrazkov vramci JComboBoxu
     */
    public static class IconListRenderer extends DefaultListCellRenderer {
        private Map<Object, Icon> icons;

        /**
         * Konstruktor vytvori novy IconListRenderer a inicalizuje atribut icons
         * @param icons mapa ktora obsahuje dve hodnoty <narodnost,obrazok_narodnsoti
         */
        public IconListRenderer(Map<Object, Icon> icons) {
            this.icons = icons;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Icon icon = this.icons.get(value);
            label.setIcon(icon);
            return label;
        }
    }
}
