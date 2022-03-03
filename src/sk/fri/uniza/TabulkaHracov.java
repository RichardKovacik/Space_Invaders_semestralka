package sk.fri.uniza;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public class TabulkaHracov extends AbstractTableModel {
    private List<Hrac> zoznamHracov;
    private String[] nazvyStlpcov = {"ID", "Meno", "Skore"};

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

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return hrac.getMeno();
            case 2:
                return hrac.getScore();
        }
        return null;
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.getClass();
    }
}
