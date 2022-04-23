package sk.fri.uniza.enums;

/**
 * 27. 2. 2022 - 15:54
 * Trieda narodnost reprezentuje narodnst daneho hraca
 * @author richard
 */
public enum Narodnost {
    USA("USA"),
    CZ("CZ"),
    PL("PL"),
    DE("DE");

    private String reprezentacia;

    /**
     * nastavi textovu reprezentaciu danej obtiaznosti
     * @param reprezentacia danej narodnosti hraca ako text
     */
    Narodnost(String reprezentacia) {
        this.reprezentacia = reprezentacia;
    }

    /**
     * @return textovu reprezentaciu danej narodnsti
     */
    public String getReprezentacia() {
        return this.reprezentacia;
    }
}
