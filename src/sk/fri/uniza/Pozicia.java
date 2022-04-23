package sk.fri.uniza;


import java.awt.*;
import java.io.Serializable;

/**
 * 27. 2. 2022 - 15:54
 * Treida sluzi na reprezentaciu pozicie x,y kazdej bytosti v hre
 * @author Richard Kovacik
 */
public class Pozicia {
    private int x;
    private int y;

    /**
     * Konstruktor vytvori novu poziciu so zdanym x,y
     * @param x suradmica
     * @param y suradnica
     */
    public Pozicia(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * vrati suradnicu y
     * @return x suradnicu
     */
    public int getX() {
        return this.x;
    }

    /**
     * nastavi x na nove x
     * @param x nova
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * vrati suradnicu y
     * @return y suradnicu
     */
    public int getY() {
        return this.y;
    }

    /**
     * nastavi povodnu suradnicu y na novu
     * @param y nova
     */
    public void setY(int y) {
        this.y = y;
    }
}
