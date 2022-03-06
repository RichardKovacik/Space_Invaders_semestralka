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

    Obtiaznost(String reprezentacia) {
        this.reprezentacia = reprezentacia;
    }

    public String getReprezentacia() {
        return this.reprezentacia;
    }
}
