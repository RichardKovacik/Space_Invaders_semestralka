package sk.fri.uniza;

import sk.fri.uniza.gui.ZadanieMenaDialog;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public class TabulkaHracov extends AbstractTableModel {
    private List<Hrac> zoznamHracov;
    private String[] nazvyStlpcov = {"Poradie", "Meno", "Skore", "Narodnost"};

    public TabulkaHracov(List<Hrac> zoznamHracov) {
        this.zoznamHracov = zoznamHracov;
    }

    @Override
    public int getRowCount() {
        return this.zoznamHracov.size();
    }

    @Override
    public int getColumnCount() {
        return this.nazvyStlpcov.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.nazvyStlpcov[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hrac hrac = this.zoznamHracov.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> hrac.getMeno();
            case 2 -> hrac.getScore();
            case 3 -> hrac.getNarodnost().getReprezentacia();
            default -> null;
        };
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.getClass();
    }
}
