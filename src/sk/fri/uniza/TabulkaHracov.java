package sk.fri.uniza;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 27. 2. 2022 - 15:54
 * Trieda repreznetuje tabluku vsetych hracov ktori hrali hru
 * @author richard
 */
public class TabulkaHracov extends AbstractTableModel {
    private List<Hrac> zoznamHracov;
    private final String[] nazvyStlpcov = {"Poradie", "Meno", "Skore", "Narodnost"};

    /**
     * Konstruktor vytvori novu tabulku so zadanymi hracmi
     * @param zoznamHracov z texoveho suboru
     */
    public TabulkaHracov(List<Hrac> zoznamHracov) {
        this.zoznamHracov = zoznamHracov;
    }

    /**
     * prepisana metoda z AbstractTableModel
     * @return pocet radkov tabulky
     */
    @Override
    public int getRowCount() {
        return this.zoznamHracov.size();
    }

    /**
     * prepisana metoda z AbstractTableModel
     * @return pocet stlpcov tabulky
     */
    @Override
    public int getColumnCount() {
        return this.nazvyStlpcov.length;
    }

    /**
     * prepisana metoda z AbstractTableModel
     * @return aktualny nazov stlpca
     */
    @Override
    public String getColumnName(int column) {
        return this.nazvyStlpcov[column];
    }

    /**
     * prpisana metoda z AbstractTableModel vracia hodnoty na danom riadku a stlpci a podla hodnot listu vyplni celu tabulku
     * @param rowIndex index riadka
     * @param columnIndex index stlpca
     * @return hodntu v dnaom riadku a stlpci
     */
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

}
