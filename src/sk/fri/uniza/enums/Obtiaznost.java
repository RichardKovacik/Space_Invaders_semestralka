package sk.fri.uniza.enums;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public enum Obtiaznost {
    LAHKA("L"),
    STREDNA("S"),
    TAZKA("T");

    private String reprezentacia;

    /**
     * nastavi textovu reprezentaciu danej obtiaznosti
     * @param reprezentacia danej obtiaznosti hraca ako text
     */
    Obtiaznost(String reprezentacia) {
        this.reprezentacia = reprezentacia;
    }
    /**
     * @return textovu reprezentaciu danej narodnsti
     */
    public String getReprezentacia() {
        return this.reprezentacia;
    }
}
