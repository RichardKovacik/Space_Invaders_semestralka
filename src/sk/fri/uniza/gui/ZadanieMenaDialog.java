package sk.fri.uniza.gui;

import sk.fri.uniza.enums.Narodnost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ZadanieMenaDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField menoField;
    private JComboBox<Narodnost> narodnostiComboBox;
    private String menoHraca;
    private Narodnost narodnostHraca;

    public ZadanieMenaDialog(Menu menu) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ZadanieMenaDialog.this.onOK();
                ZadanieMenaDialog.this.menoHraca = ZadanieMenaDialog.this.menoField.getText();
                narodnostHraca = (Narodnost)narodnostiComboBox.getSelectedItem();
                if (ZadanieMenaDialog.this.menoHraca != null && !ZadanieMenaDialog.this.menoHraca.isBlank()) {
                    menu.closeFrame();
                    new Hra(ZadanieMenaDialog.this.menoHraca, narodnostHraca);
                }

            }
        });

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

    private void createUIComponents() {
        Map<Object, Icon> icons = new HashMap<>();
        icons.put(Narodnost.CZ, new ImageIcon("src/sk/fri/uniza/flags/cz_flag.png"));
        icons.put(Narodnost.PL, new ImageIcon("src/sk/fri/uniza/flags/pl_flag.png"));
        icons.put(Narodnost.USA, new ImageIcon("src/sk/fri/uniza/flags/usa_flag.png"));
        icons.put(Narodnost.DE, new ImageIcon("src/sk/fri/uniza/flags/nem_flag.png"));
        this.narodnostiComboBox = new JComboBox<>(Narodnost.values());
        this.narodnostiComboBox.setRenderer(new IconListRenderer(icons));

    }

    public static class IconListRenderer extends DefaultListCellRenderer {
        private Map<Object, Icon> icons;

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
